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
package io.fns.calculator.filter;

import java.lang.reflect.Field;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.yaml.YamlPropertiesFactoryBean;
import org.springframework.stereotype.Component;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSConfigurationException;
import com.thetransactioncompany.cors.CORSRequestHandler;

/**
 * Adaptation of Vladimir Dzhuvinov's
 * {@link com.thetransactioncompany.cors.CORSFilter} to enable loading
 * {@link CORSConfiguration} with YAML. Employs Java Reflection to get a handle
 * on private member variables of base {@link Filter} implementation.
 * 
 * @author Chris Phillipson
 * 
 */
@Component
public class CORSFilter extends com.thetransactioncompany.cors.CORSFilter {
	
	@Autowired
	private YamlPropertiesFactoryBean yamlProperties;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		try {
			CORSConfiguration config = (CORSConfiguration) getInstanceField(this, "config");
			CORSRequestHandler handler = (CORSRequestHandler) getInstanceField(this, "handler");
			config = new CORSConfiguration(yamlProperties.getObject());
			handler = new CORSRequestHandler(config);
		} catch (CORSConfigurationException e) {
			throw new ServletException(e.getMessage(), e);
		} catch (Throwable t) {
			throw new ServletException(t.getMessage(), t);
		}
	}
	
	private static Object getInstanceField(Object instance, String fieldName) throws Throwable {
		Field field = instance.getClass().getSuperclass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(instance);
	}
	
}
