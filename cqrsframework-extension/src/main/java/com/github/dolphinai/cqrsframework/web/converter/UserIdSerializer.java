package com.github.dolphinai.cqrsframework.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.function.Function;

/**
 */
public class UserIdSerializer extends JsonSerializer<String> {

  private UserIdConverter converter;

  @Autowired
  @Lazy
  public void setConverter(final UserIdConverter converter) {
    this.converter = converter;
  }

  @Override
  public void serialize(final String value, final JsonGenerator generator, final SerializerProvider provider) throws IOException {
    String name;
    if (StringHelper.isBlank(value)) {
      name = StringHelper.EMPTY;
    } else {
      name = converter.apply(value);
    }

    generator.writeString(name);
  }

  public interface UserIdConverter extends Function<String, String > {

  }
}
