package com.spoofy.esportsclash.core.infrastructure.spring.config;

import an.awesome.pipelinr.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelineConfiguration {

    @Bean
    Pipeline pipeline(
            ObjectProvider<Command.Handler> commandHandler,
            ObjectProvider<Command.Middleware> middlewares,
            ObjectProvider<Notification.Handler> notificationHandlers
    ) {
        return new Pipelinr()
                .with(commandHandler::orderedStream)
                .with(middlewares::orderedStream)
                .with(notificationHandlers::orderedStream);
    }
}
