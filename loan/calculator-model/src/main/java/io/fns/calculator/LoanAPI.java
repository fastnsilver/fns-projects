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
package io.fns.calculator;

/**
 * @author Chris Phillipson
 * 
 */
public interface LoanAPI {
	public static final String LOAN_ENTRY_POINT = "schedule";
	public static final String GET_LOAN_DETAILS = "get/debtor/{debtor}/amount/{amount}/interest/{interest}/years/{years}/compounded/{compounded}";
	public static final String POST_LOAN_DETAILS = "post/{loan}";
}
