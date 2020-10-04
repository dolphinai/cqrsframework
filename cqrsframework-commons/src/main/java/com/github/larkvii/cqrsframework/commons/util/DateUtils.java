package com.github.larkvii.cqrsframework.commons.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public final class DateUtils {

  private DateUtils() {
  }

  public static final String PATTERN_DATE = "yyyy-MM-dd";
  public static final String PATTERN_COMPACT_DATE = "yyyyMMdd";
  public static final String PATTERN_COMPACT_MONTH = "yyyyMM";
  public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

  public static long currentTimestamp() {
    return System.currentTimeMillis();
  }

  public static Date now() {
    return Date.from(Instant.now());
  }

  /**
   * Get the Calendar with current time.
   *
   * @return
   */
  public static Calendar getCalendar() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now());
    return calendar;
  }

  public static Date parse(final String date, final String pattern) {
    Objects.requireNonNull(date);
    LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(instant);
  }

  public static Date parseByDateTime(final String date) {
    return parse(date, PATTERN_DATETIME);
  }

  public static String format(final Date date, final String pattern) {
    if (date == null) {
      return "";
    }
    Instant instant = date.toInstant();
    return format(instant, pattern);
  }

  public static String format(final Instant instant, final String pattern) {
    if (instant == null) {
      return "";
    }
    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * 转换为日期. 2020-01-01
   *
   * @param date
   * @return
   */
  public static String formatToDate(final Date date) {
    return format(date, PATTERN_DATE);
  }

  /**
   * 转换为日期. 20200101
   *
   * @param date
   * @return
   */
  public static String formatToCompactDate(final Date date) {
    return format(date, PATTERN_COMPACT_DATE);
  }

  /**
   * 转换为日期和时间. 2020-01-01 01:01:01
   *
   * @param date
   * @return
   */
  public static String formatToDateTime(final Date date) {
    return format(date, PATTERN_DATETIME);
  }

}
