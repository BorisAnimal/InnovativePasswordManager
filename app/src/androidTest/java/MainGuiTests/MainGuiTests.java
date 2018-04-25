package MainGuiTests;

import android.support.test.rule.ActivityTestRule;

import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */
public class MainGuiTests {
    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);
    @Test
    public void shouldBeAbleToLogin()
    {
        onView(withId(R.id.master_login)).perform(typeText("abc"));
        onView(withId(R.id.master_password)).perform(typeText("qweee123"));
        onView(withId(R.id.master_button)).perform(click());
        //onView(withId(R.id.action_sort)).check(matches(isDisplayed()));
    }
}