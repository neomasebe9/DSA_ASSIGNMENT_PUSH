import java.util.Scanner;

public class testClass {

    // PRIMARY, MAIN VERSION
    public static int[] addOneV2(int[] digits) {

        for (int index = digits.length - 1; index >= 0; index--) {
            if (digits[index] < 9) {
                digits[index] += 1;
                return digits;
            } else {
                digits[index] = 0;
            }

            if (index == 0 || digits[index] == 9) {
                int[] newDigits = new int[digits.length + 1];
                newDigits[0] = 1;
                return newDigits;
            }
        }

        return digits;
    }

    public static int[] addOne(int[] digits) {
        int[] digitsAddOne = new int[digits.length + 1];
        String strNumber = "";
        for (int num : digits) {
            strNumber += String.valueOf(num);
        }

        int intNum = Integer.parseInt(strNumber) + 1;
        strNumber = String.valueOf(intNum);
        for (int i = 0; i < digits.length; i++) {
            digitsAddOne[i] = Integer.parseInt(String.valueOf(strNumber.charAt(i)));
        }

        return digitsAddOne;
    }

    public static void main(String[] args) {
        int[] digits = { 9,9,1,9 };

        int[] digitsAddOne = addOneV2(digits);

        for (int i = 0; i < digitsAddOne.length; i++) {
            System.out.print(digitsAddOne[i] + " ");
        }
    }

}
