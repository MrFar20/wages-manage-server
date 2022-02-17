package com.cbadmin.app;

import com.cbmai.redis.annotation.EnableRedisChannel;
import com.cbmai.redis.annotation.EnableRedisStreamMessageListener;
import com.cbmai.systemconfig.annotation.EnableSystemConfig;
import com.cbmai.web.annotation.EnableGlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = {"com.cbadmin", "com.cbmai"})
@Slf4j
@EnableSystemConfig
@EnableGlobalExceptionHandler
@EnableRedisChannel
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.cbadmin", "com.cbmai"})
@EntityScan(basePackages = {"com.cbadmin", "com.cbmai"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10800)
@EnableScheduling
@EnableRedisStreamMessageListener
@EnableAsync
public class CbadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CbadminApplication.class);
    }

}
