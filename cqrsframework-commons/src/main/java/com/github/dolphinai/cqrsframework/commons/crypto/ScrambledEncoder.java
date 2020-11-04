package com.github.dolphinai.cqrsframework.commons.crypto;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ScrambledEncoder {

  private static final byte[] XOR_KEYS = {
    (byte) 0xc1, (byte) 0x02, (byte) 0x35, (byte) 0x71,
    (byte) 0x82, (byte) 0xfc, (byte) 0x3a, (byte) 0xa9,
    (byte) 0x16, (byte) 0x9a, (byte) 0xf3, (byte) 0xb6,
    (byte) 0x01, (byte) 0x37, (byte) 0xd2, (byte) 0xd7
  };

  private int seed = 0xbcf92de;
  private int[] sequence;
  private File filename;
  private transient byte[] content;

  public ScrambledEncoder() {
  }

  public ScrambledEncoder(final File file) throws IOException {
    this();
    if (!file.exists()) {
      throw new FileNotFoundException(file.getPath());
    }
    this.filename = file;
  }

  public void setSeed(final int seedValue) {
    this.seed = seedValue;
    this.sequence = randomSequence(seed, 4096);
  }

  protected int[] getSequence() {
    if(sequence == null) {
      return null;
    }
    return Arrays.copyOf(sequence, sequence.length);
  }

  protected byte[] getContent() {
    if(content == null) {
      return null;
    }
    return Arrays.copyOf(content, content.length);
  }

  public void setContent(final byte[] value) {
    this.content = Arrays.copyOf(value, value.length);
  }

  public void open() throws IOException {
    byte[] content = new byte[getSequence().length];
    DataInputStream reader = new DataInputStream(new FileInputStream(filename));
    reader.readFully(content);
    reader.close();
    this.setContent(decode(getSequence(), content));
  }

  public void save() throws IOException  {
    save(this.filename);
  }

  public void save(final File file) throws IOException {
    Objects.requireNonNull(file);
    try (FileOutputStream outputStream = new FileOutputStream(file)) {
      save(outputStream);
    }
  }

  public void save(final OutputStream outputStream) throws IOException {
    byte[] data = encode(getSequence(), getContent());
    try (BufferedOutputStream output = new BufferedOutputStream(outputStream)) {
      output.write(data);
      output.flush();
    }
  }

  private static byte[] encode(final int[] sequence, final byte[] data) {
    byte[] dataBytes = xor(data, XOR_KEYS);
    int size = sequence.length;
    int dataSize = dataBytes.length;
    if (dataSize > size - 4) {
      throw new IllegalStateException("Invalid data size");
    }
    byte[] result = new byte[size];
    byte[] dataSizeArray = toByteArray(dataSize);
    result[sequence[0]] = dataSizeArray[0];
    result[sequence[1]] = dataSizeArray[1];
    result[sequence[2]] = dataSizeArray[2];
    result[sequence[3]] = dataSizeArray[3];
    for (int i = 0; i < dataSize; i++) {
      result[sequence[i + 4]] = dataBytes[i];
    }
    // fill.
    Random random = new Random();
    byte[] randBytes = new byte[1];
    for (int i = dataSize + 4; i < size; i++) {
      random.nextBytes(randBytes);
      result[sequence[i]] = randBytes[0];
    }
    return result;
  }

  private static byte[] decode(final int[] sequence, final byte[] data) {
    if (sequence == null || data == null) {
      throw new NullPointerException("the argument sequence or data is null");
    }
    int size = sequence.length;
    if (data.length != size) {
      throw new IllegalArgumentException("Invalid ByteArray size");
    }
    byte[] dataSizeArray = {data[sequence[0]], data[sequence[1]], data[sequence[2]], data[sequence[3]]};
    int dataSize = toInt32(dataSizeArray);
    if (dataSize > size - 4) {
      throw new IndexOutOfBoundsException("out of the data size: " + size);
    }
    byte[] result = new byte[dataSize];
    for (int i = 0; i < dataSize; i++) {
      result[i] = data[sequence[i + 4]];
    }
    return xor(result, XOR_KEYS);
  }

  protected static int[] randomSequence(int seed, int size) {
    int[] result = new int[size];
    Random random = new Random(seed);
    for (int i = 0; i < size; i++) {
      result[i] = i;
    }
    for (int i = 0; i < size; i++) {
      int j = Math.abs(random.nextInt() % size);
      int value = result[i];
      result[i] = result[j];
      result[j] = value;
    }
    return result;
  }

  public static byte[] toByteArray(int value) {
    byte[] src = new byte[4];
    src[0] = (byte) ((value >> 24) & 0xFF);
    src[1] = (byte) ((value >> 16) & 0xFF);
    src[2] = (byte) ((value >> 8) & 0xFF);
    src[3] = (byte) (value & 0xFF);
    return src;
  }

  public static int toInt32(final byte[] values) {
    int value;
    value = (int) (((values[0] & 0xFF) << 24)
      | ((values[1] & 0xFF) << 16)
      | ((values[2] & 0xFF) << 8)
      | (values[3] & 0xFF));
    return value;
  }

  public static byte[] xor(final byte[] data, final byte[] keys) {
    if (data == null || data.length == 0) {
      return data;
    }
    byte[] result = new byte[data.length];
    for (int i = 0; i < data.length; i++) {
      result[i] = (byte) (data[i] ^ keys[i % keys.length]);
    }
    return result;
  }
}
