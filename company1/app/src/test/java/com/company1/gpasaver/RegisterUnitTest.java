package com.company1.gpasaver;

import android.widget.EditText;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegisterUnitTest {
    RegisterActivity r;
    @Before
    public void init() {
     r = new RegisterActivity();
    //     r.onCreate();
    r.etUsername =Mockito.mock(EditText .class);
    r.etPassword =Mockito.mock(EditText .class);
    r.etConfirmPassword =Mockito.mock(EditText .class);
    r.etFirstName = Mockito.mock(EditText .class);
    r.etLastName = Mockito.mock(EditText .class);
    r.etEmail =Mockito.mock(EditText .class);
}


    @Test
    public void no_inputs(){
        r.username = "";
        r.password = "";
        r.firstName = "";
        r.lastName = "";
        r.email = "";
        r.confirmPassword = "";
        assertEquals(r.validateInputs(), false);
    }
    @Test
    public void no_name(){
        r.username = "";
        r.password = "password";
        r.firstName = "name";
        r.email = "email";
        r.confirmPassword = "password";
        assertEquals(r.validateInputs(), true);
    }
    @Test
    public void no_password(){
        r.username = "test";
        r.password = "";
        r.firstName = "name";
        r.email = "email";
        r.confirmPassword = "";
        assertEquals(r.validateInputs(), false);
    }
    @Test
    public void no_email(){
        r.username = "test";
        r.password = "asda";
        r.firstName = "name";
        r.email = "";
        r.confirmPassword = "password";
        assertEquals(r.validateInputs(), false);
    }

    @Test
    public void password_no_match(){
        r.username = "test";
        r.password = "password1";
        r.firstName = "name";
        r.email = "fsdfsd";
        r.confirmPassword = "password";
        assertEquals(r.validateInputs(), false);
    }
    @Test
    public void correct_input(){
        r.username = "test";
        r.password = "password1";
        r.firstName = "name";
        r.email = "fsdfsd";
        r.confirmPassword = "password1";
        assertEquals(r.validateInputs(), true);
    }

}