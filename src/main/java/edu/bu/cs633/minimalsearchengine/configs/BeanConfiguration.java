package edu.bu.cs633.minimalsearchengine.configs;

import edu.bu.cs633.minimalsearchengine.crawler.MSEWebCrawler;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class BeanConfiguration {

    @Bean(name = "mseWebCrawler")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public MSEWebCrawler mseWebCrawler() {

        return new MSEWebCrawler();
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor asyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsyncThread::");
        executor.initialize();

        return executor;
    }

}

