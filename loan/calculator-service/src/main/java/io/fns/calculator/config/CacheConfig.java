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

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.hibernate.EhCache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import com.gemstone.gemfire.cache.Cache;

/**
 * Provides cache support
 * 
 * @author Chris Phillipson
 * 
 */
@Configuration
public class CacheConfig {
	
	public static final String LOAN_SCHEDULE_CACHE = "loanScheduleCache";
	/**
	 * <p>
	 * Provides {@link EhCache} support.<br />
	 * Credit for this implementation goes to <a href=
	 * "http://stackoverflow.com/questions/21944202/using-ehcache-in-spring-4-without-xml"
	 * >kdubb</a>.
	 * </p>
	 */
	@Configuration
	@EnableCaching
	static class DefaultCacheConfig implements CachingConfigurer {
		
		@Value("${ehcache.maxEntriesLocalHeap}")
		private int maxEntriesLocalHeap;
		
		@Bean
		@Override
		public CacheManager cacheManager() {
			CacheConfiguration cacheConfiguration = new CacheConfiguration();
			cacheConfiguration.setName(LOAN_SCHEDULE_CACHE);
			cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
			cacheConfiguration.setMaxEntriesLocalHeap(maxEntriesLocalHeap);
			net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
			config.addCache(cacheConfiguration);
			net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.newInstance(config);
			return new EhCacheCacheManager(cacheManager);
		}
		
		@Bean
		@Override
		public KeyGenerator keyGenerator() {
			return new SimpleKeyGenerator();
		}
		
	}
	
	@Configuration
	@EnableCaching
	@EnableGemfireRepositories
	@Profile(Profiles.GEMFIRE)
	static class GemfireCacheConfig {
		
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
}
