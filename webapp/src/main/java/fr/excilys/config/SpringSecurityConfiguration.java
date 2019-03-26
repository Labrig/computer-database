package fr.excilys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("user")).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/AddComputer").hasRole("ADMIN")
			.antMatchers("/EditComputer").hasRole("ADMIN")
			.antMatchers("/DeleteComputer").hasRole("ADMIN")
			.antMatchers("/").authenticated()
			.antMatchers("/FindComputerByName").authenticated()
			.antMatchers("/LoginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/Login")
			.loginProcessingUrl("/LoginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
			.failureForwardUrl("/Login?error=1")
		.and()
			.logout()
			.logoutSuccessUrl("/Login?logout=1")
			.logoutUrl("/LogoutProcess");
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
