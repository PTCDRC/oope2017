//Oope 2017 harjoitustyö
//Hakemisto-luokka
package oope2017ht.tiedot;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;

//Hakemisto periytyy Tieto-yliluokasta
public class Hakemisto extends Tieto implements Komennettava<Tieto>{
    
    //Attribuutit
    private OmaLista sisalto = new OmaLista();            //OmaLista-tyyppinen attribuutti sisältää viitteet sisältöön
    private Hakemisto yliHakemisto;        //Viite ylihakemistoon
    
    //raketajalla on paramatereinä oman ja ylihakemiston viite ------------
    public Hakemisto(StringBuilder oma, Hakemisto yli) throws IllegalArgumentException{
        
        //Kutsutaan yliluokkaa
        super(oma);
        
        //tarkistetaan onko ylihakemisto, juurihakemisto, eli null
        Tarkistaja para = new Tarkistaja();
        boolean omaOk = para.tarkistaNimi(oma.toString());
        
        //Jos syöte oli virheellinen heitetään poikkeus
        if(omaOk == false)
            throw new IllegalArgumentException();
        else{
            yliHakemisto(yli);
        }
        
    }
    

    /**Toteutetaan rajapinnan metodit*/
    
    //Aksessori joka palauttaa viitteen osaolioon------------

    public LinkitettyLista sisalto(){
        return sisalto;
    }
    
    //Haetaan tietoa hakemistosta
    public Tieto hae(String nimi) throws NullPointerException{
        //Napataan poikkeus, jos parametri on null
        try{
            //luodaan apuolio
            Tieto haettava = new Tieto(new StringBuilder(nimi));
            
            //Kutsutaan OmaLista-luokan hakuoperaatiota
            Tieto haku = (Tieto)sisalto.hae(haettava);
            
            //palauttaan viite löydettyyn olioon, null jos ei löytynyt
            return haku;
        }catch(Exception e){
            return null;
        }
    }
    
    //Lisätään hakemistoon tiedosto tai alihakemisto
    public boolean lisaa(Tieto lisattava) throws NullPointerException{
        //Paluuarvo on null, jos parametri on null tai samanniminen tieto löytyy jo hakemistosta
        if(lisattava == null || sisalto.hae(lisattava) != null)
            return false;
        else{
            //Lisätään tieto hakemistoon
            try{
                sisalto.lisaa(lisattava);
                return true;
            }catch(Exception e){
                return false;
            }
        }
    }
    
    //Poistetaan hakemistosta tiedosto tai alihakemisto
    public Tieto poista(String nimi) throws NullPointerException{
        
        //napataan poikkeus siltä varalta, että parametri on null
        try{
            //Luodaan Tieto-tyyppinen apuolio
            Tieto poistettava = new Tieto(new StringBuilder(nimi));
            
            //Kutsutaan OmaListan poistajaa
            Tieto poisto = (Tieto)sisalto.poista(poistettava);
            
            //palautetaan viite poistettuun tietoo, null jos poisto ei onnistunut
            return poisto;
        }catch(Exception e){
            return null;
        }
    }

    
    
    //aksessorit------------
    public void sisalto(OmaLista o){
        //parametrin on oltava OmaLista-tyyppinen
        if(o != null)
            sisalto = o;
    }
    
    public Hakemisto yliHakemisto(){
        return yliHakemisto;
    }
    
    public void yliHakemisto(Hakemisto y){
        yliHakemisto = y;
    }
    
    //Korvataan toString metodi
    public String toString(){
        //Poikkeus juurihakemistolle
        if(yliHakemisto != null)
            return super.toString() + "/ " + sisalto.koko();
        else
            return "/";
    }
    
    //Nykyisen rivin tulostus käyttöliittymään
    public String rivi(){
        //lisätään juurihakemistolle poikkeus, jolloin tulostaan ainoastaan "/"
        if(yliHakemisto != null)
            return yliHakemisto.rivi() + super.toString() + "/";
        else
            return "/";
    }
    
}