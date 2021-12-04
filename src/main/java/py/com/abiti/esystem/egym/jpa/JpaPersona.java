package py.com.abiti.esystem.egym.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;


import com.vaadin.ui.Notification;

import py.com.abiti.esystem.egym.controller.PersonaJpaController;
import py.com.abiti.esystem.egym.model.Persona;


public class JpaPersona extends PersonaJpaController  {

	public JpaPersona(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public List<Persona> encontrarPersonaSinSerCliente(){
		
		EntityManager em = getEntityManager();
		
		List<Persona> listPersona = null;
		
		String sqlQry = "";
		
		try {
			
				
				sqlQry = "select p.* from persona p where p.persona not in (select c.persona from cliente c) ";
				
			
			Query q = em.createNativeQuery(sqlQry, Persona.class);
			listPersona = q.getResultList();
		} catch (Exception e) {
			Notification.show(e.getMessage() +" Error al buscar persona sin cliente vinculado", Notification.TYPE_ERROR_MESSAGE );
		}finally {
			em.close();
		}
	
		return listPersona;
	
	}

}
