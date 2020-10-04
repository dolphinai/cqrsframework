package com.github.larkvii.cqrsframework.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AggregateId {

}
