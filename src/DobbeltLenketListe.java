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
        throw new NotImplementedException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T hent(int indeks) {

       indeksKontroll(indeks,false);


        return finnNode(indeks).verdi;

    }

    @Override
    public int indeksTil(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        indeksKontroll(indeks,false);
        Objects.requireNonNull(nyverdi);

        Node<T> node = finnNode(indeks);
        T verdi = node.verdi;
        node.verdi=nyverdi;


        return verdi;

    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {
        throw new NotImplementedException();
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

        Node temp = hale.forrige;

        tekst.append("[").append(hale.verdi).append(", ");

        while(temp.verdi != null && temp != hode){
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
        throw new NotImplementedException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new NotImplementedException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }




} // class DobbeltLenketListe


