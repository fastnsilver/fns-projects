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
package io.fns.calculator.model;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Chris Phillipson
 *
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanResult {
	
	private String debtor;
	private BigDecimal amount;
	private double interest;
	private int years;
	private String compounded;
	private BigDecimal payment;
	private Set<Payment> paymentSchedule;
	
	public LoanResult() {
		// GWT serialization requires a no-arg constructor
	}
	
	public LoanResult(Loan loan) {
		setLoan(loan);
	}
	
	public void setLoan(@NonNull Loan loan) {
		this.debtor = loan.getDebtor();
		this.amount = loan.getAmount();
		this.interest = loan.getInterest();
		this.years = loan.getYears();
		this.compounded = loan.getCompounded().getType();
		this.payment = loan.calculatePayment();
		this.paymentSchedule = loan.calculatePaymentSchedule();
	}
}
