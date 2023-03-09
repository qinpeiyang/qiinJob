package com.xxl.job.executor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
@MapperScan("com.xxl.job.mapper")
@ComponentScan("com.xxl.job.*")
public class XxlJobExecutorApplication {

	public static void main(String[] args) {
        SpringApplication.run(XxlJobExecutorApplication.class, args);
	}

}