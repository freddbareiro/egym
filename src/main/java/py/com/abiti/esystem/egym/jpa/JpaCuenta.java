package py.com.abiti.esystem.egym.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.abiti.esystem.egym.controller.CuentaJpaController;

public class JpaCuenta extends CuentaJpaController {

	public JpaCuenta(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
