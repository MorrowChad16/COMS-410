package com.company1.gpasaver;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.company1.gpasaver.util.PaymentsUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PaymentActivityTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.company1.gpasaver", appContext.getPackageName());
    }

    @Test
    public void testReadyToPay(){
        PaymentsUtil util = new PaymentsUtil();
        Optional<JSONObject> test = util.getIsReadyToPayRequest();
        System.out.println(test.get());
        assertEquals(test.getClass(), util.getIsReadyToPayRequest().getClass());
    }

    @Test
    public void testGetBaseRequest() throws JSONException {
        PaymentsUtil util = new PaymentsUtil();
        JSONObject test = util.getBaseRequest();
        assertEquals(test.get("apiVersion"), 2);
        assertEquals(test.get("apiVersionMinor"), 0);
    }
}
