//Ohjelman tulkki-luokka
//Tulkitsee käyttäjän syötteen, ja suorittaa toimintoja sen perusteella
package oope2017ht;

import oope2017ht.omalista.*;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import oope2017ht.tiedot.tarkistaja.*;
import oope2017ht.tiedot.*;

public class Tulkki {
    
    //Tulkin täytyy tunnistaa kaikki toimintoja suoritavat komennot
    
    private final String CD = "cd";            //-cd Hakemiston vaihto 
    private final String MD = "md";            //-md Hakemiston luonti 
    private final String MF = "mf";            //-mf Tiedoston luonti 
    private final String MV = "mv";            //-mv Tiedoston tai hakemiston nimen vaihto 
    private final String LS = "ls";            //-ls Hakemiston sisällön listaus aakkosjärjestyksessä 
    private final String CP = "cp";            //-cp tiedoston kopionti
    private final String RM = "rm";            //-rm Tiedoston tai hakemiston poistaminen nykyhakemistosta 
    private final String FIND = "find";        //-find Hakemiston ja sen alihakemistojen rekursiivinen listaus
    

    //luodaan juurihakemisto, toString metodin korvauksessa on hakemisto-luokassa poikkeus juurihakemistolle
    //luodaan myös viite nykyiseen hakemistoon
    private Hakemisto dir = new Hakemisto(new StringBuilder("juuri"), null);
    private Hakemisto nyky = dir;
    
    
    //Suorita operaatio saa käyttäjä syötteen array tyyppisenä parametrina
    //operaatio vie parametrin eteenpäin riippuen komennosta
    public boolean suorita(String[] komento){
        
        //jos parametrejä on nolla tai yli kolme, palautetaan false
        if(komento.length == 0 || komento.length > 3)
            return false;
        
        
        //tarkistetaan komennot, ja että parametreja on annettu komentoa vastaava määrä
        //viedään parametrit toimintoa suorittavaan operaatioon
        if(komento[0].equals(CD) && komento.length <= 2){
            return cd(komento);
        }else if(komento[0].equals(MD) && komento.length == 2){
            return md(komento);
        }else if(komento[0].equals(MF) && komento.length == 3){
            return mf(komento);
        }else if(komento[0].equals(MV) && komento.length == 3){
            return mv(komento);
        }else if(komento[0].equals(LS) && komento.length <= 2){
            return ls(komento);
        }else if(komento[0].equals(CP) && komento.length == 3){
            return cp(komento);
        }else if(komento[0].equals(RM) && komento.length == 2){
            return rm(komento);
        }else if(komento[0].equals(FIND) && komento.length == 1){
            return find(komento[0]);
        }else 
            return false;
        
        
    }
    
    /** Ohjelman toiminnot **/
    /** Boolean tyyppisten operaatioiden paluuarvot kertovat onnistuiko operaation toteuttaminen **/
    
    //Hakemiston vaihdon toteuttava komento x
    public boolean cd(String[] komento) throws NullPointerException{
        
        //Tarkistetaan onko hakemistossa parametria vastaavaa alihakemistoa
        //napataan NullPointerException, jos ..-komento on annettu juurihakemistossa
        try{
            //jos syötteessä on pelkkä komento "cd" siirrytään juurihakemistoon, paitsi jos ollaan jo juurihakemistossa
            if(komento.length == 1){
                nyky = dir;
                return true;
            //Jos syöte vastaa hakemiston ylihakemistoa - eli kaksi pistettä -, liikutaan ylihakemistoon
            }else if(komento[1].equals("..") && nyky.yliHakemisto() != null){
                //nyky.poista(apu);
                nyky = nyky.yliHakemisto();
                return true;
                
            //Tarkistetaan onko hakemistossa parametria vastaavaa alihakemistoa, ja liikutaan tarvittaessa
            }else if(new Hakemisto(new StringBuilder(komento[1]), nyky).equals(nyky.hae(komento[1])) 
                && nyky.hae(komento[1]) instanceof Hakemisto){
                    
                nyky = (Hakemisto)nyky.hae(komento[1]);
                return true;
             
            //hakemiston vaihto ei onnistunut
            }else
                return false;
        //Tapahtui poikkeus, palautetaan false
        }catch(Exception e){
            return false;
        }
    }
    
    //Hakemiston luonti x
    public boolean md(String[] komento) throws IllegalArgumentException{
        try{
            return nyky.lisaa(new Hakemisto(new StringBuilder(komento[1]), nyky));
        //syötteessä oli virhe
        }catch(Exception e){
            return false;
        }
    }
    
    //Tiedoston luonti x
    public boolean mf(String[] komento) throws IllegalArgumentException{
        try{
            int k = kokoMuunto(komento[2]);
            return nyky.lisaa(new Tiedosto(new StringBuilder(komento[1]), k));
        //syötteessä oli virhe
        }catch(Exception e){
            return false;
        }
    }
    
