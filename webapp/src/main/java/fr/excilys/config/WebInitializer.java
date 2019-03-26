package fr.excilys.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class [] {SpringSecurityConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class [] {SpringWebConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String [] {"/"};
	}

}
