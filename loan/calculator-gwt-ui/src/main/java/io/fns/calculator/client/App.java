package io.fns.calculator.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.dispatcher.FilterawareDispatcher;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class App implements EntryPoint {
	
	private final MyGinjector ginjector = GWT.create(MyGinjector.class);
	private final Mvp4gModule mvp4gModule = GWT.create(Mvp4gModule.class);
	
	private Logger log = Logger.getLogger(App.class.getName());
	
	@Override
	public void onModuleLoad() {
		initRestyGwt();
		mvp4gModule.createAndStartModule();
		RootLayoutPanel.get().add((IsWidget) mvp4gModule.getStartView());
		
		// See
		// http://www.summa-tech.com/blog/2012/06/11/7-tips-for-exception-handling-in-gwt/
		handleUncaughtClientSideExceptions(mvp4gModule.getEventBus());
	}
	
	private void initRestyGwt() {
		FilterawareDispatcher filterawareDispatcher = ginjector.getFilterawareDispatcher();
		filterawareDispatcher.addFilter(ginjector.getXSRFTokenDispatcherFilter());
		filterawareDispatcher.addFilter(ginjector.getXSRFTokenCallbackDispatherFilter());
		Defaults.setDispatcher(filterawareDispatcher);
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
