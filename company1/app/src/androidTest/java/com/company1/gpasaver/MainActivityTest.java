package com.company1.gpasaver;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.navigation.NavigationView;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest {
    //launch main activity
    @Rule
    public ActivityTestRule<MainActivity> RAT = new ActivityTestRule<>(MainActivity.class);

    /**
     * check that the main activity is in the correct folder
     */
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.company1.gpasaver", appContext.getPackageName());
    }

    /**
     * check that Main Activity is displayed
     */
    @Test
    public void test_activity_inView() {
        //check if the main activity is in view
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }

    /**
     * check that the NavigationView is there
     */
    @Test
    public void test_navView_inView() {
        //check if the nav view is invisible
        onView(withId(R.id.nav_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        //check if the nav view is enabled to be used
        onView(withId(R.id.nav_view)).check(matches(isEnabled()));
    }

    /**
     * check that the logout button is there
     */
    @Test
    public void test_navView_has_logout_button() {
        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {
                NavigationView navView = RAT.getActivity().findViewById(R.id.nav_view);
                MenuItem menuItem = navView.getMenu().findItem(R.id.nav_logout);
                assertNotNull(menuItem);
            }
        });
    }

    /**
     * check that the logout button goes to the login screen
     */
    @Test
    public void test_logout_button_to_login_screen() {
        //open the drawer
        onView(withId(R.id.main)).perform(DrawerActions.open());

        //click the logout button
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));

        //check if the login screen is displayed
        onView(withId(R.id.loginScreen)).check(matches(isDisplayed()));
    }


    /**
     * check that the logout button displays the "Logging out..." toast
     */
    @Test
    public void test_logout_button_displays_toast() {
        //open the drawer
        onView(withId(R.id.main)).perform(DrawerActions.open());

        //click the logout button
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_logout));

        //check if the toast is displayed
        onView(withText("Logging out...")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }
}