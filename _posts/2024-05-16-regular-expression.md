---
layout: single
title: 2024/05/16 - RegularExpression
---
# Regular Expression

## Concepts:
- **Regular Expression:** A sequence of characters that define a search pattern, used mainly for pattern matching within strings.
- **Pattern Matching:** Checking if a string matches a certain pattern specified by a regular expression.

## Program Overview:
This program prompts the user to input an email address and then validates whether it conforms to a specified pattern using regular expressions.

## Regular Expression Syntax Used:
- **^:** Indicates the start of a string.
- **$:** Indicates the end of a string.
- **.:** Matches any single character (except newline).
- **[ ]:** Matches any single character within the brackets.
- **[^ ]:** Matches any single character not within the brackets.
- **( | ):** Specifies alternatives.
- **+:** Matches one or more occurrences of the preceding element.
- **?:** Matches zero or one occurrence of the preceding element.
- **{ }:** Specifies the number of occurrences of the preceding element.
- **(?=):** Specifies a positive lookahead assertion.
- **(?!):** Specifies a negative lookahead assertion.
- **\s:** Matches any whitespace character.
- **\S:** Matches any non-whitespace character.
- **\w:** Matches any word character (alphanumeric characters plus underscore).
- **\W:** Matches any non-word character.
- **\d:** Matches any digit.
- **\D:** Matches any non-digit character.
- **\:** Escapes a metacharacter.

## Full Code


```
public class RegularExpressionApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an email address: ");
        String email = scanner.nextLine();

        if (email == null || email.equals("")) {
            System.out.println("[Error] Please enter an email address.");
            System.exit(0);
        }

        String emailReg = "^([a-zA-Z0-9_]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+)*$";

        if (!Pattern.matches(emailReg, email)) {
            System.out.println("[Error] Please enter a valid email address in the format 'username@domain'.");
            System.exit(0);
        }

        System.out.println("[Message] You have entered a valid email address.");

        scanner.close();
    }
}
```


## Key Points:
- The program uses regular expressions to validate user input, ensuring it matches a specific pattern.
- In this case, the program validates whether the input email address adheres to the format "username@domain".
- Various regular expression symbols and constructs are used to define the validation pattern.
- Upon successful validation, the program displays a confirmation message; otherwise, it prompts the user to correct the input.
