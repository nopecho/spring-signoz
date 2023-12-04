package io.nopecho.sample.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig {

    @Bean
    fun logEventThreadPool(): Executor {
        val threadPool = ThreadPoolTaskExecutor()
        threadPool.corePoolSize = 10
        threadPool.maxPoolSize = 10
        threadPool.setThreadNamePrefix("log-event-")
        threadPool.setWaitForTasksToCompleteOnShutdown(true)
        threadPool.setAwaitTerminationSeconds(30)
        return threadPool
    }
}