package com.ba.yo.innovativepasswordmanager.Cipher;

import java.security.*;

import javax.crypto.Cipher;

public class CryptoUtils {
    private static final String ALGORITHM = "RSA";
    private static KeyPair key;

    public static void setKeyword(String keyword) {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024, new SecureRandom(keyword.getBytes()));
            key = keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encryptBytes(byte[] text) throws NoKeyException {
        if (key == null) {
            throw new NoKeyException();
        }
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
            cipherText = cipher.doFinal(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public static String encrypt(String text) throws NoKeyException {
        return bytesToHex(encryptBytes(text.getBytes()));
    }

    public static byte[] decryptBytes(byte[] text) throws NoKeyException {
        if (key == null) {
            throw new NoKeyException();
        }
        byte[] dectyptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
            dectyptedText = cipher.doFinal(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dectyptedText;
    }

    public static String decrypt(String text) throws NoKeyException {
        return new String(decryptBytes(hexToBytes(text)));
    }

    public static String bytesToHex(byte b[]) {
        String hs = "", stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = java.lang.Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toLowerCase();
    }

    public static byte[] hexToBytes(String str) {
        int len = str.length();
        if (len % 2 != 0) return null;
        byte r[] = new byte[len / 2];
        int k = 0;
        for (int i = 0; i < str.length() - 1; i += 2) {
            r[k] = hexToByte(str.charAt(i), str.charAt(i + 1));
            k++;
        }
        return r;
    }

    private static byte hexToByte(char a1, char a2) {
        int k;
        if (a1 >= '0' && a1 <= '9') k = a1 - 48;
        else if (a1 >= 'a' && a1 <= 'f') k = (a1 - 97) + 10;
        else if (a1 >= 'A' && a1 <= 'F') k = (a1 - 65) + 10;
        else k = 0;
        k <<= 4;
        if (a2 >= '0' && a2 <= '9') k += a2 - 48;
        else if (a2 >= 'a' && a2 <= 'f') k += (a2 - 97) + 10;
        else if (a2 >= 'A' && a2 <= 'F') k += (a2 - 65) + 10;
        else k += 0;
        return (byte) (k & 0xff);
    }
}
