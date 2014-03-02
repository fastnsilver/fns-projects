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
package io.fns.calculator.service;

import static io.fns.calculator.config.CacheConfig.LOAN_SCHEDULE_CACHE;
import io.fns.calculator.model.Compounded;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Service for calculating loan amortization schedules.
 * </p>
 * <p>
 * Input: a {@link Loan} consisting of a debtor, amount, interest rate, term (in
 * years), and interest compounding option<br/>
 * Output: a {@link LoanResult} consisting of a period payment amount and an
 * amortization schedule.
 * </p>
 * 
 * @author Chris Phillipson
 * 
 */
@Service
@Slf4j
public class LoanService {
	
	/**
	 * Convenience method for creating a {@link Loan}
	 * 
	 * @param debtor
	 *            the payee and/or the loan originator
	 * @param amount
	 *            the total loan amount
	 * @param interest
	 *            the annual percentage interest rate (APR)
	 * @param years
	 *            the term of the loan in (whole number) years
	 * @param compounded
	 *            <p>
	 *            describes the manner in which the interest is compounded
	 *            </p>
	 *            <p>
	 *            Options are:<br/>
	 *            <ul>
	 *            <li>MONTHLY</li>
	 *            <li>QUARTERLY</li>
	 *            <li>SEMIANNUALLY</li>
	 *            <li>ANNUALLY</li>
	 *            </ul>
	 *            </p>
	 * @return a {@link Loan} that encapsulates all the parameters for input to
	 *         a {@link LoanResult}
	 */
	private Loan createLoan(String debtor, BigDecimal amount, double interest, int years, String compounded) {
		Loan loan = new Loan(debtor, amount, interest, years, Enum.valueOf(Compounded.class, compounded));
		log.info("Loan created: " + loan.toString());
		return loan;
	}
	
	/**
	 * Calculate a loan amortization schedule
	 * 
	 * @param debtor
	 *            the payee and/or the loan originator
	 * @param amount
	 *            the total loan amount
	 * @param interest
	 *            the annual percentage interest rate (APR)
	 * @param years
	 *            the term of the loan in (whole number) years
	 * @param compounded
	 *            <p>
	 *            describes the manner in which the interest is compounded
	 *            </p>
	 *            <p>
	 *            Options are:<br/>
	 *            <ul>
	 *            <li>MONTHLY</li>
	 *            <li>QUARTERLY</li>
	 *            <li>SEMIANNUALLY</li>
	 *            <li>ANNUALLY</li>
	 *            </ul>
	 *            </p>
	 * @return a {@link LoanResult}
	 */
	@Cacheable(LOAN_SCHEDULE_CACHE)
	public LoanResult calculatePaymentSchedule(String debtor, BigDecimal amount, double interest, int years,
			String compounded) {
		Loan loan = createLoan(debtor, amount, interest, years, compounded);
		LoanResult result = new LoanResult(loan);
		return result;
	}
	
	/**
	 * Variant of
	 * {@link LoanService#calculatePaymentSchedule(String, BigDecimal, double, int, String)}
	 * . Takes a single argument: {@link Loan}.
	 * 
	 * @param loan
	 *            a {@link Loan} that encapsulates all parameters for
	 *            calculating a {@link LoanResult}
	 * @return a {@link LoanResult}
	 */
	@Cacheable(LOAN_SCHEDULE_CACHE)
	public LoanResult calculatePaymentSchedule(Loan loan) {
		LoanResult result = new LoanResult(loan);
		return result;
	}
}
