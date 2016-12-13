/*
 * Lausekielinen ohjelmointi 2013.
 * Laki-kurssin toinen harjoitustyö: Tekstipohjainen Matopeli
 * Sami-Santeri Svensk
 *
 * Korjattu versio.
 * 
 */

public class Matopeli2 {

	public static int[][] kasvataPaikatTaulukkoa(int[][]paikat, int hantaRivi, int hantaSarake) {
	  int[][] uusi = new int[paikat.length + 1][2];
	  for( int i = 0; i < paikat.length; i++ ){
             uusi[i][0] = paikat[i][0];
             uusi[i][1] = paikat[i][1];
          }
	  uusi[uusi.length - 1][0] = hantaRivi;
          uusi[uusi.length - 1][1] = hantaSarake; 
	  
	  return uusi;
	}
    
      public static void tulostaAlkutekstit() { 
          System.out.println("~~~~~~~~~~~");
          System.out.println("~ W O R M ~");
          System.out.println("~~~~~~~~~~~");
      }

      //metodi, joka luo madolle koordinaatit paikat-nimiseen taulukkoon.
      public static int[][] luoMato(int[][]paikat, int pituus) {
        int rivi=0;
        int tayttoApu=pituus;
        do {
           paikat[rivi][0] = 1;
           paikat[rivi][1] = tayttoApu;
           rivi++;
           tayttoApu--;
        }
        while (rivi < pituus);
        //palautetaan paikat-taulukko ja siihen tehdyt muutokset.
        return paikat;
      }

    //metodi, joka alustaa kentän ja täyttää sen kehysmerkeillä (välilyönti) ja täyttömerkeillä (piste).
    public static char[][] alustaKentta(char[][]kentta, int pituus, int madonreikaLaskuri, int rivilkm, int sarakelkm) {
        System.out.println("Worm length: "+pituus+", wormholes: "+madonreikaLaskuri+".");
        int rivi=0;
        int sarake=0;
        char kehysmerkki = '.';
        char tayttomerkki = ' ';

        //do-while-silmukka, jossa kenttä täytetään reunoilta pisteillä ja muualta välilyönneillä.
        do {
           if (rivi == 0 || rivi == (rivilkm - 1) || sarake == 0 || sarake == (sarakelkm - 1))
              kentta[rivi][sarake] = kehysmerkki;
           else
              kentta[rivi][sarake] = tayttomerkki;

           sarake = sarake + 1;
           if (sarake == sarakelkm) {
             sarake = 0;   //40
             rivi = rivi + 1;
           }
        }
        while(rivi < rivilkm);
        //palautetaan kenttä ja siihen tehdyt muutokset.
        return kentta;
      }

   //paluu-arvoton metodi, joka alustaa kenttä2:n, liittää madon siihen ja tulostaa kenttä2:n.
    public static void alustaKentta2(char[][]kentta, char[][]kentta2, int[][]paikat, int pituus, int sarakelkm, int rivilkm) {
       int rivi=0;
       int sarake=0;
      
       //do-while silmukka, jolla kenttä kopioidaan kenttä2:een ja jossa kenttä2 tulostetaan.
       do {
          kentta2[rivi][sarake]=kentta[rivi][sarake];

          //for-silmukka, jolla madon merkit liitetään kenttä2:een.
          for (int paikanInd = 0; paikanInd < pituus; paikanInd++) {

             int merkinRivInd = paikat[paikanInd][0];
             int merkinSarInd = paikat[paikanInd][1];
            
             if (paikanInd==0)
                 kentta2[merkinRivInd][merkinSarInd] = 'X';
             else if (paikanInd==1)
                 kentta2[merkinRivInd][merkinSarInd] = 'x';
             else
                 kentta2[merkinRivInd][merkinSarInd] = 'o';
          }  
          sarake++;
          if (sarake==sarakelkm) {
              sarake=0;
              rivi++;
          }
       }
       while(rivi < rivilkm);
      }

      public static void tulostaKentta2(char[][]kentta2, int rivilkm, int sarakelkm) {
         int rivi=0;
         int sarake=0;
         do {
         System.out.print(kentta2[rivi][sarake]);   
         sarake++;
         if (sarake==sarakelkm) {
             sarake=0;
             rivi++;
             System.out.println();
         }
       }
       while(rivi < rivilkm);
     }

