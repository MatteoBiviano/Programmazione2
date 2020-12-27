# Programmazione2
Progetti in Java e Ocaml del corso di Programmazione2

# Brief Java
Si richiede di progettare, realizzare e documentare la collezione DataCounter. DataCounter è una collezione che permette di associare un valore numerico a ogni oggetto di tipo E. Intuitivamente la collezione si comporta come un dizionario con la differenza che associa un valore numerico a ogni dato di tipo E presente nella struttura. Le operazioni sono descritte di seguito. 
```java
public interface DataCounter { 
  // incrementa il valore associato all’elemento data di tipo E 
  public void incCount(E data); 
  // restituisce il numero degli elementi presenti nella collezione 
  public int getSize();
  // restituisce il valore corrente associato al parametro data, 
  // e 0 se data non appartiene alla collezione 
  public int getCount(E data); 
  // restituisce un iteratore (senza remove) per la collezione 
  public Iterator getIterator(); 
  } 
```
### 1. Si definisca la specifica completa come interfaccia Java del tipo di dato astratto DataCounter, fornendo le motivazioni delle scelte effettuate. 
### 2. Si definisca l’implementazione del tipo di dato astratto DataCounter, fornendo due implementazioni: la prima basata sulla classe Hashtable, la seconda sulla classe TreeMap. 
In entrambi i casi si devono comunque descrivere sia la funzione di astrazione sia l’invariante di rappresentazione. Si discutano le caratteristiche delle due implementazioni. Per valutare il comportamento dell’implementazione proposta si realizzi, utilizzando DataCounter, una applicazione che determina la frequenza delle parole in un documento di testo, partendo da quelle più frequenti e utilizzando l’ordine alfabetico in caso di parole con la medesima frequenza. 
