package fr.excilys.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringServiceConfiguration.class})
@ComponentScan({"fr.excilys.controller","fr.excilys.view"})
public class SpringConsoleConfiguration {

}
