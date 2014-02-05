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
package io.fns.calculator.client.view;

import io.fns.calculator.client.LoanMainEventBus;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.event.EventHandlerInterface;

/**
 * @author Chris Phillipson
 *
 */
public interface LoanMainView extends IsWidget {
	
	public interface LoanMainPresenter extends EventHandlerInterface<LoanMainEventBus> {
		void onInit();
		void onHandle(Throwable caught);
		void onSubmit(Loan loan);
	}
	
	void handle(Throwable caught);
	void prepare(LoanResult result);
	
}
