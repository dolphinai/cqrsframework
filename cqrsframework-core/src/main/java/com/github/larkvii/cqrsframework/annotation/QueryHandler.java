package com.github.larkvii.cqrsframework.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryHandler {

}
