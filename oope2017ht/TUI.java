//Ohjelman tekstipohjainen käyttöliittymä
package oope2017ht;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;
import oope2017ht.tiedot.*;

public class TUI {
    
    //Ohjelman tulkki
    private static Tulkki tulkki = new Tulkki();
    
    //Käyttöliittymän tulee tunnistaa ohjelman lopettava komento "exit"
    private final String EXIT = "exit";    //-exit Ohjelman lopetus
    
    
    //Operaatio, joka käynnistää terminaalin
    public void kaynnista(){
    
        //Käyttäjän komento tallennetaan String muodossa
        String input;
        
        boolean b = false;
        
        //Tulostetaan tervehdys
        System.out.println("Welcome to SOS.");

        
        //Luetaan käyttäjältä komentoja, kunnes ohjelman käyttö lopetetaan EXIT komennolla
        do{
            //Luetaan käyttäjältä syöte ja jaetaan se osiin split operaatiolla
            System.out.print(tulkki.rivi() + ">");
            input = In.readString();
            String[] komento = input.split(" ");
            


            
            //viedään komento tulkkiin, syötteessä on virhe, paluuarvo on false
            //exit sulkee ohjelman, eikä sitä tarvi viedä tulkkiin
            if(!input.equals(EXIT)){
                b = tulkki.suorita(komento);
                
                //Jos ohjelman suorittamisessa tapahtui virhe, tulostetaan virheilmoitus
                if(b == false)
                    System.out.println("Error!");
            }
            
        }while(!input.equals(EXIT));
        
        //Ohjelma suljetaan, tulostetaan viesti
        System.out.println("Shell terminated.");
        
    }
}