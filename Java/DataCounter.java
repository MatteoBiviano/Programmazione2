import java.util.*;

public interface DataCounter<E extends Comparable<E>> {

  /* OVERVIEW: Tipo modificabile che rappresenta una collezione di oggetti a cui è associato
               un valore numerico (rappresentativo del numero di volte in cui l'oggetto occorre
               nell'insieme).
   TYPICAL ELEMENT: Una funzione totale f : E -> N, che associa ad ogni elemento 'e' appartenente ad E, 
                    un valore n appartenente N che rappresenta il numero di volte in cui 'e' 
                    occorre nell'insieme.
  */

  // incrementa il valore associato all’elemento data di tipo E
  public void incCount(E data) throws NullPointerException;
  /* 
    REQUIRES: data != null
    MODIFIES: this
    EFFECTS: incrementa il valore associato all’elemento data, se data appartiene a this, 
            altrimenti aggiunge data a this, con valore associato pari a 1;
    THROWS: Se data=null lancia una NullPointerException  
            (eccezione disponibile in Java, unchecked)
  */

  // restituisce il numero degli elementi presenti nella collezione
  public int getSize();
  /*
    EFFECTS: restituisce il numero di elementi presenti nella collezione   
             (coincide con la cardinalità della collezione)
  */

  // restituisce il valore corrente associato al parametro data,
  // e 0 se data non appartiene alla collezione
  public int getCount(E data) throws NullPointerException;
  /* 
    REQUIRES: data != null
    EFFECTS: se data appartiene alla collezione restituisce il valore corrente ad essa
             associato, altriemnti restituisce 0
    THROWS: se data=null lancia una NullPointerException
            (eccezione disponibile in Java, unchecked)
  */
  
  // restituisce un iteratore (senza remove) per la collezione
  public Iterator<PairDataCounter<E>> getIterator();
  /*
    EFFECTS: restituisce un iteratore per la collezione, privo del metodo "remove", che itera su coppie ordinate
  */
}