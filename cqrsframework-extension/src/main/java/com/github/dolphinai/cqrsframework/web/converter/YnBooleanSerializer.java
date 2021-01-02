package com.github.dolphinai.cqrsframework.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dolphinai.cqrsframework.common.util.YnIndicator;

import java.io.IOException;

/**
 */
public class YnBooleanSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(final String value, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
    generator.writeBoolean(YnIndicator.asBoolean(value));
  }

}
