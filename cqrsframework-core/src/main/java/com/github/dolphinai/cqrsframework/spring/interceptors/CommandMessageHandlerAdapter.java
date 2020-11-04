package com.github.dolphinai.cqrsframework.spring.interceptors;

import com.github.dolphinai.cqrsframework.core.AbstractMessageHandler;
import com.github.dolphinai.cqrsframework.core.MessageHandler;
import com.github.dolphinai.cqrsframework.core.MessageHandlerAdapter;
import com.github.dolphinai.cqrsframework.core.command.CommandMessage;
import com.github.dolphinai.cqrsframework.core.command.CommandReturnHandler;
import com.github.dolphinai.cqrsframework.core.event.EventGateway;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Executable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 *
 */
@SuppressWarnings("unchecked")
public final class CommandMessageHandlerAdapter implements MessageHandlerAdapter<CommandMessage> {

  private final Map<String, MessageHandler> handlers = new ConcurrentHashMap<>();
  private final EventGateway eventGateway;

  public CommandMessageHandlerAdapter(final EventGateway eventGateway) {
    this.eventGateway = eventGateway;
  }

  @Override
  public Optional<MessageHandler> getHandler(final Class<?> payloadType) {
    return Optional.ofNullable(handlers.get(payloadType.getTypeName()));
  }

  @Override
  public boolean canHandle(final Object message) {
    return CommandMessage.class.equals(message.getClass());
  }

  @Override
  public Object onApply(final Function<CommandMessage, Object> handler, final CommandMessage message) {
    return handler.andThen(new CommandReturnHandler(eventGateway)).apply(message);
  }

  public void addHandler(Class<?> payloadType, final BeanFactory beanFactory, final String beanName, final Executable executable) {
    MessageHandler handler = handlers.get(payloadType.getTypeName());
    MessageHandler newHandler = new CommandMessageHandler(beanFactory, beanName, executable);
    if (handler == null) {
      handler = newHandler;
    } else {
      handler = handler.next(newHandler);
    }
    handlers.put(payloadType.getTypeName(), handler);
  }

  class CommandMessageHandler extends AbstractMessageHandler<CommandMessage> {

    private final BeanFactory beanFactory;
    private final String beanName;

    public CommandMessageHandler(final BeanFactory beanFactory, final String beanName, final Executable executable) {
      super(executable);
      this.beanFactory = beanFactory;
      this.beanName = beanName;
    }

    @Override
    public Object getTarget() {
      return beanFactory.getBean(beanName);
    }
  }
}
