package com.github.dolphinai.cqrsframework.vertx.providers;

import org.hibernate.validator.HibernateValidator;
import org.jboss.resteasy.plugins.validation.GeneralValidatorImpl;
import org.jboss.resteasy.plugins.validation.ValidatorContextResolver;
import org.jboss.resteasy.spi.validation.GeneralValidatorCDI;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableType;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Provider
public class HibernateValidationProvider extends ValidatorContextResolver {

  private final ValidatorFactory validatorFactory;

  public HibernateValidationProvider() {
    validatorFactory = Validation.byProvider(HibernateValidator.class)
      .configure()
      .failFast(true)
      .buildValidatorFactory();
  }

  @Override
  public GeneralValidatorCDI getContext(final Class<?> type) {
    final Set<ExecutableType> types = new HashSet<>(2);
    types.add(ExecutableType.ALL);
    return new GeneralValidatorImpl(validatorFactory, true, types);
  }
}
