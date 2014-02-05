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
package io.fns.calculator.client.view.impl;

import io.fns.calculator.client.component.ReverseCompositeView;
import io.fns.calculator.client.i18n.Messages;
import io.fns.calculator.client.view.LoanMainView;
import io.fns.calculator.client.view.LoanMainView.LoanMainPresenter;
import io.fns.calculator.model.Compounded;
import io.fns.calculator.model.Loan;
import io.fns.calculator.model.LoanResult;
import io.fns.calculator.model.Payment;

import java.math.BigDecimal;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Chris Phillipson
 * 
 */
public class LoanMainViewImpl extends ReverseCompositeView<LoanMainPresenter> implements LoanMainView {
	
	@UiTemplate("LoanMainView.ui.xml")
	interface LoanMainViewUiBinder extends UiBinder<Widget, LoanMainViewImpl> {
	}
	
	private static final LoanMainViewUiBinder binder = GWT.create(LoanMainViewUiBinder.class);
	
	@UiField
	TextBox debtor, amount, interest, years;
	
	@UiField
	ListBox compounded;
	
	@UiField(provided = true)
	FlexTable loanResults;
	
	@UiField
	Button btnSubmit;
	
	@UiField
	Label payment, error;
	
	public LoanMainViewImpl() {
		initialize();
		initWidget(binder.createAndBindUi(this));
	}
	
	private void initialize() {
		initializeCompoundedOptions();
		initializeSubmitButton();
		initializeResults();
	}
	
	private void initializeCompoundedOptions() {
		Compounded[] list = Compounded.values();
		for (Compounded c : list) {
			compounded.addItem(Messages.INSTANCE.getString(c.getType()), c.name());
		}
	}
	
	private void initializeSubmitButton() {
		btnSubmit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String d = debtor.getValue();
				BigDecimal a = new BigDecimal(amount.getValue());
				double i = Double.parseDouble(interest.getValue());
				int y = Integer.parseInt(years.getValue());
				Compounded c = Compounded.valueOf(compounded.getValue(compounded.getSelectedIndex()));
				Loan l = new Loan(d, a, i, y, c);
				getPresenter().getEventBus().submit(l);
			}
			
		});
	}
	
	private void initializeResults() {
		loanResults = new FlexTable();
		loanResults.setText(0, 0, Messages.INSTANCE.period());
		loanResults.setText(0, 1, Messages.INSTANCE.principalAmount());
		loanResults.setText(0, 2, Messages.INSTANCE.interestAmount());
		loanResults.setText(0, 3, Messages.INSTANCE.cumulativeAmount());
		loanResults.setText(0, 4, Messages.INSTANCE.balance());
		loanResults.insertRow(1);
	}
	
	@Override
	public void prepare(LoanResult result) {
		int i = 1;
		NumberFormat fmt = NumberFormat.getDecimalFormat();
		if (result != null) {
			BigDecimal p = result.getPayment();
			if (p != null) {
				payment.setText(fmt.format(p));
			}
			Set<Payment> schedule = result.getPaymentSchedule();
			for (Payment payment : schedule) {
				loanResults.setText(i, 0, String.valueOf(payment.getPeriod()));
				loanResults.setText(i, 1, fmt.format(payment.getPrincipalAmount()));
				loanResults.setText(i, 2, fmt.format(payment.getInterestAmount()));
				loanResults.setText(i, 3, fmt.format(payment.getCumulativeAmount()));
				loanResults.setText(i, 4, fmt.format(payment.getBalance()));
				i++;
			}
		}
	}
	
	@Override
	public void handle(Throwable caught) {
		error.setText(caught.getLocalizedMessage());
	}
	
}
