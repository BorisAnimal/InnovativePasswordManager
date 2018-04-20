package com.ba.yo.innovativepasswordmanager.Cipher;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            // Input message
            System.out.print("Input message: ");
            final String message = sc.nextLine();

            System.out.print("Input password: ");
            String password = sc.nextLine();

            // Main cyphering actions
            CryptoUtils.setKeyword(password);
            final String encrypted = CryptoUtils.encrypt(message);
            String decrypted = CryptoUtils.decrypt(encrypted);

            System.out.println("Original text: " + message);
            System.out.println("Encryption result: " + encrypted);
            System.out.println("Received text: " + decrypted);
            System.out.println(message.equals(decrypted) ? "Passed successfully!" : "There was an error!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getHash(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes(StandardCharsets.UTF_8));
    }
}
