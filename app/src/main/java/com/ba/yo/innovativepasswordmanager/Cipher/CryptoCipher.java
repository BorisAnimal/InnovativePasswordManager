package com.ba.yo.innovativepasswordmanager.Cipher;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * Created by Java-Ai-BOT on 4/25/2018.
 */

public class CryptoCipher extends CryptoUtils {
    private final static String TAG = "Cipher";
    private final static String MP = "Master_password";
    private final static String TOKEN = "X_Auth_Token";

    private static String getKey() {
        return SecurePreferences.getStringValue(MP, null);
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
        return encrypt(getKey(), message);
    }

    public static String decrypt(String message) throws DecryptionException {
        return decrypt(getKey(), message);
    }

}
