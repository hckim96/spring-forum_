package com.example.jpamvc.myUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PrettyTimeString {
    private static final int SEC_SEC = 1;
    private static final int MIN_SEC = SEC_SEC * 60;
    private static final int HOUR_SEC = MIN_SEC * 60;
    private static final int DAY_SEC = HOUR_SEC * 24;
    private static final int MONTH_SEC = DAY_SEC * 30;
    private static final int YEAR_SEC = MONTH_SEC * 12;

    public static String convert(LocalDateTime localDateTime) {
        long curTime = System.currentTimeMillis();
        long regTime = Timestamp.valueOf(localDateTime).getTime();
        long diff = (curTime - regTime) / 1000;
        String msg = null;
        String suffix = "년 전";
        if (diff < MIN_SEC) {
            diff = diff / SEC_SEC;
            suffix = "초 전";
        } else if (diff < HOUR_SEC) {
            diff = diff / MIN_SEC;
            suffix = "분 전";
        } else if (diff < DAY_SEC) {
            diff = diff / HOUR_SEC;
            suffix = "시간 전";
        } else if (diff < MONTH_SEC) {
            diff = diff / DAY_SEC;
            suffix = "일 전";
        } else if (diff < YEAR_SEC) {
            diff = diff / MONTH_SEC;
            suffix = "달 전";
        } else {
            diff = diff / YEAR_SEC;
            suffix = "년 전";
        }
        msg = diff + suffix;
        return msg;
    }
}
