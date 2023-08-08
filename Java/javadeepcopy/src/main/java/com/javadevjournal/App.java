package com.javadevjournal;

/**
 * Hello world!
 *
 */
public class App 
{
        public static void main(String[] args) {
return input.contains("[") && input.contains("]") && input.contains("{") && input.contains("}");
                int closingBracketIndex = input.indexOf(']');

        // Check if ']' exists and if it's not the last character
        if (closingBracketIndex != -1 && closingBracketIndex < input.length() - 1) {
            // Get the character immediately after ']'
            return input.charAt(closingBracketIndex + 1);
        }

                
        String input = "[]{*}";

        if (!pattern.startsWith("{") || !pattern.endsWith("}")) {
            return false;
        }
        
        Pattern pattern = Pattern.compile("\\[(.*?)\\]\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
            String squareBracketValue = matcher.group(1); // Value inside square brackets
            String curlyBracketValue = matcher.group(2); // Value inside curly brackets
            System.out.println("Square Bracket Value: " + squareBracketValue);
            System.out.println("Curly Bracket Value: " + curlyBracketValue);
        }
    }
    
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String regex = "^\\[(cf|fc|c|f)\\]$"; // For aggrgator inital
        String regex1 = "^\\{(\\*|\\d+)\\}$"; //For number of offers.
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
