package com.github.dolphinai.cqrsframework.core.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public final class DomainFactory implements ApplicationContextAware, InitializingBean, DisposableBean {

  private ApplicationContext applicationContext;
  private EventGateway eventGateway;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    this.eventGateway = applicationContext.getBean(EventGateway.class);
  }

  public void apply(Object... events) {
    eventGateway.publish(events);
  }

  public static <T> T load(Class<T> domainType, String aggregateId) {

    return null;
  }

  @Override
  public void destroy() throws Exception {

  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}
