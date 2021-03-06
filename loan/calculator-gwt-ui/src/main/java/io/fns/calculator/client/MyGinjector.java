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

import org.fusesource.restygwt.client.dispatcher.FilterawareDispatcher;
import org.fusesource.restygwt.client.dispatcher.XSRFTokenDispatcherFilter;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * @author Chris Phillipson
 * 
 */
@GinModules({ RestModule.class })
public interface MyGinjector extends Ginjector {
	XSRFTokenCallbackDispatcherFilter getXSRFTokenCallbackDispatherFilter();
	XSRFTokenDispatcherFilter getXSRFTokenDispatcherFilter();
	FilterawareDispatcher getFilterawareDispatcher();
}
