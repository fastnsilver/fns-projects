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

import static io.fns.calculator.LoanAPI.GET_LOAN_DETAILS;
import static io.fns.calculator.LoanAPI.POST_LOAN_DETAILS;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chris Phillipson
 *
 */
@RestController
public class LoanRestController {
	
	@Autowired
	private LoanService loanService;
	
	@RequestMapping(GET_LOAN_DETAILS)
	public LoanResult calculatePaymentSchedule(@PathVariable("debtor") String debtor,
			@PathVariable("amount") BigDecimal amount, @PathVariable("interest") double interest,
			@PathVariable("years") int years, @PathVariable("compounded") String compounded) {
		return loanService.calculatePaymentSchedule(debtor, amount, interest, years, compounded);
	}
	
	@RequestMapping(value = POST_LOAN_DETAILS, method = RequestMethod.POST)
	public LoanResult calculatePaymentSchedule(@PathVariable("loan") Loan loan) {
		return loanService.calculatePaymentSchedule(loan);
	}
}
