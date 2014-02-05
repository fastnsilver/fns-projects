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
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Chris Phillipson
 *
 */
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {
	
	private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
	
	@JsonProperty private final String debtor;
	@JsonProperty private final BigDecimal amount;
	@JsonProperty private final double interest;
	@JsonProperty private final Integer years;
	@JsonProperty private final Compounded compounded;
	
	@JsonCreator
	public Loan(@JsonProperty("debtor") String debtor, @JsonProperty("amount") BigDecimal amount,
			@JsonProperty("interest") double interest, @JsonProperty("years") Integer years,
			@JsonProperty("compounded") Compounded compounded) {
		this.debtor = debtor;
		this.amount = amount;
		this.interest = interest;
		this.years = years;
		this.compounded = compounded;
	}
	
	private double calculateRate() {
		return interest / compounded.getInterestDivisor();
	}
	
	private int calculateTerm() {
		return years * compounded.getTermMultiplier();
	}
	
	public BigDecimal calculatePayment() {
		BigDecimal rate = BigDecimal.valueOf(calculateRate());
		int term = calculateTerm();
		BigDecimal dividend = rate.multiply(amount);
		BigDecimal s1 = BigDecimal.valueOf(1).add(rate);
		BigDecimal s2 = s1.pow(term);
		BigDecimal s3 = BigDecimal.valueOf(1).setScale(10).divide(s2, ROUNDING_MODE);
		BigDecimal divisor = BigDecimal.valueOf(1).subtract(s3);
		BigDecimal result = dividend.divide(divisor, ROUNDING_MODE);
		return result;
	}
	
	public Set<Payment> calculatePaymentSchedule() {
		Set<Payment> schedule = new LinkedHashSet<Payment>();
		BigDecimal payment = calculatePayment();
		BigDecimal rate = BigDecimal.valueOf(calculateRate());
		int term = calculateTerm();
		BigDecimal balance = amount;
		BigDecimal interestAmount, principalAmount, ppi;
		BigDecimal cumulative = BigDecimal.ZERO;
		Payment periodPayment;
		for (int i = 1; i <= term; i++) {
			interestAmount = balance.multiply(rate);
			principalAmount = payment.subtract(interestAmount);
			balance = balance.subtract(principalAmount);
			ppi = principalAmount.add(interestAmount);
			if (i == 1) {
				cumulative = ppi;
			} else {
				cumulative = cumulative.add(ppi);
			}
			periodPayment = new Payment(i, principalAmount, interestAmount, cumulative, balance);
			schedule.add(periodPayment);
		}
		return schedule;
	}
	
}
