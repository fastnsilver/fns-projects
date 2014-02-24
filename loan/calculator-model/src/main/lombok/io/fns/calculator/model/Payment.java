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
public class Payment {
	
	@JsonProperty private final int period;
	@JsonProperty private final BigDecimal principalAmount;
	@JsonProperty private final BigDecimal interestAmount;
	@JsonProperty private final BigDecimal cumulativeAmount;
	@JsonProperty private final BigDecimal balance;
	
	@JsonCreator
	public Payment(@JsonProperty("period") int period, @JsonProperty("principalAmount") BigDecimal principalAmount,
			@JsonProperty("interestAmount") BigDecimal interestAmount,
			@JsonProperty("cumulativeAmount") BigDecimal cumulativeAmount, @JsonProperty("balance") BigDecimal balance) {
		this.period = period;
		this.principalAmount = principalAmount;
		this.interestAmount = interestAmount;
		this.cumulativeAmount = cumulativeAmount;
		this.balance = balance;
	}
	
}
