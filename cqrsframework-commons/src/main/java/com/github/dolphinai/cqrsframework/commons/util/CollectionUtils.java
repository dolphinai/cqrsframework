package com.github.dolphinai.cqrsframework.commons.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 */
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

  public static <T> List<T> addAll(final List<T> source, final T[] values) {
    Objects.requireNonNull(source);
    Objects.requireNonNull(values);
    for (T item : values) {
      source.add(item);
    }
    return source;
  }

}
