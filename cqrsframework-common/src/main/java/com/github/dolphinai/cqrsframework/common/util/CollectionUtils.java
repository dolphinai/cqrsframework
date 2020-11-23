package com.github.dolphinai.cqrsframework.common.util;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 */
@SuppressWarnings("unchecked")
public final class CollectionUtils {

  private CollectionUtils(){}

  public static <T> List<T> filter(final Collection<T> source, Predicate<T> filterPredicate) {
    Objects.requireNonNull(source);
    return source.stream().filter(filterPredicate).collect(Collectors.toList());
  }

  public static <S, R> List<R> transform(final Collection<S> source, final Function<S, R> transformer) {
    Objects.requireNonNull(source);
    Objects.requireNonNull(transformer);
    return source.stream().map(s -> transformer.apply(s)).collect(Collectors.toList());
  }

  public static <T> boolean isEmpty(final Collection<T> list) {
    return list == null || list.isEmpty();
  }

  public static <T> boolean isEmpty(final T[] array) {
    return array == null || array.length == 0;
  }

  /**
   * Get first element from the collection.
   *
   * @param list source
   * @param <T> Generic type
   * @return First element
   */
  public static <T> T firstElement(final Collection<T> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.iterator().next();
  }

  public static <T> Map<String, Object> asMap(final String matrixValues) {
    if (StringHelper.isBlank(matrixValues)) {
      return Collections.emptyMap();
    }
    final String[] matrixArray = StringUtils.tokenizeToStringArray(matrixValues, ";");
    final Map<String, Object> result = new HashMap<>();
    for (String item : matrixArray) {
      if (item.indexOf("=") > -1) {
        int index = item.indexOf("=");
        String key = item.substring(0, index);
        // value
        String value = item.substring(index + 1);
        if (value.indexOf(",") > -1) {
          String[] values = StringUtils.tokenizeToStringArray(value, ",");
          result.put(key, values);
        } else {
          if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            result.put(key, Boolean.valueOf(value));
          } else {
            result.put(key, value);
          }
        }
      } else {
        result.put(item, null);
      }
    }
    return result;
  }

  /**
   * Convert an Array to a Map object.
   *
   * @param values  Key1, Value1, Key2, Value2 ...
   * @param <T> Type
   * @return  Map instance
   */
  public static <T> Map<String, T> asMap(final T[] values) {
    if (isEmpty(values)) {
      return Collections.emptyMap();
    }
    final Map<String, T> result = new HashMap<>(values.length);
    for (int i = 0; i < values.length; ) {
      String key = String.valueOf(values[i]);
      T value = null;
      if (i + 1 < values.length) {
        value = values[i + 1];
      }
      result.put(key, value);
      i = i + 2;
    }
    return result;
  }
}
