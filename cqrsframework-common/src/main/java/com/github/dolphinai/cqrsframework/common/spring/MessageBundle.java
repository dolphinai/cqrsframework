package com.github.dolphinai.cqrsframework.common.spring;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.io.Closeable;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

/**
 * MessageSource bundle.
 */
public final class MessageBundle implements Closeable {

  private static final Logger log = LoggerFactory.getLogger(MessageBundle.class);
  public static final Locale NON_LOCALE = new Locale("");
  private MessageSource messageSource;

  public MessageSource source() {
    if (messageSource == null) {
      messageSource = SpringContextUtils.getBean(MessageSource.class);
    }
    return messageSource;
  }

  public String get(final String key) {
    return get(key, NON_LOCALE);
  }

  public String get(final String key, final Locale locale) {
    return get(key, null, locale).orElse("");
  }

  public Optional<String> get(final String key, final Object[] args, final Locale locale) {
    String result = null;
    if (StringHelper.isNotEmpty(key)) {
      try {
        result = source().getMessage(key, args, locale);
      } catch (NoSuchMessageException e) {
        if (locale != null) {
          try {
            result = source().getMessage(key, args, NON_LOCALE);
          } catch (Exception e1) {
            log.error("Not found the message key:" + key, e1);
          }
        }
      } catch (Exception e) {
        log.error("Failed to get message for the key:" + key, e);
      }
    }
    return Optional.ofNullable(result);
  }

  @Override
  public void close() throws IOException {
    messageSource = null;
  }

  public static MessageBundle instance() {
    return MessageBundleHolder.instance;
  }

  private static class MessageBundleHolder {
    private static final MessageBundle instance = new MessageBundle();
  }
}
