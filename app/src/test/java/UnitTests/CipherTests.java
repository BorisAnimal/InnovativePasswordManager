package UnitTests;

import com.ba.yo.innovativepasswordmanager.Cipher.CryptoUtils;
import com.ba.yo.innovativepasswordmanager.Cipher.NoKeyException;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by Java-Ai-BOT on 4/20/2018.
 */

//@RunWith(MockitoJUnitRunner.class)
public class CipherTests {

    @Test
    public void decryptEquality() throws NoKeyException {
        // given:
        final String MP = "password11";
        final String MSG = "Some user's message";
        CryptoUtils.setKeyword(MP);

        // when:
        String crypted = CryptoUtils.encrypt(MSG);

        // then:
        assertNotEquals(MSG, crypted);
        assertEquals(MSG, CryptoUtils.decrypt(crypted));
    }


    @Test
    public void keyGenerationEquality() throws NoKeyException {
        // given:
        final String MP = "password11";
        final String MSG = "Some user's message";
        CryptoUtils.setKeyword(MP);

        // when:
        String msg1 = CryptoUtils.encrypt(MSG);
        CryptoUtils.setKeyword(MP);
        String msg2 = CryptoUtils.encrypt(MSG);

        // then:
        assertFalse(msg1 == msg2);
        assertEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
    }

    @Test
    public void symetricEncryptionEquality() throws NoKeyException {
        // given:
        final String MP = "password11";
        final String MSG = "Some user's message";
        CryptoUtils.setKeyword(MP);

        // when:
        String msg1 = CryptoUtils.encrypt(MSG);
        String msg2 = CryptoUtils.encrypt(MSG);

        // then:
        assertFalse(msg1 == msg2); //links not equal
        assertEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
    }


    @Test
    public void cryptoMessageDifference() throws NoKeyException {
        // given:
        final String MP = "password11";
        final String MSG = "Some user's message";
        final String tmpMSG = "Some other message";
        CryptoUtils.setKeyword(MP);

        // when:
        String msg1 = CryptoUtils.encrypt(MSG);
        String msg2 = CryptoUtils.encrypt(tmpMSG);

        // then:
        assertNotEquals(msg1, msg2);
    }


    @Test
    public void keyDependence() throws NoKeyException {
        // given:
        final String MP = "password11";
        final String MSG = "Some user's message";
        final String tmpMP = "Some other message";
        CryptoUtils.setKeyword(MP);

        // when:
        String msg1 = CryptoUtils.encrypt(MSG);
        String dec1 = CryptoUtils.decrypt(msg1);
        CryptoUtils.setKeyword(tmpMP);
        String msg2 = CryptoUtils.encrypt(MSG);
        String dec2 = CryptoUtils.decrypt(msg2);

        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(dec1);
        System.out.println(dec2);

        // then:
        assertNotEquals(msg1, msg2);
        assertNotEquals(CryptoUtils.decrypt(msg1), CryptoUtils.decrypt(msg2));
        assertEquals(dec1, dec2);
        assertEquals(dec1, MSG);
    }
}
