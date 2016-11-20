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
import entityclasses.Route;
import entityclasses.Wegpunkte;
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
public class WegpunkteJpaController implements Serializable {

    public WegpunkteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Wegpunkte wegpunkte) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Route routenid = wegpunkte.getRoutenid();
            if (routenid != null) {
                routenid = em.getReference(routenid.getClass(), routenid.getRoutenid());
                wegpunkte.setRoutenid(routenid);
            }
            em.persist(wegpunkte);
            if (routenid != null) {
                routenid.getWegpunkteCollection().add(wegpunkte);
                routenid = em.merge(routenid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findWegpunkte(wegpunkte.getWegpunktnummer()) != null) {
                throw new PreexistingEntityException("Wegpunkte " + wegpunkte + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Wegpunkte wegpunkte) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Wegpunkte persistentWegpunkte = em.find(Wegpunkte.class, wegpunkte.getWegpunktnummer());
            Route routenidOld = persistentWegpunkte.getRoutenid();
            Route routenidNew = wegpunkte.getRoutenid();
            if (routenidNew != null) {
                routenidNew = em.getReference(routenidNew.getClass(), routenidNew.getRoutenid());
                wegpunkte.setRoutenid(routenidNew);
            }
            wegpunkte = em.merge(wegpunkte);
            if (routenidOld != null && !routenidOld.equals(routenidNew)) {
                routenidOld.getWegpunkteCollection().remove(wegpunkte);
                routenidOld = em.merge(routenidOld);
            }
            if (routenidNew != null && !routenidNew.equals(routenidOld)) {
                routenidNew.getWegpunkteCollection().add(wegpunkte);
                routenidNew = em.merge(routenidNew);
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
                Integer id = wegpunkte.getWegpunktnummer();
                if (findWegpunkte(id) == null) {
                    throw new NonexistentEntityException("The wegpunkte with id " + id + " no longer exists.");
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
            Wegpunkte wegpunkte;
            try {
                wegpunkte = em.getReference(Wegpunkte.class, id);
                wegpunkte.getWegpunktnummer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The wegpunkte with id " + id + " no longer exists.", enfe);
            }
            Route routenid = wegpunkte.getRoutenid();
            if (routenid != null) {
                routenid.getWegpunkteCollection().remove(wegpunkte);
                routenid = em.merge(routenid);
            }
            em.remove(wegpunkte);
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

    public List<Wegpunkte> findWegpunkteEntities() {
        return findWegpunkteEntities(true, -1, -1);
    }

    public List<Wegpunkte> findWegpunkteEntities(int maxResults, int firstResult) {
        return findWegpunkteEntities(false, maxResults, firstResult);
    }

    private List<Wegpunkte> findWegpunkteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Wegpunkte.class));
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

    public Wegpunkte findWegpunkte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Wegpunkte.class, id);
        } finally {
            em.close();
        }
    }

    public int getWegpunkteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Wegpunkte> rt = cq.from(Wegpunkte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
