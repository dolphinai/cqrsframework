package com.github.dolphinai.cqrsframework.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 *
 */
public final class SpringContextUtils implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(@Nullable final ApplicationContext context) throws BeansException {
    Objects.requireNonNull(context);
    applicationContext = context;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static Object getBean(final String name) {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(final Class<T> clazz) {
    return applicationContext.getBean(clazz);
  }
}
