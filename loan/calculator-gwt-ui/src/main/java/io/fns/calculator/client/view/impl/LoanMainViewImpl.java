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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Loan form
 * 
 * @author Chris Phillipson
 * 
 */
public class LoanMainViewImpl extends ReverseCompositeView<LoanMainPresenter> implements LoanMainView {
	
	@UiTemplate("LoanMainView.ui.xml")
	interface LoanMainViewUiBinder extends UiBinder<Widget, LoanMainViewImpl> {
	}
	
	private static final LoanMainViewUiBinder binder = GWT.create(LoanMainViewUiBinder.class);
	
	/**
	 * The key provider that provides the period for the payment.
	 */
	private static final ProvidesKey<Payment> KEY_PROVIDER = new ProvidesKey<Payment>() {
		@Override
		public Object getKey(Payment item) {
			return item == null ? null : item.getPeriod();
		}
	};
	
	private ListHandler<Payment> data;
	
	/**
	 * The provider that holds the list of payments from the loan schedule.
	 */
	private ListDataProvider<Payment> dataProvider = new ListDataProvider<Payment>();
	
	private Set<Column<Payment, String>> columns = new HashSet<Column<Payment, String>>();
	
	private static NumberFormat fmt = NumberFormat.getCurrencyFormat();
	
	@UiField
	FormPanel form;
	
	@UiField
	TextBox debtor, amount, interest, years;
	
	@UiField(provided = true)
	ListBox compounded;
	
	/**
	 * The main loanResults.
	 */
	@UiField(provided = true)
	DataGrid<Payment> loanResults;
	
	@UiField
	Button btnSubmit;
	
	@UiField
	Button btnReset;
	
	@UiField
	Label payment, error;
	
	public LoanMainViewImpl() {
		initialize();
		initWidget(binder.createAndBindUi(this));
		debtor.setFocus(true);
		btnSubmit.setEnabled(false);
	}
	
	private void initialize() {
		initializeCompoundedOptions();
		initializeGrid();
	}
	
	private void initializeCompoundedOptions() {
		compounded = new ListBox();
		Compounded[] list = Compounded.values();
		for (Compounded c : list) {
			compounded.addItem(Messages.INSTANCE.getString(c.getType()), c.name());
		}
	}
	
	protected void initializeGrid() {
		
		/*
		 * Set a key provider that provides a unique key for each payment.
		 */
		loanResults = new DataGrid<Payment>(KEY_PROVIDER);
		
		/*
		 * Do not refresh the headers every time the data is updated. The footer
		 * depends on the current data, so we do not disable auto refresh on the
		 * footer.
		 */
		loanResults.setAutoHeaderRefreshDisabled(true);
		
		// Set the message to display when the table is empty.
		loanResults.setEmptyTableWidget(new Label(Messages.INSTANCE.no_results()));
		
		dataProvider.addDataDisplay(loanResults);
		
	}
	
	protected Set<Column<Payment, String>> allColumns() {
		return columns;
	}
	
	// see
	// http://stackoverflow.com/questions/3772480/remove-all-columns-from-a-celltable
	// concrete classes are forced to maintain a handle on all columns added
	protected void resetTableColumns() {
		for (final Column<Payment, String> column : allColumns()) {
			loanResults.removeColumn(column);
		}
		allColumns().clear();
	}
	
	protected void seedData(Set<Payment> schedule) {
		resetTableColumns();
		
		// Attach a column sort handler to the ListDataProvider to sort the
		// list.
		data = new ListHandler<Payment>(new ArrayList<Payment>(schedule));
		loanResults.addColumnSortHandler(data);
		loanResults.setRowData(data.getList());
		
		// Initialize the columns.
		initTableColumns(data);
		
	}
	
