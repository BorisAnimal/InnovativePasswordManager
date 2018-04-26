package MainGuiTests;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.ui.EditEntryActivity;
import com.ba.yo.innovativepasswordmanager.ui.EntitySelectActivity;
import com.ba.yo.innovativepasswordmanager.ui.LoginActivity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Checks.checkNotNull;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by Java-Ai-BOT on 4/19/2018.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainGuiTests {
    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);

    private String login, password;
    private String eLogin = "modbrin", ePass = "qwerty";
    @Test
    public void a_shouldBeAbleToRegister(){
        login = getRandomString();
        password = getRandomString();
        onView(withId(R.id.btn_create_account)).perform(click());
        onView(withId(R.id.login_register)).perform(typeText(login));
        onView(withId(R.id.pass_register)).perform(typeText(password));
        onView(withId(R.id.sign_up_button)).perform(click());
        //Check fields on login page to match newly created account
        onView(withId(R.id.master_login)).check(matches(withText(login)));
        onView(withId(R.id.master_password)).check(matches(withText(password)));
    }

    @Test
    public void b_shouldBeAbleToLogin()
    {
        Intents.init();
        onView(withId(R.id.master_login)).perform(typeText(eLogin));
        onView(withId(R.id.master_password)).perform(typeText(ePass), closeSoftKeyboard());
        onView(withId(R.id.master_button)).perform(click());

        intended(hasComponent(EntitySelectActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void c_shouldBeAbleToAddEntries() {
        Intents.init();
        onView(withId(R.id.master_login)).perform(typeText(eLogin));
        onView(withId(R.id.master_password)).perform(typeText(ePass), closeSoftKeyboard());
        onView(withId(R.id.master_button)).perform(click());

        //Check if correct activity is launched
        intended(hasComponent(EntitySelectActivity.class.getName()));

        for (int i = 0; i < 5; i++) {
            onView(withId(R.id.addEntry)).perform(click());
            String value = getRandomString();
            onView(withId(R.id.loginField)).perform(typeText(value));
            onView(withId(R.id.generatePasswordButton)).perform(click());
            onView(withId(R.id.descrField)).perform(typeText(value));
            onView(withId(R.id.addButton)).perform(click());
            intended(hasComponent(EntitySelectActivity.class.getName()));
        }

        Intents.release();
    }

    @Test
    public void d_shouldBeAbleToSelect(){
        onView(withId(R.id.master_login)).perform(typeText(eLogin));
        onView(withId(R.id.master_password)).perform(typeText(ePass), closeSoftKeyboard());
        onView(withId(R.id.master_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.entry_selector)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.applet_selector)).atPosition(0).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //completion success is a return to selector activity
        onView(withId(R.id.entry_selector)).check(matches(isDisplayed()));

    }

    @Test
    public void e_shouldBeAbleToWipe(){
        onView(withId(R.id.master_login)).perform(typeText(eLogin));
        onView(withId(R.id.master_password)).perform(typeText(ePass), closeSoftKeyboard());
        onView(withId(R.id.master_button)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Manage Data")).perform(click());
        onView(withId(R.id.btn_delete_data)).perform(click());
        onView(withId(R.id.chk_delete)).perform(click());
        onView(withId(R.id.password_delete)).perform(typeText(ePass));
        onView(withId(R.id.btn_delete)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //completion success is a return to selector activity
        onView(withId(R.id.entry_selector)).check(matches(isDisplayed()));
    }

    String getRandomString(){
        return RandomStringUtils.randomAlphanumeric(10);
    }
}