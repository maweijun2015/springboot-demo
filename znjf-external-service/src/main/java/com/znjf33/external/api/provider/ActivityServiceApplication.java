package com.znjf33.external.api.provider;

import javax.sql.DataSource;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.znjf33.common.utils.DefaultProfileUtil;
import com.znjf33.common.utils.SpringUtils;

@EnableScheduling
@ServletComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement //启用事务
@ImportResource("classpath:dubbo-provider.xml")
public class ActivityServiceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ActivityServiceApplication.class);
		DefaultProfileUtil.addDefaultProfile(application);
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

	@Bean
	public SpringUtils springUtil(){
		return new SpringUtils();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return new com.alibaba.druid.pool.DruidDataSource();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Interceptor[] plugins =new Interceptor[]{new PageHelper()};
		sqlSessionFactoryBean.setPlugins(plugins);
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
