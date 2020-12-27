import java.util.*;

public class TreeDataCounter<E extends Comparable<E>> implements DataCounter<E> {
	/*
	  AF = f: E -> N tale che f(k) = tree_m.get(k) se tree_m.containsKey(k), f(k) = 0 altrimenti
	  IR = tree_m !=null 
	   	   && for all key appartenente a tree_m.keySet() si ha che tree_m.get(key)>0
	*/

	private TreeMap<E,Integer> tree_m;	
	//tree_m è un TreeMap usato per memorizzare le chiavi e il loro numero di occorrenze

	public TreeDataCounter(){	
			tree_m= new TreeMap<>(); 	//Da Java5 possiamo omettere <E, Integer>

	}

	// incrementa il valore associato all’elemento data di tipo E
 	public void incCount(E data) throws NullPointerException{
		if(data==null) throw new NullPointerException();
		if(tree_m.containsKey(data)){
			tree_m.replace(data, tree_m.get(data) + 1);
		}
		else{
			tree_m.put(data, 1);
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
  		return tree_m.size();
	/*
	   EFFECTS: restituisce il numero di elementi presenti nella collezione   
	           (coincide con la cardinalità della collezione)
	*/
  	}

	// restituisce il valore corrente associato al parametro data,
	// e 0 se data non appartiene alla collezione
	public int getCount(E data) throws NullPointerException{
		if(data==null) throw new NullPointerException();
		if(tree_m.containsKey(data)){
			return tree_m.get(data);
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
		for(E key : tree_m.keySet()){
			treeset.add(new PairDataCounter<>(key, tree_m.get(key)));
		}
		return new DataCounterIterator<E>(treeset);
	  /*
	    EFFECTS: restituisce un iteratore per la collezione, privo del metodo "remove", che itera su coppie ordinate
	  */		
	}
}