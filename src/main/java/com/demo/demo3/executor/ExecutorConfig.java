package com.demo.demo3.executor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author jiangbing
 * @time 2022/12/06
 */
@Configuration
@EnableAsync
@Log4j2
public class ExecutorConfig {

    public static final String TASK_NAME = "taskExecutor";

    public static final String SAVE_IM_CHARGE_DATA_TASK_NAME = "saveimchargedata";

    public static final String SAVE_IM_STOP_CHARGE_TASK_NAME = "saveimstopcharge";

    public static final String SAVE_BM_STOP_CHARGE_TASK_NAME = "savebmstopcharge";

    @Autowired
    private ExecutorProperties executorProperties;

    @Bean(TASK_NAME)
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(executorProperties.getCorePoolSize());
        // 设置最大线程数
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        // 设置队列容量
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        // 设置默认线程名称
        executor.setThreadNamePrefix(executorProperties.getNamePrefix());
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);

        log.info("线程池配置：{}",executorProperties);
        return executor;
    }
}
