package co.simplon.soninkrala.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateHoursUtil {

    public static LocalDateTime getDateToLocalDateTimeIfExist(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime() : null;
    }
}
