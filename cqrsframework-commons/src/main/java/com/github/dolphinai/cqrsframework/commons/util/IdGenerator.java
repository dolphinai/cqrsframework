package com.github.dolphinai.cqrsframework.commons.util;

import java.io.Serializable;
import java.util.UUID;

public interface IdGenerator<T extends Serializable> {

  T next();

  static IdGenerator<String> uuid() {
    return new UUIDGenerator();
  }

  static IdGenerator<Long> snowflakeNumberId(long workerId) {
    return new SnowflakeIdGenerator(workerId);
  }

  final class UUIDGenerator implements IdGenerator<String> {
    @Override
    public String next() {
      String id = UUID.randomUUID().toString().toLowerCase();
      return id.substring(0, 8) + id.substring(9, 13) + id.substring(14, 18) + id.substring(19, 23) + id.substring(24);
    }
  }

  final class SnowflakeIdGenerator implements IdGenerator<Long> {

    private final SnowflakeKeyGenerator uidGenerator;

    public SnowflakeIdGenerator(long workerId) {
      this.uidGenerator = new SnowflakeKeyGenerator(workerId);
    }

    @Override
    public Long next() {
      return this.uidGenerator.nextId();
    }
  }
}
