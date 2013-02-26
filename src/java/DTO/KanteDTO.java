/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Kante;
import entities.Knoten;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mariusbrederlow
 */
@Stateless
@LocalBean
public class KanteDTO {

    @PersistenceContext
    private EntityManager em;    
    private int anzahl;

    
    public void persistKante(Kante k){
        em.persist(k);
    }
    
    public Knoten getKnotenByID(int id){
        return (Knoten) em.createNamedQuery("Knoten.findById").setParameter("id", id).getSingleResult();
    }
    
    public Kante getKantebyIdZiel(Knoten k, int ziel){      
        return (Kante) em.createNamedQuery("Kante.findByIdmitZiel").setParameter("k", k).setParameter("ziel", ziel).getSingleResult();
    }
    
    public Kante getKanteById(int id){        
      return (Kante) em.createNamedQuery("Kante.findById").setParameter("id", id).getSingleResult();
    }
          
    public int getKantenAnzahl(){
      
        anzahl = em.createNamedQuery("Kante.findAll").getResultList().size();
     
        return anzahl;
    }
    
    
    
}
