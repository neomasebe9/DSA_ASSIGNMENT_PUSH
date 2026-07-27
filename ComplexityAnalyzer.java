import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class ComplexityAnalyzer {

    /*
    1. Read whole .java file into memory.
    2. Remove all comments.
    3. Isolate all methods.
    4. Process all methods individually; calc running time for each.
    5. Combine the running times.
    */

    public static String readFile(String sourceFile) {
        /*
         * This method reads the entire .java source code into a single String.
         */

        Path filePath = Path.of(sourceFile);
        try {
            String sourceCode = Files.readString(filePath);
            return sourceCode;
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e);
        } catch (IOException e) {
            System.out.println("IO Exception in readFromFile(): " + e);
        }

        return "";
    }

    public static String preProcessing(String sourceCode) {
        /*
        This method does the following:
        1. Remove all multi-line and single-line comments
        2. Replaces all text inside double quotes with empty string.
        
        The above so that when the actual analysis is done the comments are not considered.
        
        */

        // Remove single-line.
        String newString = sourceCode.replaceAll("(//).*", "");

        // Remove multi-line
        newString = newString.replaceAll("(?s)/\\*.*?\\*/", "");

        // Replace all string literals with ""
        newString = newString.replaceAll("\".*?\"", "\"\"");
        return newString;
    }

    public static ArrayList<Integer> extractMethodHeader(String sourceCode) {
        // Fixed regex: matches public, optional static/final modifiers, return type,
        // and method name cleanly
        String headerRegex = "(?s)public\\s+(?:(?:static|final|abstract|synchronized)\\s+)*+[^\\s(]+\\s+(\\w+)\\s*\\([^)]*\\)\\s*(?:throws\\s+[\\w\\s,]+)?\\s*\\{";
        ArrayList<Integer> methodIndex = new ArrayList<>();
        Pattern pattern = Pattern.compile(headerRegex);
        Matcher matcher = pattern.matcher(sourceCode);

        // Use a while loop instead of if to find ALL public methods in the source code
        while (matcher.find()) {
            String methodName = matcher.group(1);
            System.out.println("Found method: " + methodName);

            // Adding start index of the match to your list
            methodIndex.add(matcher.start());
        }

        return methodIndex;
    }

    public static void extractMethodBody(String sourceCode, int startBraceIndex) {
        int openBraces = 0;
        int bodyStart = startBraceIndex + 1;
        ArrayList<String> arrMethods = new ArrayList<>();

        for (int i = startBraceIndex; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            if (c == '{')
                openBraces++;
            else if (c == '}') {
                openBraces--;
                if (openBraces == 0) {
                    arrMethods.add(sourceCode.substring(bodyStart, i));
                }
            }
        }
    }

    public static void main(String[] args) {
        String javaString = readFile("EBook.java");

        //String testString = "//hellloooo\n//broski\nhehehe";
        //System.out.println(preProcessing(javaString));
        extractMethodHeader("testSource.java");
    }
}