package com.github.larkvii.cqrsframework.commons.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 */
public class Moment {

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
    this.zoneId = zoneValue;
    return this;
  }

  public Instant value() {
    return instant;
  }

  public Moment plusMillis(long millis) {
    return new Moment(instant.plusMillis(millis));
  }
  public Moment plusSeconds(long seconds) {
    return new Moment(instant.plusSeconds(seconds));
  }
  public Moment plus(long value, final ChronoUnit unit) {
    return new Moment(instant.plus(value, unit));
  }
  public Moment plus(final Duration duration) {
    return new Moment(instant.plus(duration));
  }
  public Moment minus(final Duration duration) {
    return new Moment(instant.minus(duration));
  }

  public String format(final String pattern) {
    return format(DateTimeFormatter.ofPattern(pattern).withZone(zoneId));
  }

  public String format(final DateTimeFormatter formatter) {
    return formatter.format(instant);
  }

  public long toTimeMillis() {
    return instant.toEpochMilli();
  }

  public Date toDate() {
    return Date.from(instant);
  }

  public Calendar toCalendar() {
    return GregorianCalendar.from(ZonedDateTime.ofInstant(instant, zoneId));
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

  @Override
  public String toString() {
    return instant.toString();
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
    return from(calendar.toInstant());
  }

  public static Moment from(long epochMills) {
    return new Moment(Instant.ofEpochMilli(epochMills));
  }

  public static Moment from(final String value) {
    Objects.requireNonNull(value);
    ZoneId zone = ZoneId.systemDefault();
    Instant result;
    if (value.indexOf(":") > -1) {
      final LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
      result = localDateTime.atZone(zone).toInstant();
    } else {
      final LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
      result = localDate.atStartOfDay(zone).toInstant();
    }
    return new Moment(result, zone);
  }

  public static Moment from(final String value, final String pattern) {
    return from(value, pattern, ZoneId.systemDefault());
  }

  public static Moment from(final String value, final String pattern, ZoneId zone) {
    Objects.requireNonNull(value);
    Objects.requireNonNull(pattern);
    Instant result;
    if (pattern.indexOf("HH") == 1 || pattern.indexOf("hh") == 1) {
      final LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
      result = localDateTime.atZone(zone).toInstant();
    } else {
      final LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
      result = localDate.atStartOfDay(zone).toInstant();
    }
    return new Moment(result, zone);
  }
}
