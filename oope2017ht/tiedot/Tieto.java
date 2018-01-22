//Oope 2017 harjoitustyö
//Tieto-yliluokka
package oope2017ht.tiedot;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;

public class Tieto implements Comparable<Tieto>{
    
    //Attribuutit--------------
    private StringBuilder nimi = new StringBuilder();    //Tiedon nimi
    
    
    
    //rakentajatat-------------       
    //Yksiparametrinen rakentaja
    public Tieto(StringBuilder syote){
        nimi(syote);
    }
    
    //Kopiorakentaja
    //Tietoluokan kopiorakentaja syväkopioi nimen
    public Tieto(Tieto t){
        //tarkistetaan viite
        if(t instanceof Tieto){
            //Syväkopioidaan nimi, luodaaan StringBuilder tyyppinen kopio-olio
            StringBuilder kopionimi = new StringBuilder(t.nimi());
            
            //Asetetaan viite kopionimeen
            nimi(kopionimi);
        }
    }
    
    
    //aksessorit ------------
    //palautetaan viite nimen kopioon
    public StringBuilder nimi(){
        //kopioidaan uuteen kopionimeen
        StringBuilder kopionimi = new StringBuilder(nimi);
        return kopionimi;
    }
    
    public void nimi(StringBuilder n) throws IllegalArgumentException {
        //tarkistetaan nimen oikeellisuus
        Tarkistaja para = new Tarkistaja();
        boolean nimiOk = para.tarkistaNimi(n.toString());
        if(nimiOk == true)
            nimi = n;
        else
            throw new IllegalArgumentException();
    }
    
    //-------------
    
    
    //Korvataan toString metodi
    public String toString(){
        return nimi.toString();
    }
    
    
    
    //Korvataan Comparable rajapinnan compareTo metodi
    public int compareTo(Tieto t) {
        //vertaillaan tietoja String luokan compareTo metodilla
        //Muunnetaan StringBuilder tyyppinen nimi String muotoon
        //if(t instanceof Hakemisto)
            return nimi.toString().compareTo(t.nimi().toString());
        /*else if (nimi.toString().compareTo(t.nimi().toString()) > 0)
            return 1;
        // Saman nimiset
        else if(nimi.toString().compareTo(t.nimi().toString()) == 0)
            return 0;
        else
            return -1;*/
    }
    
    //korvataan equals metodi
    public boolean equals(Object o){
        try{
            //Asetetaan parametriin tieto-luokan viite
            Tieto t = (Tieto)o;
            
            //Tiedot ovat samat, jos nimet ovat samat
            //StringBuilder tyyppinen nimi täytyy muuttaa String muotoon
            return nimi.toString().equals(t.nimi().toString());
        }catch(Exception e){
            return false;}
    }
    
    
}





