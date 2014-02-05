package io.fns.calculator.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class App implements EntryPoint {
	
	private Logger log = Logger.getLogger(App.class.getName());
	
	@Override
	public void onModuleLoad() {
		final Mvp4gModule mvpModule = (Mvp4gModule) GWT.create(LoanMainModule.class);
		mvpModule.createAndStartModule();
		RootLayoutPanel.get().add((Widget) mvpModule.getStartView());
		
		// See
		// http://www.summa-tech.com/blog/2012/06/11/7-tips-for-exception-handling-in-gwt/
		handleUncaughtClientSideExceptions(mvpModule.getEventBus());
	}
	
	private void handleUncaughtClientSideExceptions(final EventBus eventBus) {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(final Throwable e) {
				log.log(Level.SEVERE, "Client-side exception caught!", e);
				final Throwable unwrapped = unwrap(e);
				if (eventBus instanceof EventBusWithLookup) {
					final EventBusWithLookup bus = (EventBusWithLookup) eventBus;
					bus.dispatch("handle", unwrapped);
				}
			}
			
			public Throwable unwrap(final Throwable e) {
				if (e instanceof UmbrellaException) {
					final UmbrellaException ue = (UmbrellaException) e;
					if (ue.getCauses().size() == 1) {
						return unwrap(ue);
					}
				}
				return e;
			}
		});
	}

}
