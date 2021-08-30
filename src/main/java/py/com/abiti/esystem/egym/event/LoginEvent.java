package py.com.abiti.esystem.egym.event;

import py.com.abiti.esystem.egym.model.Usuario;

public class LoginEvent {
	private Usuario user;
	
	public LoginEvent(Usuario user){
		this.user = user;
	}
	
	public Usuario getUser(){
		return user;
	}

}
