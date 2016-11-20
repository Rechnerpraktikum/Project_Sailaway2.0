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
import entityclasses.Usr;
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
public class UsrJpaController implements Serializable {

    public UsrJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usr usr) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usr.getSchiffCollection() == null) {
            usr.setSchiffCollection(new ArrayList<Schiff>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Schiff> attachedSchiffCollection = new ArrayList<Schiff>();
            for (Schiff schiffCollectionSchiffToAttach : usr.getSchiffCollection()) {
                schiffCollectionSchiffToAttach = em.getReference(schiffCollectionSchiffToAttach.getClass(), schiffCollectionSchiffToAttach.getRufzeichen());
                attachedSchiffCollection.add(schiffCollectionSchiffToAttach);
            }
            usr.setSchiffCollection(attachedSchiffCollection);
            em.persist(usr);
            for (Schiff schiffCollectionSchiff : usr.getSchiffCollection()) {
                Usr oldUsridOfSchiffCollectionSchiff = schiffCollectionSchiff.getUsrid();
                schiffCollectionSchiff.setUsrid(usr);
                schiffCollectionSchiff = em.merge(schiffCollectionSchiff);
                if (oldUsridOfSchiffCollectionSchiff != null) {
                    oldUsridOfSchiffCollectionSchiff.getSchiffCollection().remove(schiffCollectionSchiff);
                    oldUsridOfSchiffCollectionSchiff = em.merge(oldUsridOfSchiffCollectionSchiff);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsr(usr.getUsrid()) != null) {
                throw new PreexistingEntityException("Usr " + usr + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usr usr) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usr persistentUsr = em.find(Usr.class, usr.getUsrid());
            Collection<Schiff> schiffCollectionOld = persistentUsr.getSchiffCollection();
            Collection<Schiff> schiffCollectionNew = usr.getSchiffCollection();
            Collection<Schiff> attachedSchiffCollectionNew = new ArrayList<Schiff>();
            for (Schiff schiffCollectionNewSchiffToAttach : schiffCollectionNew) {
                schiffCollectionNewSchiffToAttach = em.getReference(schiffCollectionNewSchiffToAttach.getClass(), schiffCollectionNewSchiffToAttach.getRufzeichen());
                attachedSchiffCollectionNew.add(schiffCollectionNewSchiffToAttach);
            }
            schiffCollectionNew = attachedSchiffCollectionNew;
            usr.setSchiffCollection(schiffCollectionNew);
            usr = em.merge(usr);
            for (Schiff schiffCollectionOldSchiff : schiffCollectionOld) {
                if (!schiffCollectionNew.contains(schiffCollectionOldSchiff)) {
                    schiffCollectionOldSchiff.setUsrid(null);
                    schiffCollectionOldSchiff = em.merge(schiffCollectionOldSchiff);
                }
            }
            for (Schiff schiffCollectionNewSchiff : schiffCollectionNew) {
                if (!schiffCollectionOld.contains(schiffCollectionNewSchiff)) {
                    Usr oldUsridOfSchiffCollectionNewSchiff = schiffCollectionNewSchiff.getUsrid();
                    schiffCollectionNewSchiff.setUsrid(usr);
                    schiffCollectionNewSchiff = em.merge(schiffCollectionNewSchiff);
                    if (oldUsridOfSchiffCollectionNewSchiff != null && !oldUsridOfSchiffCollectionNewSchiff.equals(usr)) {
                        oldUsridOfSchiffCollectionNewSchiff.getSchiffCollection().remove(schiffCollectionNewSchiff);
                        oldUsridOfSchiffCollectionNewSchiff = em.merge(oldUsridOfSchiffCollectionNewSchiff);
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
                Integer id = usr.getUsrid();
                if (findUsr(id) == null) {
                    throw new NonexistentEntityException("The usr with id " + id + " no longer exists.");
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
            Usr usr;
            try {
                usr = em.getReference(Usr.class, id);
                usr.getUsrid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usr with id " + id + " no longer exists.", enfe);
            }
            Collection<Schiff> schiffCollection = usr.getSchiffCollection();
            for (Schiff schiffCollectionSchiff : schiffCollection) {
                schiffCollectionSchiff.setUsrid(null);
                schiffCollectionSchiff = em.merge(schiffCollectionSchiff);
            }
            em.remove(usr);
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

    public List<Usr> findUsrEntities() {
        return findUsrEntities(true, -1, -1);
    }

    public List<Usr> findUsrEntities(int maxResults, int firstResult) {
        return findUsrEntities(false, maxResults, firstResult);
    }

    private List<Usr> findUsrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usr.class));
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

    public Usr findUsr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usr.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usr> rt = cq.from(Usr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
