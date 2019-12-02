package utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utilities {

    /**
     * Gets a Number Input from the user using Scanner
     * @param prompt the prompt string
     * @param className the number type (ex : BigDecimal.class, Double.class)
     */
    public static Object getNumberInput(String prompt, Class className) {
        System.out.format("%s : ", prompt);
        Object value = null;
        Scanner scanner = new Scanner(System.in, "UTF-8");

        if (Integer.class.equals(className)) {
            // repeats until the user enters a valid integer
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.\n");
                scanner.next();
                System.out.format("%s : ", prompt);
            }

            value = scanner.nextInt();
        }

        if (BigDecimal.class.equals(className)) {
            // repeats until the user enters a valid integer
            while (!scanner.hasNextBigDecimal()) {
                System.out.println("Please enter a valid decimal.\n");
                scanner.next();
                System.out.format("%s : ", prompt);
            }
            value = scanner.nextBigDecimal();
        }

        // moving to the next line, because scanner.next() will not move to the next line.
        scanner.nextLine();
        return value;
    }

    /**
     * Gets a String Input from the a given String array from the user using Scanner
     * @param prompt the prompt string
     * @param values the values that are allowed for the input
     */
    public static String getConditionalString(String prompt, List<String> values) {
        String stringValue = "";
        Scanner in = new Scanner(System.in);


        while (!values.contains(stringValue)) {
            System.out.printf("\n%s : ", prompt);
            stringValue = in.nextLine().toUpperCase();
            if (!values.contains(stringValue)) {
                System.out.printf("Please enter a valid option! The possible values are %s", Arrays.toString(values.toArray()));
            }
        }

        return stringValue;
    }

    /**
     * Gets a String Input from the user using Scanner
     * @param prompt the prompt string
     */
    public static String getPlainString(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.printf("\n%s : ", prompt);
        return in.nextLine().toUpperCase();
    }

}
