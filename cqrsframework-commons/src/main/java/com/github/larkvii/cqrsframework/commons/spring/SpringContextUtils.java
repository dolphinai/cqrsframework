package com.github.larkvii.cqrsframework.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 */
public final class SpringContextUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(final ApplicationContext context) throws BeansException {
    applicationContext = context;
  }

  public static Object getBean(final String name) {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(final Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }

}
