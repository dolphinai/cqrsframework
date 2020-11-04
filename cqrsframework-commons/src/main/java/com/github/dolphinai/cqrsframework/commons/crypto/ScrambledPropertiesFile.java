package com.github.dolphinai.cqrsframework.commons.crypto;

import java.io.*;
import java.util.Properties;

public class ScrambledPropertiesFile extends ScrambledEncoder {

  private Properties properties;

  public ScrambledPropertiesFile() {
    this.properties = new Properties();
  }

  public ScrambledPropertiesFile(File file) throws IOException {
    super(file);
  }

  protected Properties getProperties() {
    return properties;
  }

  public String getProperty(String name) {
    return this.properties.getProperty(name);
  }

  public void setProperty(String name, String value) {
    this.properties.setProperty(name, value);
  }

  public void removeProperty(String name) {
    this.properties.remove(name);
  }

  @Override
  public void open() throws IOException {
    super.open();
    ByteArrayInputStream byteStream = new ByteArrayInputStream(getContent());
    this.properties = new Properties();
    this.properties.load(byteStream);
  }

  @Override
  public void save(final OutputStream outputStream) throws IOException {
    try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      this.properties.store(output, null);
      output.flush();
      this.setContent(output.toByteArray());
    }
    super.save(outputStream);
  }
}
