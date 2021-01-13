package com.github.dolphinai.cqrsframework.open;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import feign.Feign;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.io.IOException;
import java.util.function.Supplier;

/**
 */
public class JacksonApiClientBuilder extends ApiClientBuilder {

  public JacksonApiClientBuilder(boolean isHystrix) {
    this(PropertyNamingStrategy.SNAKE_CASE, isHystrix);
  }

  public JacksonApiClientBuilder(final PropertyNamingStrategy namingStrategy, boolean isHystrix) {
    this(()-> {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.setPropertyNamingStrategy(namingStrategy);
      mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
          jsonGenerator.writeString("");
        }
      });
      return mapper;
    }, isHystrix);
  }

  public JacksonApiClientBuilder(final Supplier<ObjectMapper> mapperProvider, boolean isHystrix) {
    super(createBuilder(mapperProvider, isHystrix));
  }

  private static Feign.Builder createBuilder(final Supplier<ObjectMapper> mapperProvider, boolean isHystrix) {
    if(isHystrix) {
      return HystrixFeign.builder().encoder(new JacksonEncoder(mapperProvider.get())).decoder(new JacksonDecoder());
    } else {
      return Feign.builder().encoder(new JacksonEncoder(mapperProvider.get())).decoder(new JacksonDecoder());
    }
  }
}
