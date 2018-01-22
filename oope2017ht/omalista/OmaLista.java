//Oope 2017 harjoitustyö
//Omalista-luokka
package oope2017ht.omalista;

import oope2017ht.tiedot.*;
import fi.uta.csjola.oope.lista.*;
import apulaiset.*;


//OmaLista-luokassa toteutetaan Ooperoiva-rajapinnan operaatioita
public class OmaLista extends LinkitettyLista implements Ooperoiva {    
    
    
    /** hae-operaatio tutkii onko listalla parametrin mukaista alkiota
      * @param haettava viite listalta haettavaan olioon
      * @return alkio, jos haettu olio löytyi 
      * @return null jos mitään ei löytynyt
      * @throws NullPointerException, poikkeusten varalta
      */
      
    public Object hae(Object haettava) throws NullPointerException {
        //Jos parametri on null tai lista on tyhjä, paluuarvo on null
        if(haettava == null || onkoTyhja())
            return null;
        else{
            //Käydään listaa läpi, ja verrataan alkioita
            //aloitetaan listan päästä
            Solmu tutki = paa();
            
            //boolen s yllätpitää silmikointia
            boolean s = true;
            
            //Käydään listaa läpi kunnes haettava on löytynyt tai listan loppu on saavutettu
            do{
                //napataan mahdollinen poikkeus
                try{
                    //Solmun viite nykyiseen alkioon
                    Object alkio = tutki.alkio();
                    
                    //Verrataan alkioita equals operaatiolla
                    boolean vertaa = alkio.equals(haettava);
                    
                    //Jos haettava on löytynyt lopetetaan silmukointi ja palautetaan alkio
                    if(vertaa){
                        s = false;
                        return alkio;
                        
                    //lista päättyi lopetetaan silmukka ja palautetaan null
                    }else if(tutki.seuraava() == null){
                        s = false;
                        return null;
                        
                    //Haettava ei löytynyt. Siirrytään seuraavaan alkioon
                    }else
                        tutki = tutki.seuraava();
                    
                //tapahtui poikkeus, palautetaan null
                }catch(Exception e){
                    return null;
                }
            }while(s);
            
            //Palautetaan viite löydettyyn olioon. null jos mitään ei löytynyt
            return null;
        }
    }
    

    
    /** Operaatio, joka lisää listalle uuden olion
      * Koska operaatio käyttää Comparable rajapintaa, 
      * täytyy kääntäjälle kertoa lisänmääreellä metodin olevan käännettävissä
      * @param uusi olio, joka lisätään listalle
      * @return true, jos olio lisättiin listalle
      * @return false, jos tapahtui poikkeus
      * @throws NullPointerException, ClassCastException
      */
    
    //Lisätään listalle uusi alkio
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object uusi) throws NullPointerException, ClassCastException {
        
        //Jos parametri on null, sitä ei voida lisätä listalle
        if(uusi == null)
            return false;
        else if(onkoTyhja()){
            lisaaAlkuun(uusi); 
        return true;
        }else{
            //Käydään listaa läpi ja napataan poikkeukset
            try{
                int i = 0;
                do{
                    //Lisätään  Comparable viitteet kaikkiin vertailussa tarvittaviin alkioihin 
                    Comparable lisattava = (Comparable)uusi;
                    Comparable vertailtava = (Comparable)alkio(i);
                    Comparable seuraava = (Comparable)alkio(i+1);
                    Comparable edellinen = (Comparable)alkio(i-1);
                                            
                    //lisätään, jos paikka löytyi
                    
                    //Jos seuraava on null, on saavuttu listan päähän
                    if(seuraava == null){
                        //riippuen 
                        if(lisattava.compareTo(vertailtava) <= 0)
                            lisaa(i, uusi);
                        else
                            lisaaLoppuun(uusi);
                        
                        i=koko();
                        return true;
                        
                    //jos löydetty paikka on listan ensimmäinen alkio
                    }else if(edellinen == null && lisattava.compareTo(vertailtava) <= 0){
                        lisaaAlkuun(uusi);
                        i=koko();
                        return true;
                        
                    //Jos seuraava on suurempi kuin lisättävä ja nykyinen on pienempi, paikka on löytynyt    
                    }else if(lisattava.compareTo(vertailtava) > 0 && lisattava.compareTo(seuraava) <= 0){
                        lisaa(i+1, uusi);
                        i=koko();
                        return true;
                    }
                    i++;
                }while(i < koko());
            //Tapahtui poikkeus, napataan se ja palautetaan false
            }catch(Exception e){
                return false;
            }
        }
        return true;
    }
    
    /** Operaatio, joka poistaa olion listalta
      * @param poistettava viiten listalta poistettavaan olioon
      * @return paikka, jos olio poistettiin palautetaan tieton sen paikasta
      * @return null jos poistettavaa ei löytynyt listalta
      */
    
    //Poistetaan listalta parametrin nimeämä alkio equals-operaation avulla
    public Object poista(Object poistettava){
        
        //Jos parametri on null tai lista on tyhjä palautetaan null
        if(poistettava == null || onkoTyhja())
            return null;
        else{
            
            //Käydään listaa läpi, ja tarkistetaan alkioita equals operaatiolla
            //Poistettavia alkioita oletetaan olevan korkeintaa yksi kappale, 
            //joten silmukointi lopetetaan kun parametria vastaava alkio on löydetty
            int l = 0;
            
            //aloitetaan listan päästä
            Solmu nyky = paa();
            
            do{
                //apuviite
                Object paikka = nyky.alkio();
                
                //verrataan alkioita equals operaatiolla
                boolean p = paikka.equals(poistettava);
                
                //Jos poistettava alkio löytyi, poistetaan se ja lopetetaan silmukointi
                if(p){
                    poista(l);
                    nyky = null;
                    return paikka;
                //poistettavaa ei löytynyt, siirrytään seuraavaan alkioon
                }else{
                    l++;
                    nyky = nyky.seuraava();
                }
            }while(nyky != null);
            
            //poistettavaa ei löytynyt, return null
            return null;
            
            
        }
    }
    
   /** toString metodin korvaus
     */
    
   public String toString() {
      // Listan alkioittainen merkkijonoesitys tänne.
      String alkiot = "[ ";

      // Tarkistetaan, että listalla on alkioita.
      if(!onkoTyhja()) {
         // Aloitetaan parametrina saadun listan päästä.
         Solmu paikassa = paa();

         // Edetään solmu kerrallaan, kunnes löydetään alkio tai lista loppuu.
         while (paikassa != null) {
            // Liitetään apuviite paikassa-viitteeseen liittyvän solmun alkioon.
            Object paikanAlkio = paikassa.alkio();

            // Siirrytään seuraavaan solmuun. Seuraava-aksessori palauttaa
            // viitteen paikassa-viitteeseen liittyvää solmua _seuraavaan_
            // solmuun. Sijoituksen jälkeen paikassa-viite liittyy tähän solmuun.
            paikassa = paikassa.seuraava();

            // Lisätään alkio ja erotin merkkijonoon.
            alkiot += paikanAlkio;
            if (paikassa != null)
               alkiot += ", ";
         }

      }

      // Viimeistellään esitys.
      alkiot += " ]";

      // Palautetaan oma lista merkkijonona.
      return alkiot;
   }
    
}

