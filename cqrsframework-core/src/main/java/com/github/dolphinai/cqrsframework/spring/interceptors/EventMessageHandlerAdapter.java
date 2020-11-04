package com.github.dolphinai.cqrsframework.spring.interceptors;

import com.github.dolphinai.cqrsframework.core.AbstractMessageHandler;
import com.github.dolphinai.cqrsframework.core.MessageHandler;
import com.github.dolphinai.cqrsframework.core.MessageHandlerAdapter;
import com.github.dolphinai.cqrsframework.core.event.EventMessage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public class EventMessageHandlerAdapter implements MessageHandlerAdapter<EventMessage> {

  private final Map<String, MessageHandler> handlers = new ConcurrentHashMap<>();

  @Override
  public Optional<MessageHandler> getHandler(final Class<?> payloadType) {
    Optional value = Optional.ofNullable(handlers.get(payloadType.getTypeName()));
    if (!value.isPresent()) {
      Class<?>[] interfaces = payloadType.getInterfaces();
      if (interfaces != null && interfaces.length > 0) {
        for (Class<?> interfaceType : interfaces) {
          value = Optional.ofNullable(handlers.get(interfaceType.getTypeName()));
          if (value.isPresent()) {
            break;
          }
        }
      }
    }
    return value;
  }

  @Override
  public boolean canHandle(final Object message) {
    return EventMessage.class.equals(message.getClass());
  }

  public void addHandler(Class<?> payloadType, final Object bean, final Executable executable) {
    MessageHandler handler = handlers.get(payloadType.getTypeName());
    MessageHandler newHandler = new EventMessageHandler(bean, executable);
    if (handler == null) {
      handler = newHandler;
    } else {
      handler = handler.next(newHandler);
    }
    handlers.put(payloadType.getTypeName(), handler);
  }

  class EventMessageHandler extends AbstractMessageHandler<EventMessage> {

    private final Object bean;

    public EventMessageHandler(final Object bean, final Executable executable) {
      super(executable);
      this.bean = bean;
    }

    @Override
    public Object getTarget() {
      return bean;
    }
  }
}