package com.liang.microserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
@EnableDubbo
public class MicroServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServerApplication.class, args);
	}

}
