package com.github.dolphinai.cqrsframework.open;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.io.IOException;
import java.util.function.Supplier;

/**
 */
public class ApiClientFactoryImpl implements ApiClientFactory {

  private final HystrixFeign.Builder builder;

  public ApiClientFactoryImpl() {
    this(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
  }

  public ApiClientFactoryImpl(final PropertyNamingStrategy namingStrategy) {
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
    });
  }

  public ApiClientFactoryImpl(final Supplier<ObjectMapper> mapperProvider) {
    this.builder = HystrixFeign.builder().encoder(new JacksonEncoder(mapperProvider.get())).decoder(new JacksonDecoder());
  }

  @Override
  public HystrixFeign.Builder getBuilder() {
    return builder;
  }

  public static ApiClientFactory instance() {
    return ApiClientFactoryHolder.instance;
  }

  private static class ApiClientFactoryHolder {
    private static final ApiClientFactoryImpl instance = new ApiClientFactoryImpl();
  }
}
