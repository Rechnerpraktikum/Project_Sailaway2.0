/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpages;

import entityclasses.Wegpunkte;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author h1258009
 */
@Stateless
public class WegpunkteFacade extends AbstractFacade<Wegpunkte> {

    @PersistenceContext(unitName = "SailAway_WebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WegpunkteFacade() {
        super(Wegpunkte.class);
    }
    
}
