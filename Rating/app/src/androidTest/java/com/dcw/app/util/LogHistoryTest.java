package com.dcw.app.util;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * create by adao12.vip@gmail.com on 15/12/12
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class LogHistoryTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;

    @Before
    public void setUp() throws Exception {
        mLogHistory = new LogHistory();
    }

    @Test
    public void testDescribeContents() throws Exception {

    }

    @Test
    public void testWriteToParcel() throws Exception {

        mLogHistory.addEntry(TEST_STRING, TEST_LONG);

        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());
        parcel.setDataPosition(0);

        LogHistory createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<Pair<String, Long>> createdFromParcelData = createdFromParcel.getData();

        // Verify that the received data is correct.
        assertThat(createdFromParcelData.size(), is(1));
        assertThat(createdFromParcelData.get(0).first, is(TEST_STRING));
        assertThat(createdFromParcelData.get(0).second, is(TEST_LONG));
    }
}