import java.util.*;

public class DataCounterIterator<E extends Comparable<E>> implements Iterator<PairDataCounter<E>> {
	  private ArrayList<PairDataCounter<E>> item; // Contiene i valori su cui iterare
	  private int index; // Contiene l'indice corrente di iterazione

	  public DataCounterIterator(Set<PairDataCounter<E>> set) throws NullPointerException {
	    if(set == null) throw new NullPointerException();
	    item=new ArrayList<>();
	    for(PairDataCounter<E> elem : set){
	    	item.add(elem);
	    }   
	    
	    index = -1; 
	    /* 
	      all'indice viene inizialmente attribuito il valore -1 
	      affinché la prima chiamata del metodo "next" dell'iteratore 
	      imposti l'indice = 0 sse item contiene almeno un elemento
	    */
	  }

	  public boolean hasNext() {
	    return index + 1 < item.size();
	  }

	  public PairDataCounter<E> next() throws NoSuchElementException {
	    if(hasNext()) return item.get(++index);
	    else throw new NoSuchElementException();
	  }

	  public void remove() throws UnsupportedOperationException {
	    throw new UnsupportedOperationException();
	  }
	}