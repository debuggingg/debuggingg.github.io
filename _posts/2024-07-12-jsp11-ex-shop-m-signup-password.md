---
layout: single
title: 2024/07/12 JSP 11-EX-Shop-M/SignUp Password
---
## Password  messageDigest

## Utility - Java -
#### Create the Utility for encryptPassword
- **Utility Class**: This class provides utility functions for web program development.
- **encrypt Method**: This static method receives a password string, encrypts it using the SHA-256 algorithm, and returns the encrypted string.
  - **MessageDigest Object**: Used to perform encryption.
  - **messageDigest.update**: Converts the password to a byte array and updates the MessageDigest object.
  - **messageDigest.digest**: Performs the encryption and returns an encrypted byte array.
  - **Conversion to String**: The encrypted byte array is converted to a hexadecimal string.
- **Exception Handling**: Catches `NoSuchAlgorithmException` if an incorrect encryption algorithm is used and prints an error message.



```java
// Class to provide functions required for web program development
public class Utility {
    // Static method to receive a string (password), encrypt it, and return the result
    public static String encrypt(String password) {
        // Variable to store the encrypted string
        String encryptPassword = "";
        
        try {
            // Get a MessageDigest object that provides encryption functionality
            // MessageDigest.getInstance(String algorithm): Static method to return a MessageDigest object containing the encryption algorithm passed as a parameter
            // => Throws NoSuchAlgorithmException if an incorrect encryption algorithm is passed
            // One-way encryption algorithms (cannot be decrypted): MD5, SHA-1, SHA-256, SHA-512, etc.
            // Two-way encryption algorithms (can be decrypted): AES-123, RSA, etc.
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            
            // MessageDigest.update(byte[] input): Method to receive a string to be encrypted as a byte array and store it in the MessageDigest object
            // String.getBytes(): Method to convert the string stored in a String object to a byte array and return it
            messageDigest.update(password.getBytes());    
            
            // MessageDigest.digest(): Method to use the encryption algorithm stored in the MessageDigest object to encrypt the byte array elements and return the encrypted byte array
            byte[] digest = messageDigest.digest();
            
            // Convert the encrypted array elements to a String object (string) and store it
            for (int i = 0; i < digest.length; i++) {
                // Integer.toHexString(int i): Static method to convert the integer value passed as a parameter to a hexadecimal string and return it
                encryptPassword += Integer.toHexString(digest[i] & 0xff);
            }
        } catch (NoSuchAlgorithmException e) { 
            System.out.println("[Error] Incorrect encryption algorithm used.");
        }
        return encryptPassword;
    }
}
```
#### Change JSP code too ex) 
String passwd=request.getParameter("passwd");
=> String passwd=Utility.encrypt(request.getParameter("passwd"));

