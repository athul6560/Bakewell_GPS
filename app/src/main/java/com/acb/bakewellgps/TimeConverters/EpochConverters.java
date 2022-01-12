package com.acb.bakewellgps.TimeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EpochConverters {
    public static long humanReadabletoEpoch(int day, int month, int year) {
        long epoch = 0;
        try {
            epoch = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(month + "/" + day + "/" + year + " 10:00:00").getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return epoch;
    }
}
