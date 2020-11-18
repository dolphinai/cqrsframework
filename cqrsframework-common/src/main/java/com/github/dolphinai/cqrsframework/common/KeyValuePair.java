package com.github.dolphinai.cqrsframework.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 */
public final class KeyValuePair implements Map.Entry<String, String> {

  private String key;
  private String value;

  public KeyValuePair(){}

  public KeyValuePair(final String sKey, final String sValue) {
    this.key = sKey;
    this.value = sValue;
  }

  public KeyValuePair(final Map.Entry<String, String> entry) {
    this(entry.getKey(), entry.getValue());
  }

  @Override
  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public String setValue(final String value) {
    return value;
  }

  @Override
  public String toString() {
    return "KeyValuePair{" +
      "key='" + key + '\'' +
      ", value='" + value + '\'' +
      '}';
  }

  public static KeyValuePair of(final String key, final Object value) {
    return new KeyValuePair(key, value == null ? null : value.toString());
  }

  public static List<KeyValuePair> of(final Map<String, String> map) {
    if (map == null || map.isEmpty()) {
      return Collections.emptyList();
    }
    final List<KeyValuePair> result = new ArrayList<>();
    map.entrySet().forEach(entry -> result.add(new KeyValuePair(entry)));
    return result;
  }
}