	protected void initTableColumns(ListHandler<Payment> data) {
		Column<Payment, String> period = new Column<Payment, String>(new TextCell()) {
			@Override
			public String getValue(Payment payment) {
				return String.valueOf(payment.getPeriod());
			}
		};
		period.setSortable(true);
		data.setComparator(period, new Comparator<Payment>() {
			@Override
			public int compare(Payment o1, Payment o2) {
				String p1 = String.valueOf(o1.getPeriod());
				String p2 = String.valueOf(o2.getPeriod());
				return p1.compareTo(p2);
			}
		});
		loanResults.addColumn(period, Messages.INSTANCE.period());
		loanResults.setColumnWidth(period, 20, Unit.PCT);
		columns.add(period);
		
		Column<Payment, String> principalAmount = new Column<Payment, String>(new TextCell()) {
			@Override
			public String getValue(Payment payment) {
				return fmt.format(payment.getPrincipalAmount());
			}
		};
		principalAmount.setSortable(true);
		data.setComparator(principalAmount, new Comparator<Payment>() {
			@Override
			public int compare(Payment o1, Payment o2) {
				String p1 = fmt.format(o1.getPrincipalAmount());
				String p2 = fmt.format(o2.getPrincipalAmount());
				return p1.compareTo(p2);
			}
		});
		loanResults.addColumn(principalAmount, Messages.INSTANCE.principalAmount());
		loanResults.setColumnWidth(principalAmount, 20, Unit.PCT);
		columns.add(principalAmount);
		
		Column<Payment, String> interestAmount = new Column<Payment, String>(new TextCell()) {
			@Override
			public String getValue(Payment payment) {
				return fmt.format(payment.getInterestAmount());
			}
		};
		interestAmount.setSortable(true);
		data.setComparator(interestAmount, new Comparator<Payment>() {
			@Override
			public int compare(Payment o1, Payment o2) {
				String p1 = fmt.format(o1.getInterestAmount());
				String p2 = fmt.format(o2.getInterestAmount());
				return p1.compareTo(p2);
			}
		});
		loanResults.addColumn(interestAmount, Messages.INSTANCE.interestAmount());
		loanResults.setColumnWidth(interestAmount, 20, Unit.PCT);
		columns.add(interestAmount);
		
		Column<Payment, String> cumulativeAmount = new Column<Payment, String>(new TextCell()) {
			@Override
			public String getValue(Payment payment) {
				return fmt.format(payment.getCumulativeAmount());
			}
		};
		cumulativeAmount.setSortable(true);
		data.setComparator(cumulativeAmount, new Comparator<Payment>() {
			@Override
			public int compare(Payment o1, Payment o2) {
				String p1 = fmt.format(o1.getCumulativeAmount());
				String p2 = fmt.format(o2.getCumulativeAmount());
				return p1.compareTo(p2);
			}
		});
		loanResults.addColumn(cumulativeAmount, Messages.INSTANCE.cumulativeAmount());
		loanResults.setColumnWidth(cumulativeAmount, 20, Unit.PCT);
		columns.add(cumulativeAmount);
		
		Column<Payment, String> balance = new Column<Payment, String>(new TextCell()) {
			@Override
			public String getValue(Payment payment) {
				return fmt.format(payment.getBalance());
			}
		};
		balance.setSortable(true);
		data.setComparator(balance, new Comparator<Payment>() {
			@Override
			public int compare(Payment o1, Payment o2) {
				String p1 = fmt.format(o1.getBalance());
				String p2 = fmt.format(o2.getBalance());
				return p1.compareTo(p2);
			}
		});
		loanResults.addColumn(balance, Messages.INSTANCE.balance());
		loanResults.setColumnWidth(balance, 20, Unit.PCT);
		columns.add(balance);
		
	}
	
	@Override
	public void prepare(LoanResult result) {
		if (result != null) {
			BigDecimal p = result.getPayment();
			if (p != null) {
				payment.setText(fmt.format(p));
			}
			seedData(result.getPaymentSchedule());
		}
	}
	
	@Override
	public void handle(Throwable caught) {
		error.setText(caught.getLocalizedMessage());
	}
	
	@UiHandler(value = { "debtor", "amount", "interest", "years", "compounded" })
	public void validateInput(KeyUpEvent event) {
		String d = debtor.getValue();
		String a = amount.getValue();
		String i = interest.getValue();
		String y = years.getValue();
		String c = compounded.getValue(compounded.getSelectedIndex());
		
		if (isNotBlank(d) && isNotBlank(a) && isNotBlank(i) && isNotBlank(y) && isNotBlank(c)) {
			btnSubmit.setEnabled(true);
		} else {
			btnSubmit.setEnabled(false);
		}
	}
	
	@UiHandler("btnSubmit")
	public void submitLoan(ClickEvent event) {
		String d = debtor.getValue();
		BigDecimal a = new BigDecimal(amount.getValue());
		double i = Double.parseDouble(interest.getValue());
		int y = Integer.parseInt(years.getValue());
		Compounded c = Compounded.valueOf(compounded.getValue(compounded.getSelectedIndex()));
		Loan l = new Loan(d, a, i, y, c);
		getPresenter().getEventBus().submit(l);
	}
	
	@UiHandler("btnReset")
	public void resetLoan(ClickEvent event) {
		form.reset();
		btnSubmit.setEnabled(false);
		payment.setText(null);
		resetTableColumns();
	}
	
	private boolean isNotBlank(String value) {
		boolean result = true;
		if (value == null || value != null && value.trim().isEmpty()) {
			result = false;
		}
		return result;
	}
	
}
