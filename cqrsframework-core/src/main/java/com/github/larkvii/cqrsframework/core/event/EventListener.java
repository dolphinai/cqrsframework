package com.github.larkvii.cqrsframework.core.event;

import java.util.function.Consumer;

/**
 *
 */
public interface EventListener extends Consumer<EventMessageEnvelope> {

}
