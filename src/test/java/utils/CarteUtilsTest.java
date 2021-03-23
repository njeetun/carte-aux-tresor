package utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class CarteUtilsTest {


    @Test
    public void convertLocalDateTimeToString() {

        String dateTime = CarteUtils.convertLocalDateTimeToString(LocalDateTime.of(2021, 3, 9, 22, 0, 0), "yyyy-MM-dd_HH-mm-ss");
        Assert.assertEquals("2021-03-09_22-00-00", dateTime);
    }
}