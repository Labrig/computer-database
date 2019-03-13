package fr.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@Configuration
@ComponentScan({"fr.excilys.controller","fr.excilys.dao","fr.excilys.mapper","fr.excilys.service","fr.excilys.servlet","fr.excilys.validator","fr.excilys.view"})
@PropertySource(value = { "classpath:configuration.properties" })
public class SpringWebConfiguration implements WebMvcConfigurer {
	
	@Bean
    public HikariDataSource dataSource(Environment environement) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(environement.getRequiredProperty("URL"));
		dataSource.setUsername(environement.getRequiredProperty("USERNAME"));
		dataSource.setPassword(environement.getRequiredProperty("PASSWORD"));
		dataSource.setDriverClassName(environement.getRequiredProperty("DRIVER"));
        return dataSource;
    }
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/views/");
	    bean.setSuffix(".jsp");
	    return bean;
	}
}
