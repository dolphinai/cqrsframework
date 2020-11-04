package com.github.dolphinai.cqrsframework.commons.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 */
public final class MessageResource {

  private static final Logger log = LoggerFactory.getLogger(MessageResource.class);
  private MessageSource resources;

  public MessageSource getResources() {
    if (resources == null) {
      resources = SpringContextUtils.getBean(MessageSource.class);
    }
    return resources;
  }

  public String getMessage(final String key, final Locale locale) {
    return getMessage(key, null, locale);
  }

  public String getMessage(final String key, final Object[] args, final Locale locale) {
    String result = "";
    try {
      result = getResources().getMessage(key, args, locale);
    } catch (NoSuchMessageException e) {
      if (locale != null) {
        try {
          result = getResources().getMessage(key, args, null);
        } catch (Exception e1) {
          log.error("Not found the message key:" + key, e1);
        }
      }
    } catch (Exception e) {
      log.error("Failed to get message for the key:" + key, e);
    }
    return result;
  }

  public static MessageResource getInstance() {
    return MessageResourceHolder.instance;
  }

  private static class MessageResourceHolder {
    private static final MessageResource instance = new MessageResource();
  }
}
