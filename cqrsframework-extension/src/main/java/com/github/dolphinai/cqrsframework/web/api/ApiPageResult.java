package com.github.dolphinai.cqrsframework.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
@Value
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResult<T> implements Serializable {

  // dataTables
  @With
  private List<T> data;
  @With
  private Integer draw;
  private long recordsTotal;
  private long recordsFiltered;
  @With
  private Integer pageSize;
  @With
  private Boolean more;

  public static <T> ApiPageResult<T> of(final List<T> data, final long total) {
    return new ApiPageResult<>(data, null, total, total, null, null);
  }

  public static <T> ApiPageResult<T> of(final List<T> data, long total, int pageSize, int pageNum) {
    return new ApiPageResult<>(data, null, total, total, pageSize, (pageNum * pageSize) < total);
  }

  public static <T> ApiPageResult<T> startOf(final List<T> data, long total, int pageSize, long start) {
    return new ApiPageResult<>(data, null, total, total, pageSize, (start + pageSize) < total);
  }

  public static <T> ApiPageResult<T> fail() {
    final ApiPageResult<T> result = of(Collections.emptyList(), 0);
    result.withMore(false);
    return result;
  }
}
