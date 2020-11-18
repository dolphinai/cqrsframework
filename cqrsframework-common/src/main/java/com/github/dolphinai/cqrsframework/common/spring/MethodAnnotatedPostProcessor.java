package com.github.dolphinai.cqrsframework.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 */
@SuppressWarnings("unchecked")
public class MethodAnnotatedPostProcessor<T extends Annotation> implements BeanFactoryAware, BeanPostProcessor, DisposableBean {

  private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));
  private final Class<T> supportedClass;
  private BeanFactory beanFactory;
  private BiConsumer<Object, Method> methodProxyHandler;

  public MethodAnnotatedPostProcessor(final Class<T> supportedClazz) {
    this.supportedClass = supportedClazz;
  }

  public Class<T> getSupportedType() {
    return supportedClass;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  public void setMethodProxyHandler(final BiConsumer<Object, Method> proxyHandler) {
    this.methodProxyHandler = proxyHandler;
  }

  @Override
  public void setBeanFactory(@Nullable final BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessBeforeInitialization(@Nullable final Object bean, @Nullable final String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(@Nullable final Object bean, @Nullable final String beanName) throws BeansException {
    if (bean instanceof Annotation) {
      return bean;
    }
    final Set<Method> annotatedMethods = AnnotationHelper.getAnnotatedMethods(getSupportedType(), bean, nonAnnotatedClasses);
    if (annotatedMethods != null && !annotatedMethods.isEmpty()) {
      annotatedMethods.forEach(method -> {
        createProxy(bean, method);
      });
    }
    return bean;
  }

  protected void createProxy(final Object bean, final Method method) {
    if (this.methodProxyHandler != null) {
      this.methodProxyHandler.accept(bean, method);
    }
  }

  @Override
  public void destroy() throws Exception {
    this.nonAnnotatedClasses.clear();
  }
}
