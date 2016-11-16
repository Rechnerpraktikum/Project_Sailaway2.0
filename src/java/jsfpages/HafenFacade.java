/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpages;

import entityclasses.Hafen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author h1258009
 */
@Stateless
public class HafenFacade extends AbstractFacade<Hafen> {

    @PersistenceContext(unitName = "SailAway_WebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HafenFacade() {
        super(Hafen.class);
    }
    
}
