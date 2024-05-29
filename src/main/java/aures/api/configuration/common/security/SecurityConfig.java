/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: SecurityConfig
 *	@CreatedOn	: 06-26-2023
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Security
 *
 **/

package aures.api.configuration.common.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import aures.api.service.common.security.UserDetailService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
    private JwtAuthFilter jwtAuthFilter;
	
	@Autowired
    private UserDetailService userDetailService;
	
	@Bean
	FilterRegistrationBean<CorsFilter> rmsCorsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Collections.singletonList("*"));
	    //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	    config.setAllowedMethods(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(new CorsFilter(source));
	    filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return filter;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/file/**").permitAll()
                        .anyRequest().permitAll())
                        //.authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailService)
                .build();
        
        
        /*return http
	        .csrf()
	        	.disable()
	        .authorizeHttpRequests()
	        	.requestMatchers("/api/v1/auth/**")
	        		.permitAll()
	        	.anyRequest()
	        		.authenticated()
	        	.and()
	        		.sessionManagement()
	        			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        	.and()
	        		.authenticationProvider(authenticationProvider())
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);*/
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailService);
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}