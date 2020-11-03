package com.github.larkvii.cqrsframework.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 */
public final class NameValuePair implements Serializable {

  private String name;
  private String value;

  public NameValuePair() { }

  public NameValuePair(final String strName, final String strValue) {
    this.name = strName;
    this.value = strValue;
  }

  public NameValuePair(Map.Entry entry) {
    this.name = String.valueOf(entry.getKey());
    this.value = String.valueOf(entry.getValue());
  }

  public String getName() {
    return name;
  }

  public void setName(final String val) {
    this.name = val;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String val) {
    this.value = val;
  }

  public static NameValuePair of(final String name, final String value) {
    return new NameValuePair(name, value);
  }

  public static List<NameValuePair> of(final Map map) {
    if (map == null || map.isEmpty()) {
      return Collections.emptyList();
    }
    List<NameValuePair> result = new ArrayList<>();
    map.keySet().forEach(key -> result.add(NameValuePair.of(String.valueOf(key), String.valueOf(map.get(key)))));
    return result;
  }
}
