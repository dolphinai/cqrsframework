package com.github.dolphinai.cqrsframework.commons.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

/**
 */
public class MethodAnnotatedInterceptor<T extends Annotation> implements MethodInterceptor {

  private final Class<T> annotationType;
  private BiConsumer<T, MethodInvocation> preHandler;

  public MethodAnnotatedInterceptor(final Class<T> type) {
    this.annotationType = type;
  }

  public void setPreHandler(final BiConsumer<T, MethodInvocation> methodPreHandler) {
    this.preHandler = methodPreHandler;
  }

  @Override
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    T annotation = getAnnotation(invocation.getMethod());
    if (annotation != null) {
      onPreHandler(annotation, invocation);
    }
    return invocation.proceed();
  }

  protected void onPreHandler(final T annotationObject, final MethodInvocation invocation) {
    if(preHandler != null) {
      preHandler.accept(annotationObject, invocation);
    }
  }

  @Nullable
  protected T getAnnotation(final Method method) {
    if (method.isAnnotationPresent(annotationType)) {
      return method.getAnnotation(annotationType);
    }
    return null;
  }
}
