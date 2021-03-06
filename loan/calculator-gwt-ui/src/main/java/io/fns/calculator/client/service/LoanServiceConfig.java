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
package io.fns.calculator.client.service;

import com.google.gwt.i18n.client.Constants;

/**
 * @author Chris Phillipson
 * 
 */
public interface LoanServiceConfig extends Constants {

	@DefaultStringValue("localhost")
	String host();
	
	@DefaultIntValue(9000)
	int httpPort();
}
