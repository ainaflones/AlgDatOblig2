////////////////// class DobbeltLenketListe //////////////////////////////

/**
 * Aina Flønes, S305075
 * Wei-Ting Kao, s334005
 * Wai Shing Hung, s331376
 */

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen, skal økes med 1 for hver innlegging
    private int endringer;         // antall endringer i listen, skal økes med 1 for hver endring i listen


    private Node<T> finnNode(int indeks) {

        /**
         * Oppgave 3a
         *
         */


        Node<T> temp=hode;

        if(indeks < antall/2) {
            temp=hode;
            for (int i = 0; i < indeks; i++) {
                temp = temp.neste;
            }
        }
        else if(indeks >= antall/2 ){
            temp= hale;
            for(int i = 1; i< antall-indeks; i++) {
                temp = temp.forrige;
            }

        }

       return temp;
    }


    public DobbeltLenketListe() {

        this.hode=null;
        this.hale=null;
        antall=0;
        endringer=0;
    }

    public DobbeltLenketListe(T[] a) {

        /**
         * Oppgave 1
         */

        this();


        int i = 0;
        for (; i < a.length && a[i] == null; i++);


            if(i <a.length) {

                Node<T> node = hode = new Node<>(a[i], null, null);
                antall = 1;
                hale = hode;
                Node<T> temp = hode;

                for (i++; i < a.length; i++) {

                    if(a[i] !=null) {
                        node = node.neste = new Node<>(a[i], temp, null);
                        temp.neste = node;
                        temp = temp.neste;
                        antall++;
                        endringer++;
                    }

                }


                hale = node;


        }
    }

    public Liste<T> subliste(int fra, int til){
        /**
         * Oppgave 3b
         *
         */

        fratilKontroll(antall,fra,til);

        Liste<T> liste = new DobbeltLenketListe<>();
        Node<T> temp = finnNode(fra);
        Node<T> tilNode = finnNode(til);

        while(temp != tilNode){

           liste.leggInn(temp.verdi);
           temp = temp.neste;

        }
        if(tilNode == hale && hale != null){
            liste.leggInn(hale.verdi);
        }




        return liste;
    }

    @Override
    public int antall(){
    /**
     * Oppgave 1
     *
     */

    return antall;
}

    @Override
    public boolean tom() {

        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {

        /**
         * Oppgave 2b
         */
        Objects.requireNonNull(verdi);

        if (antall == 0) {

            hode = new Node<>(verdi, null, null);
            hale = hode;
            antall++;
            endringer++;
        } else {

            Node<T> node = new Node<>(verdi, hale, null);
            hale.neste = node;
            hale = node;
            endringer++;
            antall++;

        }

      return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);

        if (antall == 0) {
            hode = new Node<>(verdi, null, null);
            hale = hode;
            antall++;
            endringer++;
        } else {
            if (indeks == 0) {
                Node<T> node = new Node<>(verdi, null, hode);
                hode.forrige = node;
                hode = node;
                antall++;
                endringer++;
            } else if (indeks == antall) {
                Node<T> node = new Node<>(verdi, hale, null);
                hale.neste = node;
                hale = node;
                antall++;
                endringer++;
            } else {
                Node<T> p = hode;
                for (int i = 0; i < indeks; i++) {
                    p = p.neste;
                }
                Node<T> q = p.forrige;
                Node<T> node = new Node<>(verdi, p.forrige, p);
                p.forrige = node;
                q.neste = node;
                antall++;
                endringer++;
            }
        }
    }

    @Override
    public boolean inneholder(T verdi) {

        /**
         * Oppgave 4
         */

        return indeksTil(verdi) !=-1;

    }

    @Override
    public T hent(int indeks) {

       indeksKontroll(indeks,false);


        return finnNode(indeks).verdi;

    }

    @Override
    public int indeksTil(T verdi) {

        /**
         * Oppgave 4
         */

        if(verdi == null){
            return -1;
        }

        Node <T> temp = hode;

        for(int i = 0; i<antall; i++){
            if(temp.verdi.equals(verdi)){
                return i;
            }
            temp=temp.neste;
        }
        return -1;


    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        indeksKontroll(indeks,false);
        Objects.requireNonNull(nyverdi);

        Node<T> node = finnNode(indeks);
        T verdi = node.verdi;
        node.verdi=nyverdi;
        endringer++;


        return verdi;

    }


    //Oppgave 6

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;

        Node<T> q = hode, p = null, r = null;

        while (q != null) {
            if (q.verdi.equals(verdi)) break;
            q = q.neste;
        }

        if (q == null) return false;
        else if (q == hode) {
            hode = hode.neste;
        } else {
            p = q.forrige;
            r = q.neste;
            p.neste = q.neste;
            if (r != null) r.forrige = p;
        }

        if (q == hale) {
            hale = p;
        }

        q.verdi = null;
        q.neste = null;

        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if (indeks == 0) {
            temp = hode.verdi;
            hode = hode.neste;
            if (antall == 1) hale = null;
        } else {
            Node<T> p = finnNode(indeks - 1);
            Node<T> q = p.neste;
            Node<T> r = null;
            if(q != null) r = q.neste;

            temp = q.verdi;

            if (q == hale) hale = p;

            p.neste = q.neste;
            if (r != null) r.forrige = p;

            if(q != null){
                q.forrige = null;
                q.neste = null;
                q = null;
            }

        }

        antall--;
        endringer++;
        return temp;
    }

    /**
     * Oppgave 7
     */

    // 2. måte er valgt fordi når liste inneholder 1000000 elementer, tok 1. måte 21 millisekunder til å kjøre,
    // og 2. måte tok 12 millisekunder til å kjøre

    // 1. måte:
