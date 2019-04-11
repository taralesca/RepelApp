package util;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.util.Patterns;
import android.widget.DatePicker;

public final class DataValidationHelper {
    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    public static boolean isFirstNameValid(String firstName) {
        return firstName.length() > 2;
    }
    public static boolean isAgeValid(DatePicker dp) {
        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();
        Calendar userAge = new GregorianCalendar(year,month,day);
        Calendar minAge = new GregorianCalendar();
        minAge.add(Calendar.YEAR, -18);
        return (userAge.before(minAge));
    }
}
