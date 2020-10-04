package com.github.larkvii.cqrsframework.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;

import javax.ws.rs.core.Application;

/**
 *
 */
@Slf4j
public abstract class AbstractServerVerticle extends AbstractVerticle {

  private final Application restApplication;
  private final RestServerOptions restServerOptions;

  public AbstractServerVerticle(Application application, RestServerOptions restServerOptions) {
    this.restApplication = application;
    this.restServerOptions = restServerOptions;
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {

    final Router router = Router.router(vertx);
    // register
    onRegisterRouterHandlers(router, restApplication, restServerOptions);
    // start
    onCreateHttpServer(startPromise, router, restServerOptions.getPort());
  }

  protected void onRegisterRouterHandlers(final Router router, Application application, RestServerOptions serverOptions) {
    VertxResteasyDeployment deployment = new VertxResteasyDeployment();
    deployment.setAddCharset(true);
    deployment.setApplication(application);
    deployment.setResourceFactories(serverOptions.resourceFactories());
    deployment.setProviders(serverOptions.providers());
    deployment.start();

    VertxRequestHandler restRequestHandler = new VertxRequestHandler(vertx, deployment);

    // Start the front end server using the Jax-RS controller
    router.route("/api/*").handler(routingContext -> {
      restRequestHandler.handle(routingContext.request());
    });

    // 配置静态文件
    router.route("/*").handler(StaticHandler.create());
    // Swagger
    if (serverOptions.swaggerEnable()) {
      router.route("/swagger-ui/*").handler(StaticHandler.create("META-INF/swagger-ui/"));
    }
  }

  protected void onCreateHttpServer(final Promise<Void> startPromise, final Router router, int port) {
    vertx.createHttpServer().requestHandler(router).listen(port, ar -> {
      if (ar.failed()) {
        startPromise.fail(ar.cause());
      } else {
        log.info("HttpServer started on port {}", ar.result().actualPort());
        startPromise.complete();
      }
    });
  }
}
