package com.javadevjournal;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
                String regex = "\\[(cf|fc|c|f){1}\\]\\{(\\d+|\\*)\\}";

        String regexq = "\\[(cf|fc|c|f){1}\\]\\{\\d*\\*?\\}";
    }

    public static boolean isValid(String pattern) {
        // Check if the pattern starts with '[' and ends with '}'
        if (!pattern.startsWith("[") || !pattern.endsWith("}")) {
            return false;
        }

        // Get the content inside the square brackets and curly brackets
        String contentInBrackets = pattern.substring(1, pattern.length() - 1);

        // Check if the content inside square brackets contains c or f or cf or fc
        if (!contentInBrackets.matches("^(cf|fc|c|f)$")) {
            return false;
        }

        // Get the content inside the curly brackets
        String contentInCurlyBraces = pattern.substring(pattern.indexOf("{") + 1, pattern.length() - 1);

        // Check if the content inside curly brackets is a number or an asterisk
        if (!contentInCurlyBraces.matches("^\\d+|\\*$")) {
            return false;
        }

        // Check if the second last character is an asterisk or a numeric value
        char secondLastChar = pattern.charAt(pattern.length() - 2);
        if (secondLastChar != '*' && !Character.isDigit(secondLastChar)) {
            return false;
        }

        return true;
    }
}
