package com.github.larkvii.cqrsframework.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/api/v1")
@Slf4j
public final class SwaggerOpenApiDocument {

  private final OpenApiContext openApiContext;

  public SwaggerOpenApiDocument(final OpenApiContext openApiContext) {
    this.openApiContext = openApiContext;
  }

  @GET
  @Path("/swagger.json")
  @Produces(MediaType.APPLICATION_JSON)
  public String doc() {

    boolean pretty = false;
    OpenAPI oas = openApiContext.read();
    try {
      // 格式化oas的内容
      String result = pretty ? Json.pretty(oas) : Json.mapper().writeValueAsString(oas);
      return result;
    } catch (JsonProcessingException e) {
      log.error("Failed to get the OpenApi data", e);
    }
    return "{}";
  }
}