      //metodi, joka liikuttaa madon päätä. Palauttaa muuttuneen paikat-taulukon.
      public static int[][] liikutaPaata(char valinta, char edellinen, int[][]paikat) {

         //Madon pään liikuttaminen suuntavalinnan mukaan.
         if (valinta=='u' && edellinen != 'd') {
            //madon pään riviarvo pienenee yhdellä.
            paikat[0][0]--; 
         }
         else if (valinta=='d' && edellinen != 'u') {
            //madon pään riviarvo suurenee yhdellä.
            paikat[0][0]++;
         }
         else if (valinta=='l' && edellinen != 'r') {
            //madon pään sarakearvo pienenee yhdellä
            paikat[0][1]--;
         }
         else if (valinta=='r' && edellinen != 'l') {
            //madon pään sarakearvo suurenee yhdellä.
            paikat[0][1]++;
         }
         return paikat;
     }

   //int-tyyppinen metodi, joka tutkii madon reunanylityksiä.
   public static int tutkiReunanylitys(int[][]paikat, int madonreikaLaskuri, int rivilkm, int sarakelkm, char [][]kentta) { 
         char tayttomerkki = ' ';
         char kehysmerkki = '.';
         if (paikat[0][0] == 0 && kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki) {
             madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][0] < 0) {
             paikat[0][0] = rivilkm-1;
             if(kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki)
                madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][0] == (rivilkm-1) && kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki) {
             madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][0] == rivilkm) {
             paikat[0][0] = 0;
             if(kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki)
                madonreikaLaskuri++;          
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][1] == 0 && kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki) {
             madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][1] < 0) {
             paikat[0][1] = (sarakelkm-1);
             if(kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki)
                madonreikaLaskuri++;  
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][1] == (sarakelkm-1) && kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki) {
             madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         if (paikat[0][1] == sarakelkm) {
             paikat[0][1] = 0;
             if(kentta[paikat[0][0]][paikat[0][1]] == kehysmerkki)
                madonreikaLaskuri++;
             kentta[paikat[0][0]][paikat[0][1]] = tayttomerkki;
         }
         return madonreikaLaskuri;
         }

    //metodi, joka vaihtaa madon pään ja hännän paikkoja keskenään. Main-metodissa on ehto,
    //jonka mukaan metodi suoritetaan vain jos käyttäjä on syöttänyt s-kirjaimen.
    public static int[][] kaannaMato(int[][]paikat, int pituus, boolean syoteOK) {

           //swap-toimintoa varten oleva taulukko.
           int[][] swapApu = new int[paikat.length][2];
           int paikanInd=0;
           int a = 1;
           syoteOK=true;

           //do-while-silmukka, joka kopioi paikat-taulukon arvot swapApu-taulukkoon päinvastaisessa
           //järjestyksessä, esim. ensimmäinen paikat-taulukon rivi sijoitetaan swapApu-taulukon
           //viimeiseen riviin.
           do {
              swapApu[paikanInd][0]=paikat[pituus - a][0];
              swapApu[paikanInd][1]=paikat[pituus - a][1];
              paikanInd++;
              a++;
           }
           while(paikanInd < pituus);
           paikanInd = 0;

           //Tässä silmukassa swapApu-taulukon arvot sijoitetaan paikat-taulukkoon suoraan.
           do {
              paikat[paikanInd][0]=swapApu[paikanInd][0];
              paikat[paikanInd][1]=swapApu[paikanInd][1];
              paikanInd++;
           }
           while(paikanInd < pituus);
           return paikat;
    }

    public static char paatteleEdellinen(int[][]paikat, char edellinen) {
         if (paikat[0][0] < paikat[1][0])
            edellinen='u';
         else if (paikat[0][1] < paikat[1][1])
            edellinen='l';
         else if (paikat[0][0] > paikat[1][0])
            edellinen='d';
         else if (paikat[0][1] > paikat[1][1])
            edellinen='r';
         return edellinen;
    }

    public static boolean onkoSyoty(char[][]kentta2, int[][]paikat, boolean syonti) {
      char tayttomerkki = ' ';
      if (kentta2[paikat[0][0]][paikat[0][1]] == '+') {
          kentta2[paikat[0][0]][paikat[0][1]] = tayttomerkki;
          syonti=true;
      }
      else
          syonti=false;
      return syonti;
    }

      //metodi, jolla paikat-taulukko saa uudet arvot eli madon merkkien koordinaatit muuttuvat
      //madon liikkuessa johonkin suuntaan, ei madon vaihtaessa päätä.
      public static int[][] liikutaMatoa (int[][]paikat, int pituus, char valinta, boolean syonti, boolean syoteOK, int vanhaRivi, int vanhaSarake) {
        int paikanInd=pituus-2;  
        if (valinta != 's' && !syonti && syoteOK) {

          //do-while-silmukka, joka suoritetaan kun mato liikkuu eikä syö.
          //paikat-taulukossa viimeinen rivi siis korvataan sitä ylemmällä rivillä ja niin eteenpäin.
          do {
             if (paikanInd==1) {
                paikat[paikanInd+1][0]=paikat[paikanInd][0];
                paikat[paikanInd+1][1]=paikat[paikanInd][1];
                paikat[paikanInd][0]=vanhaRivi;
                paikat[paikanInd][1]=vanhaSarake;
             }
             else {
                paikat[paikanInd+1][0]=paikat[paikanInd][0];
                paikat[paikanInd+1][1]=paikat[paikanInd][1];
             }
             paikanInd--;
          }
          while(paikanInd >=1);   
       }

      if (valinta != 's' && syonti && syoteOK) {   
        paikanInd=pituus-1;
        //do-while-silmukka, joka suoritetaan kun mato liikkuu ja syö.
        //Erotuksena edelliseen silmukkaan, tässä paikat-taulukon alin rivi tallennetaan ja sijoitetaan
        //uuteen paikat-taulukkoon ja tätä kautta mato kasvaa.
        do {
           if (paikanInd==1) {
              paikat[paikanInd+1][0]=paikat[paikanInd][0];
              paikat[paikanInd+1][1]=paikat[paikanInd][1];
              paikat[paikanInd][0]=vanhaRivi;
              paikat[paikanInd][1]=vanhaSarake;
           }
           else {
              paikat[paikanInd+1][0]=paikat[paikanInd][0];
              paikat[paikanInd+1][1]=paikat[paikanInd][1];
           }
           paikanInd--;
        }
        while(paikanInd >=1);  
       }
       //lopuksi palautetaan paikat-taulukkoon tehdyt muutokset.
       return paikat;
       }

    //metodi, joka tutkii osuuko mato itseensä, onko madonreikiä liikaa vai onko käyttäjä päättänyt lopettaa pelin.
    //metodi palauttaa boolean-tyyppisen muuttujan, joka muuttuu todeksi, jos joku edellisistä tapauksista on tapahtunut.
    public static boolean lopetetaanko(int[][]paikat, boolean lopeta, int pituus, int madonreikaLaskuri, char valinta) {
      lopeta=false;
      int paikanInd=2;
      do {
      if (paikat[0][0]==paikat[paikanInd][0] && paikat[0][1]==paikat[paikanInd][1]) {
          lopeta=true;
          break;
      }
      else 
         paikanInd++;
      }
      while(paikanInd < pituus);

      //Tutkitaan onko madonreikälaskuri liian suuri eli suurempi kuin 3.
      if (madonreikaLaskuri > 3) 
         lopeta=true;
      if (valinta=='q')
         lopeta=true;
      return lopeta;
         }

      //metodi, joka alustaa kenttä2:n kentässä tapahtuneiden muutosten perusteella.
      public static char[][] alusta(char[][]kentta, char[][]kentta2, int rivilkm, int sarakelkm, boolean syonti) {
      int rivi   =  0;
      int sarake  = 0;
      do {
         if (kentta2[rivi][sarake] == '+' && !syonti)
             kentta2[rivi][sarake] = '+';   
         else
             kentta2[rivi][sarake]=kentta[rivi][sarake];         
         sarake = sarake + 1;
         if (sarake == sarakelkm) {
            sarake = 0;  
            rivi = rivi + 1;
         }
      }
      while(rivi < rivilkm);

      //Jos mato on syönyt, uusi ruokamerkki ilmaantuu kenttä2:een.    

      return kentta2;
      }

      //metodi, joka liittää madon merkit kenttään ja tulostaa itse kentän. (kenttä2).
      public static char[][] alustaMato (char[][]kentta, char[][]kentta2, int[][]paikat, int pituus, int rivilkm, int sarakelkm, boolean syonti) {
      int rivi=0;
      int sarake=0;
      final char PAA = 'X';
      final char PAA2 = 'x';
      final char MATOMERKKI = 'o';

      //do-while-silmukka, jossa kentän merkit tulostetaan näytölle, ja joka pitää
      //sisällään madon merkkien sijoitukseen käytettävän for-silmukan.
      do { 
        if (kentta2[rivi][sarake]=='+' && !syonti)
            kentta2[rivi][sarake]='+';
        else
            kentta2[rivi][sarake]=kentta[rivi][sarake]; 
        for (int paikanInd = 0; paikanInd < pituus; paikanInd++) {

            int merkinRivInd = paikat[paikanInd][0];
            int merkinSarInd = paikat[paikanInd][1];
            
            if (paikanInd==0)
                kentta2[merkinRivInd][merkinSarInd] = PAA;
            else if (paikanInd==1)
                kentta2[merkinRivInd][merkinSarInd] = PAA2;
            else
                kentta2[merkinRivInd][merkinSarInd] = MATOMERKKI;
            }  
         sarake++;
         if (sarake==sarakelkm) {
             sarake=0;
             rivi++;
         }
       }
       while(rivi < rivilkm);

       if(syonti)
          Automaatti.tarjoile(kentta2);

       return kentta2;
       } 

       public static void tulostaPelikentta(char[][]kentta2, int sarakelkm, int rivilkm) {
         int sarake=0;
         int rivi=0;
         do {
            System.out.print(kentta2[rivi][sarake]);   
            sarake++;
            if (sarake==sarakelkm) {
               sarake=0;
               rivi++;
               System.out.println();
            }
         }
         while(rivi < rivilkm);
       }

      public static void main(String []args) {
    try {
      //Seuraavat kolme muuttujaa syötetään komentoparametreina.
      //Automaatin käynnistäjä.

      int parametreja = args.length;

      int siemen = Integer.parseInt(args[0]);

      //rivien lukumäärä.
      int rivilkm = Integer.parseInt(args[1]);

      //sarakkeiden lukumäärä.
      int sarakelkm = Integer.parseInt(args[2]);

      //Madon pituus, joka on alussa 5.
      int pituus=5;

      //madon koordinaattien määrittämiseen luotu paikat-taulukko, joka on luotu tarpeeksi isoksi.
      int[][] paikat = new int [pituus][2];
     
      //pelikenttä, joka pitää sisällään vain välilyönnit, pisteet ja ruokamerkin.
      char [][] kentta = new char [rivilkm][sarakelkm];

      //pelikenttä, joka tulostetaan näytölle, joka sisältää madon ja johon kopioidaan aiempi kenttä.
      char [][] kentta2 = new char [rivilkm][sarakelkm]; 

      //Edellistä valintaa ilmaisema muuttuja. Alustetaan r, koska alkutilanteessa mato on menossa
      //oikealle päin (right).
      char edellinen = 'r';

      //lippumuuttuja.
      boolean lopeta=false;

      //Laskuri, joka laskee madon läpäisyt reunoilla.
      int madonreikaLaskuri=0;

      //lippumuuttujia.
      boolean syoteOK=true;
      boolean syonti=false;

      tulostaAlkutekstit();

      if (parametreja != 3 || rivilkm < 3 || sarakelkm < 7) {
          System.out.println("Invalid command-line argument!");
          System.out.println("Bye, see you soon.");
          }
      else {

      //Luodaan madolle koordinaatit.
      luoMato(paikat, pituus);

      //Automaatin käynnistys.
      Automaatti.kaynnista(siemen);

      //Kenttä alustetaan. Tämä tehdään vain kerran.
      alustaKentta(kentta, pituus, madonreikaLaskuri, rivilkm, sarakelkm); 

      //Ruokamerkki heitetään alustuksen jälkeen kentälle.
      alustaKentta2(kentta, kentta2, paikat, pituus, sarakelkm, rivilkm); 

      Automaatti.tarjoile(kentta2);

      tulostaKentta2(kentta2, rivilkm, sarakelkm);   

      //Do-while-silmukka, joka pitää sisällään suuntavalinnat ja madon liikuttamisen ja pelikentän
      //tulostukset ja jota toistetaan kunnes lippumuuttuja lopeta muuttuu todeksi.
      do {

      //Pään koordinaattien tallentaminen.
      int vanhaRivi = paikat[0][0];
      int vanhaSarake = paikat[0][1];
      int hantaRivi = paikat[pituus-1][0];
      int hantaSarake = paikat[pituus-1][1];

      //tulostetaan infoteksti.
      System.out.println("(l)eft, (r)ight, (u)p, (d)own, (s)wap or (q)uit?");

      //Käyttäjältä luettava suuntavalinta.
      char valinta = In.readChar();

      //Jos valinta ei ole eri suuntaan kuin edellinen, suoritetaan if-lauseen operaatiot ja madon päätä
      //siirtävä metodi.
      if ((valinta == 'u' && edellinen != 'd') || (valinta == 'd' && edellinen != 'u') || (valinta == 'r' && edellinen != 'l') || (valinta == 'l' && edellinen != 'r')) {
         syoteOK=true;
         lopeta=false;
         liikutaPaata(valinta, edellinen, paikat);

         //Tutkitaan onko madonpää kentän reunoilla vai jopa ylittänyt reunan, jolloin pää heitetään vastakkaiselle
         //reunalle. Molemmissa tapauksissa madonreikiä laskeva laskuri kasvaa yhdellä.

         madonreikaLaskuri=tutkiReunanylitys(paikat, madonreikaLaskuri, rivilkm, sarakelkm, kentta);

         //Lopuksi tallennetaan valinta edellinen-muuttujaan.
         edellinen=valinta;
         }
      //Jos valinta on eri suuntaan kuin edellinen valinta, todetaan että syöte ei ole ok.
      else if ((valinta == 'u' && edellinen == 'd') || (valinta == 'd' && edellinen == 'u') || (valinta == 'r' && edellinen == 'l') || (valinta == 'l' && edellinen == 'r')) {
          lopeta=false;
          syoteOK=false;
      }
      else if (valinta == 's') {
         //Kun on valittu swap, suoritetaan madon pään ja hännän paikat keskenään vaihtava metodi.
         kaannaMato(paikat, pituus, syoteOK);

         //Tutkitaan paatteleEdellinen-metodissa mihin suuntaan mato on menossa kääntämisen jälkeen ja näin
         //saadaan selville mikä on edellinen-muuttujan uusi arvo.
         edellinen = paatteleEdellinen(paikat, edellinen);
         syonti=false;
         }
      //Jos valinta on quit, lopetetaan ohjelma.
      else if (valinta=='q') {
         lopeta=true;
         syoteOK=true;
      }
      //Jos valinta on joku muu, todetaan ettei syöte kelpaa ja lippumuuttuja syoteOK on epätosi.
      else {
         lopeta=false;
         syoteOK=false;
      }

      //Jos mato syö, ruokamerkki korvataan täyttömerkillä (välilyönti).
      syonti=onkoSyoty(kentta2, paikat, syonti);
      if(syonti)
         paikat = kasvataPaikatTaulukkoa(paikat, hantaRivi, hantaSarake);

      //Matoa liikutetaan. Metodin sisällä on määritelty, että mitään ei tapahdu, jos valinta
      //on s eli swap eli madon kääntäminen.
      liikutaMatoa (paikat, pituus, valinta, syonti, syoteOK, vanhaRivi, vanhaSarake);
      
      //Jos mato on syönyt, pituuslaskuri kasvaa yhdellä.
      if (syonti)
         pituus++;      

      lopeta = lopetetaanko(paikat, lopeta, pituus, madonreikaLaskuri, valinta);

      //Jos metodi palauttaa lippumuuttujan arvolla tosi, ohjelman suoritus katkeaa tässä kohtaa
      //ja tulostetaan lopputekstit.
      if(lopeta) {
         System.out.println("Bye, see you soon.");
         break;
       }

      //Tulostetaan infoteksti.
      System.out.println("Worm length: "+pituus+", wormholes: "+madonreikaLaskuri+".");

      //alustetaan kenttä2 kopioimalla kenttä siihen.
      alusta(kentta, kentta2, rivilkm, sarakelkm, syonti);

      //mato liitetään kenttä2:een ja kenttä2 tulostetaan näytölle.
      alustaMato (kentta, kentta2, paikat, pituus, rivilkm, sarakelkm, syonti);

      tulostaPelikentta(kentta2, sarakelkm, rivilkm);
      }
      while(!lopeta);
    }
    }
      catch (NumberFormatException e) {
          System.out.println("~~~~~~~~~~~");
          System.out.println("~ W O R M ~");
          System.out.println("~~~~~~~~~~~");
          System.out.println("Invalid command-line argument!");
          System.out.println("Bye, see you soon.");
      }
      catch (ArrayIndexOutOfBoundsException e) {
          System.out.println("~~~~~~~~~~~");
          System.out.println("~ W O R M ~");
          System.out.println("~~~~~~~~~~~");
          System.out.println("Invalid command-line argument!");
          System.out.println("Bye, see you soon.");
      }
      catch (NegativeArraySizeException e) {
          System.out.println("~~~~~~~~~~~");
          System.out.println("~ W O R M ~");
          System.out.println("~~~~~~~~~~~");
          System.out.println("Invalid command-line argument!");
          System.out.println("Bye, see you soon.");
      }
   }
}
