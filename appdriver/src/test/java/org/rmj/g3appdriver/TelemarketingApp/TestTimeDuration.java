package org.rmj.g3appdriver.TelemarketingApp;

import com.google.type.DateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class TestTimeDuration {
    private String sTimeStrt = "8:30:59";
    private String sTimeEnd = "9:42:01";
    private Date timeCallStrt;
    private Date timeCallEnd;
    private String sDateStrt = "2024/01/29";
    private String sDateEnd = "2024/02/28";
    private Date dCallStrt;
    private Date dCallEnd;

    @Before
    public void InitTime() throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        this.timeCallStrt = timeFormat.parse(sTimeStrt);
        this.timeCallEnd = timeFormat.parse(sTimeEnd);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        this.dCallStrt = dateFormat.parse(sDateStrt);
        this.dCallEnd = dateFormat.parse(sDateEnd);
    }

    @Test
    public void GetTimeDuration(){
        long lTimeValue = timeCallStrt.getTime() - timeCallEnd.getTime();

        long lHourDuration = TimeUnit.HOURS.convert(lTimeValue, TimeUnit.MILLISECONDS);
        long lMinDuration = TimeUnit.MINUTES.convert(lTimeValue, TimeUnit.MILLISECONDS);
        long lSecDuration = TimeUnit.SECONDS.convert(lTimeValue, TimeUnit.MILLISECONDS);

        System.out.println(lHourDuration);
        System.out.println(lMinDuration % 60);
        System.out.println((lSecDuration) % 60);
    }
    @Test
    public void GetDateDuration(){
        long lDateDuration = dCallEnd.getTime() - dCallStrt.getTime();
        System.out.println(TimeUnit.DAYS.convert(lDateDuration, TimeUnit.MILLISECONDS));
    }
}
