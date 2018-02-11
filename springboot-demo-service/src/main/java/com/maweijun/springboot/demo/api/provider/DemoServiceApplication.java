package com.maweijun.springboot.demo.api.provider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;


@EnableScheduling
@ServletComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement //启用事务
@ImportResource("classpath:dubbo-provider.xml")
public class DemoServiceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(DemoServiceApplication.class);

		HashMap defProperties = new HashMap();
		defProperties.put("spring.profiles.default", "local");
		application.setDefaultProperties(defProperties);

		application.setRegisterShutdownHook(false);
		application.setBannerMode(Banner.Mode.OFF);
		Environment env = application.run(args).getEnvironment();
		LOGGER.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n\t" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"));
	}
}
