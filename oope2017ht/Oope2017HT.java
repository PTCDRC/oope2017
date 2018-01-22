//Ohjelman ajo-luokka
package oope2017ht;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;
import oope2017ht.tiedot.*;


public class Oope2017HT {
    
    /** private static TUI kayttoLtm käyttöliittymä ohjelmalle
      */
    private static TUI kayttoLtm;
    
    public static void main(String[] args){
    
    //Luodaan ohjelmalle käyttöliittymä
    kayttoLtm = new TUI();
    
    //käynnistetään käyttöliittymä
    kayttoLtm.kaynnista();
    
    }
}