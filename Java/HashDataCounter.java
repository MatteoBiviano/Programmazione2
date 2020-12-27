import java.util.*;

public class HashDataCounter<E extends Comparable<E>> implements DataCounter<E> {
	/*
	  AF = f: E -> N tale che f(k) = hash_t.get(k) se hash_t.containsKey(k), f(k) = 0 altrimenti
	  IR = hash_t !=null 
	   	   && for all key appartenente a hash_t.keySet() si ha che hash_t.get(key)>0
	*/

	private Hashtable<E,Integer> hash_t;	
	//hash_t è una Hashtable usata per memorizzare le chiavi e il loro numero di occorrenze

	public HashDataCounter(){	
			hash_t= new Hashtable<>(); 	//Da Java5 possiamo omettere <E, Integer>

	}

	// incrementa il valore associato all’elemento data di tipo E
 	public void incCount(E data) throws NullPointerException{
		if(data==null) throw new NullPointerException();
		if(hash_t.containsKey(data)){
			hash_t.replace(data, hash_t.get(data) + 1);
		}
		else{
			hash_t.put(data, 1);
		}

	  /* 
	    REQUIRES: data != null
	    MODIFIES: this
	    EFFECTS: incrementa il valore associato all’elemento data, se data appartiene a this, 
	            altrimenti aggiunge data a this, con valore associato pari a 1;
	    THROWS: Se data=null lancia una NullPointerException  
   		         (eccezione disponibile in Java, unchecked)
  	  */
   	}

  	// restituisce il numero degli elementi presenti nella collezione
  	public int getSize(){
  		return hash_t.size();
	/*
	   EFFECTS: restituisce il numero di elementi presenti nella collezione   
	           (coincide con la cardinalità della collezione)
	*/
  	}

	// restituisce il valore corrente associato al parametro data,
	// e 0 se data non appartiene alla collezione
	public int getCount(E data) throws NullPointerException{
		if(data==null) throw new NullPointerException();
		if(hash_t.containsKey(data)){
			return hash_t.get(data);
		} 
		return 0;
	  /* 
	    REQUIRES: data != null
	    EFFECTS: se data appartiene alla collezione restituisce il valore corrente associato  
	             ad essa, altrimenti restituisce 0
	    THROWS: se data=null lancia una NullPointerException
	            (eccezione disponibile in Java, unchecked)
	  */
	}

	// restituisce un iteratore (senza remove) per la collezione
	public Iterator<PairDataCounter<E>> getIterator(){
		TreeSet<PairDataCounter<E>> treeset = new TreeSet<>();
		for(E key : hash_t.keySet()){
			treeset.add(new PairDataCounter<>(key, hash_t.get(key)));
		}
		return new DataCounterIterator<E>(treeset);
	  /*
	    EFFECTS: restituisce un iteratore per la collezione, privo del metodo "remove", che itera su coppie ordinate
	  */		
	}
}