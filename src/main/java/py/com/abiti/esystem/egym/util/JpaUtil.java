package py.com.abiti.esystem.egym.util;



import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	// Para PostgreSQL
	private static final EntityManagerFactory emf;
	static {
		try {
			emf = Persistence.createEntityManagerFactory("EgymSysPU");

		} catch (Throwable t) {
			t.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

}