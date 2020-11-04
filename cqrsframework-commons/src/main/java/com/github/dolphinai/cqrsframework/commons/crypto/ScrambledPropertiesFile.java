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

  /*
    public static void main(String[] args) {

    File  file = new File("E:/workspaces/password3.dat");
        try {
            ScrambledPropertiesFile spf = new ScrambledPropertiesFile();
            spf.setProperty("name", "data3");
            spf.setProperty("name1", "data3");
            spf.setProperty("name2", "dxxxxxxxxxxxxxxxxxxxxxxxata3");
            spf.setProperty("name3", "dataxxxxxxxxxxxxxxxxxxx3");
            spf.setProperty("name4", "dataxxxxxxxxxxxxxxxxx3");
            spf.setProperty("name5", "dataxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx3");
            spf.setProperty("name6", "datxxxxxxxxxxxxxxxxxxxa3");
            spf.save(file);

            ScrambledPropertiesFile spf2 = new ScrambledPropertiesFile(file);
            spf2.open();
            System.out.println(spf2.properties);
            //spf.setProperty("data", "data3");
            //spf.save();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   */
}
