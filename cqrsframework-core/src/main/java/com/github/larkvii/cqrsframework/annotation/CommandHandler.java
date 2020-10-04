package com.github.larkvii.cqrsframework.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandHandler {

  String[] name() default {};

  @AliasFor("name")
  String[] value() default {};
}
