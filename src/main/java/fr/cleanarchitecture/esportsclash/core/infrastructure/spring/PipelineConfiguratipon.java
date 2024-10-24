package fr.cleanarchitecture.esportsclash.core.infrastructure.spring;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PipelineConfiguratipon {
    @Bean
    public Pipeline getPipeline(
            ObjectProvider<Command.Handler> commandHandler,
            ObjectProvider<Command.Middleware> commandMiddleware,
            ObjectProvider<Command.Handler> notifcationHandles
    ) {
        return new Pipelinr()
                .with(commandHandler::orderedStream)
                .with(commandMiddleware::orderedStream)
                .with(notifcationHandles::orderedStream);
    }
}
