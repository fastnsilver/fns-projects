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
package io.fns;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import com.gemstone.gemfire.cache.Cache;

/**
 * @author Chris Phillipson
 *
 */
@Configuration
@EnableCaching
@EnableGemfireRepositories
public class GemfireConfig {
	
	@Bean
	CacheFactoryBean cacheFactoryBean() {
		return new CacheFactoryBean();
	}
	
	@Bean
	LocalRegionFactoryBean<Integer, Integer> localRegionFactoryBean(final Cache cache) {
		return new LocalRegionFactoryBean<Integer, Integer>() {
			{
				setCache(cache);
				setName("loans");
			}
		};
	}
	
	@Bean
	GemfireCacheManager cacheManager(final Cache gemfireCache) {
		return new GemfireCacheManager() {
			{
				setCache(gemfireCache);
			}
		};
	}
	
}
