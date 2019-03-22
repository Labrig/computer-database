package fr.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringBindingConfiguration.class, SpringDAOConfiguration.class})
@ComponentScan({"fr.excilys.service","fr.excilys.validator"})
public class SpringServiceConfiguration {
	
}
