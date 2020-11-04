package com.github.larkvii.cqrsframework.commons.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 */
public final class Moment {

  private static final ZoneId TIMEZONE_UTC = ZoneId.of("UTC");
  private final ZonedDateTime dateTime;

  private Moment(final Instant instant, final ZoneId zone) {
    this(ZonedDateTime.ofInstant(instant, zone));
  }
  private Moment(final ZonedDateTime dateTimeValue) {
    this.dateTime = dateTimeValue;
  }

  public Moment offset(final ZoneOffset offset) {
    return new Moment(ZonedDateTime.ofInstant(dateTime.toLocalDateTime(), offset, dateTime.getZone()));
  }

  public Moment zone(final ZoneId zoneValue) {
    return new Moment(dateTime.toInstant(), zoneValue);
  }

  public Moment utc() {
    return zone(TIMEZONE_UTC);
  }

  public ZonedDateTime value() {
    return dateTime;
  }
  public Instant instant() {
    return dateTime.toInstant();
  }
  public long valueOf() {
    return instant().toEpochMilli();
  }

  public Calendar calendar() {
    return GregorianCalendar.from(dateTime);
  }

  public long diff(final Moment target) {
    return diff(target, ChronoUnit.SECONDS);
  }

  public long diff(final Moment target, final ChronoUnit unit) {
    return unit.between(this.dateTime, target.dateTime);
  }

  public Moment truncateToHours() {
    return new Moment(dateTime.truncatedTo(ChronoUnit.HOURS));
  }
  public Moment truncateTime() {
    return new Moment(dateTime.truncatedTo(ChronoUnit.DAYS));
  }

  public Moment addSeconds(long seconds) {
    return new Moment(dateTime.plusSeconds(seconds));
  }
  public Moment add(long value, final ChronoUnit unit) {
    return new Moment(dateTime.plus(value, unit));
  }
  public Moment add(final Duration duration) {
    return new Moment(dateTime.plus(duration));
  }
  public Moment subtractSeconds(long seconds) {
    return new Moment(dateTime.minusSeconds(seconds));
  }
  public Moment subtract(long value, final ChronoUnit unit) {
    return new Moment(dateTime.minus(value, unit));
  }
  public Moment subtract(final Duration duration) {
    return new Moment(dateTime.minus(duration));
  }

  public String format(final String pattern) {
    return format(DateTimeFormatter.ofPattern(pattern));
  }
  public String format(final DateTimeFormatter formatter) {
    return dateTime.format(formatter);
  }

  public Date toDate() {
    return Date.from(dateTime.toInstant());
  }
  public Timestamp toTimestamp() {
    return Timestamp.from(dateTime.toInstant());
  }
  public LocalDateTime toLocalDateTime() {
    return dateTime.toLocalDateTime();
  }
  public LocalDate toLocalDate() {
    return dateTime.toLocalDate();
  }
  public LocalTime toLocalTime() {
    return dateTime.toLocalTime();
  }

  @Override
  public String toString() {
    return dateTime.toString();
  }

  public static boolean isValid(final String date, final String pattern) {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
    boolean resultFlag = false;
    try {
      formatter.parse(date);
      resultFlag = true;
    } catch (Exception e) {
      // ignore
    }
    return resultFlag;
  }

  public static Moment now() {
    return new Moment(ZonedDateTime.now());
  }

  public static Moment of(final ZonedDateTime dateTime) {
    return new Moment(dateTime);
  }

  public static Moment of(final Instant instant) {
    return of(instant, ZoneId.systemDefault());
  }

  public static Moment of(final Instant instant, final ZoneId zone) {
    return new Moment(instant, zone);
  }

  public static Moment from(final Date date) {
    return of(date.toInstant());
  }

  public static Moment from(final Calendar calendar) {
    final ZoneId zoneValue = calendar.getTimeZone() == null ? ZoneId.systemDefault() : calendar.getTimeZone().toZoneId();
    return new Moment(calendar.toInstant(), zoneValue);
  }

  public static Moment from(long epochMills) {
    return new Moment(Instant.ofEpochMilli(epochMills), ZoneId.systemDefault());
  }

  public static Moment from(final String value) {
    return from(value, TIMEZONE_UTC);
  }

  public static Moment from(final String value, final ZoneId zone) {
    Objects.requireNonNull(zone);
    return from(value, DateTimeFormatter.ISO_DATE_TIME.withZone(zone));
  }

  public static Moment from(final String value, final String pattern) {
    return from(value, pattern, ZoneId.systemDefault());
  }

  public static Moment from(final String value, final String pattern, final ZoneId zone) {
    Objects.requireNonNull(pattern);
    Objects.requireNonNull(zone);
    return from(value, DateTimeFormatter.ofPattern(pattern).withZone(zone));
  }

  public static Moment from(final String value, final DateTimeFormatter formatter) {
    Objects.requireNonNull(value);
    Objects.requireNonNull(formatter);
    final ZoneId zone = formatter.getZone() == null ? ZoneId.systemDefault() : formatter.getZone();
    final TemporalAccessor accessor = formatter.parseBest(value, LocalDateTime::from, LocalDate::from, LocalTime::from);
    ZonedDateTime dateTime;
    if (accessor instanceof LocalDateTime) {
      dateTime = ((LocalDateTime) accessor).atZone(zone);
    } else if (accessor instanceof LocalDate) {
      dateTime = ((LocalDate) accessor).atStartOfDay(zone);
    } else if (accessor instanceof LocalTime) {
      dateTime = ((LocalTime) accessor).atDate(LocalDate.now()).atZone(zone);
    } else {
      throw new DateTimeException("Unknown DateTimeFormatter");
    }
    return new Moment(dateTime);
  }
}
