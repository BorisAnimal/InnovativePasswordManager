package com.ba.yo.innovativepasswordmanager.Cipher;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {
    private static final String CYPHER_ALGO = "AES";
    private static final String RANDOM_ALGO = "SHA1PRNG";


    public static String hash256(String input) throws NoSuchAlgorithmException {
        return toHex(MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8)));
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
        return toHex(encrypt(getRawKey(key.getBytes()), message.getBytes()));
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
        return new String(decrypt(getRawKey(key.getBytes()), toByte(encrypted)));
    }

    private static byte[] getRawKey(byte[] seed) {
        try {

            KeyGenerator keyGen = KeyGenerator.getInstance(CYPHER_ALGO);
            SecureRandom sr = SecureRandom.getInstance(RANDOM_ALGO);
            sr.setSeed(seed);
            keyGen.init(128, sr);  // 192 and 256 bits may not be available
            return keyGen.generateKey().getEncoded();

        } catch (NoSuchAlgorithmException e) {
            // only if algorithm name will be changed in a wrong way
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

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            // only if algorithm name will be changed in a wrong way
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

    private static String toHex(byte[] buf) {
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
