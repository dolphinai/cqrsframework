package com.github.dolphinai.cqrsframework.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResult<T> implements Serializable {

  // dataTables
  private List<T> data;
  private Integer draw;
  private long recordsTotal;
  private long recordsFiltered;
  private String error;
  private Integer size;
  private Boolean more;

  public ApiPageResult(List<T> data, long total) {
    this(data, total, null, null);
  }

  public ApiPageResult(List<T> data, long total, Integer pageSize, Boolean more) {
    this.data = data;
    this.recordsTotal = total;
    this.recordsFiltered = total;
    this.size = pageSize;
    this.more = more;
  }

  public ApiPageResult<T> with(int pageSize, int pageNum) {
    this.setSize(pageSize);
    return withMore((pageNum * pageSize) < this.recordsTotal);
  }
  public ApiPageResult<T> withMore(Boolean value) {
    this.more = value;
    return this;
  }
  public ApiPageResult<T> withMore(int pageSize, int startPosition) {
    this.setSize(pageSize);
    return withMore((startPosition + pageSize) < this.recordsTotal);
  }

  public static final <T> ApiPageResult<T> of(final List<T> data, final long total) {
    return new ApiPageResult<>(data, total, null, null);
  }

  public static final <T> ApiPageResult<T> fail() {
    return fail(null);
  }

  public static final <T> ApiPageResult<T> fail(final String errorMessage) {
    final ApiPageResult<T> result = new ApiPageResult<>(Collections.emptyList(), 0);
    result.setMore(false);
    result.setError(errorMessage);
    return result;
  }
}
