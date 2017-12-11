package com.cinemaBook.globals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {
    private Date date;

    public DateFormatter(Date date) {
        this.date = date;
    }

    public String str() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

         int h = calendar.get(Calendar.HOUR_OF_DAY);
         int m = calendar.get(Calendar.MINUTE);

         String s = h + ":";

         if (h < 10) {
             s = "0" + s;
         }

         if (m < 10) {
             s += "0";
         }

         s += m;

        return date + " " + s;
    }
}
