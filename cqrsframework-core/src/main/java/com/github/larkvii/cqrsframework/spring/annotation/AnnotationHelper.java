package com.github.larkvii.cqrsframework.spring.annotation;

import lombok.experimental.UtilityClass;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

final class AnnotationHelper {

  private AnnotationHelper() {
  }

  public static boolean isBeanAnnotated(Method method, Class<? extends Annotation> annotationClass) {
    return AnnotatedElementUtils.hasAnnotation(method, annotationClass);
  }

  public static <T extends Annotation> Map<Method, T> getAnnotatedMethods(final Class<T> annotationClass, final Object bean, final Set<Class<?>> nonAnnotatedClasses) {
    Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
    final Map<Method, T> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
      new MethodIntrospector.MetadataLookup<T>() {
        @Override
        public T inspect(Method method) {
          T handlerMethod = AnnotatedElementUtils.getMergedAnnotation(method, annotationClass);
          return handlerMethod;
        }
      });
    if (annotatedMethods.isEmpty()) {
      nonAnnotatedClasses.add(targetClass);
    }
    return annotatedMethods;
  }

}
