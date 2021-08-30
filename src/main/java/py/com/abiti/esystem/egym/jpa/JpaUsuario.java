package py.com.abiti.esystem.egym.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import py.com.abiti.esystem.egym.controller.UsuarioJpaController;
import py.com.abiti.esystem.egym.model.Usuario;









public class JpaUsuario extends UsuarioJpaController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JpaUsuario(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean login(String user, String pass){
		EntityManager em = getEntityManager();
		Boolean adelante = false;
		//String password = "'" + Crypto.encriptarPass(pass)+ "'";
		//password = pass;
		try {
			String sql = "select * from usuario where alias = ?1 and clave = ?2" ;
			Query q = em.createNativeQuery(sql);
			q.setParameter(1, user);
			q.setParameter(2, pass);
			if (    !(q.getSingleResult().equals(null))   ) {
				if( !q.getSingleResult().toString().equals("0")){
					adelante = true;
				}
			}
			
			
		} catch (Exception e) {
			//Notification.show("Error al buscar datos " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return adelante;
	}
	
	
	
	public Usuario findUsuarioByUser(String user) {
    	EntityManager em = getEntityManager();
    	Usuario usuario = null;
    	try {
    		
    		
            javax.persistence.criteria.CriteriaBuilder cb = em.getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
            javax.persistence.criteria.Root<Usuario> u = cq.from(Usuario.class);
            cq.where(cb.equal(u.get("alias"), user));
            Query q = em.createQuery(cq).setHint("eclipselink.refresh", true).setMaxResults(1);
            usuario = (Usuario) q.getSingleResult();
    	} catch (Exception ex) {
    		// retornara null
        } finally {
            em.close();
        }
    	
    	return usuario;
    }

}
