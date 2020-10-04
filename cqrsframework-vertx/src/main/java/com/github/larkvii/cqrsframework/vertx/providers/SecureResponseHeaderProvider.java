package com.github.larkvii.cqrsframework.vertx.providers;

import org.jboss.resteasy.spi.AsyncWriterInterceptor;
import org.jboss.resteasy.spi.AsyncWriterInterceptorContext;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

/**
 *
 */
@Provider
@SuppressWarnings("unchecked")
public class SecureResponseHeaderProvider implements AsyncWriterInterceptor {

  @Override
  public CompletionStage<Void> asyncAroundWriteTo(final AsyncWriterInterceptorContext context) {
    onPopulateHeaders(context.getHeaders());
    return context.asyncProceed();
  }

  @Override
  public void aroundWriteTo(final WriterInterceptorContext context) throws IOException, WebApplicationException {

  }

  protected void onPopulateHeaders(final MultivaluedMap headers) {
    Objects.requireNonNull(headers);
    headers.add("Content-Type", "application/json;charset=utf-8");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Strict-Transport-Security", "max-age=3600; includeSubDomains");
    headers.add("X-Frame-Options", "sameorigin");
    headers.add("X-Content-Type-Options", "nosniff");
    headers.add("X-XSS-Protection", "1; mode=block");
  }
}
