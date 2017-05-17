package com.rollbar.payload.data.body;

import com.rollbar.testing.GetAndSet;
import com.rollbar.testing.TestThat;
import com.rollbar.utilities.ArgumentNullException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chris on 11/25/15.
 */
public class CrashReportTest {
    private CrashReport report;

    @Before
    public void setUp() throws Exception {
        report = new CrashReport("HI");
    }

    @Test
    public void testRaw() throws Exception {
        TestThat.getAndSetWorks(report, "OOPS", "You broke it", new GetAndSet<CrashReport, String>() {
            public String get(CrashReport crashReport) {
                return crashReport.raw();
            }

            public CrashReport set(CrashReport crashReport, String val) {
                try {
                    return crashReport.raw(val);
                } catch (ArgumentNullException e) {
                    fail("Neither is null");
                }
                return null;
            }
        });
    }
}