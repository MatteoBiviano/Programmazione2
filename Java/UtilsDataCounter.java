import java.util.*;
import java.io.*;

public class UtilsDataCounter{
    public static <E extends DataCounter<String>> void testerException(E datacount){
        //Sottoponiamo l'oggetto datacounter ad alcune prove per valutarne  
        //il corretto comportamento in situazioni "particolari"

        //Valutiamo il comportamento nel caso si tenti di incrementare  
        //in valore associato ad un elemento nullo
        try{
            datacount.incCount(null);
            assert(false);
        } catch(NullPointerException exc){
            System.out.println("NullPointerException: non si pu\u00F2 incrementare una chiave nulla");
        }

        //Valutiamo il comportamento nel caso si tenti di ottenere  
        //un valore associato ad un elemento nullo
        try{
            datacount.getCount(null);
            assert(false);
        } catch(NullPointerException exc){
            System.out.println("NullPointerException: non si pu\u00F2 accedere al valore di una chiave nulla");
        }

        //Verifichiamo che la dimensione di datacounter sia pari a 0    
        assert(datacount.getSize()==0);


        //Verifichiamo che il comportamento dell'Iteratore sia quello atteso
        //per fare questo inseriamo prima una stringa
        datacount.incCount("pippo");
        Iterator<PairDataCounter<String>> iterator = datacount.getIterator();
        assert(iterator.hasNext());
        iterator.next();
        assert(!iterator.hasNext());

        try{
            iterator.remove();
            assert(false);
        } catch(UnsupportedOperationException exc){
            System.out.println("UnsupportedOperationException: l'iteratore non supporta l'operazione di 'remove'");
        }
    }
	public static <E extends DataCounter<String>> void testerFile(E datacount) throws IOException{
        FileReader file;
        try{
            file=new FileReader("Test0.txt");
        }catch(FileNotFoundException exc){
            System.out.println("Il testcase non è presente");
            return;
        }
    	BufferedReader buffer;
    	buffer=new BufferedReader(file);
        
    	String line;
		
		line=buffer.readLine();
    	while(line!=null){
            line=line.toLowerCase();
            line=line.replaceAll("[ \\p{Punct}]", " ");         
    		StringTokenizer st = new StringTokenizer(line);
     		while (st.hasMoreTokens()) {
         		datacount.incCount(st.nextToken());

     		}
     		line=buffer.readLine();
    	}
        buffer.close();
    	System.out.println("\nNumber of keys: " + datacount.getSize());
    	Iterator<PairDataCounter<String>> iterator = datacount.getIterator();
        int i=1;
    	while(iterator.hasNext()){
    		PairDataCounter key=iterator.next();
    		System.out.print(i + ". " + key.getKey());
    		System.out.println(" | " + key.getCount());
            i++;
    	}
	}
}