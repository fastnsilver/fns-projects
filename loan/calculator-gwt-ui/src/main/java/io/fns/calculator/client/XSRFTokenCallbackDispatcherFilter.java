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

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.callback.FilterawareRequestCallback;
import org.fusesource.restygwt.client.callback.RetryingCallbackFactory;
import org.fusesource.restygwt.client.callback.XSRFToken;
import org.fusesource.restygwt.client.callback.XSRFTokenCallbackFilter;
import org.fusesource.restygwt.client.dispatcher.DispatcherFilter;

import com.google.gwt.http.client.RequestBuilder;

/**
 * @author Chris Phillipson
 * 
 */
public class XSRFTokenCallbackDispatcherFilter implements DispatcherFilter {
	
	private XSRFToken xsrf;
	
	public XSRFTokenCallbackDispatcherFilter(final XSRFToken xsrf) {
		this.xsrf = xsrf;
	}
	
	// see
	// http://ars-codia.raphaelbauer.com/2011/11/how-to-write-custom-callback-filter-for.html
	@Override
	public boolean filter(Method method, RequestBuilder builder) {
		XSRFTokenCallbackFilter filter = new XSRFTokenCallbackFilter(xsrf);
		RetryingCallbackFactory factory = new RetryingCallbackFactory(filter);
		FilterawareRequestCallback callback = factory.createCallback(method);
		builder.setCallback(callback);
		return true;
	}
	
}
