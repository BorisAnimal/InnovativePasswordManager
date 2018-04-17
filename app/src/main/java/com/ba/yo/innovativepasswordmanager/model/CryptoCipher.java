package com.ba.yo.innovativepasswordmanager.model;

import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

import de.adorsys.android.securestoragelibrary.*;

/**
 * Created by Java-Ai-BOT on 4/15/2018.
 */

public class CryptoCipher {
    private final static String PROVIDER = "AndroidKeyStore";
    private final static String TAG = "Cipher";
    private final static String MP = "Master_password";
    private final static String TOKEN = "X_Auth_Token";

    @NonNull
    public static String hash256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return null;
        }
    }

    @NonNull
    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static void storeMP(String mPass) {
        SecurePreferences.setValue(MP, mPass);
    }

    public static void storeToken(String token) {
        SecurePreferences.setValue(TOKEN, token);
    }

    public static String getToken() {
        return SecurePreferences.getStringValue(TOKEN, null);
    }


    public static String encrypt(String message) {
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            String key = SecurePreferences.getStringValue(MP, null);
            sks = new SecretKeySpec(key.getBytes(), "AES");
        } catch (Exception e) {
            Log.e("Crypto", "AES secret key spec error");
        }
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(message.getBytes());
        } catch (Exception e) {
            Log.e("Crypto", "AES encryption error\n" + e.getLocalizedMessage());
        }

        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);// + "\n";
    }

    public static String decrypt(String message) {
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            String key = SecurePreferences.getStringValue(MP, null);
            sks = new SecretKeySpec(key.getBytes(), "AES");
        } catch (Exception e) {
            Log.e("Crypto", "AES secret key spec error");
        }
        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try

        {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(message.getBytes());
        } catch (
                Exception e)

        {
            Log.e("Crypto", "AES decryption error");
        }
        return new String(decodedBytes);
    }

    /**
     * Do it every time app onDestroy()
     */
    public static void clearAll() {
        SecurePreferences.clearAllValues();
    }

}
