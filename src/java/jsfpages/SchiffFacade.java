/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfpages;

import entityclasses.Schiff;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author h1258009
 */
@Stateless
public class SchiffFacade extends AbstractFacade<Schiff> {

    @PersistenceContext(unitName = "SailAway_WebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SchiffFacade() {
        super(Schiff.class);
    }
    
}