/*    @Override
    public void nullstill() {
        long tid = System.currentTimeMillis();

        Node<T> p = hode;
        Node<T> q = null;

        while (p != null) {
            q = p.neste;  // moving the q-point to p.neste

            p.neste = null; // nullstill p
            p.verdi = null;
            p.forrige = null;

            p = q; // moving the p-pointer to q (p.neste)
        }

        hode = hale = null;
        antall = 0;

        tid = System.currentTimeMillis() - tid;
        System.out.println(tid);
    }*/

    // 2. måte:
    @Override
    public void nullstill() {
        long tid = System.currentTimeMillis();

        int antallGanger = antall;
        for(int i = 0; i < antallGanger; i++){
            fjern(0);
        }

        tid = System.currentTimeMillis() - tid;
        System.out.println(tid);

        endringer++;
    }

    @Override
    public String toString() {
        /**
         * Oppgave 2a
         */


        StringBuilder tekst = new StringBuilder();


        if(antall == 0){
            return "[]";
        }
        else if(antall == 1){
            tekst.append("[").append(hode.verdi).append("]");
            return tekst.toString();
        }

        Node temp = hode.neste;

        tekst.append("[").append(hode.verdi);

        while(temp != null){
            tekst.append(", ").append(temp.verdi);
            temp=temp.neste;
        }

        tekst.append("]");

        return tekst.toString();


    }

    public String omvendtString() {

        /**
         * Oppgave 2a
         */

        StringBuilder tekst = new StringBuilder();

        if(antall == 0){
            return "[]";
        }
        else if(antall == 1){
            tekst.append("[").append(hode.verdi).append("]");
            return tekst.toString();
        }

        Node<T> temp = hale.forrige;

        tekst.append("[").append(hale.verdi).append(", ");

        while(temp.forrige != null && temp != hode){
            tekst.append(temp.verdi).append(", ");
            temp=temp.forrige;
        }

        tekst.append(hode.verdi).append("]");

        return tekst.toString();

    }

    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {

        indeksKontroll(indeks,false);

        return new DobbeltLenketListeIterator(indeks);

    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){

            denne = hode;
            fjernOK = false;
            iteratorendringer = endringer;

        }

        private DobbeltLenketListeIterator(int indeks){

            denne = hode;

            for(int i = 0; i<indeks; i++){
                denne= denne.neste;
            }
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext(){

            return denne != null;

        }

        @Override
        public T next(){
            /**
             * Oppgave 8a)
             */

            if(iteratorendringer!=endringer){
                throw new ConcurrentModificationException("Det har blitt gjort endringer på tabellen med en annen iterator");
            }
            if(!hasNext()){
                throw new NoSuchElementException("Det er ikke flere elementer igjen i listen");
            }

            fjernOK = true;
            T tempVerdi = denne.verdi;
            denne = denne.neste;
            return tempVerdi;


        }

        @Override
        public void remove(){

            if(iteratorendringer!=endringer){
                throw new ConcurrentModificationException("Listen er endret!");
            }

            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;
            Node<T> q = hode;

            if (hode.neste == denne) {
                hode = hode.neste;
                if (hode != null) hode.forrige = null;

                if (denne == null) hale = null;
            } else {
                Node<T> r = hode;

                while (r.neste.neste != denne) {
                    r = r.neste;
                }

                q = r.neste;
                r.neste = denne;
                if (denne != null){
                    denne.forrige = r;
                } else {
                    hale = r;
                }
            }
            q.verdi = null;
            q.neste = null;
            q.forrige = null;

            antall--;
            endringer++;
            iteratorendringer++;
        }

        Iterator<T> iterator(){
            return new DobbeltLenketListeIterator();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }




} // class DobbeltLenketListe


