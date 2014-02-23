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
package io.fns.calculator.client.presenter;

import io.fns.calculator.client.LoanMainEventBus;
import io.fns.calculator.client.service.LoanService;
import io.fns.calculator.client.view.LoanMainView;
import io.fns.calculator.client.view.LoanMainView.LoanMainPresenter;
import io.fns.calculator.client.view.impl.LoanMainViewImpl;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

/**
 * @author Chris Phillipson
 * 
 */
@Presenter(view = LoanMainViewImpl.class)
public class LoanMainPresenterImpl extends BasePresenter<LoanMainView, LoanMainEventBus> implements LoanMainPresenter {
	
	LoanService loanService;

	@Inject
	public void setLoanService(LoanService loanService) {
		this.loanService = loanService;
	}
	
	@Override
	public void onInit() {
		// do nothing
	}
	
	@Override
	public void onStart() {
		view.prepare(null);
	}
	
	@Override
	public void onHandle(Throwable caught) {
		view.handle(caught);
	}
	
	@Override
	public void onSubmit(Loan loan) {
		loanService.calculateLoanSchedule(loan, new MethodCallback<LoanResult>() {
			
			@Override
			public void onFailure(Method method, Throwable exception) {
				eventBus.handle(exception);
			}
			
			@Override
			public void onSuccess(Method method, LoanResult response) {
				view.prepare(response);
			}
			
		});
		
	}
	
}
