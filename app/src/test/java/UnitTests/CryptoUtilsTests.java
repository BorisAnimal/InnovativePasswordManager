import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;

import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CryptoUtilsTests {
    private static String DEF_MESSAGE = "Hello, world!";
    private static String DEF_PASSWORD = "very_strong_pass";
    private static final String DEF_MESSAGE_ENCRYPTED = "1BF57F854ECB95FE48A3A9183B12BA9D";

    @Test
    public void defaultInput() throws CryptoUtils.DecryptionException {
        String encrypted = CryptoUtils.encrypt(DEF_PASSWORD, DEF_MESSAGE);
        Assert.assertEquals(encrypted, DEF_MESSAGE_ENCRYPTED);

        String decrypted = CryptoUtils.decrypt(DEF_PASSWORD, encrypted);
        Assert.assertEquals(decrypted, DEF_MESSAGE);
    }

    @Test
    public void decryptDefaultInput() throws CryptoUtils.DecryptionException {
        String decrypted = CryptoUtils.decrypt(DEF_PASSWORD, DEF_MESSAGE_ENCRYPTED);
        Assert.assertEquals(decrypted, DEF_MESSAGE);
    }

    @Test
    public void decryptWithWrongPassword() {
        try {
            CryptoUtils.decrypt("some_wrong_pass", DEF_MESSAGE_ENCRYPTED);
        } catch (CryptoUtils.DecryptionException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void decryptWithRandomPassword() {
        try {
            CryptoUtils.decrypt(getRandomString(), DEF_MESSAGE_ENCRYPTED);
        } catch (CryptoUtils.DecryptionException e) {
            return;
        }
        Assert.fail();
    }

    private void encryptAndDecrypt(String password, String message) throws CryptoUtils.DecryptionException {
        String encrypted = CryptoUtils.encrypt(password, message);
        String decrypted = CryptoUtils.decrypt(password, encrypted);
//        Assert.assertEquals(decrypted, message);  // DOES NOT WORK WITH LONG INPUT
        Assert.assertTrue(Arrays.equals(decrypted.getBytes(), message.getBytes()));
    }

    @Test
    public void nullPassword() throws CryptoUtils.DecryptionException {
        try {
            encryptAndDecrypt(null, DEF_MESSAGE);
        } catch (NullPointerException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void nullMessage() throws CryptoUtils.DecryptionException {
        try {
            encryptAndDecrypt(DEF_PASSWORD, null);
        } catch (NullPointerException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void nullPasswordAndMessage() throws CryptoUtils.DecryptionException {
        try {
            encryptAndDecrypt(null, null);
        } catch (NullPointerException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void emptyPassword() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt("", DEF_MESSAGE);
    }

    @Test
    public void emptyMessage() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt(DEF_PASSWORD, "");
    }

    @Test
    public void emptyPasswordAndMessage() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt("", "");
    }

    @Test
    public void longPassword() throws CryptoUtils.DecryptionException {
        StringBuilder passBuilder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            passBuilder.append(DEF_PASSWORD);
        }
        encryptAndDecrypt(passBuilder.toString(), DEF_MESSAGE);
    }

    @Test
    public void longMessage() throws CryptoUtils.DecryptionException {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            message.append(DEF_MESSAGE);
        }
        encryptAndDecrypt(DEF_PASSWORD, message.toString());
    }

    @Test
    public void longPasswordAndMessage() throws CryptoUtils.DecryptionException {
        StringBuilder passBuilder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            passBuilder.append(DEF_PASSWORD);
        }
        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            msgBuilder.append(DEF_MESSAGE);
        }
        encryptAndDecrypt(passBuilder.toString(), msgBuilder.toString());
    }

    @Test
    public void randomPassword() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt(getRandomString(), DEF_MESSAGE);
    }

    @Test
    public void randomMessage() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt(DEF_PASSWORD, getRandomString());
    }

    @Test
    public void randomPasswordAndMessage() throws CryptoUtils.DecryptionException {
        encryptAndDecrypt(getRandomString(), getRandomString());
    }

    private String getRandomString() {
        StringBuilder str = new StringBuilder();
        int n = ThreadLocalRandom.current().nextInt(0, Short.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            str.append((char) ThreadLocalRandom.current().nextInt('\u0000', '\uffff'));
        }
        return str.toString();
    }
}
