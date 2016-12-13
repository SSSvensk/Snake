import java.util.Random; // Random-luokka k‰yttˆˆn.

/*
 * Lausekielinen ohjelmointi, syksy 2013, toinen harjoitustyˆ.
 *
 * Apuluokka, jonka avulla pelikentt‰‰n sijoitetaan madon ruokaa.
 *
 * VAIN KURSSIN VASTUUOPETTAJA SAA MUUTTAA TƒTƒ LUOKKAA.
 *
 * ƒLƒ KOPIOI METODEJA TƒSTƒ LUOKASTA OMAAN OHJELMAASI.
 *
 * Jorma Laurikkala, Informaatiotieteiden yksikkˆ, Tampereen yliopisto,
 * jorma.laurikkala@uta.fi.
 *
 * Versio 1.0.
 *
 * Viimeksi muutettu 1.12.2013.
 *
 */

public class Automaatti {

   /*__________________________________________________________________________
    *
    * 1. Julkiset luokkavakiot.
    *
    */
   
   // Merkki kent‰n taustalle.
   public static final char TAUSTA = ' ';
   
   // Ruoan symboli.
   public static final char RUOKA = '+';

   // Kent‰n rivien v‰himm‰ism‰‰r‰.
   public static final int RIVMINLKM = 3;

   // Kent‰n sarakkeiden v‰himm‰ism‰‰r‰.
   public static final int SARMINLKM = 7;

   /*__________________________________________________________________________
    *
    * 2. K‰tketyt attribuutit.
    *
    */

   // Maailmalta k‰tketty pseudosatunnaislukugeneraattori.
   private static Random generaattori;

   // Tosi, jos on kutsuttu kaynnista-metodia.
   private static boolean kaynnissa = false;

   /*__________________________________________________________________________
    *
    * 3. Harjoitustyˆohjelmasta kutsuttavat julkiset metodit.
    *
    */

   /* K‰ynnistet‰‰n ruoka-automaatti. Automaatin pseudosatunnaislukugeneraattori
    * alustetaan siemenluvun avulla. Metodia voidaan kutsua vain kerran; uusi kutsu
    * aiheuttaa ajonaikaisen virheen.
    */
   public static final void kaynnista(int siemen) {
      // Ensimm‰inen kutsu.
      if (!kaynnissa) {
         // Luodaan pseudosatunnaislukugeneraattori annetulla siemenluvulla.
         // Tietyll‰ siemenluvulla saadaan tietty sarja pseudosatunnaislukuja.
         generaattori = new Random(siemen);

         // Automaatti on nyt k‰ynniss‰.
         kaynnissa = true;
      }

      // Heitet‰‰n poikkeus, jos metodia kutsuttiin uudelleen.
      else
         throw new IllegalArgumentException("Automaton already running!");
   }

   /* Sijoittaa ruokamerkin satunnaisesti valittuun paikkaan kent‰ll‰.
    * Ruoka sijoitetaan vain tyhj‰‰n (taustamerkin sis‰lt‰v‰‰n) sis‰paikkaan.
    * Toisin sanoen ruokamerkki ei voi olla reunalla eik‰ se voi korvata madon
    * merkkej‰. Paluuarvo on true, jos kent‰ll‰ on tilaa ruoalle. Paluuarvo on
    * false, jos kent‰lle ei ole varattu muistia tai muistia on varattu liian
    * v‰h‰n tai jos kentt‰ on t‰ynn‰. Metodi ei tarkista kent‰n sis‰ltˆ‰ t‰m‰n
    * tarkemmin. Metodia voi kutsua vasta, kun automaatti on k‰ynnistetty.
    * Metodin kutsu ennen automaatin k‰ynnist‰mist‰ aiheuttaa ajonaikaisen
    * virheen.
    */
   public static final boolean tarjoile(char[][] kentta) {
      // Heitet‰‰n poikkeus, jos automaattia ei ole k‰ynnistetty.
      if (!kaynnissa)
         throw new IllegalArgumentException("Automaton not running!");

      // Oletetaan, ett‰ matoa ei voida ruokkia.
      boolean ruokaOnKentalla = false;

      // Yritet‰‰n ruokkia vain, jos taulukolle on varattu muistia ja muistia
      // on sen verran, ett‰ ohjelma ei kaadu taulukkoa k‰sitelt‰ess‰.
      if (kentta != null && kentta.length >= RIVMINLKM 
      && kentta[0].length >= SARMINLKM) {
         // P‰‰tell‰‰n viimeisen sis‰paikan rivi- ja sarakeindeksit.
         int vikaSisarivi = kentta.length - 2;
         int vikaSisasarake = kentta[0].length - 2;

         // P‰‰tell‰‰n sis‰paikkojen lukum‰‰r‰.
         int sisapaikkoja = (kentta.length - 2) * (kentta[0].length - 2);

         // Taulukko tyhjien sis‰paikkojen rivi- ja sarakeindekseille.
         // Taulukolle varataan varmuuden vuoksi muistia suurimman mahdollisen
         // tilatarpeen mukaan (kaikki sis‰paikat tyhji‰).
         int[][] tyhjatPaikat = new int[sisapaikkoja][2];

         // Laskuri tyhjille sis‰paikoille.
         int tyhjia = 0;

         // K‰yd‰‰n l‰pi kaikki sis‰paikat ja sijoitetaan tyhjien paikkojen
         // indeksit taulukkoon. Taulukon rivin ensimm‰isess‰ alkiossa on
         // paikan rivi-indeksi ja toisessa alkiossa paikan sarakeindeksi.
         for (int rivi = 1; rivi <= vikaSisarivi; rivi++)
            for (int sarake = 1; sarake <= vikaSisasarake; sarake++)
               // Lˆydettiin tyhj‰ paikka.
               if (kentta[rivi][sarake] == TAUSTA) {
                  // Paikan rivi- ja sarakeindeksit paikkataulukon riville.
                  tyhjatPaikat[tyhjia][0] = rivi;
                  tyhjatPaikat[tyhjia][1] = sarake;

                  // P‰ivitet‰‰n laskuria.
                  tyhjia++;
               }

         // Ruoka mahtuu kent‰lle.
         if (tyhjia > 0) {
            // Valitaan satunnaisesti tyhj‰ paikka.
            int paikka = generaattori.nextInt(tyhjia);

            // Sijoitetaan valittuun paikkaan ruokaa.
            int ruokaRivi = tyhjatPaikat[paikka][0];
            int ruokaSarake = tyhjatPaikat[paikka][1];
            kentta[ruokaRivi][ruokaSarake] = RUOKA;

            // K‰‰nnet‰‰n lippu.
            ruokaOnKentalla = true;
         }
      }

      // Palautetaan lippumuuttujan arvo.
      return ruokaOnKentalla;
   }
}
