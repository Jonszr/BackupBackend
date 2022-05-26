package ca.sait.backup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ca.sait.backup.config.oauth.PrincipalOauth2UserService;


@Configuration
@EnableWebSecurity // //Register Spring security filter
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured annotation active
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); 
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() // need to authenticated
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // need this role, if not, no access
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll()
		.and()
		.formLogin() //move to login page s
		.loginPage("/general/login") // move to login page
		.loginProcessingUrl("/login") //if call /login address, Spring security take the address and do the login.
		.defaultSuccessUrl("/")
		.and() // google login start
		.oauth2Login()
		.loginPage("/general/login") // google login end insert login homepage url
		.userInfoEndpoint()
		.userService(principalOauth2UserService); //
		
	}	
}
