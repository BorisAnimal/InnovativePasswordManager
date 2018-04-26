package com.ba.yo.innovativepasswordmanager.Cipher;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CryptoUtils {
    private static final String CYPHER_ALGO = "AES";
    private static final String RANDOM_ALGO = "PBKDF2WithHmacSHA1";
    private static final byte[] SALT = {-74, -57, 117, 106, -69, 125, 88, 62, 56, -11, -110, 21,
            -51, -117, -71, 3, -90, -75, -19, 38, -34, 93, 40, -21};
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 24;

    /**
     * Example of CryptoUtils class usage.
     *
     * @param args System arguments (unused)
     * @throws DecryptionException Error during decryption
     */
    public static void main(String[] args) throws DecryptionException {
        System.out.println("========== CryptoUtils example of usage ==========");
        String MESSAGE = "Hello, world!";
        String PASSWORD = "complicated_password";
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
     * Get closely undecipherable representation of the string according to the key.
     *
     * @param key     Key for cyphering the message (important to store somewhere or memorize)
     * @param message Message to encrypt
     * @return Encrypted version of message (maybe decrypted using 'decrypt' method)
     */
    public static String encrypt(String key, String message) {
        if (key == null || message == null) {
            throw new NullPointerException();
        }
        return toHex(encrypt(getRawKey(key.toCharArray()), message.getBytes()));
    }

    /**
     * Get the original representation of the string according to the key.
     *
     * @param key       Key for cyphering the message (important to store somewhere or memorize)
     * @param encrypted Encrypted version of the original message
     * @return Original message as it was before encryption using method 'encrypt'
     * @throws DecryptionException Error during decryption
     */
    public static String decrypt(String key, String encrypted) throws DecryptionException {
        if (key == null || encrypted == null) {
            throw new NullPointerException();
        }
        return new String(decrypt(getRawKey(key.toCharArray()), toByte(encrypted)));
    }

    private static byte[] getRawKey(char[] seed) {
        try {

            PBEKeySpec spec = new PBEKeySpec(seed, SALT, ITERATIONS, KEY_LENGTH * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(RANDOM_ALGO);
            return skf.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(-1);
            return new byte[]{};  // unreachable
        }
    }

    private static byte[] encrypt(byte[] rawKey, byte[] message) {
        try {

            Cipher cipher = Cipher.getInstance(CYPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rawKey, CYPHER_ALGO));
            return cipher.doFinal(message);

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

    private static byte[] decrypt(byte[] rawKey, byte[] encrypted) throws DecryptionException {
        try {

            Cipher cipher = Cipher.getInstance(CYPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rawKey, CYPHER_ALGO));
            return cipher.doFinal(encrypted);

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getLocalizedMessage());
            throw new DecryptionException();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // only if algorithm name will be changed in a wrong way
            e.printStackTrace();
            System.exit(-1);
            return new byte[]{};  // unreachable
        }
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++)
            res[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return res;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer res = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(res, aBuf);
        }
        return res.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * Error of CryptoUtils.decrypt method
     */
    public static class DecryptionException extends Exception {
    }
}
