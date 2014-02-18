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
package io.fns.calculator.client.component;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;

/**
 * @author Chris Phillipson
 *
 */
public class CaptionLayoutPanel extends CaptionPanel implements RequiresResize,ProvidesResize {
	
	@Override
	public void onResize() {
		if (getContentWidget() instanceof RequiresResize) {
			((RequiresResize) getContentWidget()).onResize();
		}
	}
}