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
public enum Compounded {
	
	MONTHLY("monthly", 1200, 12),
	QUARTERLY("quarterly", 400, 4),
	SEMIANNUALLY("semi_annually", 200, 2),
	ANNUALLY("annually", 100, 1);
	
	@JsonProperty private String type;
	@JsonProperty private int interestDivisor;
	@JsonProperty private int termMultiplier;
	
	@JsonCreator
	Compounded(@JsonProperty("type") String type, @JsonProperty("interestDivisor") int interestDivisor,
			@JsonProperty("termMultiplier") int termMultiplier) {
		this.type = type;
		this.interestDivisor = interestDivisor;
		this.termMultiplier = termMultiplier;
	}
	
}