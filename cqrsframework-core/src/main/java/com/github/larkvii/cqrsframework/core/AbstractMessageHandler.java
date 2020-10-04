package com.github.larkvii.cqrsframework.core;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;

/**
 *
 */
public abstract class AbstractMessageHandler<T extends Message> implements MessageHandler<T> {

  private final Executable executable;
  private final boolean methodExecutable;

  public AbstractMessageHandler(final Executable executable) {
    this.executable = executable;
    this.methodExecutable = (executable instanceof Method);
  }

  protected final boolean isMethodExecutable() {
    return methodExecutable;
  }

  protected boolean canApply(final Object message) {
    return true;
  }

  @Override
  public Object apply(final T message) {
    if (!canApply(message)) {
      return null;
    }
    int parameterCount = this.executable.getParameterCount();
    Object[] parameters = new Object[parameterCount];
    if (parameters.length >= 1) {
      parameters[0] = message.getPayload();
      if (parameters.length >= 2) {
        parameters[1] = message.getMetadata();
      }
    }
    // execute
    Object result = null;
    try {
      if (isMethodExecutable()) {
        Method method = (Method) this.executable;
        ReflectionUtils.makeAccessible(method);
        result = method.invoke(getTarget(), parameters);
      } else if (this.executable instanceof Constructor) {
        Constructor constructor = (Constructor) this.executable;
        ReflectionUtils.makeAccessible(constructor);
        result = constructor.newInstance(parameters);
      }
    } catch (InvocationTargetException e) {
      ReflectionUtils.rethrowRuntimeException(e.getTargetException());
    } catch (IllegalAccessException e) {
      throw new UndeclaredThrowableException(e);
    } catch (InstantiationException e) {
      throw new UndeclaredThrowableException(e);
    }
    return result;
  }

  protected abstract Object getTarget();

}
