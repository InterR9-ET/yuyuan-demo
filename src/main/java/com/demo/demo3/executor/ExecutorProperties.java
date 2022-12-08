package com.demo.demo3.executor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author jiangbing
 * @time 2022/12/06
 */
@Component
@ConfigurationProperties(prefix = "yuyuan.executor.thread")
@Data
public class ExecutorProperties {
    private int corePoolSize=5;
    private int maxPoolSize=100;
    private int queueCapacity=1000;
    private int keepAliveSeconds=60;
    private String namePrefix="yuyuan-";
}
