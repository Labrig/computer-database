package fr.excilys.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CDBInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
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
