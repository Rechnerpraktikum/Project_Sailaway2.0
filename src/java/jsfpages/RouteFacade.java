/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpages;

import entityclasses.Route;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author h1258009
 */
@Stateless
public class RouteFacade extends AbstractFacade<Route> {

    @PersistenceContext(unitName = "SailAway_WebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RouteFacade() {
        super(Route.class);
    }
    
}
