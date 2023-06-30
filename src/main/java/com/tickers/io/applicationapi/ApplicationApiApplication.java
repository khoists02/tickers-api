package com.tickers.io.applicationapi;

import com.tickers.io.applicationapi.classes.MyJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaRepositories(repositoryFactoryBeanClass = MyJpaRepositoryFactoryBean.class, basePackages = "com.tickers.io")
@ComponentScan(basePackages = {"com.tickers.io"})
@EntityScan(basePackages = "com.tickers.io")
public class ApplicationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationApiApplication.class, args);
	}

}
