package com.ba.yo.innovativepasswordmanager.Cipher;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoUtils {
    private static final String HASH_ALGO = "SHA-256";
    private static final String CYPHER_ALGO = "AES/GCM/NoPadding";
    private static final String CYPHER_SEC_KEY_ALGO = "AES";
    private static final String PASS_HASH_ALGO = "PBKDF2WithHmacSHA1";
    private static final byte[] SALT = "leaderSucciLeadsUsToVictory".getBytes();
    private static final int ITERATIONS = 100_000;
    private static final int KEY_LENGTH = 32;

    private static String lastKey = null;
    private static SecretKeySpec keySpec = null;

    /**
     * Example of CryptoUtils class usage.
     *
     * @param args System arguments (unused)
     * @throws DecryptionException Error during decryption
     */
    public static void main(String[] args) throws DecryptionException {
        System.out.println("========== CryptoUtils example of usage ==========");
        String MESSAGE = "Hello, world!";
        String PASSWORD = "very_complicated_password";
        System.out.println("Message: \"" + MESSAGE + "\"");
        System.out.println("Password: \"" + PASSWORD + "\"");

        String encrypted = encrypt(PASSWORD, MESSAGE);
        System.out.println("Encrypted data: " + encrypted);

        String decrypted = decrypt(PASSWORD, encrypted);
        System.out.println("Decrypted data: \"" + decrypted + "\"");

        System.out.println("Equal: " + String.valueOf(MESSAGE.equals(decrypted)));
        System.out.println("==================================================");
    }

    /**
     * Get hash representation of a string (default SHA-256).
     *
     * @param input String to hash
     * @return Hashed string in form of bytes
     */
    public static byte[] getHash(String input) {
        try {
            return MessageDigest.getInstance(HASH_ALGO).digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            // only if algorithm name will be changed in a wrong way
            e.printStackTrace();
            System.exit(-1);
            return null;  // unreachable
        }
    }

    /**
     * Get closely undecipherable representation of the string according to the key.
     *
     * @param key     Key for the encryption
     * @param message Message to encrypt
     * @return Encrypted version of message (maybe decrypted using 'decrypt' method)
     */
    public static String encrypt(String key, String message) {
        if (key == null || message == null) {
            throw new NullPointerException();
        }
        if (lastKey == null || !lastKey.equals(key)) {
            setKey(key);
        }
        return toHex(encrypt(message.getBytes()));
    }

    /**
     * Get the original representation of the string according to the key.
     *
     * @param key       Key for the decryption
     * @param encrypted Encrypted version of the original message
     * @return Original message as it was before encryption using method 'encrypt'
     * @throws DecryptionException Error during decryption
     */
    public static String decrypt(String key, String encrypted) throws DecryptionException {
        if (key == null || encrypted == null) {
            throw new NullPointerException();
        }
        if (lastKey == null || !lastKey.equals(key)) {
            setKey(key);
        }
        return new String(decrypt(toByte(encrypted)));
    }

    private static void setKey(String key) {
        try {

            SecretKeyFactory f = SecretKeyFactory.getInstance(PASS_HASH_ALGO);
            KeySpec spec = new PBEKeySpec(key.toCharArray(), SALT, ITERATIONS, KEY_LENGTH * 8);
            SecretKey secretKey = f.generateSecret(spec);
            keySpec = new SecretKeySpec(secretKey.getEncoded(), CYPHER_SEC_KEY_ALGO);
            lastKey = key;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static byte[] encrypt(byte[] message) {
        try {

            Cipher cipher = Cipher.getInstance(CYPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] iv = cipher.getIV();
            byte[] cipherText = cipher.doFinal(message);
            byte[] res = new byte[12 + message.length + 16];
            System.arraycopy(iv, 0, res, 0, 12);
            System.arraycopy(cipherText, 0, res, 12, cipherText.length);
            return res;

        } catch (InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException
                | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            e.printStackTrace();
            System.exit(-1);
            return new byte[]{};  // unreachable
        }
    }

    private static byte[] decrypt(byte[] encrypted) throws DecryptionException {
        try {

            Cipher cipher = Cipher.getInstance(CYPHER_ALGO);
            GCMParameterSpec params = new GCMParameterSpec(128, encrypted, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, params);
            return cipher.doFinal(encrypted, 12, encrypted.length - 12);

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new DecryptionException();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
            // only if algorithm name will be changed in a wrong way
            e.printStackTrace();
            System.exit(-1);
            return new byte[]{};  // unreachable
        }
    }

    /**
     * Get hex representation of a bytes array.
     *
     * @param bytes Bytes array to represent as a string
     * @return String representation of bytes array
     */
    protected static String toHex(byte[] bytes) {
        if (bytes == null)
            return "";
        StringBuffer res = new StringBuffer(2 * bytes.length);
        for (byte aBuf : bytes) {
            appendHex(res, aBuf);
        }
        return res.toString();
    }

    /**
     * Get byte representation of a hex string.
     *
     * @param hexString Bytes array to represent as a string
     * @return String representation of bytes array
     */
    protected static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++)
            res[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return res;
    }

    private final static String HEX = "0123456789abcdef";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * Error of CryptoUtils.decrypt method
     * 88995cf00ac5e130b3d8f50d68f166ff7564814db8a958fa0e3dc5b5ff875b25c1ab307213c722f873524f6f7746376fe8d675235b613ecde682fd06b915646f2061f832f82c
     */
    public static class DecryptionException extends Exception {
    }
}
