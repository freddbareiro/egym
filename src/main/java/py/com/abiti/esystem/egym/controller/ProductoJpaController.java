/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.esystem.egym.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.abiti.esystem.egym.model.CuentaProducto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.esystem.egym.controller.exceptions.IllegalOrphanException;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.Producto;

/**
 *
 * @author matia
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getCuentaProductoList() == null) {
            producto.setCuentaProductoList(new ArrayList<CuentaProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CuentaProducto> attachedCuentaProductoList = new ArrayList<CuentaProducto>();
            for (CuentaProducto cuentaProductoListCuentaProductoToAttach : producto.getCuentaProductoList()) {
                cuentaProductoListCuentaProductoToAttach = em.getReference(cuentaProductoListCuentaProductoToAttach.getClass(), cuentaProductoListCuentaProductoToAttach.getCuentaProducto());
                attachedCuentaProductoList.add(cuentaProductoListCuentaProductoToAttach);
            }
            producto.setCuentaProductoList(attachedCuentaProductoList);
            em.persist(producto);
            for (CuentaProducto cuentaProductoListCuentaProducto : producto.getCuentaProductoList()) {
                Producto oldProductoOfCuentaProductoListCuentaProducto = cuentaProductoListCuentaProducto.getProducto();
                cuentaProductoListCuentaProducto.setProducto(producto);
                cuentaProductoListCuentaProducto = em.merge(cuentaProductoListCuentaProducto);
                if (oldProductoOfCuentaProductoListCuentaProducto != null) {
                    oldProductoOfCuentaProductoListCuentaProducto.getCuentaProductoList().remove(cuentaProductoListCuentaProducto);
                    oldProductoOfCuentaProductoListCuentaProducto = em.merge(oldProductoOfCuentaProductoListCuentaProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getProducto());
            List<CuentaProducto> cuentaProductoListOld = persistentProducto.getCuentaProductoList();
            List<CuentaProducto> cuentaProductoListNew = producto.getCuentaProductoList();
            List<String> illegalOrphanMessages = null;
            for (CuentaProducto cuentaProductoListOldCuentaProducto : cuentaProductoListOld) {
                if (!cuentaProductoListNew.contains(cuentaProductoListOldCuentaProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaProducto " + cuentaProductoListOldCuentaProducto + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CuentaProducto> attachedCuentaProductoListNew = new ArrayList<CuentaProducto>();
            for (CuentaProducto cuentaProductoListNewCuentaProductoToAttach : cuentaProductoListNew) {
                cuentaProductoListNewCuentaProductoToAttach = em.getReference(cuentaProductoListNewCuentaProductoToAttach.getClass(), cuentaProductoListNewCuentaProductoToAttach.getCuentaProducto());
                attachedCuentaProductoListNew.add(cuentaProductoListNewCuentaProductoToAttach);
            }
            cuentaProductoListNew = attachedCuentaProductoListNew;
            producto.setCuentaProductoList(cuentaProductoListNew);
            producto = em.merge(producto);
            for (CuentaProducto cuentaProductoListNewCuentaProducto : cuentaProductoListNew) {
                if (!cuentaProductoListOld.contains(cuentaProductoListNewCuentaProducto)) {
                    Producto oldProductoOfCuentaProductoListNewCuentaProducto = cuentaProductoListNewCuentaProducto.getProducto();
                    cuentaProductoListNewCuentaProducto.setProducto(producto);
                    cuentaProductoListNewCuentaProducto = em.merge(cuentaProductoListNewCuentaProducto);
                    if (oldProductoOfCuentaProductoListNewCuentaProducto != null && !oldProductoOfCuentaProductoListNewCuentaProducto.equals(producto)) {
                        oldProductoOfCuentaProductoListNewCuentaProducto.getCuentaProductoList().remove(cuentaProductoListNewCuentaProducto);
                        oldProductoOfCuentaProductoListNewCuentaProducto = em.merge(oldProductoOfCuentaProductoListNewCuentaProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CuentaProducto> cuentaProductoListOrphanCheck = producto.getCuentaProductoList();
            for (CuentaProducto cuentaProductoListOrphanCheckCuentaProducto : cuentaProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the CuentaProducto " + cuentaProductoListOrphanCheckCuentaProducto + " in its cuentaProductoList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
