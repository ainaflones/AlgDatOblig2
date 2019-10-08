public class Hovedklasse {

    public static void main(String args[] ){

        /**
         * Oppgave 1: sjekker om følgende metode gir feilmelding
         */


        /*Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());
        */

       /* String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste2 = new DobbeltLenketListe<>(s);
        System.out.println(liste2.antall() + " " + liste2.tom());

        System.out.print(liste2.toString());
        */

        /**
         * Oppgave 2a: Sjekker toString.metodene
         *
         */

        /*String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l1.toString() + " " + l2.toString()

                + " " + l3.toString() + " " + l1.omvendtString() + " " + l2.omvendtString() + " " + l3.omvendtString());

        */
        /**
         * Oppgave 2b: Sjekker legg inn(T verdi);
         */


        /*DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();

        System.out.println(liste.toString() + " " + liste.omvendtString());

        for (int i = 1; i <= 3; i++) {

            liste.leggInn(i);

            System.out.println(liste.toString() + " " + liste.omvendtString()); }

         */

/**
 * Tester oppgave 3
 */

/*

        Integer[] liste = {1,2,3,4,5,6};

        Liste<Integer> nodeListe = new DobbeltLenketListe<Integer>(liste);

        System.out.println(nodeListe.toString());

        System.out.println(nodeListe.hent(2));
        System.out.println(nodeListe.hent(3));
        System.out.println(nodeListe.hent(4));

        Liste<Integer> sub= ((DobbeltLenketListe<Integer>) nodeListe).subliste(2,4);
        System.out.println(sub.toString()+ " Antall: " + sub.antall());


        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste2 = new DobbeltLenketListe<>(c);
        System.out.println(liste2.subliste(3,8)); // [D, E, F, G, H]
        System.out.println(liste2.subliste(5,5)); // []
        System.out.println(liste2.subliste(8,liste2.antall())); // [I, J]
        System.out.println(liste2.subliste(9,10));
        System.out.println(liste2.subliste(0,11)); //Kaster unntak

        System.out.println(liste2.hent(0));

        System.out.println(liste2.antall());

        Integer[] d = {1};
        DobbeltLenketListe<Integer> liste3 = new DobbeltLenketListe<>(d);

        System.out.println(liste3.hent(0));

*/
        /**
         * Test oppgave 8
         */

        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste = new DobbeltLenketListe<>(navn);

        liste.forEach(s -> System.out.print(s + " "));
        System.out.println();
        for (String s : liste) System.out.print(s + " ");


        System.out.println();

        /**
        * Test oppgave 8
         */


        liste.fjernHvis(navn2 -> navn2.charAt(0) =='B');// fjerner navn som starter med B
        System.out.println(liste + "​ ​" + ((DobbeltLenketListe<String>) liste).omvendtString());

        Liste<String> liste2 = new DobbeltLenketListe<>(navn);
        liste2.fjernHvis(navn3 -> navn3.length() != 5);
        System.out.println(liste2 + "​ ​" + ((DobbeltLenketListe<String>) liste2).omvendtString());





    }



}
