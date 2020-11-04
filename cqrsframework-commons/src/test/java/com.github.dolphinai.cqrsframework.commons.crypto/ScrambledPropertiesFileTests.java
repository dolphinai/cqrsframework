package com.github.dolphinai.cqrsframework.commons.crypto;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 */
public class ScrambledPropertiesFileTests {

  @Test
  public void testSave() throws IOException {

    File  file = new File("password3.dat");
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
      System.out.println(spf2.getProperties());
      //spf.setProperty("data", "data3");
      //spf.save();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
