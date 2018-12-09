package base.utils;

import java.time.LocalDateTime;

/**
 * @Author: sunshanghai
 * @Date: 2018/11/26 09:07
 */
public class XDateUtils {
    /**
     * 是否为闰年
     *
     * @param year 给定年份
     * @return true则为闰年, false则为非闰年
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * @param dateTime
     * @return
     * @throws IllegalArgumentException
     * @see XDateUtils#getDays(int year, int month)
     */
    public static int getDays(LocalDateTime dateTime) throws IllegalArgumentException {
        return getDays(dateTime.getYear(), dateTime.getMonthValue());
    }

    /**
     * 获取指定月份的天数
     *
     * @param year
     * @param month month-of-year, from 1 to 12
     * @return 返回天数
     */
    public static int getDays(int year, int month) throws IllegalArgumentException {
        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 2:
                days = isLeapYear(year) ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            default:
                throw new IllegalArgumentException("您输入的参数有误.");
        }
        return days;
    }
}
