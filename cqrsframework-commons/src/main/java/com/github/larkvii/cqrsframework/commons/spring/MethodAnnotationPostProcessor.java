package com.github.larkvii.cqrsframework.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 */
@SuppressWarnings("unchecked")
public class MethodAnnotationPostProcessor<T extends Annotation> implements BeanFactoryAware, BeanPostProcessor, DisposableBean {

  private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));
  private final Class<T> supportedClass;
  private final BiConsumer<Object, Method> methodProxyHandler;
  private BeanFactory beanFactory;

  public MethodAnnotationPostProcessor(final Class<T> supportedClazz, final BiConsumer<Object, Method> methodProxyHandler) {
    this.supportedClass = supportedClazz;
    this.methodProxyHandler = methodProxyHandler;
  }

  public Class<T> getSupportedType() {
    return supportedClass;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  @Override
  public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
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

  protected void createProxy(Object bean, Method method) {
    this.methodProxyHandler.accept(bean, method);
  }

  @Override
  public void destroy() throws Exception {
    this.nonAnnotatedClasses.clear();
  }
}
