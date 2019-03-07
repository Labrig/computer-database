package fr.excilys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan("fr.excilys")
@PropertySource(value = { "classpath:configuration.properties" })
public class SpringConfiguration {

    @Autowired
    private Environment environement;
	
	@Bean
    public HikariDataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(environement.getRequiredProperty("URL"));
		dataSource.setUsername(environement.getRequiredProperty("USERNAME"));
		dataSource.setPassword(environement.getRequiredProperty("PASSWORD"));
		dataSource.setDriverClassName(environement.getRequiredProperty("DRIVER"));
        return dataSource;
    }
}
