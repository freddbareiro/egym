/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.Cuenta;
import py.com.abiti.esystem.egym.model.PagoCuenta;

/**
 *
 * @author matia
 */
public class PagoCuentaJpaController implements Serializable {

    public PagoCuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoCuenta pagoCuenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = pagoCuenta.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getCuenta());
                pagoCuenta.setCuenta(cuenta);
            }
            em.persist(pagoCuenta);
            if (cuenta != null) {
                cuenta.getPagoCuentaList().add(pagoCuenta);
                cuenta = em.merge(cuenta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoCuenta pagoCuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoCuenta persistentPagoCuenta = em.find(PagoCuenta.class, pagoCuenta.getPagoCuenta());
            Cuenta cuentaOld = persistentPagoCuenta.getCuenta();
            Cuenta cuentaNew = pagoCuenta.getCuenta();
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getCuenta());
                pagoCuenta.setCuenta(cuentaNew);
            }
            pagoCuenta = em.merge(pagoCuenta);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.getPagoCuentaList().remove(pagoCuenta);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.getPagoCuentaList().add(pagoCuenta);
                cuentaNew = em.merge(cuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoCuenta.getPagoCuenta();
                if (findPagoCuenta(id) == null) {
                    throw new NonexistentEntityException("The pagoCuenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoCuenta pagoCuenta;
            try {
                pagoCuenta = em.getReference(PagoCuenta.class, id);
                pagoCuenta.getPagoCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoCuenta with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuenta = pagoCuenta.getCuenta();
            if (cuenta != null) {
                cuenta.getPagoCuentaList().remove(pagoCuenta);
                cuenta = em.merge(cuenta);
            }
            em.remove(pagoCuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoCuenta> findPagoCuentaEntities() {
        return findPagoCuentaEntities(true, -1, -1);
    }

    public List<PagoCuenta> findPagoCuentaEntities(int maxResults, int firstResult) {
        return findPagoCuentaEntities(false, maxResults, firstResult);
    }

    private List<PagoCuenta> findPagoCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoCuenta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PagoCuenta findPagoCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoCuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoCuenta> rt = cq.from(PagoCuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
