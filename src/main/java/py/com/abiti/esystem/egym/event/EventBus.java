package py.com.abiti.esystem.egym.event;

import py.com.abiti.esystem.egym.EgymUI;

public class EventBus {

	public static void register(final Object listener){
		EgymUI.getEventBus().register(listener);
	}
	
	public static void unregister(final Object listener){
		EgymUI.getEventBus().unregister(listener);
	}
	
	public static void post(final Object listener){
		EgymUI.getEventBus().post(listener);
	}
}
