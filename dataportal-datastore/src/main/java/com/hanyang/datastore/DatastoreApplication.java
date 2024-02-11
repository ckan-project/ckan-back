package com.hanyang.datastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.hanyang.datastore","com.hanyang.dataportal.core.global"})
public class DatastoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(DatastoreApplication.class, args);
	}

}
