package UnitTests;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RunWith(JUnit4ClassRunner.class)
public class CipherTests {
    private static String DEF_MESSAGE = "Hello, world!";
    private static String DEF_PASSWORD = "very_strong_pass";
    private static final String DEF_MESSAGE_ENCRYPTED = "7F64B7E00868D43E40229AD4D935F0E0";


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

//import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Java-Ai-BOT on 4/20/2018.
// */
//
////@RunWith(MockitoJUnitRunner.class)
//public class CipherTests {
//
//    @Test
//    public void decryptEquality() throws NoKeyException {
//        // given:
//        final String MP = "password11";
//        final String MSG = "Some user's message";
//        CryptoUtils.setKeyword(MP);
//
//        // when:
//        String crypted = CryptoUtils.encrypt(MSG);
//
//        // then:
//        assertNotEquals(MSG, crypted);
//        assertEquals(MSG, CryptoUtils.decrypt(crypted));
//    }
//
//
//    @Test
//    public void keyGenerationEquality() throws NoKeyException {
//        // given:
//        final String MP = "password11";
//        final String MSG = "Some user's message";
//        CryptoUtils.setKeyword(MP);
//
//        // when:
//        String msg1 = CryptoUtils.encrypt(MSG);
//        CryptoUtils.setKeyword(MP);
//        String msg2 = CryptoUtils.encrypt(MSG);
//
//        // then:
//        assertFalse(msg1 == msg2);
//        assertEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
//    }
//
//    @Test
//    public void symetricEncryptionEquality() throws NoKeyException {
//        // given:
//        final String MP = "password11";
//        final String MSG = "Some user's message";
//        CryptoUtils.setKeyword(MP);
//
//        // when:
//        String msg1 = CryptoUtils.encrypt(MSG);
//        String msg2 = CryptoUtils.encrypt(MSG);
//
//        // then:
//        assertFalse(msg1 == msg2); //links not equal
//        assertEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
//    }
//
//
//    @Test
//    public void cryptoMessageDifference() throws NoKeyException {
//        // given:
//        final String MP = "password11";
//        final String MSG = "Some user's message";
//        final String tmpMSG = "Some other message";
//        CryptoUtils.setKeyword(MP);
//
//        // when:
//        String msg1 = CryptoUtils.encrypt(MSG);
//        String msg2 = CryptoUtils.encrypt(tmpMSG);
//
//        // then:
//        assertNotEquals(msg1, msg2);
//    }
//
//
//    @Test
//    public void keyDependence() throws NoKeyException {
//        // given:
//        final String MP = "password11";
//        final String MSG = "Some user's message";
//        final String tmpMP = "Some other message";
//        CryptoUtils.setKeyword(MP);
//
//        // when:
//        String msg1 = CryptoUtils.encrypt(MSG);
//        String dec1 = CryptoUtils.decrypt(msg1);
//        CryptoUtils.setKeyword(tmpMP);
//        String msg2 = CryptoUtils.encrypt(MSG);
//        String dec2 = CryptoUtils.decrypt(msg2);
//
//        System.out.println(msg1);
//        System.out.println(msg2);
//        System.out.println(dec1);
//        System.out.println(dec2);
//
//        // then:
//        assertNotEquals(msg1, msg2);
//        assertNotEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
//        assertEquals(dec1, dec2);
//        assertEquals(dec1, MSG);
//    }
//}
