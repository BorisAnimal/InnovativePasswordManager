package UnitTests;

import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.controllers.EditEntryController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainUnitTests {
    @Mock
    private EditEntryMVC.View view;

    //Generate random password
    @Test
    public void generateRandomPasswordWorks() {
        // given:
        EditEntryController controller = new EditEntryController(view);

        // when:
        controller.generateRandomPassword();

        // then:
        verify(view).setPassword(anyString());
    }
}
