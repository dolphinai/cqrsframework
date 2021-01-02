package com.github.dolphinai.cqrsframework.common.util;

import java.util.*;
import java.util.function.Consumer;

/**
 */
public final class CriteriaMap implements Map<String, Object> {

  private final Map<String,Object> internalMap;

  public CriteriaMap(final Map<String, Object> map) {
    this.internalMap = map;
  }

  @Override
  public int size() {
    return internalMap.size();
  }

  @Override
  public boolean isEmpty() {
    return internalMap.isEmpty();
  }

  @Override
  public boolean containsKey(final Object key) {
    return internalMap.containsKey(key);
  }

  @Override
  public boolean containsValue(final Object value) {
    return internalMap.containsValue(value);
  }

  @Override
  public Object get(final Object key) {
    if (!internalMap.containsKey(key)) {
      return null;
    }
    return internalMap.get(key);
  }

  @Override
  public Object put(final String key, final Object value) {
    Object result = internalMap.put(key, value);
    return result;
  }

  @Override
  public Object remove(final Object key) {
    return internalMap.remove(key);
  }

  @Override
  public void putAll(final Map<? extends String, ?> m) {
    if (m != null) {
      this.internalMap.putAll(m);
    }
  }

  @Override
  public void clear() {
    this.internalMap.clear();
  }

  @Override
  public Set<String> keySet() {
    return internalMap.keySet();
  }

  @Override
  public Collection<Object> values() {
    return internalMap.values();
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return internalMap.entrySet();
  }

  public CriteriaMap with(final String key, final Object value) {
    if (key != null && value != null) {
      this.put(key, value);
    }
    return this;
  }

  public String getStringValue(final String key) {
    Object value = get(key);
    return value == null ? null : value.toString();
  }

  public Integer getIntegerValue(final String key) {
    Object value = get(key);
    if (value == null) {
      return null;
    } else if (value instanceof Integer) {
      return (Integer) value;
    } else {
      return Integer.valueOf(value.toString());
    }
  }

  public Boolean getBooleanValue(final String key) {
    Object value = get(key);
    if (value == null) {
      return null;
    } else if (value instanceof Boolean) {
      return (Boolean) value;
    } else {
      return Boolean.valueOf(value.toString());
    }
  }

  public CriteriaMap handle(final String key, final Consumer<Object> valueHandler) {
    if (containsKey(key)) {
      Object value = get(key);
      valueHandler.accept(value);
    }
    return this;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    this.entrySet().forEach(entry -> {
      builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
    });
    if(builder.length() > 0) {
      builder.setLength(builder.length() - 1);
    }
    return builder.toString();
  }

  public static CriteriaMap of() {
    return new CriteriaMap(new HashMap<>());
  }

  public static CriteriaMap singletonOf(final String key, final Object value) {
    Objects.requireNonNull(key);
    return new CriteriaMap(Collections.singletonMap(key, value));
  }
}
