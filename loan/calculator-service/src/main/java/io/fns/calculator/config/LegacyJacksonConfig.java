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

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;


/**
 * Re-enables {@link HttpMessageConverter} for legacy Jackson (&lt; 2.x)
 * implementation.
 * 
 * @author Chris Phillipson
 * 
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class LegacyJacksonConfig {
	
	@Autowired
	private ListableBeanFactory beanFactory;
	
	@PostConstruct
	public void init() {
		Collection<ObjectMapper> mappers = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(this.beanFactory, ObjectMapper.class)
				.values();
		Collection<Module> modules = BeanFactoryUtils.beansOfTypeIncludingAncestors(
				this.beanFactory, Module.class).values();
		for (ObjectMapper mapper : mappers) {
			mapper.registerModule(modules.iterator().next());  // there can be only one!
			break;
		}
	}
	
	@Bean
	@ConditionalOnMissingBean
	public Module jaxsonModule() {
		return new SimpleModule("legacyJacksonModule", Version.unknownVersion());
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper jaxsonObjectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter(
			ObjectMapper objectMapper) {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		return converter;
	}
}
