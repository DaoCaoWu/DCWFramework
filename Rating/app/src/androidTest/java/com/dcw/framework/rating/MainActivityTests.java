package com.dcw.framework.rating;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.dcw.app.R;
import com.dcw.app.presentation.MainActivity;

/**
 * create by adao12.vip@gmail.com on 15/12/11
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testGreet() {
        MainActivity activity = getActivity();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                getInstrumentation().callActivityOnResume(getActivity());
            }
        });

        // Type name in text input
        // ----------------------

        final EditText nameEditText =
                (EditText) activity.findViewById(R.id.et_account);

        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                if (nameEditText != null) {
                    nameEditText.requestFocus();
                }
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("13570320927");
    }
}
