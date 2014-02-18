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
import io.fns.calculator.client.view.NotFoundView;
import io.fns.calculator.client.view.NotFoundView.NotFoundPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Chris Phillipson
 * 
 */
public class NotFoundViewImpl extends ReverseCompositeView<NotFoundPresenter> implements NotFoundView {
	
	@UiTemplate("NotFoundView.ui.xml")
	interface NotFoundViewUiBinder extends UiBinder<Widget, NotFoundViewImpl> {
	}
	
	private static final NotFoundViewUiBinder binder = GWT.create(NotFoundViewUiBinder.class);
	
	@UiField
	Label message;
	
	public NotFoundViewImpl() {
		initWidget(binder.createAndBindUi(this));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see io.fns.calculator.client.view.NotFoundView#show404()
	 */
	@Override
	public void show404() {
		message.setText(Messages.INSTANCE.oops());
	}
	
}
