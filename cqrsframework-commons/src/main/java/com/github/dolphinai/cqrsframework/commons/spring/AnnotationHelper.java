package com.github.dolphinai.cqrsframework.commons.spring;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public final class AnnotationHelper {

  private AnnotationHelper() {
  }

  public static boolean isBeanAnnotated(Method method, Class<? extends Annotation> annotationClass) {
    return AnnotatedElementUtils.hasAnnotation(method, annotationClass);
  }

  public static <T extends Annotation> Set<Method> getAnnotatedMethods(final Class<T> annotationClass, final Object bean, final Set<Class<?>> nonAnnotatedClasses) {
    Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
    String typeName = targetClass.getName();
    if (nonAnnotatedClasses.contains(targetClass)
      || typeName.startsWith("org.springframework")
      || typeName.startsWith("org.jboss")
      || typeName.startsWith("org.hibernate")
      || typeName.startsWith("java.")
      || typeName.startsWith("javax.")) {
      return null;
    }
    final Map<Method, T> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
      new MethodIntrospector.MetadataLookup<T>() {
        @Override
        public T inspect(Method method) {
          return AnnotatedElementUtils.getMergedAnnotation(method, annotationClass);
        }
      });
    if (annotatedMethods.isEmpty()) {
      nonAnnotatedClasses.add(targetClass);
    }
    return annotatedMethods.keySet();
  }
}
