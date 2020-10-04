package com.github.larkvii.cqrsframework.examples.ui.config;

import com.github.larkvii.cqrsframework.spring.config.CQRSConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 */
@Configuration
@Import(CQRSConfiguration.class)
public class WebConfiguration {


}
