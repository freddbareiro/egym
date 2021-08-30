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

import py.com.abiti.esystem.egym.controller.exceptions.IllegalOrphanException;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.Cliente;
import py.com.abiti.esystem.egym.model.PlanPago;
import py.com.abiti.esystem.egym.model.PagoCuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.esystem.egym.model.Cuenta;
import py.com.abiti.esystem.egym.model.CuentaProducto;

/**
 *
 * @author matia
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) {
        if (cuenta.getPagoCuentaList() == null) {
            cuenta.setPagoCuentaList(new ArrayList<PagoCuenta>());
        }
        if (cuenta.getCuentaProductoList() == null) {
            cuenta.setCuentaProductoList(new ArrayList<CuentaProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = cuenta.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getCliente());
                cuenta.setCliente(cliente);
            }
            PlanPago planPago = cuenta.getPlanPago();
            if (planPago != null) {
                planPago = em.getReference(planPago.getClass(), planPago.getPlanPago());
                cuenta.setPlanPago(planPago);
            }
            List<PagoCuenta> attachedPagoCuentaList = new ArrayList<PagoCuenta>();
            for (PagoCuenta pagoCuentaListPagoCuentaToAttach : cuenta.getPagoCuentaList()) {
                pagoCuentaListPagoCuentaToAttach = em.getReference(pagoCuentaListPagoCuentaToAttach.getClass(), pagoCuentaListPagoCuentaToAttach.getPagoCuenta());
                attachedPagoCuentaList.add(pagoCuentaListPagoCuentaToAttach);
            }
            cuenta.setPagoCuentaList(attachedPagoCuentaList);
            List<CuentaProducto> attachedCuentaProductoList = new ArrayList<CuentaProducto>();
            for (CuentaProducto cuentaProductoListCuentaProductoToAttach : cuenta.getCuentaProductoList()) {
                cuentaProductoListCuentaProductoToAttach = em.getReference(cuentaProductoListCuentaProductoToAttach.getClass(), cuentaProductoListCuentaProductoToAttach.getCuentaProducto());
                attachedCuentaProductoList.add(cuentaProductoListCuentaProductoToAttach);
            }
            cuenta.setCuentaProductoList(attachedCuentaProductoList);
            em.persist(cuenta);
            if (cliente != null) {
                cliente.getCuentaList().add(cuenta);
                cliente = em.merge(cliente);
            }
            if (planPago != null) {
                planPago.getCuentaList().add(cuenta);
                planPago = em.merge(planPago);
            }
            for (PagoCuenta pagoCuentaListPagoCuenta : cuenta.getPagoCuentaList()) {
                Cuenta oldCuentaOfPagoCuentaListPagoCuenta = pagoCuentaListPagoCuenta.getCuenta();
                pagoCuentaListPagoCuenta.setCuenta(cuenta);
                pagoCuentaListPagoCuenta = em.merge(pagoCuentaListPagoCuenta);
                if (oldCuentaOfPagoCuentaListPagoCuenta != null) {
                    oldCuentaOfPagoCuentaListPagoCuenta.getPagoCuentaList().remove(pagoCuentaListPagoCuenta);
                    oldCuentaOfPagoCuentaListPagoCuenta = em.merge(oldCuentaOfPagoCuentaListPagoCuenta);
                }
            }
            for (CuentaProducto cuentaProductoListCuentaProducto : cuenta.getCuentaProductoList()) {
                Cuenta oldCuentaOfCuentaProductoListCuentaProducto = cuentaProductoListCuentaProducto.getCuenta();
                cuentaProductoListCuentaProducto.setCuenta(cuenta);
                cuentaProductoListCuentaProducto = em.merge(cuentaProductoListCuentaProducto);
                if (oldCuentaOfCuentaProductoListCuentaProducto != null) {
                    oldCuentaOfCuentaProductoListCuentaProducto.getCuentaProductoList().remove(cuentaProductoListCuentaProducto);
                    oldCuentaOfCuentaProductoListCuentaProducto = em.merge(oldCuentaOfCuentaProductoListCuentaProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getCuenta());
            Cliente clienteOld = persistentCuenta.getCliente();
            Cliente clienteNew = cuenta.getCliente();
            PlanPago planPagoOld = persistentCuenta.getPlanPago();
            PlanPago planPagoNew = cuenta.getPlanPago();
            List<PagoCuenta> pagoCuentaListOld = persistentCuenta.getPagoCuentaList();
            List<PagoCuenta> pagoCuentaListNew = cuenta.getPagoCuentaList();
            List<CuentaProducto> cuentaProductoListOld = persistentCuenta.getCuentaProductoList();
            List<CuentaProducto> cuentaProductoListNew = cuenta.getCuentaProductoList();
            List<String> illegalOrphanMessages = null;
            for (PagoCuenta pagoCuentaListOldPagoCuenta : pagoCuentaListOld) {
                if (!pagoCuentaListNew.contains(pagoCuentaListOldPagoCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagoCuenta " + pagoCuentaListOldPagoCuenta + " since its cuenta field is not nullable.");
                }
            }
            for (CuentaProducto cuentaProductoListOldCuentaProducto : cuentaProductoListOld) {
                if (!cuentaProductoListNew.contains(cuentaProductoListOldCuentaProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaProducto " + cuentaProductoListOldCuentaProducto + " since its cuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getCliente());
                cuenta.setCliente(clienteNew);
            }
            if (planPagoNew != null) {
                planPagoNew = em.getReference(planPagoNew.getClass(), planPagoNew.getPlanPago());
                cuenta.setPlanPago(planPagoNew);
            }
            List<PagoCuenta> attachedPagoCuentaListNew = new ArrayList<PagoCuenta>();
            for (PagoCuenta pagoCuentaListNewPagoCuentaToAttach : pagoCuentaListNew) {
                pagoCuentaListNewPagoCuentaToAttach = em.getReference(pagoCuentaListNewPagoCuentaToAttach.getClass(), pagoCuentaListNewPagoCuentaToAttach.getPagoCuenta());
                attachedPagoCuentaListNew.add(pagoCuentaListNewPagoCuentaToAttach);
            }
            pagoCuentaListNew = attachedPagoCuentaListNew;
            cuenta.setPagoCuentaList(pagoCuentaListNew);
            List<CuentaProducto> attachedCuentaProductoListNew = new ArrayList<CuentaProducto>();
            for (CuentaProducto cuentaProductoListNewCuentaProductoToAttach : cuentaProductoListNew) {
                cuentaProductoListNewCuentaProductoToAttach = em.getReference(cuentaProductoListNewCuentaProductoToAttach.getClass(), cuentaProductoListNewCuentaProductoToAttach.getCuentaProducto());
                attachedCuentaProductoListNew.add(cuentaProductoListNewCuentaProductoToAttach);
            }
            cuentaProductoListNew = attachedCuentaProductoListNew;
            cuenta.setCuentaProductoList(cuentaProductoListNew);
            cuenta = em.merge(cuenta);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getCuentaList().remove(cuenta);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getCuentaList().add(cuenta);
                clienteNew = em.merge(clienteNew);
            }
            if (planPagoOld != null && !planPagoOld.equals(planPagoNew)) {
                planPagoOld.getCuentaList().remove(cuenta);
                planPagoOld = em.merge(planPagoOld);
            }
            if (planPagoNew != null && !planPagoNew.equals(planPagoOld)) {
                planPagoNew.getCuentaList().add(cuenta);
                planPagoNew = em.merge(planPagoNew);
            }
            for (PagoCuenta pagoCuentaListNewPagoCuenta : pagoCuentaListNew) {
                if (!pagoCuentaListOld.contains(pagoCuentaListNewPagoCuenta)) {
                    Cuenta oldCuentaOfPagoCuentaListNewPagoCuenta = pagoCuentaListNewPagoCuenta.getCuenta();
                    pagoCuentaListNewPagoCuenta.setCuenta(cuenta);
                    pagoCuentaListNewPagoCuenta = em.merge(pagoCuentaListNewPagoCuenta);
                    if (oldCuentaOfPagoCuentaListNewPagoCuenta != null && !oldCuentaOfPagoCuentaListNewPagoCuenta.equals(cuenta)) {
                        oldCuentaOfPagoCuentaListNewPagoCuenta.getPagoCuentaList().remove(pagoCuentaListNewPagoCuenta);
                        oldCuentaOfPagoCuentaListNewPagoCuenta = em.merge(oldCuentaOfPagoCuentaListNewPagoCuenta);
                    }
                }
            }
            for (CuentaProducto cuentaProductoListNewCuentaProducto : cuentaProductoListNew) {
                if (!cuentaProductoListOld.contains(cuentaProductoListNewCuentaProducto)) {
                    Cuenta oldCuentaOfCuentaProductoListNewCuentaProducto = cuentaProductoListNewCuentaProducto.getCuenta();
                    cuentaProductoListNewCuentaProducto.setCuenta(cuenta);
                    cuentaProductoListNewCuentaProducto = em.merge(cuentaProductoListNewCuentaProducto);
                    if (oldCuentaOfCuentaProductoListNewCuentaProducto != null && !oldCuentaOfCuentaProductoListNewCuentaProducto.equals(cuenta)) {
                        oldCuentaOfCuentaProductoListNewCuentaProducto.getCuentaProductoList().remove(cuentaProductoListNewCuentaProducto);
                        oldCuentaOfCuentaProductoListNewCuentaProducto = em.merge(oldCuentaOfCuentaProductoListNewCuentaProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getCuenta();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getCuenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PagoCuenta> pagoCuentaListOrphanCheck = cuenta.getPagoCuentaList();
            for (PagoCuenta pagoCuentaListOrphanCheckPagoCuenta : pagoCuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the PagoCuenta " + pagoCuentaListOrphanCheckPagoCuenta + " in its pagoCuentaList field has a non-nullable cuenta field.");
            }
            List<CuentaProducto> cuentaProductoListOrphanCheck = cuenta.getCuentaProductoList();
            for (CuentaProducto cuentaProductoListOrphanCheckCuentaProducto : cuentaProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the CuentaProducto " + cuentaProductoListOrphanCheckCuentaProducto + " in its cuentaProductoList field has a non-nullable cuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cliente = cuenta.getCliente();
            if (cliente != null) {
                cliente.getCuentaList().remove(cuenta);
                cliente = em.merge(cliente);
            }
            PlanPago planPago = cuenta.getPlanPago();
            if (planPago != null) {
                planPago.getCuentaList().remove(cuenta);
                planPago = em.merge(planPago);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
