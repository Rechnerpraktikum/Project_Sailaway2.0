/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaclasses;

import entityclasses.Crewmember;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entityclasses.Schiff;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpaclasses.exceptions.NonexistentEntityException;
import jpaclasses.exceptions.PreexistingEntityException;
import jpaclasses.exceptions.RollbackFailureException;

/**
 *
 * @author h1258009
 */
public class CrewmemberJpaController implements Serializable {

    public CrewmemberJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Crewmember crewmember) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiff rufzeichen = crewmember.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen = em.getReference(rufzeichen.getClass(), rufzeichen.getRufzeichen());
                crewmember.setRufzeichen(rufzeichen);
            }
            em.persist(crewmember);
            if (rufzeichen != null) {
                rufzeichen.getCrewmemberCollection().add(crewmember);
                rufzeichen = em.merge(rufzeichen);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCrewmember(crewmember.getCrewmemberid()) != null) {
                throw new PreexistingEntityException("Crewmember " + crewmember + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Crewmember crewmember) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crewmember persistentCrewmember = em.find(Crewmember.class, crewmember.getCrewmemberid());
            Schiff rufzeichenOld = persistentCrewmember.getRufzeichen();
            Schiff rufzeichenNew = crewmember.getRufzeichen();
            if (rufzeichenNew != null) {
                rufzeichenNew = em.getReference(rufzeichenNew.getClass(), rufzeichenNew.getRufzeichen());
                crewmember.setRufzeichen(rufzeichenNew);
            }
            crewmember = em.merge(crewmember);
            if (rufzeichenOld != null && !rufzeichenOld.equals(rufzeichenNew)) {
                rufzeichenOld.getCrewmemberCollection().remove(crewmember);
                rufzeichenOld = em.merge(rufzeichenOld);
            }
            if (rufzeichenNew != null && !rufzeichenNew.equals(rufzeichenOld)) {
                rufzeichenNew.getCrewmemberCollection().add(crewmember);
                rufzeichenNew = em.merge(rufzeichenNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = crewmember.getCrewmemberid();
                if (findCrewmember(id) == null) {
                    throw new NonexistentEntityException("The crewmember with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Crewmember crewmember;
            try {
                crewmember = em.getReference(Crewmember.class, id);
                crewmember.getCrewmemberid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The crewmember with id " + id + " no longer exists.", enfe);
            }
            Schiff rufzeichen = crewmember.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen.getCrewmemberCollection().remove(crewmember);
                rufzeichen = em.merge(rufzeichen);
            }
            em.remove(crewmember);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Crewmember> findCrewmemberEntities() {
        return findCrewmemberEntities(true, -1, -1);
    }

    public List<Crewmember> findCrewmemberEntities(int maxResults, int firstResult) {
        return findCrewmemberEntities(false, maxResults, firstResult);
    }

    private List<Crewmember> findCrewmemberEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Crewmember.class));
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

    public Crewmember findCrewmember(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Crewmember.class, id);
        } finally {
            em.close();
        }
    }

    public int getCrewmemberCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Crewmember> rt = cq.from(Crewmember.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
