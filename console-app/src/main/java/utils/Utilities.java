package utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utilities {

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
//        scanner.close();
        return value;
    }

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

    public static String getPlainString(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.printf("\n%s : ", prompt);
        return in.nextLine().toUpperCase();
    }

    public static String getConditionalString(String prompt, List<String> values, boolean canBeEmpty) {
        if (canBeEmpty) {
            values.add(" ");
        }

        return getConditionalString(prompt, values);
    }

}
