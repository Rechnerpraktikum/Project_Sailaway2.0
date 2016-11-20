/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaclasses;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entityclasses.Hafen;
import entityclasses.Kanten;
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
public class KantenJpaController implements Serializable {

    public KantenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kanten kanten) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Hafen unlocode = kanten.getUnlocode();
            if (unlocode != null) {
                unlocode = em.getReference(unlocode.getClass(), unlocode.getUnlocode());
                kanten.setUnlocode(unlocode);
            }
            em.persist(kanten);
            if (unlocode != null) {
                unlocode.getKantenCollection().add(kanten);
                unlocode = em.merge(unlocode);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findKanten(kanten.getKantennummer()) != null) {
                throw new PreexistingEntityException("Kanten " + kanten + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kanten kanten) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Kanten persistentKanten = em.find(Kanten.class, kanten.getKantennummer());
            Hafen unlocodeOld = persistentKanten.getUnlocode();
            Hafen unlocodeNew = kanten.getUnlocode();
            if (unlocodeNew != null) {
                unlocodeNew = em.getReference(unlocodeNew.getClass(), unlocodeNew.getUnlocode());
                kanten.setUnlocode(unlocodeNew);
            }
            kanten = em.merge(kanten);
            if (unlocodeOld != null && !unlocodeOld.equals(unlocodeNew)) {
                unlocodeOld.getKantenCollection().remove(kanten);
                unlocodeOld = em.merge(unlocodeOld);
            }
            if (unlocodeNew != null && !unlocodeNew.equals(unlocodeOld)) {
                unlocodeNew.getKantenCollection().add(kanten);
                unlocodeNew = em.merge(unlocodeNew);
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
                Integer id = kanten.getKantennummer();
                if (findKanten(id) == null) {
                    throw new NonexistentEntityException("The kanten with id " + id + " no longer exists.");
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
            Kanten kanten;
            try {
                kanten = em.getReference(Kanten.class, id);
                kanten.getKantennummer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kanten with id " + id + " no longer exists.", enfe);
            }
            Hafen unlocode = kanten.getUnlocode();
            if (unlocode != null) {
                unlocode.getKantenCollection().remove(kanten);
                unlocode = em.merge(unlocode);
            }
            em.remove(kanten);
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

    public List<Kanten> findKantenEntities() {
        return findKantenEntities(true, -1, -1);
    }

    public List<Kanten> findKantenEntities(int maxResults, int firstResult) {
        return findKantenEntities(false, maxResults, firstResult);
    }

    private List<Kanten> findKantenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kanten.class));
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

    public Kanten findKanten(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kanten.class, id);
        } finally {
            em.close();
        }
    }

    public int getKantenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kanten> rt = cq.from(Kanten.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
