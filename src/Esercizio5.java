/*
 * Marianna Gimigliano
 * Matricola: 0000915343
 * marianna.gimigliano@studio.unibo.it
 * 
 * Compilare con: javac Esercizio5.java
 * Eseguire con: java -cp . Esercizio5 <file_input>
 *
 * Questo programma risolve il problema delle disuguaglianze attraverso una versione
 * dell'algoritmo di DFS. Le disuguaglianze vengono trattate come coppie di nodi aventi un arco
 * dal nodo di valore minore al nodo di valore maggiore, formando così un grafo orientato.
 * Si esplora tale grafo alla ricerca di cicli. Quando si incontra un ciclo significa che tutti i 
 * nodi che ne fanno parte hanno lo stesso valore, quindi un'uguaglianza data in input è verificata
 * se i suoi valori si trovano all'interno di un ciclo.
 * Es. x1 <= x2 <= x3 <= x4 <= x1, allora per essere verificata deve essere vero che x1=x2=x3=x4.
 * 
 * Costo computazionale: O(n+m)
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Esercizio5 {
	
	enum Color {WHITE, GREY, BLACK};
	int numNodi;
	int numArchi;
	Vector<LinkedList<Arco>> listaAdiacenza;
	Color colore[]; 
	List<Integer> predecessore;  //lista di nodi predecessori nella visita rispetto al nodo corrente
	Vector<LinkedList<Integer>> uguaglianze; //vettore di liste contenenti le uguaglianze in input
	List<Boolean> risultato; //lista di booleani corrispondenti alla verifica delle uguaglianze
	
	public class Arco {
		final int srg;
        final int dst;

        public Arco(int srg, int dst) {
            this.srg = srg;
            this.dst = dst;
        }
	}
	
	/*
	 * Legge i dati in input da un file e crea un'istanza della classe.
	 * O(n) + O(m) + O(u), con n-nodi, m-archi, u-uguaglianze.
	 */
	public Esercizio5(String file){
        try {
            Scanner scan = new Scanner(new FileReader(file));
            listaAdiacenza = new Vector<LinkedList<Arco>>(numNodi);
            uguaglianze = new Vector<LinkedList<Integer>>();
            risultato = new LinkedList<>();
            numNodi = scan.nextInt();
            numArchi = scan.nextInt();
            scan.nextLine();
            for(int i=0; i<numNodi; i++) { 
            	listaAdiacenza.add(i, new LinkedList<Arco>());
            }
		    for(int i=0; i<numArchi; i++) {
		    	final int srg = scan.nextInt();
	            final int dst = scan.nextInt();
	            final Arco nuovoArco = new Arco(srg, dst);
	           	listaAdiacenza.get(srg).add(nuovoArco);
		    }
		    scan.nextLine();
		    int j = 0;
		    //crea le liste di coppie di uguaglianze e le salva nel vettore
		    while(scan.hasNext()) {
		    	uguaglianze.add(j, new LinkedList<Integer>());
		    	uguaglianze.get(j).add(scan.nextInt());
		    	uguaglianze.get(j).add(scan.nextInt());
		    	j++;
		    }
		    //inizializza la lista delle uguaglianze a false.
		    //se le uguaglianze sono banali (del tipo xi = xi), setta il risultato a true
		    for (int i = 0; i < uguaglianze.size(); i++) {
				if(uguaglianze.get(i).get(0) == uguaglianze.get(i).get(1)) {
					risultato.add(i, true);
				} else {
					risultato.add(i, false);
				}
			}
		    scan.close(); 
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

	/*
	 * Visita tramite il procedimento dell'algoritmo DFS tutti i nodi del grafo.
	 * Quando cerca di visitare un nodo grigio significa che è stato rilevato un ciclo, allora
	 * controlla se esiste un'uguaglianza i cui valori si trovano all'interno di esso.
	 * O(n+m) + O(p) + O(u) = O(n+m), con n-nodi, m-archi, u-uguaglianze, p-predecessori.
	 */
	private void visitaDFS(int u) {
		List<Integer> elemCiclo = new LinkedList<>(); //lista di supporto per salvare i nodi appartenenti ad un ciclo
		int i = 0;
	    colore[u] = Color.GREY;
	    Iterator<Arco> it = listaAdiacenza.get(u).iterator();
	    while (it.hasNext()) {
	    	final Arco arco = it.next();
	    	final int v = arco.dst;
	        if(colore[v] == Color.WHITE) {
	        	predecessore.add(i, u);
	        	i++;
	        	visitaDFS(v);
	        } else if(colore[v] == Color.GREY){ //rilevo un ciclo
	        	predecessore.add(i, u);
	        	i++;
	        	//se in nodo grigio che cerco di visitare è già all'interno della lista dei predecessori
	        	if(predecessore.contains(v)) { 
	        		//scorre la lista dei predecessori fino a risalire al primo elemento del ciclo e
	        		//salva tali nodi in una lista provvisoria
	        		for(int j=0; j<=predecessore.indexOf(v); j++) {
	        			elemCiclo.add(predecessore.get(j));
	        		}
	        		//scorre le uguaglianze. Se l'uguaglianza non è ancora stata verificata e
	        		//se il ciclo contiene i suoi valori, allora questi valori saranno equivalenti
	        		//e viene settato il risultato a true
	        		for(int j=0; j<uguaglianze.size(); j++) {	
	        			if(risultato.get(j) == false && elemCiclo.contains(uguaglianze.get(j).get(0)) 
	        					&& elemCiclo.contains(uguaglianze.get(j).get(1))) {
	        				risultato.set(j, true);
						}
	        			
	        		}
	        	}
	        	
	        }
	    }
	    colore[u] = Color.BLACK;
	}
	
	/*
	 * Per ogni nodo di colore bianco chiama l'algoritmo di visita DFS
	 * e al termine stampa il risultato finale.
	 * O(n), con n-nodi.
	 */
	public void visita() {
		predecessore = new LinkedList<>();
        colore = new Color[numNodi];   
        Arrays.fill(colore, Color.WHITE);
        for(int v=0; v<numNodi; v++) {
            if(colore[v] == Color.WHITE) {
            	visitaDFS(v);
            }
        }
        for(int i=0; i<risultato.size(); i++) {
        	System.out.println(risultato.get(i));
        }
    }

	public static void main(String[] args) {
		if (args.length != 1) {
            System.err.println("Indicare sulla riga di comando il nome di un file di input");
            System.exit(1);
        } else {
        	Esercizio5 es5 = new Esercizio5(args[0]);
        	es5.visita();
        }
	}
}
