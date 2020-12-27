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
Quando si lavora con la frequenza di parole nei documenti, è spesso auspicabile considerare solo le radici delle parole. In questo modo, "dorme", "dormire" e "dormiente" sono tutti considerati la stessa parola. Questo processo si chiama stemming e viene utilizzato nei motori di ricerca e in molti altri contesti. Nel caso del progetto, non è richiesto di effettuare effettivamente lo stemming, ma suggeriamo di normalizzare i documenti in ingresso nel modo seguente 1. convertire tutte le lettere in lettere minuscole, quindi "La" e "la" sono la stessa parola 2. rimuovere tutta la punteggiatura dal testo.

# Brief Ocaml
Si consideri un’estensione del linguaggio didattico funzionale presentato a lezione che permetta di manipolare alberi binari di espressioni. L’estensione minimale dei tipi è riportata di seguito 
```OCaml
type exp = ... | ETree of tree (* gli alberi sono anche espressioni *) | ApplyOver of exp * exp (* applicazione di funzione ai nodi *) 
          | Update of (ide list) * exp * exp (* aggiornamento di un nodo *) | Select of (ide list) * exp * exp (* selezione condizionale di un nodo *)
and tree = Empty (* albero vuoto *) | Node of ide * exp * tree * tree (* albero binario *) 
```
Ogni nodo di un albero, oltre ai figli, ha associato un identificatore (tag) e un’espressione. Quando un albero è definito, le espressioni dei nodi devono essere valutate, e solo quelle. I tag servono a caratterizzare (in maniera eventualmente non univoca) cammini nell’albero. Ad esempio, l’espressione 
```Ocaml
ETree(Node(“a”, EInt 1, Node(“b”, EInt 2, Empty, Empty), Node(“c”, EInt 3, Empty, Empty)) 
```
denota un albero con un nodo radice (che ha associati il tag “a” e il valore Int 1) e due foglie, mentre i cammini possibili sono [], [a], [a;b] e [a;c]. In questo caso, i cammini sono univoci, mentre non è detto che questo avvenga per ogni scelta degli identificatori associati ai nodi, come è possibile che una sequenza di identificatori non denoti nessun nodo (come [b] nel caso precedente). Adesso il significato di ApplyOver(exf, ext) diventa ovvio: si tratta di applicare la funzione denotata dal primo parametro exf al valore associato a ogni nodo dell’albero denotato dal secondo parametro ext, aggiornandolo di conseguenza. Invece, Update(idl, exf, ext) aggiorna solo il valore del nodo (o dei nodi) identificati dal cammino idl nell’albero ext applicando la funzione denotata da exf, mentre non esegue nessun aggiornamento se nessun nodo corrisponde al cammino indicato. Infine, Select(idl, exf, ext) restituisce un sotto-albero di ext la cui radice è uno dei nodi di ext che sono individuati dal cammino idl e il cui valore soddisfa la proprietà definita dalla funzione denotata da exf (funzione che restituisce un valore booleano). L’operazione Select restituisce l’albero vuoto se nessun nodo corrisponde al cammino indicato, oppure se nessun valore dei nodi corrispondenti al cammino soddisfa la condizione. 
### 1. Si estenda l’interprete OCaML del linguaggio funzionale assumendo la regola di scoping statico. 
### 2. Si fornisca di conseguenza il type checker dinamico del linguaggio risultante. 
### 3. Si verifichi la correttezza dell’interprete progettando ed eseguendo una quantità di casi di test sufficiente a testare tutti gli operatori aggiuntivi. La sintassi astratta suggerita può essere modificata e, se ritenuto necessario, estesa
