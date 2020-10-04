package com.github.larkvii.cqrsframework.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

  String[] name() default {};

  @AliasFor("name")
  String[] value() default {};

  String[] topic() default {};

  boolean transactional() default false;
}
