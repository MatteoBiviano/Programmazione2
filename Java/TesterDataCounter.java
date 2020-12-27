import java.util.*;
import java.io.*;

public class TesterDataCounter{

	public static void main(String[] args) throws IOException{
		Scanner s= new Scanner(System.in);
		System.out.println("Inserire:\n >> '0' per valutare il comportamento di HashDataCounter;\n >> '1' per valutare il comportamento di TreeDataCounter.");
		int i = s.nextInt();
		DataCounter<String> datacount1;
		DataCounter<String> datacount2;
 		switch(i){
			case 0:
				datacount1= new HashDataCounter<>();
				UtilsDataCounter.testerException(datacount1);
 				datacount2 = new HashDataCounter<>();
 				UtilsDataCounter.testerFile(datacount2);
				break;
			case 1:
				datacount1 = new TreeDataCounter<>();
				UtilsDataCounter.testerException(datacount1);
			 	datacount2 = new TreeDataCounter<>();
				UtilsDataCounter.testerFile(datacount2);
				break;	
			default:
				System.out.println("Il valore inserito non \u00E8 corretto");
				return;		
		}
		System.out.println("\nTest svolto correttamente");
	}
}