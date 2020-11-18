package com.github.dolphinai.cqrsframework.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 */
public final class NameValuePair implements Serializable {

  private final String name;
  private final String value;

  public NameValuePair(final String name, final String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "NameValuePair{" +
      "name='" + name + '\'' +
      ", value='" + value + '\'' +
      '}';
  }

  public static NameValuePair of(final String name, final Object value) {
    return new NameValuePair(name, value == null ? null : value.toString());
  }

  public static List<NameValuePair> of(final Map<String, String> map) {
    if (map == null || map.isEmpty()) {
      return Collections.emptyList();
    }
    final List<NameValuePair> result = new ArrayList<>();
    map.entrySet().forEach(entry -> result.add(new NameValuePair(entry.getKey(), entry.getValue())));
    return result;
  }
}
