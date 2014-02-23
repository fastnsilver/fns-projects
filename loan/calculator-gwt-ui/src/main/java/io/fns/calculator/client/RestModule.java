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
package io.fns.calculator.client;

import io.fns.calculator.client.service.LoanService;
import io.fns.calculator.client.service.LoanServiceConfig;

import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.callback.XSRFToken;
import org.fusesource.restygwt.client.dispatcher.DefaultFilterawareDispatcher;
import org.fusesource.restygwt.client.dispatcher.FilterawareDispatcher;
import org.fusesource.restygwt.client.dispatcher.XSRFTokenDispatcherFilter;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * GIN module that provides an {@link XSRFToken} and an associated
 * {@link XSRFTokenDispatcherFilter} for use by RestyGWT. Ultimate credit for
 * this implementation goes to <a href=
 * "https://groups.google.com/forum/m/#!msg/restygwt/czMSJ_YI3Rs/O5VpkdoSuXgJ"
 * >Benjamin Makus</a>.
 * 
 * @author Chris Phillipson
 * 
 */
public class RestModule extends AbstractGinModule {
	
	@Override
	protected void configure() {
	}
	
	@Provides
	@Singleton
	public XSRFToken provideXSRFToken() {
		XSRFToken xsrfToken = new XSRFToken();
		
		// get the CSRF token from the HTML document and initialize the
		// XSRFToken
		NodeList<MetaElement> metaTags = Document.get().getElementsByTagName(MetaElement.TAG).cast();
		for (int i = 0; i < metaTags.getLength(); i++) {
			if (metaTags.getItem(i).getName().equals("X-CSRF-TOKEN")) {
				xsrfToken.setToken(metaTags.getItem(i).getContent());
				break;
			}
		}
		
		return xsrfToken;
	}
	
	@Provides
	@Singleton
	@Inject
	public XSRFTokenCallbackDispatcherFilter provideXSRFTokenCallbackDispatcherFilter(XSRFToken xsrf) {
		return new XSRFTokenCallbackDispatcherFilter(xsrf);
	}
	
	@Provides
	@Singleton
	@Inject
	public XSRFTokenDispatcherFilter provideXSRFTokenDispatcherFilter(XSRFToken xsrf) {
		return new XSRFTokenDispatcherFilter(xsrf);
	}
	
	@Provides
	@Singleton
	public FilterawareDispatcher provideDispatcher() {
		return new DefaultFilterawareDispatcher();
	}
	
	@Provides
	@Singleton
	public LoanService provideLoanService() {
		LoanServiceConfig config = GWT.create(LoanServiceConfig.class);
		LoanService loanService = GWT.create(LoanService.class);
		String url = "http://" + config.host() + ":" + config.httpPort();
		Resource resource = new Resource(url);
		((RestServiceProxy) loanService).setResource(resource);
		return loanService;
	}
}