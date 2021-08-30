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
import py.com.abiti.esystem.egym.model.Persona;
import py.com.abiti.esystem.egym.model.Cuenta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import py.com.abiti.esystem.egym.controller.exceptions.IllegalOrphanException;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.Cliente;
/**
 *
 * @author matia
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getCuentaList() == null) {
            cliente.setCuentaList(new ArrayList<Cuenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getPersona());
                cliente.setPersona(persona);
            }
            List<Cuenta> attachedCuentaList = new ArrayList<Cuenta>();
            for (Cuenta cuentaListCuentaToAttach : cliente.getCuentaList()) {
                cuentaListCuentaToAttach = em.getReference(cuentaListCuentaToAttach.getClass(), cuentaListCuentaToAttach.getCuenta());
                attachedCuentaList.add(cuentaListCuentaToAttach);
            }
            cliente.setCuentaList(attachedCuentaList);
            em.persist(cliente);
            if (persona != null) {
                persona.getClienteList().add(cliente);
                persona = em.merge(persona);
            }
            for (Cuenta cuentaListCuenta : cliente.getCuentaList()) {
                Cliente oldClienteOfCuentaListCuenta = cuentaListCuenta.getCliente();
                cuentaListCuenta.setCliente(cliente);
                cuentaListCuenta = em.merge(cuentaListCuenta);
                if (oldClienteOfCuentaListCuenta != null) {
                    oldClienteOfCuentaListCuenta.getCuentaList().remove(cuentaListCuenta);
                    oldClienteOfCuentaListCuenta = em.merge(oldClienteOfCuentaListCuenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCliente());
            Persona personaOld = persistentCliente.getPersona();
            Persona personaNew = cliente.getPersona();
            List<Cuenta> cuentaListOld = persistentCliente.getCuentaList();
            List<Cuenta> cuentaListNew = cliente.getCuentaList();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaListOldCuenta : cuentaListOld) {
                if (!cuentaListNew.contains(cuentaListOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaListOldCuenta + " since its cliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getPersona());
                cliente.setPersona(personaNew);
            }
            List<Cuenta> attachedCuentaListNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaListNewCuentaToAttach : cuentaListNew) {
                cuentaListNewCuentaToAttach = em.getReference(cuentaListNewCuentaToAttach.getClass(), cuentaListNewCuentaToAttach.getCuenta());
                attachedCuentaListNew.add(cuentaListNewCuentaToAttach);
            }
            cuentaListNew = attachedCuentaListNew;
            cliente.setCuentaList(cuentaListNew);
            cliente = em.merge(cliente);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getClienteList().remove(cliente);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getClienteList().add(cliente);
                personaNew = em.merge(personaNew);
            }
            for (Cuenta cuentaListNewCuenta : cuentaListNew) {
                if (!cuentaListOld.contains(cuentaListNewCuenta)) {
                    Cliente oldClienteOfCuentaListNewCuenta = cuentaListNewCuenta.getCliente();
                    cuentaListNewCuenta.setCliente(cliente);
                    cuentaListNewCuenta = em.merge(cuentaListNewCuenta);
                    if (oldClienteOfCuentaListNewCuenta != null && !oldClienteOfCuentaListNewCuenta.equals(cliente)) {
                        oldClienteOfCuentaListNewCuenta.getCuentaList().remove(cuentaListNewCuenta);
                        oldClienteOfCuentaListNewCuenta = em.merge(oldClienteOfCuentaListNewCuenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuenta> cuentaListOrphanCheck = cliente.getCuentaList();
            for (Cuenta cuentaListOrphanCheckCuenta : cuentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Cuenta " + cuentaListOrphanCheckCuenta + " in its cuentaList field has a non-nullable cliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona persona = cliente.getPersona();
            if (persona != null) {
                persona.getClienteList().remove(cliente);
                persona = em.merge(persona);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
