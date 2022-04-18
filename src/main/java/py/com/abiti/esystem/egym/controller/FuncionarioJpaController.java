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
import py.com.abiti.esystem.egym.model.Cargo;
import py.com.abiti.esystem.egym.model.Horarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.esystem.egym.controller.exceptions.IllegalOrphanException;
import py.com.abiti.esystem.egym.controller.exceptions.NonexistentEntityException;
import py.com.abiti.esystem.egym.model.Funcionario;

/**
 *
 * @author matia
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) {
        if (funcionario.getHorariosList() == null) {
            funcionario.setHorariosList(new ArrayList<Horarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = funcionario.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getCargo());
                funcionario.setCargo(cargo);
            }
            List<Horarios> attachedHorariosList = new ArrayList<Horarios>();
            for (Horarios horariosListHorariosToAttach : funcionario.getHorariosList()) {
                horariosListHorariosToAttach = em.getReference(horariosListHorariosToAttach.getClass(), horariosListHorariosToAttach.getHorario());
                attachedHorariosList.add(horariosListHorariosToAttach);
            }
            funcionario.setHorariosList(attachedHorariosList);
            em.persist(funcionario);
            if (cargo != null) {
                cargo.getFuncionarioList().add(funcionario);
                cargo = em.merge(cargo);
            }
            for (Horarios horariosListHorarios : funcionario.getHorariosList()) {
                Funcionario oldFuncionarioOfHorariosListHorarios = horariosListHorarios.getFuncionario();
                horariosListHorarios.setFuncionario(funcionario);
                horariosListHorarios = em.merge(horariosListHorarios);
                if (oldFuncionarioOfHorariosListHorarios != null) {
                    oldFuncionarioOfHorariosListHorarios.getHorariosList().remove(horariosListHorarios);
                    oldFuncionarioOfHorariosListHorarios = em.merge(oldFuncionarioOfHorariosListHorarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getFuncionario());
            Cargo cargoOld = persistentFuncionario.getCargo();
            Cargo cargoNew = funcionario.getCargo();
            List<Horarios> horariosListOld = persistentFuncionario.getHorariosList();
            List<Horarios> horariosListNew = funcionario.getHorariosList();
            List<String> illegalOrphanMessages = null;
            for (Horarios horariosListOldHorarios : horariosListOld) {
                if (!horariosListNew.contains(horariosListOldHorarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horarios " + horariosListOldHorarios + " since its funcionario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getCargo());
                funcionario.setCargo(cargoNew);
            }
            List<Horarios> attachedHorariosListNew = new ArrayList<Horarios>();
            for (Horarios horariosListNewHorariosToAttach : horariosListNew) {
                horariosListNewHorariosToAttach = em.getReference(horariosListNewHorariosToAttach.getClass(), horariosListNewHorariosToAttach.getHorario());
                attachedHorariosListNew.add(horariosListNewHorariosToAttach);
            }
            horariosListNew = attachedHorariosListNew;
            funcionario.setHorariosList(horariosListNew);
            funcionario = em.merge(funcionario);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getFuncionarioList().remove(funcionario);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getFuncionarioList().add(funcionario);
                cargoNew = em.merge(cargoNew);
            }
            for (Horarios horariosListNewHorarios : horariosListNew) {
                if (!horariosListOld.contains(horariosListNewHorarios)) {
                    Funcionario oldFuncionarioOfHorariosListNewHorarios = horariosListNewHorarios.getFuncionario();
                    horariosListNewHorarios.setFuncionario(funcionario);
                    horariosListNewHorarios = em.merge(horariosListNewHorarios);
                    if (oldFuncionarioOfHorariosListNewHorarios != null && !oldFuncionarioOfHorariosListNewHorarios.equals(funcionario)) {
                        oldFuncionarioOfHorariosListNewHorarios.getHorariosList().remove(horariosListNewHorarios);
                        oldFuncionarioOfHorariosListNewHorarios = em.merge(oldFuncionarioOfHorariosListNewHorarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionario.getFuncionario();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getFuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Horarios> horariosListOrphanCheck = funcionario.getHorariosList();
            for (Horarios horariosListOrphanCheckHorarios : horariosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Horarios " + horariosListOrphanCheckHorarios + " in its horariosList field has a non-nullable funcionario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cargo cargo = funcionario.getCargo();
            if (cargo != null) {
                cargo.getFuncionarioList().remove(funcionario);
                cargo = em.merge(cargo);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
