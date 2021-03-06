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
package io.fns.calculator.client.i18n;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * @author Chris Phillipson
 * 
 */
public interface Messages extends ConstantsWithLookup {
	
	public static Messages INSTANCE = GWT.create(Messages.class);
	
	String app_name();
	
	String amount();
	
	String balance();
	
	String compounded();
	
	String cumulativeAmount();
	
	String debtor();
	
	String interest();
	
	String interestAmount();
	
	String oops();
	
	String no_results();
	
	String payment();
	
	String period();
	
	String principalAmount();
	
	String reset();

	String schedule();
	
	String send();
	
	String years();
	
	String monthly();
	
	String quarterly();
	
	String semi_annually();
	
	String annually();
	
}
