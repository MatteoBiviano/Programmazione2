import java.util.*;
import java.io.*;


public class UtilsWordFrequencyCount{
    private int n_words;
    private int spaces;
    private int not_spaces; 
    private DataCounter<String> datacount;
    public UtilsWordFrequencyCount(FileReader file_test)throws IOException{
        Scanner s= new Scanner(System.in);
        System.out.println("Inserire:\n >> '0' per valutare il comportamento su HashDataCounter;\n >> '1' per valutare il comportamento su TreeDataCounter.");
        int i = s.nextInt();
        switch(i){
            case 0:
                datacount = new HashDataCounter<>();
                break;
            case 1:
                datacount = new TreeDataCounter<>();
                break;
            default:
                System.out.println("Inserire un valore valido");
                return;    
        }
        n_words=0; //Conta il numero di parole presenti nel testo
        spaces=-1; //Conta il numero di caratteri (spazi compresi) presenti nel testo
        not_spaces=0; //Conta il numero di caratteri (spazi esclusi) presenti nel testo
        BufferedReader buffer=new BufferedReader(file_test);

        String line;
        String line_withoutspace;
        
        line=buffer.readLine();
        while(line!=null){
            spaces++;
            spaces+=line.length();
            line_withoutspace=line.replaceAll("[\\p{Space}]", "");
            not_spaces+= line_withoutspace.length();
            line=line.replaceAll("[\\p{Punct}]", " ");  
            line=line.toLowerCase(); 
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens()) {
                datacount.incCount(st.nextToken());
                n_words++;
            }
            line=buffer.readLine();
        }
        buffer.close(); 
    }

    public int getNumberOfWords(){
        return n_words;
    }
    public int getCharactersWithSpaces(){
        return spaces;
    }
    public int getCharactersWithoutSpaces(){
        return not_spaces;
    }
    public DataCounter<String> getDataCount(){
        return datacount;
    }
}