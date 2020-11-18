package com.github.dolphinai.cqrsframework.common.util;

import java.util.Optional;
import java.util.function.Supplier;

/**
 */
public final class DbUtils {

  private DbUtils() {

  }

  public static String asLikeStartValue(final String filter) {
    if (StringHelper.isEmpty(filter)) {
      return "%";
    }
    return "%" + filter;
  }

  public static String asLikeEndValue(final String filter) {
    if (StringHelper.isEmpty(filter)) {
      return "%";
    }
    return filter + "%";
  }
  public static String asLikeValue(final String filter) {
    if (StringHelper.isEmpty(filter)) {
      return "%";
    }
    return "%" + filter + "%";
  }

  public static RowAffected affected(final Supplier<Integer> sqlHandler) {
    return new RowAffected(sqlHandler.get());
  }

  public static final class RowAffected {

    private final int rows;

    RowAffected(final int affected) {
      this.rows = affected;
    }

    public boolean single() {
      return expect(1);
    }

    public <T> Optional<T> single(final Supplier<T> resultHandler) {
      if (single()) {
        return Optional.ofNullable(resultHandler.get());
      }
      return Optional.empty();
    }

    public boolean many() {
      return rows >= 1;
    }

    public <T> Optional<T> many(final Supplier<T> resultHandler) {
      if (many()) {
        return Optional.ofNullable(resultHandler.get());
      }
      return Optional.empty();
    }

    public boolean expect(int expectedRows) {
      return expectedRows == rows;
    }
  }
}
