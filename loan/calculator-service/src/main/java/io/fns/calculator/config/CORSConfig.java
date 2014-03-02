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


import io.fns.calculator.filter.CORSFilter;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.yaml.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * {@link CORSFilter}'s configuration.
 * 
 * @author Chris Phillipson
 * 
 */
@Configuration
public class CORSConfig {
	
	/**
	 * Factory for converting <code>application.yml</code> on classpath to
	 * {@link Properties}.
	 */
	@Bean
	public YamlPropertiesFactoryBean yamlProperties() throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
		bean.setResources(resolver.getResources("classpath:application.yml"));
		return bean;
	}
}
