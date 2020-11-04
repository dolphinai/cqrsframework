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
  private final Instant instant;
  private ZoneId zoneId;

  public Moment() {
    this(Instant.now(), ZoneId.systemDefault());
  }
  public Moment(final Instant instantValue) {
    this(instantValue, ZoneId.systemDefault());
  }
  public Moment(final Instant instantValue, final ZoneId zoneValue) {
    this.instant = instantValue;
    this.zoneId = zoneValue;
  }

  public Moment zone(final ZoneId zoneValue) {
    return new Moment(instant, zoneValue);
  }

  public Moment utc() {
    return zone(TIMEZONE_UTC);
  }

  public Instant value() {
    return instant;
  }

  public long valueOf() {
    return instant.toEpochMilli();
  }

  public Calendar calendar() {
    return GregorianCalendar.from(ZonedDateTime.ofInstant(instant, zoneId));
  }

  public long diff(final Moment target) {
    return diff(target, ChronoUnit.SECONDS);
  }

  public long diff(final Moment target, final ChronoUnit unit) {
    return unit.between(this.toLocalDateTime(), target.toLocalDateTime());
  }

  public Moment truncateMinutes() {
    return new Moment(instant.truncatedTo(ChronoUnit.HOURS));
  }
  public Moment truncateTime() {
    return new Moment(instant.truncatedTo(ChronoUnit.DAYS));
  }

  public Moment addMillis(long millis) {
    return new Moment(instant.plusMillis(millis));
  }
  public Moment addSeconds(long seconds) {
    return new Moment(instant.plusSeconds(seconds));
  }
  public Moment add(long value, final ChronoUnit unit) {
    return new Moment(instant.plus(value, unit));
  }
  public Moment add(final Duration duration) {
    return new Moment(instant.plus(duration));
  }
  public Moment subtractMillis(long millis) {
    return new Moment(instant.minusMillis(millis));
  }
  public Moment subtractSeconds(long seconds) {
    return new Moment(instant.minusSeconds(seconds));
  }
  public Moment subtract(long value, final ChronoUnit unit) {
    return new Moment(instant.minus(value, unit));
  }
  public Moment subtract(final Duration duration) {
    return new Moment(instant.minus(duration));
  }

  public String format(final String pattern) {
    return format(DateTimeFormatter.ofPattern(pattern).withZone(zoneId));
  }

  public String format(final DateTimeFormatter formatter) {
    return formatter.format(instant);
  }

  public Date toDate() {
    return Date.from(instant);
  }

  public Timestamp toTimestamp() {
    return Timestamp.from(instant);
  }

  public LocalDateTime toLocalDateTime() {
    return LocalDateTime.ofInstant(instant, zoneId);
  }
  public LocalDate toLocalDate() {
    return toLocalDateTime().toLocalDate();
  }
  public LocalTime toLocalTime() {
    return toLocalDateTime().toLocalTime();
  }

  @Override
  public String toString() {
    return instant.toString();
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
    return new Moment();
  }

  public static Moment from(final Instant instant) {
    return new Moment(instant);
  }

  public static Moment from(final Date date) {
    return from(date.toInstant());
  }

  public static Moment from(final Calendar calendar) {
    final ZoneId zoneValue = calendar.getTimeZone() == null ? ZoneId.systemDefault() : calendar.getTimeZone().toZoneId();
    return new Moment(calendar.toInstant(), zoneValue);
  }

  public static Moment from(long epochMills) {
    return new Moment(Instant.ofEpochMilli(epochMills));
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

  public static Moment from(final String value, final String pattern, ZoneId zone) {
    Objects.requireNonNull(pattern);
    Objects.requireNonNull(zone);
    return from(value, DateTimeFormatter.ofPattern(pattern).withZone(zone));
  }

  public static Moment from(final String value, final DateTimeFormatter formatter) {
    Objects.requireNonNull(value);
    Objects.requireNonNull(formatter);
    final ZoneId zone = formatter.getZone() == null ? ZoneId.systemDefault() : formatter.getZone();
    final TemporalAccessor accessor = formatter.parseBest(value, LocalDateTime::from, LocalDate::from, LocalTime::from);
    Instant instantValue;
    if (accessor instanceof LocalDateTime) {
      instantValue = ((LocalDateTime) accessor).atZone(zone).toInstant();
    } else if (accessor instanceof LocalDate) {
      instantValue = ((LocalDate) accessor).atStartOfDay(zone).toInstant();
    } else if (accessor instanceof LocalTime) {
      instantValue = ((LocalTime) accessor).atDate(LocalDate.now()).atZone(zone).toInstant();
    } else {
      throw new DateTimeException("Unknown DateTimeFormatter");
    }
    return new Moment(instantValue, zone);
  }
}
