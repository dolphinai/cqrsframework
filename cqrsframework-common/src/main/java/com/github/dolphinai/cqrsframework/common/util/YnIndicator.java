package com.github.dolphinai.cqrsframework.common.util;

/**
 */
public final class YnIndicator {

  private YnIndicator(){}

  public static final String NOT_APPLICABLE = "N/A";
  public static final String YES = "Y";
  public static final String NO = "N";

  public static String toYnString(final String value) {
    if (value == null) {
      return null;
    }
    return (YES.equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)) ? YES : NO;
  }

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

  public static void main(String[] args) {

    System.out.println(toYnString("N"));
    System.out.println(toYnString("true"));
    System.out.println(toYnString("Y"));
    System.out.println(toYnString(false));
  }
}
