package fr.excilys.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@Configuration
@ComponentScan({"fr.excilys.controller","fr.excilys.dao","fr.excilys.dto","fr.excilys.mapper","fr.excilys.service","fr.excilys.servlet","fr.excilys.validator","fr.excilys.view"})
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
	
	@Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
	
	@Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("my-locale-cookie");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	    registry.addResourceHandler("/font/**").addResourceLocations("/font/");
	}
	
	@Bean
    public LocaleChangeInterceptor localeInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }
}
