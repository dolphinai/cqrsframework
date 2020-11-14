package com.github.dolphinai.cqrsframework.commons.util;

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

  public static boolean singleAffected(final Supplier<Integer> sqlHandler) {
    return manyAffected(sqlHandler, 1);
  }

  public static boolean manyAffected(final Supplier<Integer> sqlHandler) {
    Integer affected = sqlHandler.get();
    return affected != null && affected >= 1;
  }

  public static boolean manyAffected(final Supplier<Integer> sqlHandler, int expectedRows) {
    Integer affected = sqlHandler.get();
    return affected != null && affected.equals(expectedRows);
  }

}
