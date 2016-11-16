/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaclasses;

import entityclasses.Hafen;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entityclasses.Kanten;
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
public class HafenJpaController implements Serializable {

    public HafenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hafen hafen) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (hafen.getKantenCollection() == null) {
            hafen.setKantenCollection(new ArrayList<Kanten>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Kanten> attachedKantenCollection = new ArrayList<Kanten>();
            for (Kanten kantenCollectionKantenToAttach : hafen.getKantenCollection()) {
                kantenCollectionKantenToAttach = em.getReference(kantenCollectionKantenToAttach.getClass(), kantenCollectionKantenToAttach.getKantennummer());
                attachedKantenCollection.add(kantenCollectionKantenToAttach);
            }
            hafen.setKantenCollection(attachedKantenCollection);
            em.persist(hafen);
            for (Kanten kantenCollectionKanten : hafen.getKantenCollection()) {
                Hafen oldUnlocodeOfKantenCollectionKanten = kantenCollectionKanten.getUnlocode();
                kantenCollectionKanten.setUnlocode(hafen);
                kantenCollectionKanten = em.merge(kantenCollectionKanten);
                if (oldUnlocodeOfKantenCollectionKanten != null) {
                    oldUnlocodeOfKantenCollectionKanten.getKantenCollection().remove(kantenCollectionKanten);
                    oldUnlocodeOfKantenCollectionKanten = em.merge(oldUnlocodeOfKantenCollectionKanten);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHafen(hafen.getUnlocode()) != null) {
                throw new PreexistingEntityException("Hafen " + hafen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hafen hafen) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Hafen persistentHafen = em.find(Hafen.class, hafen.getUnlocode());
            Collection<Kanten> kantenCollectionOld = persistentHafen.getKantenCollection();
            Collection<Kanten> kantenCollectionNew = hafen.getKantenCollection();
            Collection<Kanten> attachedKantenCollectionNew = new ArrayList<Kanten>();
            for (Kanten kantenCollectionNewKantenToAttach : kantenCollectionNew) {
                kantenCollectionNewKantenToAttach = em.getReference(kantenCollectionNewKantenToAttach.getClass(), kantenCollectionNewKantenToAttach.getKantennummer());
                attachedKantenCollectionNew.add(kantenCollectionNewKantenToAttach);
            }
            kantenCollectionNew = attachedKantenCollectionNew;
            hafen.setKantenCollection(kantenCollectionNew);
            hafen = em.merge(hafen);
            for (Kanten kantenCollectionOldKanten : kantenCollectionOld) {
                if (!kantenCollectionNew.contains(kantenCollectionOldKanten)) {
                    kantenCollectionOldKanten.setUnlocode(null);
                    kantenCollectionOldKanten = em.merge(kantenCollectionOldKanten);
                }
            }
            for (Kanten kantenCollectionNewKanten : kantenCollectionNew) {
                if (!kantenCollectionOld.contains(kantenCollectionNewKanten)) {
                    Hafen oldUnlocodeOfKantenCollectionNewKanten = kantenCollectionNewKanten.getUnlocode();
                    kantenCollectionNewKanten.setUnlocode(hafen);
                    kantenCollectionNewKanten = em.merge(kantenCollectionNewKanten);
                    if (oldUnlocodeOfKantenCollectionNewKanten != null && !oldUnlocodeOfKantenCollectionNewKanten.equals(hafen)) {
                        oldUnlocodeOfKantenCollectionNewKanten.getKantenCollection().remove(kantenCollectionNewKanten);
                        oldUnlocodeOfKantenCollectionNewKanten = em.merge(oldUnlocodeOfKantenCollectionNewKanten);
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
                String id = hafen.getUnlocode();
                if (findHafen(id) == null) {
                    throw new NonexistentEntityException("The hafen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Hafen hafen;
            try {
                hafen = em.getReference(Hafen.class, id);
                hafen.getUnlocode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hafen with id " + id + " no longer exists.", enfe);
            }
            Collection<Kanten> kantenCollection = hafen.getKantenCollection();
            for (Kanten kantenCollectionKanten : kantenCollection) {
                kantenCollectionKanten.setUnlocode(null);
                kantenCollectionKanten = em.merge(kantenCollectionKanten);
            }
            em.remove(hafen);
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

    public List<Hafen> findHafenEntities() {
        return findHafenEntities(true, -1, -1);
    }

    public List<Hafen> findHafenEntities(int maxResults, int firstResult) {
        return findHafenEntities(false, maxResults, firstResult);
    }

    private List<Hafen> findHafenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hafen.class));
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

    public Hafen findHafen(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hafen.class, id);
        } finally {
            em.close();
        }
    }

    public int getHafenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hafen> rt = cq.from(Hafen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
