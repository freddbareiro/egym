package py.com.abiti.esystem.egym.jpa;

import javax.persistence.EntityManagerFactory;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;

import py.com.abiti.esystem.egym.controller.ClienteJpaController;

public class JpaCliente extends ClienteJpaController {

	public JpaCliente(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
