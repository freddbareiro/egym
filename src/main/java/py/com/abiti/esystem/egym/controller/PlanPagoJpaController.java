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
import py.com.abiti.esystem.egym.model.Cuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.esystem.egym.controller.exceptions.IllegalOrphanException;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.PlanPago;

/**
 *
 * @author matia
 */
public class PlanPagoJpaController implements Serializable {

    public PlanPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlanPago planPago) {
        if (planPago.getCuentaList() == null) {
            planPago.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : planPago.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            planPago.setCuentaList(attachedCuentaList);
            em.persist(planPago);
            for (Cuenta cuentaListCuenta : planPago.getCuentaList()) {
                PlanPago oldPlanPagoOfCuentaListCuenta = cuentaListCuenta.getPlanPago();
                cuentaListCuenta.setPlanPago(planPago);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldPlanPagoOfCuentaListCuenta != null) {
                    oldPlanPagoOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldPlanPagoOfCuentaListCuenta = em.merge(oldPlanPagoOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlanPago planPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlanPago persistentPlanPago = em.find(PlanPago.class, planPago.getPlanPago());
            List<Cuenta> cuentaListOld = persistentPlanPago.getCuentaList();
            List<Cuenta> cuentaListNew = planPago.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its planPago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCuenta());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            planPago.setCuentaList(cuentaListNew);
            planPago = em.merge(planPago);
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    PlanPago oldPlanPagoOfCuentaListNewCuenta = cuentaListNewCuenta.getPlanPago();
                    cuentaListNewCuenta.setPlanPago(planPago);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldPlanPagoOfCuentaListNewCuenta != null && !oldPlanPagoOfCuentaListNewCuenta.equals(planPago)) {
                        oldPlanPagoOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldPlanPagoOfCuentaListNewCuenta = em.merge(oldPlanPagoOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = planPago.getPlanPago();
                if (findPlanPago(id) == null) {
                    throw new NonexistentEntityException("The planPago with id " + id + " no longer exists.");
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
            PlanPago planPago;
            try {
                planPago = em.getReference(PlanPago.class, id);
                planPago.getPlanPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = planPago.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PlanPago (" + planPago + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable planPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(planPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlanPago> findPlanPagoEntities() {
        return findPlanPagoEntities(true, -1, -1);
    }

    public List<PlanPago> findPlanPagoEntities(int maxResults, int firstResult) {
        return findPlanPagoEntities(false, maxResults, firstResult);
    }

    private List<PlanPago> findPlanPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlanPago.class));
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

    public PlanPago findPlanPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlanPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlanPago> rt = cq.from(PlanPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
