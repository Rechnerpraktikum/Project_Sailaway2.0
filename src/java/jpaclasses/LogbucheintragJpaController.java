/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaclasses;

import entityclasses.Logbucheintrag;
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
public class LogbucheintragJpaController implements Serializable {

    public LogbucheintragJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logbucheintrag logbucheintrag) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiff rufzeichen = logbucheintrag.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen = em.getReference(rufzeichen.getClass(), rufzeichen.getRufzeichen());
                logbucheintrag.setRufzeichen(rufzeichen);
            }
            em.persist(logbucheintrag);
            if (rufzeichen != null) {
                rufzeichen.getLogbucheintragCollection().add(logbucheintrag);
                rufzeichen = em.merge(rufzeichen);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLogbucheintrag(logbucheintrag.getEintragnummer()) != null) {
                throw new PreexistingEntityException("Logbucheintrag " + logbucheintrag + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logbucheintrag logbucheintrag) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Logbucheintrag persistentLogbucheintrag = em.find(Logbucheintrag.class, logbucheintrag.getEintragnummer());
            Schiff rufzeichenOld = persistentLogbucheintrag.getRufzeichen();
            Schiff rufzeichenNew = logbucheintrag.getRufzeichen();
            if (rufzeichenNew != null) {
                rufzeichenNew = em.getReference(rufzeichenNew.getClass(), rufzeichenNew.getRufzeichen());
                logbucheintrag.setRufzeichen(rufzeichenNew);
            }
            logbucheintrag = em.merge(logbucheintrag);
            if (rufzeichenOld != null && !rufzeichenOld.equals(rufzeichenNew)) {
                rufzeichenOld.getLogbucheintragCollection().remove(logbucheintrag);
                rufzeichenOld = em.merge(rufzeichenOld);
            }
            if (rufzeichenNew != null && !rufzeichenNew.equals(rufzeichenOld)) {
                rufzeichenNew.getLogbucheintragCollection().add(logbucheintrag);
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
                Integer id = logbucheintrag.getEintragnummer();
                if (findLogbucheintrag(id) == null) {
                    throw new NonexistentEntityException("The logbucheintrag with id " + id + " no longer exists.");
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
            Logbucheintrag logbucheintrag;
            try {
                logbucheintrag = em.getReference(Logbucheintrag.class, id);
                logbucheintrag.getEintragnummer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logbucheintrag with id " + id + " no longer exists.", enfe);
            }
            Schiff rufzeichen = logbucheintrag.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen.getLogbucheintragCollection().remove(logbucheintrag);
                rufzeichen = em.merge(rufzeichen);
            }
            em.remove(logbucheintrag);
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

    public List<Logbucheintrag> findLogbucheintragEntities() {
        return findLogbucheintragEntities(true, -1, -1);
    }

    public List<Logbucheintrag> findLogbucheintragEntities(int maxResults, int firstResult) {
        return findLogbucheintragEntities(false, maxResults, firstResult);
    }

    private List<Logbucheintrag> findLogbucheintragEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logbucheintrag.class));
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

    public Logbucheintrag findLogbucheintrag(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logbucheintrag.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogbucheintragCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logbucheintrag> rt = cq.from(Logbucheintrag.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
