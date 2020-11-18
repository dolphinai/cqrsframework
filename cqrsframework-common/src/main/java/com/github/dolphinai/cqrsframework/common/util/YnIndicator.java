package com.github.dolphinai.cqrsframework.common.util;

/**
 */
public final class YnIndicator {

  private YnIndicator(){}

  public static final String NOT_APPLICABLE = "N/A";
  public static final String YES = "Y";
  public static final String NO = "N";

  public static String toYnString(final boolean value) {
    return value ? YES : NO;
  }

  public static boolean asBoolean(final String yesNo) {
    return Boolean.TRUE.equals(asBoolean(yesNo, false));
  }

  public static Boolean asBoolean(final String yesOrNo, final boolean defaultValue) {
    if (yesOrNo == null) {
      return defaultValue;
    }
    return YES.equalsIgnoreCase(yesOrNo);
  }
}
