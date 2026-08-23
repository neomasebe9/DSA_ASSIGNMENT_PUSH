import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexityAnalyzer {

    public static void main(String[] args) {
        // Find all .java files in the current directory (excluding ComplexityAnalyzer
        // itself)
        File currentDir = new File(".");
        File[] files = currentDir
                .listFiles((dir, name) -> name.endsWith(".java") && !name.equals("ComplexityAnalyzer.java"));

        if (files == null || files.length == 0) {
            System.out.println("No target .java source files found in the current directory.");
            return;
        }

        System.out.println("=================================================");
        System.out.println("       AUTOMATED TIME COMPLEXITY REPORT          ");
        System.out.println("=================================================");

        for (File file : files) {
            analyzeFile(file);
        }
    }

    private static void analyzeFile(File file) {
        System.out.println("\n-------------------------------------------------");
        System.out.println("CLASS FILE: " + file.getName());
        System.out.println("-------------------------------------------------");

        String rawCode;
        try {
            rawCode = Files.readString(Path.of(file.getPath()));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        String cleanCode = preProcessCode(rawCode);
        List<MethodData> methods = extractMethods(cleanCode);

        if (methods.isEmpty()) {
            System.out.println("No methods detected in this class.");
            return;
        }

        for (MethodData method : methods) {
            analyzeMethod(method);
        }
    }

    // This method strips out comments and string literals to prevent false counts

    private static String preProcessCode(String code) {
        // Remove multi-line comments
        String clean = code.replaceAll("(?s)/\\*.*?\\*/", "");
        // Remove single-line comments
        clean = clean.replaceAll("//.*", "");
        // Replace string contents with empty string ""
        clean = clean.replaceAll("\".*?\"", "\"\"");
        return clean;
    }

    // Step 2: Extract methods from preprocessed source code using brace tracking
    private static List<MethodData> extractMethods(String code) {
        List<MethodData> methods = new ArrayList<>();

        // Regex matches standard public/private/protected or package-private method
        // headers
        String headerRegex = "(?:public|protected|private|static|final|abstract|synchronized|\\s)+[\\w<>\\[\\]]+\\s+(\\w+)\\s*\\([^)]*\\)\\s*(?:throws\\s+[\\w\\s,]+)?\\s*\\{";
        Pattern pattern = Pattern.compile(headerRegex);
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            String methodName = matcher.group(1);
            int startBraceIndex = matcher.end() - 1;

            // Extract method body using curly brace balancing
            int openBraces = 0;
            int bodyStart = startBraceIndex + 1;
            int bodyEnd = -1;

            for (int i = startBraceIndex; i < code.length(); i++) {
                char c = code.charAt(i);
                if (c == '{')
                    openBraces++;
                else if (c == '}') {
                    openBraces--;
                    if (openBraces == 0) {
                        bodyEnd = i;
                        break;
                    }
                }
            }

            if (bodyEnd != -1) {
                String body = code.substring(bodyStart, bodyEnd);
                methods.add(new MethodData(methodName, body));
            }
        }

        return methods;
    }

    // Step 3: Analyze method operations and construct complexity formulas
    private static void analyzeMethod(MethodData method) {
        String body = method.body;

        int totalAssignments = 0;
        int totalArithmetic = 0;
        int totalComparisons = 0;
        int totalMethodCalls = 0;
        int totalStringConcat = 0;
        int loopCount = 0;

        // Break into lines to analyze operations inside and outside loops
        String[] lines = body.split("\n");
        int baseOps = 0;
        int loopOps = 0;
        boolean inLoop = false;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            // Check for loop structures
            if (line.startsWith("for") || line.startsWith("while")) {
                loopCount++;
                inLoop = true;
            }

            // 1. Comparisons
            Pattern compPat = Pattern.compile("==|!=|<=|>=|<|>");
            Matcher compMat = compPat.matcher(line);
            int compLine = 0;
            while (compMat.find())
                compLine++;

            // Clean comparisons out of line before checking assignments so '==' or '<='
            // isn't counted as '='
            String lineNoComp = line.replaceAll("==|!=|<=|>=|<|>", " ");

            // 2. Assignments
            Pattern assignPat = Pattern.compile("=");
            Matcher assignMat = assignPat.matcher(lineNoComp);
            int assignLine = 0;
            while (assignMat.find())
                assignLine++;

            // 3. String Concat (+ or += with string or print statements)
            int concatLine = 0;
            if (line.contains("System.out.print") || line.contains("+=")) {
                Pattern concatPat = Pattern.compile("\\+");
                Matcher concatMat = concatPat.matcher(line);
                while (concatMat.find())
                    concatLine++;
            }

            // 4. Arithmetic operations
            Pattern arithPat = Pattern.compile("[+\\-*/]");
            Matcher arithMat = arithPat.matcher(line);
            int arithLine = 0;
            while (arithMat.find())
                arithLine++;
            // Subtract concats from total arithmetic
            arithLine = Math.max(0, arithLine - concatLine);

            // 5. Method calls (e.g., identifier(...))
            Pattern callPat = Pattern.compile("\\b(?!if|for|while|switch|return\\b)\\w+\\s*\\(");
            Matcher callMat = callPat.matcher(line);
            int callLine = 0;
            while (callMat.find())
                callLine++;

            int lineTotal = compLine + assignLine + concatLine + arithLine + callLine;

            totalComparisons += compLine;
            totalAssignments += assignLine;
            totalStringConcat += concatLine;
            totalArithmetic += arithLine;
            totalMethodCalls += callLine;

            if (inLoop) {
                loopOps += lineTotal;
            } else {
                baseOps += lineTotal;
            }
        }

        // Print Operation Breakdown
        System.out.println("  Method: " + method.name + "()");
        System.out.println("    - Assignments (=): " + totalAssignments);
        System.out.println("    - Arithmetic (+, -, *, /): " + totalArithmetic);
        System.out.println("    - Comparisons (==, !=, <, >, <=, >=): " + totalComparisons);
        System.out.println("    - String Concatenations: " + totalStringConcat);
        System.out.println("    - Method Calls: " + totalMethodCalls);
        System.out.println("    - Loops Detected: " + loopCount);

        // Compute Tau, Simplified, and Big-O
        String tau;
        String simplified;
        String bigO;

        if (loopCount == 0) {
            tau = "T(n) = " + baseOps;
            simplified = "T(n) = " + baseOps;
            bigO = "O(1)";
        } else if (loopCount == 1) {
            tau = "T(n) = " + baseOps + " + n(" + loopOps + ")";
            int coeff = loopOps;
            simplified = "T(n) = " + (coeff == 1 ? "n" : coeff + "n") + (baseOps > 0 ? " + " + baseOps : "");
            bigO = "O(n)";
        } else {
            tau = "T(n) = " + baseOps + " + n^" + loopCount + "(" + loopOps + ")";
            simplified = "T(n) = " + loopOps + "n^" + loopCount + (baseOps > 0 ? " + " + baseOps : "");
            bigO = "O(n^" + loopCount + ")";
        }

        System.out.println("    --------------------------------------------");
        System.out.println("    tau-notation : " + tau);
        System.out.println("    Simplified   : " + simplified);
        System.out.println("    Big-Oh       : " + bigO);
    }

    private static class MethodData {
        String name;
        String body;

        MethodData(String name, String body) {
            this.name = name;
            this.body = body;
        }
    }
}