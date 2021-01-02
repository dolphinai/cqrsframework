package com.github.dolphinai.cqrsframework.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 */
public class StringToArraySerializer extends JsonSerializer<String> {

  public String getDelimiter() {
    return "\n";
  }

  @Override
  public void serialize(final String value, final JsonGenerator generator, final SerializerProvider serializerProvider) throws IOException {
    String[] data;
    if (StringUtils.hasText(value)) {
      data = StringUtils.tokenizeToStringArray(value, getDelimiter());
    } else {
      data = new String[0];
    }
    generator.writeArray(data, 0, data.length);
  }
}
