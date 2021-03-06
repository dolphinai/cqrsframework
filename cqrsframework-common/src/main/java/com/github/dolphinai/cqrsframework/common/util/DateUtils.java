package com.github.dolphinai.cqrsframework.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Date Utility.
 */
public final class DateUtils {

  public static final String PATTERN_DATE = "yyyy-MM-dd";
  public static final String PATTERN_COMPACT_DATE = "yyyyMMdd";
  public static final String PATTERN_COMPACT_MONTH = "yyyyMM";
  public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

  private DateUtils() {
  }

  public static Date from(final String date, final String pattern) {
    Objects.requireNonNull(date);
    Objects.requireNonNull(pattern);
    LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date fromDateTime(final String date) {
    return from(date, PATTERN_DATETIME);
  }

  public static String toDateString(final Date date) {
    return format(date, PATTERN_DATE);
  }

  public static String toCompactDateString(final Date date) {
    return format(date, PATTERN_COMPACT_DATE);
  }

  public static String toDateTimeString(final Date date) {
    return format(date, PATTERN_DATETIME);
  }

  public static String format(final Date date, final String pattern) {
    if (date == null) {
      return "";
    }
    LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

}
