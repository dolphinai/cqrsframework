package com.github.dolphinai.cqrsframework.commons.util;

import java.io.Serializable;
import java.util.UUID;

/**
 * Id Generator support.
 *
 * @param <T> Id Type.
 */
public interface IdGenerator<T extends Serializable> {

  /**
   * Gets the next id.
   *
   * @return New id
   */
  T next();

  /**
   * UUID generator.
   * @return IdGenerator instance
   */
  static IdGenerator<String> uuid() {
    return new UUIDGenerator();
  }

  /**
   * Snowflake generator.
   * @param workerId Worker id.
   * @return IdGenerator instance
   */
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
