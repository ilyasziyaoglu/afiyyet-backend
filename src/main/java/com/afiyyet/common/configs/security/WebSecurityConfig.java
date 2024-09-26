package com.afiyyet.common.configs.security;

import com.afiyyet.common.user.service.UserService;
import com.afiyyet.common.webfilters.JwtRequestFilter;
import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-21
 */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

	private final UserService userDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(requests ->
				requests
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
					.requestMatchers(
						new AntPathRequestMatcher("/order/create"),
						new AntPathRequestMatcher("/menu/**"),
						new AntPathRequestMatcher("/contactform"),
						new AntPathRequestMatcher("/reservation/reserve"),
						new AntPathRequestMatcher("/comment/insert-comment"),
//						new AntPathRequestMatcher("/**/guest**"),
						new AntPathRequestMatcher("/auth/**"),
						new AntPathRequestMatcher("/webjars/**"),
						new AntPathRequestMatcher("/swagger*/**"),
						new AntPathRequestMatcher("/v2/**"),
						new AntPathRequestMatcher("/v3/**")
					).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyAuthority("USER", "ADMIN")
					.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority("ADMIN")
					.anyRequest().permitAll()
			)
			.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowCredentials(true);

		corsConfig.addAllowedOrigin("https://afiyyet.com");
		corsConfig.addAllowedOrigin("http://localhost:4200");
		corsConfig.addAllowedOrigin("http://localhost:4200");
		// Replace with your allowed origin
		corsConfig.addAllowedMethod("*");
		corsConfig.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsFilter(source);
	}

	@Bean
	public FilterRegistrationBean<JwtRequestFilter> jwtFilterRegistration(JwtRequestFilter filter) {
		FilterRegistrationBean<JwtRequestFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationProvider... authenticationProviders) {
		return new ProviderManager(authenticationProviders);
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider
		provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
