import java.util.*;
import java.io.*;

public class WordFrequencyCount{
    public static void main(String[] args) throws IOException{
        System.out.println("Inserire:\n >> '0' per Test0.txt;\n >> '1' per Test1.txt;\n >> '2' per Test2.txt;\n >> '3' per Test3.txt.");
        Scanner sc = new Scanner(System.in);
        String x;
        int i=sc.nextInt();
        switch(i){
            case 0:
                x="Test0.txt";
                break;
            case 1:
                x="Test1.txt";
                break;
            case 
            2:
                x="Test2.txt";
                break;
            case 3:
                x="Test3.txt";
                break;
            default:
                System.out.println("Inserire un valore valido");
                return;
        }
        FileReader file;
        try{
            file=new FileReader(x);
        }catch(FileNotFoundException exc){
            System.out.println("Inserire il nome di un file esistente");
            return;
        }
        UtilsWordFrequencyCount test = new UtilsWordFrequencyCount(file);
       
        System.out.println("Number of words: " + test.getNumberOfWords());
        System.out.println("Number of characters(including spaces): " + test.getCharactersWithSpaces());
        System.out.println("Number of characters(without spaces): " + test.getCharactersWithoutSpaces());
        
        DataCounter<String> data= test.getDataCount();
        Iterator<PairDataCounter<String>> iterator = data.getIterator();
        int j=1;
        if(test.getNumberOfWords()==0){
            System.out.println("Abbiamo inserito un testo privo di parole");
            return;
        }
        System.out.println("WordFrequencyCount:");
        while(iterator.hasNext()){
            PairDataCounter key=iterator.next();
            System.out.print(j + ". " + key.getKey());
            System.out.println(" | " + key.getCount());
            j++;

        }
    }
}