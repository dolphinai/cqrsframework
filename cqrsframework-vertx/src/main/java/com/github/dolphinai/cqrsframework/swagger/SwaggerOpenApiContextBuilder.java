package com.github.dolphinai.cqrsframework.swagger;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.GenericOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.OpenApiContextLocator;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Application;

/**
 *
 */
@SuppressWarnings("unchecked")
@Slf4j
final class SwaggerOpenApiContextBuilder<T extends SwaggerOpenApiContextBuilder> extends GenericOpenApiContextBuilder<SwaggerOpenApiContextBuilder> {

  private final Application application;

  public SwaggerOpenApiContextBuilder(final Application application) {
    this.application = application;
  }

  @Override
  public OpenApiContext buildContext(final boolean init) throws OpenApiConfigurationException {
    if (StringUtils.isBlank(this.ctxId)) {
      this.ctxId = "openapi.context.id.default";
    }
    if(log.isDebugEnabled()) {
      log.debug("buildContext, context.id={}", this.ctxId);
    }
    OpenApiContext ctx = OpenApiContextLocator.getInstance().getOpenApiContext(this.ctxId);
    if (ctx == null) {
      OpenApiContext rootCtx = OpenApiContextLocator.getInstance().getOpenApiContext("openapi.context.id.default");

      JaxrsOpenApiContext context = new JaxrsOpenApiContext<>();
      ctx = context.app(this.application).openApiConfiguration(getOpenApiConfiguration()).id(this.ctxId).parent(rootCtx);
      if (ctx.getConfigLocation() == null && this.configLocation != null) {
        ((GenericOpenApiContext) ctx).configLocation(this.configLocation);
      }

      if (((GenericOpenApiContext) ctx).getResourcePackages() == null && this.resourcePackages != null) {
        ((GenericOpenApiContext) ctx).resourcePackages(this.resourcePackages);
      }

      if (((GenericOpenApiContext) ctx).getResourceClasses() == null && this.resourceClasses != null) {
        ((GenericOpenApiContext) ctx).resourceClasses(this.resourceClasses);
      }

      if (init) {
        ctx.init();
      }
    }

    return ctx;
  }
}
