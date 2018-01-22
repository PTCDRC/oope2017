//Oope 2017 harjoitustyö
//Tiedosto-luokka
package oope2017ht.tiedot;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;

public class Tiedosto extends Tieto {

    //Attribuutit---------
    private int koko;    //Tiedoston koko, >= 0
    
    
    //rakentajat----------
    //kaksiparametrinen rakentaja, joka heittää virheelliset arvot
    public Tiedosto(StringBuilder n, int k) throws IllegalArgumentException{
        
        super(n);
        
        //Tarkistetaan parametrit
        Tarkistaja para = new Tarkistaja();
        boolean nimiOk = para.tarkistaNimi(n.toString());
        boolean kokoOk = para.tarkistaKoko(k);
        //Jos nimi tai koko oli virheellinen, heitetään poikkeus
        if(nimiOk == false || kokoOk == false)
            throw new IllegalArgumentException();
        else{
            koko(k);
        }

    }
    
    //kopiorakentaja
    public Tiedosto(Tiedosto t){
        
        //Kutsutaan yliluokan kopiorakentajaa
        super(t);
        
        if(t instanceof Tiedosto){
            //kopioidaan koko

            koko(t.koko());
        }
    }
    
    //Aksessorit---------------
    public int koko(){
        return koko;
    }
    
    //Asetetaan koolle arvo, muunnetaan tässä metodissa
    //aksessori int tyyppiselle parametrille
    public void koko(int k){
        if(k > 0)
            koko = k;
    }
    
    //Korvataan toString-metodi
    public String toString(){
        return super.toString() + " " + koko;
    }
    
    
}