package py.com.abiti.esystem.egym.event;

import com.vaadin.ui.UI;

public class LogoutEvent {
	public LogoutEvent(){
		UI.getCurrent().getPage().setUriFragment("");
	}
}
