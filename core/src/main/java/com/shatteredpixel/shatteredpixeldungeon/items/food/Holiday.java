package com.shatteredpixel.shatteredpixeldungeon.items.food;

import java.util.Calendar;

public class Holiday {
    private static HolidayEnum holiday;

    static {
        initializeHoliday();
    }
    public static HolidayEnum getHoliday() {
        return holiday;
    }
    private static void initializeHoliday() {
        holiday = HolidayEnum.NONE;

        final Calendar calendar = Calendar.getInstance();
        switch(calendar.get(Calendar.MONTH)){
            case Calendar.JANUARY:
                if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
                    holiday = HolidayEnum.XMAS;
                break;
            case Calendar.OCTOBER:
                if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
                    holiday = HolidayEnum.HWEEN;
                break;
            case Calendar.NOVEMBER:
                if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
                    holiday = HolidayEnum.HWEEN;
                break;
            case Calendar.DECEMBER:
                if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
                    holiday = HolidayEnum.XMAS;
                break;
        }
    }
}
