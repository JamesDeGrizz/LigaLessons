package ru.hofftech.liga.lessons.parcelloader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableAsync
@EnableScheduling
public class TaskConfig {
    @Value("${task.executor.core-pool-size}")
    private int corePoolSize;
    @Value("${task.executor.max-pool-size}")
    private int maxPoolSize;
    @Value("${task.executor.queue-capacity}")
    private int queueCapacity;
    @Value("${task.executor.thread-name-prefix}")
    private String threadNamePrefix;
    @Value("${task.scheduler.pool-size}")
    private int poolSize;
    @Value("${task.scheduler.thread-name-prefix}")
    private String schedulerThreadNamePrefix;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    @Bean(name = "taskScheduler")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setThreadNamePrefix(schedulerThreadNamePrefix);
        scheduler.initialize();
        return scheduler;
    }
}
