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

import static io.fns.CacheConfig.LOAN_SCHEDULE_CACHE;
import io.fns.calculator.model.Compounded;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Chris Phillipson
 *
 */
@Service
@Slf4j
public class LoanService {
	
	private Loan createLoan(String debtor, BigDecimal amount, double interest, int years, String compounded) {
		Loan loan = new Loan(debtor, amount, interest, years, Enum.valueOf(Compounded.class, compounded));
		log.info("Loan created: " + loan.toString());
		return loan;
	}
	
	@Cacheable(LOAN_SCHEDULE_CACHE)
	public LoanResult calculatePaymentSchedule(String debtor, BigDecimal amount, double interest, int years,
			String compounded) {
		Loan loan = createLoan(debtor, amount, interest, years, compounded);
		LoanResult result = new LoanResult(loan);
		return result;
	}
	
	@Cacheable(LOAN_SCHEDULE_CACHE)
	public LoanResult calculatePaymentSchedule(Loan loan) {
		LoanResult result = new LoanResult(loan);
		return result;
	}
}
