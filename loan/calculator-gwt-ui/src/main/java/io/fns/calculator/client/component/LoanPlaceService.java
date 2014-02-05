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
package io.fns.calculator.client.component;

import com.mvp4g.client.history.PlaceService;

/**
 * MVP4G equivalent of a "place service". Basically responsible for building
 * tokens used by GWT's History feature takign into account a navigation event's
 * name and params.
 * 
 * @author Chris Phillipson
 * 
 */
public class LoanPlaceService extends PlaceService {
	
	@Override
	public String tokenize(String eventName, String param) {
		// always add the paramSeparator since "/" is used for module separator
		// ad paramSeparator
		String token = eventName + getParamSeparator();
		if (param != null && param.length() > 0) {
			token += param;
		}
		return token;
	}
	
	@Override
	protected String getParamSeparator() {
		return "/";
	}
}
