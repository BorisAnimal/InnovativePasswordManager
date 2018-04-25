package com.ba.yo.innovativepasswordmanager.Cipher;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * Created by Java-Ai-BOT on 4/25/2018.
 */

public class CryptoCipher extends CryptoUtils {
    private final static String TAG = "Cipher";
    private final static String MP = "Master_password";
    private final static String TOKEN = "X_Auth_Token";

    private static String getKey() {
        String key = SecurePreferences.getStringValue(MP, null);
        Log.d(TAG, "getKey: " + key);
        return key;
    }

    public static void storeMP(String mPass) {
        SecurePreferences.setValue(MP, mPass);
    }

    public static void storeToken(String token) {
        SecurePreferences.setValue(TOKEN, token);
    }

    public static boolean checkMP(String mPass) {
        return mPass != null && mPass.equals(SecurePreferences.getStringValue(MP, ""));
    }

    public static String getToken() {
        return SecurePreferences.getStringValue(TOKEN, null);
    }

    /**
     * Do it every time app onDestroy()
     */
    public static void clearAll() {
        SecurePreferences.clearAllValues();
    }


    public static String encrypt(String message) {
        Log.d(TAG, "encrypt: message: " + message);
        String enc = encrypt(getKey(), message);
        Log.d(TAG, "encrypt: enc: " + enc);
        try {
            Log.d(TAG, "on place decipher: " + decrypt(enc));
        } catch (DecryptionException e) {
            Log.e(TAG, "encrypt: " + e);
            e.printStackTrace();
        }
        return enc;
//        return message;
    }

    public static String decrypt(String message) throws DecryptionException {
        Log.d(TAG, "decrypt: message: " + message);
        String enc = decrypt(getKey(), message);
        Log.d(TAG, "decrypt: dec: " + enc);
        return enc;
//        return message;
    }

    public static String hash256(String input) throws NoSuchAlgorithmException {
        return toHex(MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8)));
    }

}
