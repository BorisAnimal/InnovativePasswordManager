package UnitTests;

import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.controllers.EditEntryController;
import com.ba.yo.innovativepasswordmanager.model.ApiClient;
import com.ba.yo.innovativepasswordmanager.model.CryptoCipher;
import com.ba.yo.innovativepasswordmanager.model.RetrofitService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainUnitTests {
    @Mock
    private EditEntryMVC.View view;
    @Mock
    private ApiClient api;


    private EditEntryController controller;


    @Before
    public void setUp() throws Exception {
        controller = new EditEntryController(view);
    }

    @After
    public void teardown() {
        controller = null;
    }

    //Generate random password
    @Test
    public void generateRandomPasswordWorks() {
        // given:
        // -----


        // when:
        controller.generateRandomPassword();

        // then:
        verify(view).setPassword(anyString());
        verify(view).showNotification(anyString());
    }

    @Test
    public void commitEntryEmptyPassword() {
        // given
        String pass = "";
        String login = "user_login";
        String description = "";
//        when(interactor.fetchMovies(anyInt())).thenReturn(responseObservable);
        when(CryptoCipher.encrypt(pass)).thenReturn(pass);
        when(CryptoCipher.encrypt(login)).thenReturn(login);

        // when
        controller.commitEntry(login, pass, description);

        // then
        verify(api, never()).postAccount(anyString(), login, pass, description);
    }
}
