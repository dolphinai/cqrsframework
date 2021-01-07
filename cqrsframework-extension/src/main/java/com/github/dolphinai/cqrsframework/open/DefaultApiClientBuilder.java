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
public class DefaultApiClientBuilder implements ApiClientBuilder {

  private final Feign.Builder builder;

  public DefaultApiClientBuilder(boolean isHystrix) {
    this(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES, isHystrix);
  }

  public DefaultApiClientBuilder(final PropertyNamingStrategy namingStrategy, boolean isHystrix) {
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

  public DefaultApiClientBuilder(final Supplier<ObjectMapper> mapperProvider, boolean isHystrix) {
    if(isHystrix) {
      this.builder = HystrixFeign.builder().encoder(new JacksonEncoder(mapperProvider.get())).decoder(new JacksonDecoder());
    } else {
      this.builder = Feign.builder().encoder(new JacksonEncoder(mapperProvider.get())).decoder(new JacksonDecoder());
    }
  }

  public DefaultApiClientBuilder(final Feign.Builder builder) {
    this.builder = builder;
  }

  @Override
  public Feign.Builder getBuilder() {
    return builder;
  }

  public static ApiClientBuilder instance() {
    return ApiClientFactoryHolder.instance;
  }

  private static class ApiClientFactoryHolder {
    private static final DefaultApiClientBuilder instance = new DefaultApiClientBuilder(false);
  }
}
