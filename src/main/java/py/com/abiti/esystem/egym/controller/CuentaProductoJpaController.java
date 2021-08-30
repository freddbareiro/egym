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
import py.com.abiti.esystem.egym.model.CuentaProducto;
import py.com.abiti.esystem.egym.model.Producto;


/**
 *
 * @author matia
 */
public class CuentaProductoJpaController implements Serializable {

    public CuentaProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaProducto cuentaProducto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = cuentaProducto.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getCuenta());
                cuentaProducto.setCuenta(cuenta);
            }
            Producto producto = cuentaProducto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getProducto());
                cuentaProducto.setProducto(producto);
            }
            em.persist(cuentaProducto);
            if (cuenta != null) {
                cuenta.getCuentaProductoList().add(cuentaProducto);
                cuenta = em.merge(cuenta);
            }
            if (producto != null) {
                producto.getCuentaProductoList().add(cuentaProducto);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaProducto cuentaProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuentaProducto persistentCuentaProducto = em.find(CuentaProducto.class, cuentaProducto.getCuentaProducto());
            Cuenta cuentaOld = persistentCuentaProducto.getCuenta();
            Cuenta cuentaNew = cuentaProducto.getCuenta();
            Producto productoOld = persistentCuentaProducto.getProducto();
            Producto productoNew = cuentaProducto.getProducto();
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getCuenta());
                cuentaProducto.setCuenta(cuentaNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getProducto());
                cuentaProducto.setProducto(productoNew);
            }
            cuentaProducto = em.merge(cuentaProducto);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.getCuentaProductoList().remove(cuentaProducto);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.getCuentaProductoList().add(cuentaProducto);
                cuentaNew = em.merge(cuentaNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getCuentaProductoList().remove(cuentaProducto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getCuentaProductoList().add(cuentaProducto);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentaProducto.getCuentaProducto();
                if (findCuentaProducto(id) == null) {
                    throw new NonexistentEntityException("The cuentaProducto with id " + id + " no longer exists.");
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
            CuentaProducto cuentaProducto;
            try {
                cuentaProducto = em.getReference(CuentaProducto.class, id);
                cuentaProducto.getCuentaProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaProducto with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuenta = cuentaProducto.getCuenta();
            if (cuenta != null) {
                cuenta.getCuentaProductoList().remove(cuentaProducto);
                cuenta = em.merge(cuenta);
            }
            Producto producto = cuentaProducto.getProducto();
            if (producto != null) {
                producto.getCuentaProductoList().remove(cuentaProducto);
                producto = em.merge(producto);
            }
            em.remove(cuentaProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaProducto> findCuentaProductoEntities() {
        return findCuentaProductoEntities(true, -1, -1);
    }

    public List<CuentaProducto> findCuentaProductoEntities(int maxResults, int firstResult) {
        return findCuentaProductoEntities(false, maxResults, firstResult);
    }

    private List<CuentaProducto> findCuentaProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaProducto.class));
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

    public CuentaProducto findCuentaProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaProducto> rt = cq.from(CuentaProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
