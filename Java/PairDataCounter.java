import java.util.*;

public class PairDataCounter<E extends Comparable<E>> implements Comparable<PairDataCounter<E>> {
    private E word;
    private int count;

    PairDataCounter(E word) {
    	this(word, 1);
    }

    PairDataCounter(E word, int count) throws NullPointerException, IllegalArgumentException {
        if(word==null) throw new NullPointerException("Parametro Nullo");
        if(count<1) throw new IllegalArgumentException("Numero di Occorrenze non valido");
        this.word = word;
        this.count = count;
    }

    public E getKey(){ 
    	return word;
    }

    public int getCount(){
    	return count;
    }
    public int compareTo(PairDataCounter<E> elem1) {
        if(this.getCount()==(elem1.getCount())){
            //CASO: due chiavi con lo stesso valore   
            //EFFETTO: ordinate secondo ordine lessicografico
            if(this.getKey().compareTo(elem1.getKey())>0)
                return 1;
            return -1;
        }
        //CASO: due chiavi con valore diverso   
        //EFFETTO: ordinate secondo valore decrescente
        return Integer.compare(elem1.getCount(),this.getCount());
    }
}