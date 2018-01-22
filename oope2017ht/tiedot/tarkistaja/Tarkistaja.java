//tarkistaja-luokka tarkistaa nimen ja koon
package oope2017ht.tiedot.tarkistaja;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.*;

public class Tarkistaja {
    
    //Operaatio tarkistaa nimen
    public boolean tarkistaNimi(String nimi){
        //Nimessä ei saa esiintyä erikoismerkkejä
        boolean nimiOk = false;
        int p = 0;
        //tarkistetaan erikoismerkit ja pisteet
        for(int i = 0; i < nimi.length(); i++){
            if(
            (nimi.charAt(i) >= 'A'  && nimi.charAt(i) <= 'Z') ||
            (nimi.charAt(i) >= 'a'  && nimi.charAt(i) <= 'z') ||
            (nimi.charAt(i) >= '0'  && nimi.charAt(i) <= '9') ||
            nimi.charAt(i) == '.' || nimi.charAt(i) == '_' 
            ){
                nimiOk = true;
                if(nimi.charAt(i) == '.')
                    p++;
            }else{
                nimiOk = false;
                i = nimi.length();
            }
            if(nimi == ".")
                p = 2;
        }
        
        //pisteiden tarkistus
        if(p > 1)
            nimiOk = false;
        
        return nimiOk;
    }
    
    
    //Operaatio tarkistaa koon
    public boolean tarkistaKoko(int koko){
        if(koko >= 0)
            return true;
        else 
            return false;
    }
}
