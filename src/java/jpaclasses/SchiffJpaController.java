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
import entityclasses.Schiffsmodell;
import entityclasses.Usr;
import entityclasses.Route;
import java.util.ArrayList;
import java.util.Collection;
import entityclasses.Crewmember;
import entityclasses.Logbucheintrag;
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
public class SchiffJpaController implements Serializable {

    public SchiffJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Schiff schiff) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (schiff.getRouteCollection() == null) {
            schiff.setRouteCollection(new ArrayList<Route>());
        }
        if (schiff.getCrewmemberCollection() == null) {
            schiff.setCrewmemberCollection(new ArrayList<Crewmember>());
        }
        if (schiff.getLogbucheintragCollection() == null) {
            schiff.setLogbucheintragCollection(new ArrayList<Logbucheintrag>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiffsmodell modellid = schiff.getModellid();
            if (modellid != null) {
                modellid = em.getReference(modellid.getClass(), modellid.getModellid());
                schiff.setModellid(modellid);
            }
            Usr usrid = schiff.getUsrid();
            if (usrid != null) {
                usrid = em.getReference(usrid.getClass(), usrid.getUsrid());
                schiff.setUsrid(usrid);
            }
            Collection<Route> attachedRouteCollection = new ArrayList<Route>();
            for (Route routeCollectionRouteToAttach : schiff.getRouteCollection()) {
                routeCollectionRouteToAttach = em.getReference(routeCollectionRouteToAttach.getClass(), routeCollectionRouteToAttach.getRoutenid());
                attachedRouteCollection.add(routeCollectionRouteToAttach);
            }
            schiff.setRouteCollection(attachedRouteCollection);
            Collection<Crewmember> attachedCrewmemberCollection = new ArrayList<Crewmember>();
            for (Crewmember crewmemberCollectionCrewmemberToAttach : schiff.getCrewmemberCollection()) {
                crewmemberCollectionCrewmemberToAttach = em.getReference(crewmemberCollectionCrewmemberToAttach.getClass(), crewmemberCollectionCrewmemberToAttach.getCrewmemberid());
                attachedCrewmemberCollection.add(crewmemberCollectionCrewmemberToAttach);
            }
            schiff.setCrewmemberCollection(attachedCrewmemberCollection);
            Collection<Logbucheintrag> attachedLogbucheintragCollection = new ArrayList<Logbucheintrag>();
            for (Logbucheintrag logbucheintragCollectionLogbucheintragToAttach : schiff.getLogbucheintragCollection()) {
                logbucheintragCollectionLogbucheintragToAttach = em.getReference(logbucheintragCollectionLogbucheintragToAttach.getClass(), logbucheintragCollectionLogbucheintragToAttach.getEintragnummer());
                attachedLogbucheintragCollection.add(logbucheintragCollectionLogbucheintragToAttach);
            }
            schiff.setLogbucheintragCollection(attachedLogbucheintragCollection);
            em.persist(schiff);
            if (modellid != null) {
                modellid.getSchiffCollection().add(schiff);
                modellid = em.merge(modellid);
            }
            if (usrid != null) {
                usrid.getSchiffCollection().add(schiff);
                usrid = em.merge(usrid);
            }
            for (Route routeCollectionRoute : schiff.getRouteCollection()) {
                Schiff oldRufzeichenOfRouteCollectionRoute = routeCollectionRoute.getRufzeichen();
                routeCollectionRoute.setRufzeichen(schiff);
                routeCollectionRoute = em.merge(routeCollectionRoute);
                if (oldRufzeichenOfRouteCollectionRoute != null) {
                    oldRufzeichenOfRouteCollectionRoute.getRouteCollection().remove(routeCollectionRoute);
                    oldRufzeichenOfRouteCollectionRoute = em.merge(oldRufzeichenOfRouteCollectionRoute);
                }
            }
            for (Crewmember crewmemberCollectionCrewmember : schiff.getCrewmemberCollection()) {
                Schiff oldRufzeichenOfCrewmemberCollectionCrewmember = crewmemberCollectionCrewmember.getRufzeichen();
                crewmemberCollectionCrewmember.setRufzeichen(schiff);
                crewmemberCollectionCrewmember = em.merge(crewmemberCollectionCrewmember);
                if (oldRufzeichenOfCrewmemberCollectionCrewmember != null) {
                    oldRufzeichenOfCrewmemberCollectionCrewmember.getCrewmemberCollection().remove(crewmemberCollectionCrewmember);
                    oldRufzeichenOfCrewmemberCollectionCrewmember = em.merge(oldRufzeichenOfCrewmemberCollectionCrewmember);
                }
            }
            for (Logbucheintrag logbucheintragCollectionLogbucheintrag : schiff.getLogbucheintragCollection()) {
                Schiff oldRufzeichenOfLogbucheintragCollectionLogbucheintrag = logbucheintragCollectionLogbucheintrag.getRufzeichen();
                logbucheintragCollectionLogbucheintrag.setRufzeichen(schiff);
                logbucheintragCollectionLogbucheintrag = em.merge(logbucheintragCollectionLogbucheintrag);
                if (oldRufzeichenOfLogbucheintragCollectionLogbucheintrag != null) {
                    oldRufzeichenOfLogbucheintragCollectionLogbucheintrag.getLogbucheintragCollection().remove(logbucheintragCollectionLogbucheintrag);
                    oldRufzeichenOfLogbucheintragCollectionLogbucheintrag = em.merge(oldRufzeichenOfLogbucheintragCollectionLogbucheintrag);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSchiff(schiff.getRufzeichen()) != null) {
                throw new PreexistingEntityException("Schiff " + schiff + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Schiff schiff) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Schiff persistentSchiff = em.find(Schiff.class, schiff.getRufzeichen());
            Schiffsmodell modellidOld = persistentSchiff.getModellid();
            Schiffsmodell modellidNew = schiff.getModellid();
            Usr usridOld = persistentSchiff.getUsrid();
            Usr usridNew = schiff.getUsrid();
            Collection<Route> routeCollectionOld = persistentSchiff.getRouteCollection();
            Collection<Route> routeCollectionNew = schiff.getRouteCollection();
            Collection<Crewmember> crewmemberCollectionOld = persistentSchiff.getCrewmemberCollection();
            Collection<Crewmember> crewmemberCollectionNew = schiff.getCrewmemberCollection();
            Collection<Logbucheintrag> logbucheintragCollectionOld = persistentSchiff.getLogbucheintragCollection();
            Collection<Logbucheintrag> logbucheintragCollectionNew = schiff.getLogbucheintragCollection();
            if (modellidNew != null) {
                modellidNew = em.getReference(modellidNew.getClass(), modellidNew.getModellid());
                schiff.setModellid(modellidNew);
            }
            if (usridNew != null) {
                usridNew = em.getReference(usridNew.getClass(), usridNew.getUsrid());
                schiff.setUsrid(usridNew);
            }
            Collection<Route> attachedRouteCollectionNew = new ArrayList<Route>();
            for (Route routeCollectionNewRouteToAttach : routeCollectionNew) {
                routeCollectionNewRouteToAttach = em.getReference(routeCollectionNewRouteToAttach.getClass(), routeCollectionNewRouteToAttach.getRoutenid());
                attachedRouteCollectionNew.add(routeCollectionNewRouteToAttach);
            }
            routeCollectionNew = attachedRouteCollectionNew;
            schiff.setRouteCollection(routeCollectionNew);
            Collection<Crewmember> attachedCrewmemberCollectionNew = new ArrayList<Crewmember>();
            for (Crewmember crewmemberCollectionNewCrewmemberToAttach : crewmemberCollectionNew) {
                crewmemberCollectionNewCrewmemberToAttach = em.getReference(crewmemberCollectionNewCrewmemberToAttach.getClass(), crewmemberCollectionNewCrewmemberToAttach.getCrewmemberid());
                attachedCrewmemberCollectionNew.add(crewmemberCollectionNewCrewmemberToAttach);
            }
            crewmemberCollectionNew = attachedCrewmemberCollectionNew;
            schiff.setCrewmemberCollection(crewmemberCollectionNew);
            Collection<Logbucheintrag> attachedLogbucheintragCollectionNew = new ArrayList<Logbucheintrag>();
            for (Logbucheintrag logbucheintragCollectionNewLogbucheintragToAttach : logbucheintragCollectionNew) {
                logbucheintragCollectionNewLogbucheintragToAttach = em.getReference(logbucheintragCollectionNewLogbucheintragToAttach.getClass(), logbucheintragCollectionNewLogbucheintragToAttach.getEintragnummer());
                attachedLogbucheintragCollectionNew.add(logbucheintragCollectionNewLogbucheintragToAttach);
            }
            logbucheintragCollectionNew = attachedLogbucheintragCollectionNew;
            schiff.setLogbucheintragCollection(logbucheintragCollectionNew);
            schiff = em.merge(schiff);
            if (modellidOld != null && !modellidOld.equals(modellidNew)) {
                modellidOld.getSchiffCollection().remove(schiff);
                modellidOld = em.merge(modellidOld);
            }
            if (modellidNew != null && !modellidNew.equals(modellidOld)) {
                modellidNew.getSchiffCollection().add(schiff);
                modellidNew = em.merge(modellidNew);
            }
            if (usridOld != null && !usridOld.equals(usridNew)) {
                usridOld.getSchiffCollection().remove(schiff);
                usridOld = em.merge(usridOld);
            }
            if (usridNew != null && !usridNew.equals(usridOld)) {
                usridNew.getSchiffCollection().add(schiff);
                usridNew = em.merge(usridNew);
            }
            for (Route routeCollectionOldRoute : routeCollectionOld) {
                if (!routeCollectionNew.contains(routeCollectionOldRoute)) {
                    routeCollectionOldRoute.setRufzeichen(null);
                    routeCollectionOldRoute = em.merge(routeCollectionOldRoute);
                }
            }
            for (Route routeCollectionNewRoute : routeCollectionNew) {
                if (!routeCollectionOld.contains(routeCollectionNewRoute)) {
                    Schiff oldRufzeichenOfRouteCollectionNewRoute = routeCollectionNewRoute.getRufzeichen();
                    routeCollectionNewRoute.setRufzeichen(schiff);
                    routeCollectionNewRoute = em.merge(routeCollectionNewRoute);
                    if (oldRufzeichenOfRouteCollectionNewRoute != null && !oldRufzeichenOfRouteCollectionNewRoute.equals(schiff)) {
                        oldRufzeichenOfRouteCollectionNewRoute.getRouteCollection().remove(routeCollectionNewRoute);
                        oldRufzeichenOfRouteCollectionNewRoute = em.merge(oldRufzeichenOfRouteCollectionNewRoute);
                    }
                }
            }
            for (Crewmember crewmemberCollectionOldCrewmember : crewmemberCollectionOld) {
                if (!crewmemberCollectionNew.contains(crewmemberCollectionOldCrewmember)) {
                    crewmemberCollectionOldCrewmember.setRufzeichen(null);
                    crewmemberCollectionOldCrewmember = em.merge(crewmemberCollectionOldCrewmember);
                }
            }
            for (Crewmember crewmemberCollectionNewCrewmember : crewmemberCollectionNew) {
                if (!crewmemberCollectionOld.contains(crewmemberCollectionNewCrewmember)) {
                    Schiff oldRufzeichenOfCrewmemberCollectionNewCrewmember = crewmemberCollectionNewCrewmember.getRufzeichen();
                    crewmemberCollectionNewCrewmember.setRufzeichen(schiff);
                    crewmemberCollectionNewCrewmember = em.merge(crewmemberCollectionNewCrewmember);
                    if (oldRufzeichenOfCrewmemberCollectionNewCrewmember != null && !oldRufzeichenOfCrewmemberCollectionNewCrewmember.equals(schiff)) {
                        oldRufzeichenOfCrewmemberCollectionNewCrewmember.getCrewmemberCollection().remove(crewmemberCollectionNewCrewmember);
                        oldRufzeichenOfCrewmemberCollectionNewCrewmember = em.merge(oldRufzeichenOfCrewmemberCollectionNewCrewmember);
                    }
                }
            }
            for (Logbucheintrag logbucheintragCollectionOldLogbucheintrag : logbucheintragCollectionOld) {
                if (!logbucheintragCollectionNew.contains(logbucheintragCollectionOldLogbucheintrag)) {
                    logbucheintragCollectionOldLogbucheintrag.setRufzeichen(null);
                    logbucheintragCollectionOldLogbucheintrag = em.merge(logbucheintragCollectionOldLogbucheintrag);
                }
            }
            for (Logbucheintrag logbucheintragCollectionNewLogbucheintrag : logbucheintragCollectionNew) {
                if (!logbucheintragCollectionOld.contains(logbucheintragCollectionNewLogbucheintrag)) {
                    Schiff oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag = logbucheintragCollectionNewLogbucheintrag.getRufzeichen();
                    logbucheintragCollectionNewLogbucheintrag.setRufzeichen(schiff);
                    logbucheintragCollectionNewLogbucheintrag = em.merge(logbucheintragCollectionNewLogbucheintrag);
                    if (oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag != null && !oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag.equals(schiff)) {
                        oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag.getLogbucheintragCollection().remove(logbucheintragCollectionNewLogbucheintrag);
                        oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag = em.merge(oldRufzeichenOfLogbucheintragCollectionNewLogbucheintrag);
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
                String id = schiff.getRufzeichen();
                if (findSchiff(id) == null) {
                    throw new NonexistentEntityException("The schiff with id " + id + " no longer exists.");
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
            Schiff schiff;
            try {
                schiff = em.getReference(Schiff.class, id);
                schiff.getRufzeichen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The schiff with id " + id + " no longer exists.", enfe);
            }
            Schiffsmodell modellid = schiff.getModellid();
            if (modellid != null) {
                modellid.getSchiffCollection().remove(schiff);
                modellid = em.merge(modellid);
            }
            Usr usrid = schiff.getUsrid();
            if (usrid != null) {
                usrid.getSchiffCollection().remove(schiff);
                usrid = em.merge(usrid);
            }
            Collection<Route> routeCollection = schiff.getRouteCollection();
            for (Route routeCollectionRoute : routeCollection) {
                routeCollectionRoute.setRufzeichen(null);
                routeCollectionRoute = em.merge(routeCollectionRoute);
            }
            Collection<Crewmember> crewmemberCollection = schiff.getCrewmemberCollection();
            for (Crewmember crewmemberCollectionCrewmember : crewmemberCollection) {
                crewmemberCollectionCrewmember.setRufzeichen(null);
                crewmemberCollectionCrewmember = em.merge(crewmemberCollectionCrewmember);
            }
            Collection<Logbucheintrag> logbucheintragCollection = schiff.getLogbucheintragCollection();
            for (Logbucheintrag logbucheintragCollectionLogbucheintrag : logbucheintragCollection) {
                logbucheintragCollectionLogbucheintrag.setRufzeichen(null);
                logbucheintragCollectionLogbucheintrag = em.merge(logbucheintragCollectionLogbucheintrag);
            }
            em.remove(schiff);
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

    public List<Schiff> findSchiffEntities() {
        return findSchiffEntities(true, -1, -1);
    }

    public List<Schiff> findSchiffEntities(int maxResults, int firstResult) {
        return findSchiffEntities(false, maxResults, firstResult);
    }

    private List<Schiff> findSchiffEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Schiff.class));
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

    public Schiff findSchiff(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Schiff.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchiffCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Schiff> rt = cq.from(Schiff.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
