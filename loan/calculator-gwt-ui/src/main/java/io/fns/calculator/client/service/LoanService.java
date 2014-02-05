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

import static io.fns.calculator.LoanAPI.LOAN_ENTRY_POINT;
import static io.fns.calculator.LoanAPI.POST_LOAN_DETAILS;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;

/**
 * @author Chris Phillipson
 * 
 */
public interface LoanService extends RestService {
	
	public static class Util {
		private static LoanService instance;
		
		public static LoanService get() {
			if (instance == null) {
				instance = GWT.create(LoanService.class);
			}
			Resource resource = new Resource(GWT.getModuleBaseURL() + LOAN_ENTRY_POINT);
			((RestServiceProxy) instance).setResource(resource);
			
			return instance;
		}
	}
	
	@POST
	@Path("/" + POST_LOAN_DETAILS)
	public void calculateLoanSchedule(@PathParam("loan") Loan loan, MethodCallback<LoanResult> callback);
}
