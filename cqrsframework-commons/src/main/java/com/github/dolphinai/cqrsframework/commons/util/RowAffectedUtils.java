package com.github.dolphinai.cqrsframework.commons.util;

import java.util.function.Supplier;

/**
 */
public final class RowAffectedUtils {

  private RowAffectedUtils() {
  }

  public static boolean single(final Supplier<Integer> sqlHandler) {
    return many(sqlHandler, 1);
  }

  public static boolean many(final Supplier<Integer> sqlHandler) {
    Integer affected = sqlHandler.get();
    return affected != null && affected.intValue() >= 1;
  }

  public static boolean many(final Supplier<Integer> sqlHandler, int expectedRows) {
    Integer affected = sqlHandler.get();
    return affected != null && affected.equals(expectedRows);
  }

}
