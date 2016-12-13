/*
 * Lausekielinen ohjelmointi 2013.
 * Laki-kurssin toinen harjoitusty�: Tekstipohjainen Matopeli
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

    //metodi, joka alustaa kent�n ja t�ytt�� sen kehysmerkeill� (v�lily�nti) ja t�ytt�merkeill� (piste).
    public static char[][] alustaKentta(char[][]kentta, int pituus, int madonreikaLaskuri, int rivilkm, int sarakelkm) {
        System.out.println("Worm length: "+pituus+", wormholes: "+madonreikaLaskuri+".");
        int rivi=0;
        int sarake=0;
        char kehysmerkki = '.';
        char tayttomerkki = ' ';

        //do-while-silmukka, jossa kentt� t�ytet��n reunoilta pisteill� ja muualta v�lily�nneill�.
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
        //palautetaan kentt� ja siihen tehdyt muutokset.
        return kentta;
      }

   //paluu-arvoton metodi, joka alustaa kentt�2:n, liitt�� madon siihen ja tulostaa kentt�2:n.
    public static void alustaKentta2(char[][]kentta, char[][]kentta2, int[][]paikat, int pituus, int sarakelkm, int rivilkm) {
       int rivi=0;
       int sarake=0;
      
       //do-while silmukka, jolla kentt� kopioidaan kentt�2:een ja jossa kentt�2 tulostetaan.
       do {
          kentta2[rivi][sarake]=kentta[rivi][sarake];

          //for-silmukka, jolla madon merkit liitet��n kentt�2:een.
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

      //metodi, joka liikuttaa madon p��t�. Palauttaa muuttuneen paikat-taulukon.
      public static int[][] liikutaPaata(char valinta, char edellinen, int[][]paikat) {

         //Madon p��n liikuttaminen suuntavalinnan mukaan.
         if (valinta=='u' && edellinen != 'd') {
            //madon p��n riviarvo pienenee yhdell�.
            paikat[0][0]--; 
         }
         else if (valinta=='d' && edellinen != 'u') {
            //madon p��n riviarvo suurenee yhdell�.
            paikat[0][0]++;
         }
         else if (valinta=='l' && edellinen != 'r') {
            //madon p��n sarakearvo pienenee yhdell�
            paikat[0][1]--;
         }
         else if (valinta=='r' && edellinen != 'l') {
            //madon p��n sarakearvo suurenee yhdell�.
            paikat[0][1]++;
         }
         return paikat;
     }

   //int-tyyppinen metodi, joka tutkii madon reunanylityksi�.
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

    //metodi, joka vaihtaa madon p��n ja h�nn�n paikkoja kesken��n. Main-metodissa on ehto,
    //jonka mukaan metodi suoritetaan vain jos k�ytt�j� on sy�tt�nyt s-kirjaimen.
    public static int[][] kaannaMato(int[][]paikat, int pituus, boolean syoteOK) {

           //swap-toimintoa varten oleva taulukko.
           int[][] swapApu = new int[paikat.length][2];
           int paikanInd=0;
           int a = 1;
           syoteOK=true;

           //do-while-silmukka, joka kopioi paikat-taulukon arvot swapApu-taulukkoon p�invastaisessa
           //j�rjestyksess�, esim. ensimm�inen paikat-taulukon rivi sijoitetaan swapApu-taulukon
           //viimeiseen riviin.
           do {
              swapApu[paikanInd][0]=paikat[pituus - a][0];
              swapApu[paikanInd][1]=paikat[pituus - a][1];
              paikanInd++;
              a++;
           }
           while(paikanInd < pituus);
           paikanInd = 0;

           //T�ss� silmukassa swapApu-taulukon arvot sijoitetaan paikat-taulukkoon suoraan.
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
      //madon liikkuessa johonkin suuntaan, ei madon vaihtaessa p��t�.
      public static int[][] liikutaMatoa (int[][]paikat, int pituus, char valinta, boolean syonti, boolean syoteOK, int vanhaRivi, int vanhaSarake) {
        int paikanInd=pituus-2;  
        if (valinta != 's' && !syonti && syoteOK) {

          //do-while-silmukka, joka suoritetaan kun mato liikkuu eik� sy�.
          //paikat-taulukossa viimeinen rivi siis korvataan sit� ylemm�ll� rivill� ja niin eteenp�in.
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
        //do-while-silmukka, joka suoritetaan kun mato liikkuu ja sy�.
        //Erotuksena edelliseen silmukkaan, t�ss� paikat-taulukon alin rivi tallennetaan ja sijoitetaan
        //uuteen paikat-taulukkoon ja t�t� kautta mato kasvaa.
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

    //metodi, joka tutkii osuuko mato itseens�, onko madonreiki� liikaa vai onko k�ytt�j� p��tt�nyt lopettaa pelin.
    //metodi palauttaa boolean-tyyppisen muuttujan, joka muuttuu todeksi, jos joku edellisist� tapauksista on tapahtunut.
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

      //Tutkitaan onko madonreik�laskuri liian suuri eli suurempi kuin 3.
      if (madonreikaLaskuri > 3) 
         lopeta=true;
      if (valinta=='q')
         lopeta=true;
      return lopeta;
         }

      //metodi, joka alustaa kentt�2:n kent�ss� tapahtuneiden muutosten perusteella.
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

      //Jos mato on sy�nyt, uusi ruokamerkki ilmaantuu kentt�2:een.    

      return kentta2;
      }

      //metodi, joka liitt�� madon merkit kentt��n ja tulostaa itse kent�n. (kentt�2).
      public static char[][] alustaMato (char[][]kentta, char[][]kentta2, int[][]paikat, int pituus, int rivilkm, int sarakelkm, boolean syonti) {
      int rivi=0;
      int sarake=0;
      final char PAA = 'X';
      final char PAA2 = 'x';
      final char MATOMERKKI = 'o';

      //do-while-silmukka, jossa kent�n merkit tulostetaan n�yt�lle, ja joka pit��
      //sis�ll��n madon merkkien sijoitukseen k�ytett�v�n for-silmukan.
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
      //Seuraavat kolme muuttujaa sy�tet��n komentoparametreina.
      //Automaatin k�ynnist�j�.

      int parametreja = args.length;

      int siemen = Integer.parseInt(args[0]);

      //rivien lukum��r�.
      int rivilkm = Integer.parseInt(args[1]);

      //sarakkeiden lukum��r�.
      int sarakelkm = Integer.parseInt(args[2]);

      //Madon pituus, joka on alussa 5.
      int pituus=5;

      //madon koordinaattien m��ritt�miseen luotu paikat-taulukko, joka on luotu tarpeeksi isoksi.
      int[][] paikat = new int [pituus][2];
     
      //pelikentt�, joka pit�� sis�ll��n vain v�lily�nnit, pisteet ja ruokamerkin.
      char [][] kentta = new char [rivilkm][sarakelkm];

      //pelikentt�, joka tulostetaan n�yt�lle, joka sis�lt�� madon ja johon kopioidaan aiempi kentt�.
      char [][] kentta2 = new char [rivilkm][sarakelkm]; 

      //Edellist� valintaa ilmaisema muuttuja. Alustetaan r, koska alkutilanteessa mato on menossa
      //oikealle p�in (right).
      char edellinen = 'r';

      //lippumuuttuja.
      boolean lopeta=false;

      //Laskuri, joka laskee madon l�p�isyt reunoilla.
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

      //Automaatin k�ynnistys.
      Automaatti.kaynnista(siemen);

      //Kentt� alustetaan. T�m� tehd��n vain kerran.
      alustaKentta(kentta, pituus, madonreikaLaskuri, rivilkm, sarakelkm); 

      //Ruokamerkki heitet��n alustuksen j�lkeen kent�lle.
      alustaKentta2(kentta, kentta2, paikat, pituus, sarakelkm, rivilkm); 

      Automaatti.tarjoile(kentta2);

      tulostaKentta2(kentta2, rivilkm, sarakelkm);   

      //Do-while-silmukka, joka pit�� sis�ll��n suuntavalinnat ja madon liikuttamisen ja pelikent�n
      //tulostukset ja jota toistetaan kunnes lippumuuttuja lopeta muuttuu todeksi.
      do {

      //P��n koordinaattien tallentaminen.
      int vanhaRivi = paikat[0][0];
      int vanhaSarake = paikat[0][1];
      int hantaRivi = paikat[pituus-1][0];
      int hantaSarake = paikat[pituus-1][1];

      //tulostetaan infoteksti.
      System.out.println("(l)eft, (r)ight, (u)p, (d)own, (s)wap or (q)uit?");

      //K�ytt�j�lt� luettava suuntavalinta.
      char valinta = In.readChar();

      //Jos valinta ei ole eri suuntaan kuin edellinen, suoritetaan if-lauseen operaatiot ja madon p��t�
      //siirt�v� metodi.
      if ((valinta == 'u' && edellinen != 'd') || (valinta == 'd' && edellinen != 'u') || (valinta == 'r' && edellinen != 'l') || (valinta == 'l' && edellinen != 'r')) {
         syoteOK=true;
         lopeta=false;
         liikutaPaata(valinta, edellinen, paikat);

         //Tutkitaan onko madonp�� kent�n reunoilla vai jopa ylitt�nyt reunan, jolloin p�� heitet��n vastakkaiselle
         //reunalle. Molemmissa tapauksissa madonreiki� laskeva laskuri kasvaa yhdell�.

         madonreikaLaskuri=tutkiReunanylitys(paikat, madonreikaLaskuri, rivilkm, sarakelkm, kentta);

         //Lopuksi tallennetaan valinta edellinen-muuttujaan.
         edellinen=valinta;
         }
      //Jos valinta on eri suuntaan kuin edellinen valinta, todetaan ett� sy�te ei ole ok.
      else if ((valinta == 'u' && edellinen == 'd') || (valinta == 'd' && edellinen == 'u') || (valinta == 'r' && edellinen == 'l') || (valinta == 'l' && edellinen == 'r')) {
          lopeta=false;
          syoteOK=false;
      }
      else if (valinta == 's') {
         //Kun on valittu swap, suoritetaan madon p��n ja h�nn�n paikat kesken��n vaihtava metodi.
         kaannaMato(paikat, pituus, syoteOK);

         //Tutkitaan paatteleEdellinen-metodissa mihin suuntaan mato on menossa k��nt�misen j�lkeen ja n�in
         //saadaan selville mik� on edellinen-muuttujan uusi arvo.
         edellinen = paatteleEdellinen(paikat, edellinen);
         syonti=false;
         }
      //Jos valinta on quit, lopetetaan ohjelma.
      else if (valinta=='q') {
         lopeta=true;
         syoteOK=true;
      }
      //Jos valinta on joku muu, todetaan ettei sy�te kelpaa ja lippumuuttuja syoteOK on ep�tosi.
      else {
         lopeta=false;
         syoteOK=false;
      }

      //Jos mato sy�, ruokamerkki korvataan t�ytt�merkill� (v�lily�nti).
      syonti=onkoSyoty(kentta2, paikat, syonti);
      if(syonti)
         paikat = kasvataPaikatTaulukkoa(paikat, hantaRivi, hantaSarake);

      //Matoa liikutetaan. Metodin sis�ll� on m��ritelty, ett� mit��n ei tapahdu, jos valinta
      //on s eli swap eli madon k��nt�minen.
      liikutaMatoa (paikat, pituus, valinta, syonti, syoteOK, vanhaRivi, vanhaSarake);
      
      //Jos mato on sy�nyt, pituuslaskuri kasvaa yhdell�.
      if (syonti)
         pituus++;      

      lopeta = lopetetaanko(paikat, lopeta, pituus, madonreikaLaskuri, valinta);

      //Jos metodi palauttaa lippumuuttujan arvolla tosi, ohjelman suoritus katkeaa t�ss� kohtaa
      //ja tulostetaan lopputekstit.
      if(lopeta) {
         System.out.println("Bye, see you soon.");
         break;
       }

      //Tulostetaan infoteksti.
      System.out.println("Worm length: "+pituus+", wormholes: "+madonreikaLaskuri+".");

      //alustetaan kentt�2 kopioimalla kentt� siihen.
      alusta(kentta, kentta2, rivilkm, sarakelkm, syonti);

      //mato liitet��n kentt�2:een ja kentt�2 tulostetaan n�yt�lle.
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
