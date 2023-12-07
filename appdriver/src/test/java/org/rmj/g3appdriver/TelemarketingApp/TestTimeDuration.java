package org.rmj.g3appdriver.TelemarketingApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class TestTimeDuration {
    private String sCallStrt = "2023-12-07 8:30:30";
    private String sCallEnd = "2023-12-07 9:30:01";
    private Date dCallStrt;
    private Date dCallEnd;

    @Before
    public void InitTime() throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dCallStrt = timeFormat.parse(sCallStrt);
        this.dCallEnd = timeFormat.parse(sCallEnd);
    }

    @Test
    public void GetTimeDuration(){
        long lTimeValue = dCallEnd.getTime() - dCallStrt.getTime();

        long lHourDuration = TimeUnit.HOURS.convert(lTimeValue, TimeUnit.MILLISECONDS);
        long lMinDuration = TimeUnit.MINUTES.convert(lTimeValue, TimeUnit.MILLISECONDS);
        long lSecDuration = TimeUnit.SECONDS.convert(lTimeValue, TimeUnit.MILLISECONDS);

        System.out.println(lHourDuration);
        System.out.println(lMinDuration);
        System.out.println((lSecDuration / 1000) % 60);
    }
}
