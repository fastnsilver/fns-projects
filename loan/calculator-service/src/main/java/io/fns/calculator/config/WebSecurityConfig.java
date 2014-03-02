/**
 * Copyright 2014 fastnsilver.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fns.calculator.config;

import io.fns.calculator.LoanAPI;
import io.fns.calculator.filter.CsrfTokenGeneratorFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * @author Chris Phillipson
 *
 */
@Configuration
@EnableWebMvcSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 6)
public class WebSecurityConfig {
	
	private static final String REALM = "Loan Calculator API";
	
	@Configuration
	@Profile(Profiles.SECURE)
	static class SecureConfig extends WebSecurityConfigurerAdapter {
		
		@Value("${loan.service.user}")
		private String user;
		
		@Value("${loan.service.password}")
		private String password;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// http://docs.spring.io/spring-security/site/docs/3.2.x/reference/htmlsingle/#csrf-configure
			http.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class);
			http.authorizeRequests().antMatchers(LoanAPI.BASE).hasRole("USER").anyRequest().authenticated();
			http.httpBasic().realmName(REALM);
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
			authManagerBuilder.inMemoryAuthentication().withUser(user)
			.password(password).roles("USER");
		}
	}
	
	@Configuration
	static class InsecureConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// https://gist.github.com/joshlong/0951f7b27749ef8c22a3
			http.csrf().disable(); // disables the CSRF check
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.requestMatchers().and().authorizeRequests().antMatchers("/").permitAll().anyRequest().anonymous();
		}
		
	}
	
}