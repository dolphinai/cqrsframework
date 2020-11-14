package com.github.dolphinai.cqrsframework.commons.util;

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

  /**
   * Convert an Array to a Map object.
   *
   * @param values  Key1, Value1, Key2, Value2 ...
   * @param <T> Type
   * @return  Map instance
   */
  public static <T> Map<String, T> asMap(final T... values) {
    if (values == null || values.length == 0) {
      return Collections.emptyMap();
    }
    final Map<String, T> result = new HashMap<>();
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
