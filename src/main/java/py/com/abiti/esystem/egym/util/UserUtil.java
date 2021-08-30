package py.com.abiti.esystem.egym.util;

import com.vaadin.server.VaadinSession;

import py.com.abiti.esystem.egym.jpa.JpaUsuario;
import py.com.abiti.esystem.egym.model.Usuario;






public class UserUtil {
	
	private static final String KEY = "currentuser";
	private static JpaUsuario jpaUsuario = new JpaUsuario(JpaUtil.getEntityManagerFactory());
	
	public static void setUsuario(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
	}
	
	public static Usuario getUsuario() {
    	return (Usuario) VaadinSession.getCurrent().getAttribute(KEY); 
    }
	
	public static void set(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
    }
	
	public static boolean isLoggedIn() {
        return getUsuario() != null;
    }

}
