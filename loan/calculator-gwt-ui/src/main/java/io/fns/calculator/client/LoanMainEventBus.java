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
package io.fns.calculator.client;

import io.fns.calculator.client.component.LoanPlaceService;
import io.fns.calculator.client.presenter.LoanMainPresenterImpl;
import io.fns.calculator.client.presenter.NotFoundPresenterImpl;
import io.fns.calculator.model.Loan;

import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.PlaceService;
import com.mvp4g.client.event.EventBusWithLookup;

/**
 * @author Chris Phillipson
 * 
 */
@Debug(logLevel = LogLevel.DETAILED)
@PlaceService(LoanPlaceService.class)
@Events(module = LoanMainModule.class, startPresenter = LoanMainPresenterImpl.class, historyOnStart = true)
@Filters(filterClasses = {}, forceFilters = true)
public interface LoanMainEventBus extends EventBusWithLookup {
	
	// bootstrap

	@InitHistory
	@Event(handlers = LoanMainPresenterImpl.class)
	void init();
	
	// exception cases

	@NotFoundHistory
	@Event(handlers = NotFoundPresenterImpl.class)
	void show404();
	
	@Event(handlers = LoanMainPresenterImpl.class)
	void handle(Throwable caught);
	
	// loan calculation

	@Event(handlers = LoanMainPresenterImpl.class)
	void submit(Loan loan);
	
}
