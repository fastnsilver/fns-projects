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
package io.fns.calculator.cors;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yaml.snakeyaml.Yaml;

/**
 * <p>
 * Spring-enabled Cross Origin Resource Sharing (CORS) Request Filter
 * </p>
 * <p>
 * When you make an AJAX request from your domain to another domain, the first
 * thing that will happen is an OPTIONS request will be made to the URI in
 * question to determine what requirements are needed to complete this request.
 * This request is where we need to verify that the origin is actually allowed
 * to make that request and send back a response to the browser permitting it to
 * proceed. We will retrieve the “Origin” header from the servlet request and
 * verify that it’s one of the origins permitted to to make CORS requests to the
 * application. Assuming the origin is permitted, we’ll add a
 * “Access-Control-Allow-Origin” header to the servlet response which will
 * contain the value of the incoming “Origin” header.
 * </p>
 * 
 * <p>
 * Credit where credit is <a href=
 * "http://jpgmr.wordpress.com/2013/12/12/cross-origin-resource-sharing-cors-requests-with-spring-mvc/"
 * >due</a>.
 * </p>
 * 
 * @author Patrick Grimard
 * 
 */
@Component
public class CORSFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		InputStream in = getClass().getResourceAsStream("/application.yml");
		Yaml yaml = new Yaml();
		// Parse the YAML file and return the output as a series of Maps and Lists
		Map<String, Object> prop = (Map<String, Object>) yaml.load(in);
		in.close();
		
		Set<String> allowedOrigins = new HashSet<String>(Arrays.asList (((String) prop.get("allowed.origins")).split(",")));
		
		if(request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			String originHeader = request.getHeader("Origin");
			
			if(allowedOrigins.contains(request.getHeader("Origin"))) {
				response.addHeader("Access-Control-Allow-Origin", originHeader);
			}
			
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.addHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		}
		
		filterChain.doFilter(request, response);
	}
}
