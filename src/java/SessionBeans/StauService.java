/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeans;

import DTO.KanteDTO;
import entities.Kante;
import entities.Knoten;
import interfaces.IStauService;
import java.util.logging.Logger;
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
    private Kante qkante;
    private Kante zkante;
    static final int MAX_STAULAENGE = 10;
    static final Logger logger = Logger.getLogger("logger");
    
    
    public int generiereRandomInt(int anzahl){
        return (int) (Math.random()*anzahl)+1;
    }
    
    
    
    @Override
    @Schedule(minute = "0/1", hour = "*", persistent=false)
    public void generiereStau(){
        logger.info("<< Starte Staugenerierung >>");
        
        int anzahlKanten = dto.getKantenAnzahl();
        int updateKanten = (int) (Math.random()*anzahlKanten)+1;
        Knoten k;
        
        int kante_id;
        int quellid;
        int i;
        
        logger.info("Generiere Stau auf " + updateKanten + " Kanten von insgesamt " + anzahlKanten + " Kanten");
        for(i=0;i<updateKanten;i++){
        
        int stauLaenge = (int) (Math.random()*MAX_STAULAENGE)+1;

        kante_id = generiereRandomInt(anzahlKanten);       
        qkante = dto.getKanteById(kante_id);
        quellid = qkante.getKnotenid().getId();
        k = dto.getKnotenByID(qkante.getZielknotenid());
        
        logger.info("Waehle Kante von " + qkante.getKnotenid().getName() + " nach " + k.getName());
        
        zkante = dto.getKantebyIdZiel(k,quellid);
        
       qkante.setGewicht(stauLaenge);
       zkante.setGewicht(stauLaenge); 
       logger.info("Verzoegerung ist " + (qkante.getGewicht()+qkante.getMinProKM()) + " Minuten pro 10 Km");
       dto.persistKante(qkante);
       dto.persistKante(zkante);       
        }  
    }
    

}
