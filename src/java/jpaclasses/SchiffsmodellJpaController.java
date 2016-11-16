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
import entityclasses.Schiff;
import entityclasses.Schiffsmodell;
import java.util.ArrayList;
import java.util.Collection;
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
public class SchiffsmodellJpaController implements Serializable {

    public SchiffsmodellJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Schiffsmodell schiffsmodell) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (schiffsmodell.getSchiffCollection() == null) {
            schiffsmodell.setSchiffCollection(new ArrayList<Schiff>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Schiff> attachedSchiffCollection = new ArrayList<Schiff>();
            for (Schiff schiffCollectionSchiffToAttach : schiffsmodell.getSchiffCollection()) {
                schiffCollectionSchiffToAttach = em.getReference(schiffCollectionSchiffToAttach.getClass(), schiffCollectionSchiffToAttach.getRufzeichen());
                attachedSchiffCollection.add(schiffCollectionSchiffToAttach);
            }
            schiffsmodell.setSchiffCollection(attachedSchiffCollection);
            em.persist(schiffsmodell);
            for (Schiff schiffCollectionSchiff : schiffsmodell.getSchiffCollection()) {
                Schiffsmodell oldModellidOfSchiffCollectionSchiff = schiffCollectionSchiff.getModellid();
                schiffCollectionSchiff.setModellid(schiffsmodell);
                schiffCollectionSchiff = em.merge(schiffCollectionSchiff);
                if (oldModellidOfSchiffCollectionSchiff != null) {
                    oldModellidOfSchiffCollectionSchiff.getSchiffCollection().remove(schiffCollectionSchiff);
                    oldModellidOfSchiffCollectionSchiff = em.merge(oldModellidOfSchiffCollectionSchiff);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSchiffsmodell(schiffsmodell.getModellid()) != null) {
                throw new PreexistingEntityException("Schiffsmodell " + schiffsmodell + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Schiffsmodell schiffsmodell) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiffsmodell persistentSchiffsmodell = em.find(Schiffsmodell.class, schiffsmodell.getModellid());
            Collection<Schiff> schiffCollectionOld = persistentSchiffsmodell.getSchiffCollection();
            Collection<Schiff> schiffCollectionNew = schiffsmodell.getSchiffCollection();
            Collection<Schiff> attachedSchiffCollectionNew = new ArrayList<Schiff>();
            for (Schiff schiffCollectionNewSchiffToAttach : schiffCollectionNew) {
                schiffCollectionNewSchiffToAttach = em.getReference(schiffCollectionNewSchiffToAttach.getClass(), schiffCollectionNewSchiffToAttach.getRufzeichen());
                attachedSchiffCollectionNew.add(schiffCollectionNewSchiffToAttach);
            }
            schiffCollectionNew = attachedSchiffCollectionNew;
            schiffsmodell.setSchiffCollection(schiffCollectionNew);
            schiffsmodell = em.merge(schiffsmodell);
            for (Schiff schiffCollectionOldSchiff : schiffCollectionOld) {
                if (!schiffCollectionNew.contains(schiffCollectionOldSchiff)) {
                    schiffCollectionOldSchiff.setModellid(null);
                    schiffCollectionOldSchiff = em.merge(schiffCollectionOldSchiff);
                }
            }
            for (Schiff schiffCollectionNewSchiff : schiffCollectionNew) {
                if (!schiffCollectionOld.contains(schiffCollectionNewSchiff)) {
                    Schiffsmodell oldModellidOfSchiffCollectionNewSchiff = schiffCollectionNewSchiff.getModellid();
                    schiffCollectionNewSchiff.setModellid(schiffsmodell);
                    schiffCollectionNewSchiff = em.merge(schiffCollectionNewSchiff);
                    if (oldModellidOfSchiffCollectionNewSchiff != null && !oldModellidOfSchiffCollectionNewSchiff.equals(schiffsmodell)) {
                        oldModellidOfSchiffCollectionNewSchiff.getSchiffCollection().remove(schiffCollectionNewSchiff);
                        oldModellidOfSchiffCollectionNewSchiff = em.merge(oldModellidOfSchiffCollectionNewSchiff);
                    }
                }
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
                Integer id = schiffsmodell.getModellid();
                if (findSchiffsmodell(id) == null) {
                    throw new NonexistentEntityException("The schiffsmodell with id " + id + " no longer exists.");
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
            Schiffsmodell schiffsmodell;
            try {
                schiffsmodell = em.getReference(Schiffsmodell.class, id);
                schiffsmodell.getModellid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The schiffsmodell with id " + id + " no longer exists.", enfe);
            }
            Collection<Schiff> schiffCollection = schiffsmodell.getSchiffCollection();
            for (Schiff schiffCollectionSchiff : schiffCollection) {
                schiffCollectionSchiff.setModellid(null);
                schiffCollectionSchiff = em.merge(schiffCollectionSchiff);
            }
            em.remove(schiffsmodell);
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

    public List<Schiffsmodell> findSchiffsmodellEntities() {
        return findSchiffsmodellEntities(true, -1, -1);
    }

    public List<Schiffsmodell> findSchiffsmodellEntities(int maxResults, int firstResult) {
        return findSchiffsmodellEntities(false, maxResults, firstResult);
    }

    private List<Schiffsmodell> findSchiffsmodellEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Schiffsmodell.class));
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

    public Schiffsmodell findSchiffsmodell(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Schiffsmodell.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchiffsmodellCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Schiffsmodell> rt = cq.from(Schiffsmodell.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
