/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wu.com;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author h1252164
 */
@Stateless
public class HafenFacade extends AbstractFacade<Hafen> {

    @PersistenceContext(unitName = "SailAwayJCIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HafenFacade() {
        super(Hafen.class);
    }
    
}