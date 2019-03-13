package fr.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"fr.excilys.controller","fr.excilys.dao","fr.excilys.mapper","fr.excilys.service","fr.excilys.servlet","fr.excilys.validator","fr.excilys.view"})
@PropertySource(value = { "classpath:configuration.properties" })
public class SpringCliConfiguration {

	@Bean
    public HikariDataSource dataSource(Environment environement) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(environement.getRequiredProperty("URL"));
		dataSource.setUsername(environement.getRequiredProperty("USERNAME"));
		dataSource.setPassword(environement.getRequiredProperty("PASSWORD"));
		dataSource.setDriverClassName(environement.getRequiredProperty("DRIVER"));
        return dataSource;
    }
}
