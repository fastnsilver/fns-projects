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

import static io.fns.calculator.LoanAPI.POST_LOAN_DETAILS;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;

import com.mvp4g.client.annotation.Service;

/**
 * @author Chris Phillipson
 * 
 */
@Service(generatedClass = LoanService.class)
public interface LoanService extends RestService {
	
	public static String BASE_URL = "http://" + LoanServiceConfig.INSTANCE.host() + ":"
			+ LoanServiceConfig.INSTANCE.httpPort();
	
	public static Resource RESOURCE = new Resource(BASE_URL + "/");
	
	@POST
	@Path("/" + POST_LOAN_DETAILS)
	@Options(expect = { 200, 201 })
	public void calculateLoanSchedule(@PathParam("loan") Loan loan, MethodCallback<LoanResult> callback);
}