    //Tiedoston tai hakemiston nimen vaihto x
    public boolean mv(String[] komento) throws IllegalArgumentException{
           
        //napataan poikkeus
        try{
            //Tarkistetaan onko hakemistossa parametria vastaavaa tiedostoa, tai alihakemistoa
            //Uutta nimeä vastaavaa alihakemistoa tai tiedostoa ei saa olla hakemistossa
            if(nyky.hae(komento[1]) != null && nyky.hae(komento[2]) == null){
                
                //korvataan uudelleen nimetyllä kopiolla
                //nyky.hae(komento[1]).nimi(new StringBuilder(komento[2]));
                if(nyky.hae(komento[1]) instanceof Tiedosto){
                    Tiedosto vt = (Tiedosto)nyky.hae(komento[1]);
                    nyky.poista(komento[1]);
                    vt.nimi(new StringBuilder(komento[2]));
                    nyky.lisaa(vt);
                    
                }else if(nyky.hae(komento[1]) instanceof Hakemisto){
                    Hakemisto vh = (Hakemisto)nyky.hae(komento[1]);
                    nyky.poista(komento[1]);
                    vh.nimi(new StringBuilder(komento[2]));
                    nyky.lisaa(vh);
                    
                }
                return true;
            
            //Tiedostoa ei löytynyt
            }else
                return false;
            
        //Uudessa nimessä oli virhe
        }catch(Exception e){
            return false;
        }
    }
    
    //Hakemiston sisällön listaus aakkosjärjestyksessä x
    public boolean ls(String[] komento){
        
        if(komento.length == 1){
            //tulostetaan silmukan avulla
            //lista on automaatisesti oikeassa järjestyksessä, omalista-luokan vuoksi
            for(int i = 0; i < nyky.sisalto().koko() ; i++){
                System.out.print(nyky.sisalto().alkio(i).toString());
                System.out.println();
            }
            return true;
            
        //Tulostetaan Hakemisto ja Tiedosto tyyppiset tulosteet
        }else if(nyky.hae(komento[1]) != null){
            //Hakemisto
            if(nyky.hae(komento[1]) instanceof Hakemisto){
                //Hakemiston tulostamisen yhteydessä tulostetaan hakemistossa olevien tietojen määrä
                System.out.println(nyky.hae(komento[1]).toString());
                return true;
            //Tiedosto
            }else if(nyky.hae(komento[1]) instanceof Tiedosto){
                System.out.println(nyky.hae(komento[1]).toString());
                return true;
            }
        }
        return false;  
    }
    
    //tiedoston kopionti x
    public boolean cp(String[] komento) throws IllegalArgumentException{
        
        //hakemistosta täytyy löytyä parametrin 1 mukainen tiedosto,
        //eikä siellä saa olla parametrin 2 nimistä tiedostoa
        //Uudessa nimessä ei saa olle virhettä, ja kopioinnin kohteen tulee olla tiedosto
        try{
            //tarkistetaan nykyhakemiston sisältö
            if(nyky.hae(komento[1]) != null 
                && nyky.hae(komento[2]) == null 
                && nyky.hae(komento[1]) instanceof Tiedosto){
                
                //jos ehdot täyttyvät voidaan aloittaa kopiointi
                Tiedosto kopio = new Tiedosto((Tiedosto)nyky.hae(komento[1]));
                kopio.nimi(new StringBuilder(komento[2]));
                nyky.lisaa(kopio);
                return true;
                
            //ehdot eivät täyttyneet
            }else
                return false;
            
        }catch(Exception e){
            return false;
        }
    }
    
    //Tiedoston tai hakemiston poistaminen nykyhakemistosta x
    public boolean rm(String[] komento){
        if(nyky.poista(komento[1]) != null)
            return true;
        else
            return false;
    }  
    
    //Hakemiston ja sen alihakemistojen rekursiivinen listaus
    public boolean find(String komento) {
        
        //find komennolle poikkeus sitä varten, kun operaatio kutsuu itseään
        if(!komento.equals("find"))
            nyky = (Hakemisto)nyky.hae(komento);
        
        //Käydään sisältä läpi. Operaatio kutsuu itseään, kun tulostetaan alihakemistoja
        for(int i = 0; i < nyky.sisalto().koko() ; i++){
            System.out.println(rivi() + nyky.sisalto().alkio(i).toString());
            
            if(nyky.sisalto().alkio(i) instanceof Hakemisto){
                String alih = nyky.sisalto().alkio(i).toString();
                alih = alih.replaceAll("/", "");
                String[] a = alih.split(" "); alih = a[0];
                find(alih);
                if(nyky.yliHakemisto() != null)
                    nyky = nyky.yliHakemisto();
            }
           

        }
        
        return true;
    }

    /** Lisätoimintoja **/
    
    //Muutetaan koko String int-tyyppiseksi
    public int kokoMuunto(String koko){
        //Jos muuntaminen ei onistu palautetaan -1, jonka tarkistaja-luokka hylkää
        try { 
            int c = Integer.parseInt(koko); 
            return c;
        } catch(NumberFormatException e) { 
            return -1; 
        } catch(NullPointerException e) {
            return -1;
        } 
    }
    
    //Nykyisen rivin tulostus käyttöliittymään
    public String rivi(){

            String r = nyky.rivi();

        //poistetaan kaikki välilyönnit ja numerot Hakemisto-luokan toString metodin palautteesta
        r = r.replaceAll(" ", "");
        String[] rs = r.split(" ");
        r = rs[0];
        return r;
    }
    
}










