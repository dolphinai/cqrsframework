package com.github.dolphinai.cqrsframework.core.event.repository;

import java.util.Map;

/**
 *
 */
public interface DomainEventEntry {

  String getEventId();

  Long getTimestamp();

  String getAggregateId();

  String getAggregateType();

  Class<?> getPayloadType();

  Object getPayload();

  Map<String, Object> getMetadata();

}
