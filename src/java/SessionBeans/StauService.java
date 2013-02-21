/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import DTO.KanteDTO;
import entities.Kante;
import entities.Knoten;
import interfaces.IStauService;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author mariusbrederlow
 */
@Stateless
@PermitAll
@Remote(IStauService.class)
public class StauService implements IStauService{

    @EJB
    private KanteDTO dto;
    private List kanten;
    private Kante qkante;
    private Kante zkante;
    
    
    
    public int generiereRandomInt(int anzahl){
        return (int) (Math.random()*anzahl)+1;
    }
    
    
    
    @Override
    @Schedule(minute = "0/1", hour = "*", persistent=false)
    public void generiereStau(){
        System.out.println("STAU");
        int anzahlKanten;
        int updateKanten = (int) (Math.random()*10)+1;; 
        Knoten k;
        
        int kante_id;
        int quellid;
        int i;
        
        System.out.println("Anzahl der Durchlaeufe " + updateKanten);
        for(i=1;i<updateKanten;i++){
        int stauLaenge = (int) (Math.random()*10)+1;
        anzahlKanten = dto.getKantenAnzahl();
        
        
        
        
        kante_id = generiereRandomInt(anzahlKanten);
        
        qkante = dto.getKanteById(kante_id);
        System.out.println("Wähle Kante " + qkante.getId());
        
        quellid = qkante.getKnotenid().getId();
       
        
        k = dto.getKnotenByID(qkante.getZielknotenid());
        System.out.println("Ziel der Kante Knoten" + k.getId());
        System.out.println("Ziel der Kante Quellid" + quellid);
        
        zkante = dto.getKantebyIdZiel(k,quellid);
        
       qkante.setGewicht(stauLaenge);
       zkante.setGewicht(stauLaenge); 
       
       dto.persistKante(qkante);
       dto.persistKante(zkante);
        
        
       
        }  
    }
    

}
