package com.github.dolphinai.cqrsframework.commons.util;

import com.github.dolphinai.cqrsframework.commons.AppRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Generate 64bits uid (long), default allocated as below:<br>
 * <pre>{@code
 * +------+----------------------+----------------+-----------+
 * | sign |     delta seconds    | worker node id | sequence  |
 * +------+----------------------+----------------+-----------+
 *   1bit          28bits              24bits         11bits
 * }</pre>
 * <p>
 * You can also specified the bits by Spring property setting.
 * <li>snowflake.timeBits: default as 32
 * <li>snowflake.workerBits: default as 8
 * <li>snowflake.dbBits: default as 8
 * <li>snowflake.seqBits: default as 15
 * <li>snowflake.baseDate: Epoch date string format 'yyyy-MM-dd'. Default as '2018-07-01'<p>
 *
 * <b>Note that:</b> The total bits must be 64 -1
 *
 * @author yutianbao@baidu, john.huang
 */
public final class SnowflakeKeyGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(SnowflakeKeyGenerator.class);

  public static final int TOTAL_BITS = 1 << 6;

  /**
   * Bits for [sign-> second-> workId-> sequence]
   */
  private final int signBits = 1;

  // the start time, default is 2018-7-1
  private final long baseEpoch = 1530374400000L;

  // delta seconds
  private int timeBits = 32;

  // worker node id bits
  private int workerBits = 8;

  // sequence bits
  private int seqBits = 15;

  /**
   * Volatile fields caused by nextId()
   */
  private long sequence = 0L;
  private long lastSecond = -1L;

  private final long maxDeltaSeconds;
  private final long maxWorkerId;
  private final long maxSequence;

  private final int timestampShift;
  private final int workerIdShift;

  private final long workerId;

  /**
   * Initialize a uid generator with specified settings.
   *
   * @param workerId   specify an id for the worker, the worker is the app to generate uid
   * @param timeBits   time bits length
   * @param workerBits worker id bits length
   * @param seqBits    sequence bits length
   */
  public SnowflakeKeyGenerator(long workerId, int timeBits, int workerBits, int seqBits) {
    this.workerId = workerId;

    this.timeBits = timeBits;
    this.workerBits = workerBits;
    this.seqBits = seqBits;

    int allocateTotalBits = signBits + timeBits + workerBits + seqBits;

    // "allocate not enough 64 bits"
    if (allocateTotalBits != TOTAL_BITS) {
      LOGGER.error("allocate not enough 64 bits");
    }

    // initialize max value
    this.maxDeltaSeconds = ~(-1L << timeBits);
    this.maxWorkerId = ~(-1L << workerBits);
    this.maxSequence = ~(-1L << seqBits);

    if (workerId > maxWorkerId) {
      LOGGER.error("workerId exceed the max value" + maxWorkerId);
    }

    // initialize shift
    this.timestampShift = workerBits + seqBits;
    this.workerIdShift = seqBits;
  }

  public SnowflakeKeyGenerator(long workerId) {
    this(workerId, 28, 24, 11);
  }

  public long getUID() throws AppRuntimeException {
    try {
      return nextId();
    } catch (Exception e) {
      LOGGER.error("Generate unique id exception. ", e);
      throw new AppRuntimeException(e);
    }
  }

  public String parseUID(long uid) {
    // parse UID
    long sequence = (uid << (TOTAL_BITS - seqBits)) >>> (TOTAL_BITS - seqBits);
    long workerId = (uid << (timeBits + signBits)) >>> (TOTAL_BITS - workerBits);
    long deltaSeconds = uid >>> (workerBits + seqBits);

    Date thatTime = new Date(TimeUnit.SECONDS.toMillis(baseEpoch + deltaSeconds));
    String thatTimeStr = DateUtils.toDateTimeString(thatTime);

    // format as string
    return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
      uid, thatTimeStr, workerId, sequence);
  }

  /**
   * Get UID
   *
   * @return UID
   * @throws AppRuntimeException in the case: Clock moved backwards; Exceeds the max timestamp
   */
  protected synchronized long nextId() {
    long currentSecond = getCurrentSecond();

    // Clock moved backwards, refuse to generate uid
    if (currentSecond < lastSecond) {
      long refusedSeconds = lastSecond - currentSecond;
      throw new AppRuntimeException("Clock moved backwards. Refusing for " + refusedSeconds + " seconds");
    }

    // At the same second, increase sequence
    if (currentSecond == lastSecond) {
      sequence = (sequence + 1) & maxSequence;
      // Exceed the max sequence, we wait the next second to generate uid
      if (sequence == 0) {
        currentSecond = getNextSecond(lastSecond);
      }

      // At the different second, sequence restart from zero
    } else {
      sequence = 0L;
    }

    lastSecond = currentSecond;

    // Allocate bits for UID
    long deltaSeconds = currentSecond - baseEpoch;
    return (deltaSeconds << timestampShift) | (workerId << workerIdShift) | sequence;
  }

  /**
   * Get next millisecond
   */
  private long getNextSecond(long lastTimestamp) {
    long timestamp = getCurrentSecond();
    while (timestamp <= lastTimestamp) {
      timestamp = getCurrentSecond();
    }

    return timestamp;
  }

  /**
   * Get current second
   */
  private long getCurrentSecond() {
    long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    if (currentSecond - baseEpoch > maxDeltaSeconds) {
      throw new AppRuntimeException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
    }

    return currentSecond;
  }

  /**
   * Allocate bits for UID according to delta seconds & workerId & sequence<br>
   * <b>Note that: </b>The highest bit will always be 0 for sign
   *
   * @param deltaSeconds
   * @param workerId
   * @param sequence
   * @return
   */
  public long allocate(long deltaSeconds, long workerId, long dbId, long sequence) {
    return (deltaSeconds << timestampShift) | (workerId << workerIdShift) | sequence;
  }

  public void setTimeBits(int timeBits) {
    if (timeBits > 0) {
      this.timeBits = timeBits;
    }
  }

  public void setWorkerBits(int workerBits) {
    if (workerBits > 0) {
      this.workerBits = workerBits;
    }
  }

  public void setSeqBits(int seqBits) {
    if (seqBits > 0) {
      this.seqBits = seqBits;
    }
  }

  /**
   * Get the worker id by using the last x bits of the local ip address
   *
   * @throws AppRuntimeException
   */
  public static long getWorkerIdByIP(int bits) throws AppRuntimeException {
    int shift = 64 - bits;
    String serverAddr = MachineUtils.getHostAddress();
    if (serverAddr == null) {
      throw new AppRuntimeException("UnknownHostException");
    }
    long ip = IpUtils.toIpV4Long(serverAddr);
    return (ip << shift) >>> shift;
  }
}
