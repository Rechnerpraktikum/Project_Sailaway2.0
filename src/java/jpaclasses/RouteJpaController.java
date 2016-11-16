/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaclasses;

import entityclasses.Route;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entityclasses.Schiff;
import entityclasses.Wegpunkte;
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
public class RouteJpaController implements Serializable {

    public RouteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Route route) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (route.getWegpunkteCollection() == null) {
            route.setWegpunkteCollection(new ArrayList<Wegpunkte>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiff rufzeichen = route.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen = em.getReference(rufzeichen.getClass(), rufzeichen.getRufzeichen());
                route.setRufzeichen(rufzeichen);
            }
            Collection<Wegpunkte> attachedWegpunkteCollection = new ArrayList<Wegpunkte>();
            for (Wegpunkte wegpunkteCollectionWegpunkteToAttach : route.getWegpunkteCollection()) {
                wegpunkteCollectionWegpunkteToAttach = em.getReference(wegpunkteCollectionWegpunkteToAttach.getClass(), wegpunkteCollectionWegpunkteToAttach.getWegpunktnummer());
                attachedWegpunkteCollection.add(wegpunkteCollectionWegpunkteToAttach);
            }
            route.setWegpunkteCollection(attachedWegpunkteCollection);
            em.persist(route);
            if (rufzeichen != null) {
                rufzeichen.getRouteCollection().add(route);
                rufzeichen = em.merge(rufzeichen);
            }
            for (Wegpunkte wegpunkteCollectionWegpunkte : route.getWegpunkteCollection()) {
                Route oldRoutenidOfWegpunkteCollectionWegpunkte = wegpunkteCollectionWegpunkte.getRoutenid();
                wegpunkteCollectionWegpunkte.setRoutenid(route);
                wegpunkteCollectionWegpunkte = em.merge(wegpunkteCollectionWegpunkte);
                if (oldRoutenidOfWegpunkteCollectionWegpunkte != null) {
                    oldRoutenidOfWegpunkteCollectionWegpunkte.getWegpunkteCollection().remove(wegpunkteCollectionWegpunkte);
                    oldRoutenidOfWegpunkteCollectionWegpunkte = em.merge(oldRoutenidOfWegpunkteCollectionWegpunkte);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRoute(route.getRoutenid()) != null) {
                throw new PreexistingEntityException("Route " + route + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Route route) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Route persistentRoute = em.find(Route.class, route.getRoutenid());
            Schiff rufzeichenOld = persistentRoute.getRufzeichen();
            Schiff rufzeichenNew = route.getRufzeichen();
            Collection<Wegpunkte> wegpunkteCollectionOld = persistentRoute.getWegpunkteCollection();
            Collection<Wegpunkte> wegpunkteCollectionNew = route.getWegpunkteCollection();
            if (rufzeichenNew != null) {
                rufzeichenNew = em.getReference(rufzeichenNew.getClass(), rufzeichenNew.getRufzeichen());
                route.setRufzeichen(rufzeichenNew);
            }
            Collection<Wegpunkte> attachedWegpunkteCollectionNew = new ArrayList<Wegpunkte>();
            for (Wegpunkte wegpunkteCollectionNewWegpunkteToAttach : wegpunkteCollectionNew) {
                wegpunkteCollectionNewWegpunkteToAttach = em.getReference(wegpunkteCollectionNewWegpunkteToAttach.getClass(), wegpunkteCollectionNewWegpunkteToAttach.getWegpunktnummer());
                attachedWegpunkteCollectionNew.add(wegpunkteCollectionNewWegpunkteToAttach);
            }
            wegpunkteCollectionNew = attachedWegpunkteCollectionNew;
            route.setWegpunkteCollection(wegpunkteCollectionNew);
            route = em.merge(route);
            if (rufzeichenOld != null && !rufzeichenOld.equals(rufzeichenNew)) {
                rufzeichenOld.getRouteCollection().remove(route);
                rufzeichenOld = em.merge(rufzeichenOld);
            }
            if (rufzeichenNew != null && !rufzeichenNew.equals(rufzeichenOld)) {
                rufzeichenNew.getRouteCollection().add(route);
                rufzeichenNew = em.merge(rufzeichenNew);
            }
            for (Wegpunkte wegpunkteCollectionOldWegpunkte : wegpunkteCollectionOld) {
                if (!wegpunkteCollectionNew.contains(wegpunkteCollectionOldWegpunkte)) {
                    wegpunkteCollectionOldWegpunkte.setRoutenid(null);
                    wegpunkteCollectionOldWegpunkte = em.merge(wegpunkteCollectionOldWegpunkte);
                }
            }
            for (Wegpunkte wegpunkteCollectionNewWegpunkte : wegpunkteCollectionNew) {
                if (!wegpunkteCollectionOld.contains(wegpunkteCollectionNewWegpunkte)) {
                    Route oldRoutenidOfWegpunkteCollectionNewWegpunkte = wegpunkteCollectionNewWegpunkte.getRoutenid();
                    wegpunkteCollectionNewWegpunkte.setRoutenid(route);
                    wegpunkteCollectionNewWegpunkte = em.merge(wegpunkteCollectionNewWegpunkte);
                    if (oldRoutenidOfWegpunkteCollectionNewWegpunkte != null && !oldRoutenidOfWegpunkteCollectionNewWegpunkte.equals(route)) {
                        oldRoutenidOfWegpunkteCollectionNewWegpunkte.getWegpunkteCollection().remove(wegpunkteCollectionNewWegpunkte);
                        oldRoutenidOfWegpunkteCollectionNewWegpunkte = em.merge(oldRoutenidOfWegpunkteCollectionNewWegpunkte);
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
                Integer id = route.getRoutenid();
                if (findRoute(id) == null) {
                    throw new NonexistentEntityException("The route with id " + id + " no longer exists.");
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
            Route route;
            try {
                route = em.getReference(Route.class, id);
                route.getRoutenid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The route with id " + id + " no longer exists.", enfe);
            }
            Schiff rufzeichen = route.getRufzeichen();
            if (rufzeichen != null) {
                rufzeichen.getRouteCollection().remove(route);
                rufzeichen = em.merge(rufzeichen);
            }
            Collection<Wegpunkte> wegpunkteCollection = route.getWegpunkteCollection();
            for (Wegpunkte wegpunkteCollectionWegpunkte : wegpunkteCollection) {
                wegpunkteCollectionWegpunkte.setRoutenid(null);
                wegpunkteCollectionWegpunkte = em.merge(wegpunkteCollectionWegpunkte);
            }
            em.remove(route);
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

    public List<Route> findRouteEntities() {
        return findRouteEntities(true, -1, -1);
    }

    public List<Route> findRouteEntities(int maxResults, int firstResult) {
        return findRouteEntities(false, maxResults, firstResult);
    }

    private List<Route> findRouteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Route.class));
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

    public Route findRoute(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Route.class, id);
        } finally {
            em.close();
        }
    }

    public int getRouteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Route> rt = cq.from(Route.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
