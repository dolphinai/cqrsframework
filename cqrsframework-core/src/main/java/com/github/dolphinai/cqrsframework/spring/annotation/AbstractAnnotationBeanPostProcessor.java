package com.github.dolphinai.cqrsframework.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class AbstractAnnotationBeanPostProcessor implements BeanFactoryAware, BeanPostProcessor {

  private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap(64));
  private BeanFactory beanFactory;

  @Override
  public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
    if (bean instanceof Annotation) {
      return bean;
    }

    Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
    Map<Method, Class<? extends Annotation>> annotatedMethods = getAnnotatedMethods(targetClass, nonAnnotatedClasses, getSupportedMessageTypes());
    if (annotatedMethods != null && !annotatedMethods.isEmpty()) {
      annotatedMethods.forEach((method, annotation) -> {
        createProxy(bean, method, annotation);
      });
    }
    return bean;
  }

  private Map<Method, Class<? extends Annotation>> getAnnotatedMethods(final Class<?> beanTargetClass, final Set<Class<?>> nonAnnotatedClass, final Set<Class<? extends Annotation>> supportedAnnotationTypes) {
    String typeName = beanTargetClass.getName();
    if (nonAnnotatedClasses.contains(beanTargetClass)
      || typeName.startsWith("org.springframework")
      || typeName.startsWith("java.")
      || typeName.startsWith("javax.")) {
      return null;
    }

    final Map<Method, Class<? extends Annotation>> annotatedMethods = MethodIntrospector.selectMethods(beanTargetClass,
      new MethodIntrospector.MetadataLookup<Class<? extends Annotation>>() {
        @Override
        public Class<? extends Annotation> inspect(Method method) {
          val iter = supportedAnnotationTypes.iterator();
          while (iter.hasNext()) {
            Class<? extends Annotation> clazz = iter.next();
            if (AnnotationUtils.findAnnotation(method, clazz) != null) {
              return clazz;
            }
          }
          return null;
        }
      });
    if (annotatedMethods.isEmpty()) {
      nonAnnotatedClass.add(beanTargetClass);
    }
    return annotatedMethods;
  }

  protected abstract Set<Class<? extends Annotation>> getSupportedMessageTypes();

  protected abstract void createProxy(Object bean, Method method, Class<? extends Annotation> annotation);
}
