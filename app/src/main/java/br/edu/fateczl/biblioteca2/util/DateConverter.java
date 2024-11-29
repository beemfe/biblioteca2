/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class DateConverter {
    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

    public static Date stringToDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        dateFormat.setLenient(false);
        return dateFormat.parse(dateStr);
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static boolean isValidDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                return false;
            }
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(dateStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int year = cal.get(Calendar.YEAR);
            if (year < 2000 || year > 2100) {
                return false;
            }

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDateRange(String startDateStr, String endDateStr) {
        try {
            Date startDate = stringToDate(startDateStr);
            Date endDate = stringToDate(endDateStr);

            if (startDate == null || endDate == null) {
                return false;
            }

            return !endDate.before(startDate);
        } catch (ParseException e) {
            return false;
        }
    }
}