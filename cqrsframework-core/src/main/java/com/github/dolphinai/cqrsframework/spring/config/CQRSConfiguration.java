package com.github.dolphinai.cqrsframework.spring.config;

import com.github.dolphinai.cqrsframework.core.MessageHandlerInterceptor;
import com.github.dolphinai.cqrsframework.core.MessageInterceptorFactory;
import com.github.dolphinai.cqrsframework.core.command.CommandGatewayImpl;
import com.github.dolphinai.cqrsframework.core.event.*;
import com.github.dolphinai.cqrsframework.core.command.CommandBus;
import com.github.dolphinai.cqrsframework.core.command.CommandBusImpl;
import com.github.dolphinai.cqrsframework.core.command.CommandGateway;
import com.github.dolphinai.cqrsframework.spring.annotation.AggregateContextRefreshedListener;
import com.github.dolphinai.cqrsframework.spring.annotation.EventHandlerAnnotationBeanPostProcessor;
import com.github.dolphinai.cqrsframework.spring.interceptors.CommandMessageHandlerAdapter;
import com.github.dolphinai.cqrsframework.spring.interceptors.EventMessageHandlerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

public class CQRSConfiguration {

  private static final String NAME_COMMAND_BUS = "commandBus";

  @Value("${application.cqrs.asyncEventBus:false}")
  public boolean asyncEventBus;

  @Bean
  public AggregateContextRefreshedListener applicationContextRefreshedListener(final CommandMessageHandlerAdapter commandMessageHandlerAdapter) {
    return new AggregateContextRefreshedListener(commandMessageHandlerAdapter);
  }

  @Bean
  public EventHandlerAnnotationBeanPostProcessor messageHandlingAnnotationBeanPostProcessor(final EventMessageHandlerAdapter eventMessageHandlerAdapter) {
    return new EventHandlerAnnotationBeanPostProcessor(eventMessageHandlerAdapter);
  }

  @Bean(NAME_COMMAND_BUS)
  public CommandBus commandBus(final MessageInterceptorFactory messageInterceptorFactory) {
    return new CommandBusImpl(messageInterceptorFactory);
  }

  @Bean
  @DependsOn(NAME_COMMAND_BUS)
  public CommandGateway commandGateway(final @Qualifier(NAME_COMMAND_BUS) CommandBus commandBus) {
    return new CommandGatewayImpl(commandBus);
  }

  @Bean
  public EventSubscription eventSubscription() {
    return new GuavaEventSubscription("Domain_EventBus", asyncEventBus);
  }

  @Bean
  public EventGateway eventGateway(final EventSubscription eventSubscription) {
    return new EventGatewayImpl(eventSubscription);
  }

  @Bean
  public EventListener eventListener(final EventSubscription eventSubscription, final MessageInterceptorFactory messageInterceptorFactory) {
    return new EventListenerImpl(eventSubscription, messageInterceptorFactory);
  }

  @Bean
  public DomainFactory eventLifecycle() {
    return new DomainFactory();
  }

  @Bean
  public CommandMessageHandlerAdapter commandMessageHandlerAdapter(final EventGateway eventGateway) {
    return new CommandMessageHandlerAdapter(eventGateway);
  }

  @Bean
  public EventMessageHandlerAdapter eventMessageHandlerAdapter() {
    return new EventMessageHandlerAdapter();
  }

  @Bean
  public MessageInterceptorFactory messageInterceptorFactory(final CommandMessageHandlerAdapter commandMessageHandlerAdapter, final EventMessageHandlerAdapter eventMessageHandlerAdapter) {
    MessageInterceptorFactory factory = new MessageInterceptorFactory();
    factory.addInterceptor(new MessageHandlerInterceptor(commandMessageHandlerAdapter));
    factory.addInterceptor(new MessageHandlerInterceptor(eventMessageHandlerAdapter));
    return factory;
  }

}
