package com.github.larkvii.cqrsframework.vertx;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

/**
 *
 */
@Slf4j
public final class RestResourceBindingProcessor implements BeanFactoryAware, BeanPostProcessor {

  private BeanFactory beanFactory;
  private final RestServerOptions restServerOptions;

  public RestResourceBindingProcessor(final RestServerOptions restServerOptions) {
    this.restServerOptions = restServerOptions;
  }

  @Override
  public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
    Class<?> beanClass = bean.getClass();
    if (AnnotationUtils.findAnnotation(bean.getClass(), Path.class) != null) {
      if (log.isDebugEnabled()) {
        log.debug("Path: name={}, class={}", beanName, beanClass);
      }
      restServerOptions.resourceFactories().add(new SpringResourceFactory(beanName, beanFactory, bean.getClass()));
    } else if (AnnotationUtils.findAnnotation(bean.getClass(), Provider.class) != null) {
      if (log.isDebugEnabled()) {
        log.debug("Provider: name={}, class={}", beanName, beanClass);
      }
      restServerOptions.providers().add(bean);
    }
    return bean;
  }
}
