/*
 * Marianna Gimigliano
 * Matricola: 0000915343
 * marianna.gimigliano@studio.unibo.it
 * 
 * Compilare con: javac Esercizio2.java
 * Eseguire con: java -cp . Esercizio2 <file_input>
 *
 * Il programma risolve il problema delle ceste attraverso 
 * un algoritmo divide-et-impera.
 * 
 * Costo computazionale: O(plogp) + O(p) + O(c) + O(p*c^2) = O(p*c^2)
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Esercizio2 {
	
	List<Integer> prodotti;
	int[] ceste;
	int soglia;
	int numSoci;
	int cestaValMin;
	int cestaMin; //cesta con valore minimo
	int cestaMax = -1; //cesta con valore massimo
	
	/*
	 * Legge i dati in input da un file e crea un'istanza della classe.
	 */
	public Esercizio2(String file) {
        try {
            Scanner scan = new Scanner(new FileReader(file));
            numSoci = scan.nextInt();
            soglia = scan.nextInt();
            prodotti = new LinkedList<>(); 
            ceste = new int[numSoci];
            while(scan.hasNextInt()) {
                prodotti.add(scan.nextInt());
            }
            Collections.sort(prodotti); //utilizza mergesort, O(plogp) con p-prodotti
            Collections.reverse(prodotti); //ordina in senso decrescente i valori dei prodotti, O(p) con p-prodotti
        } catch(IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }
	
	/*
	 * Se possibile, chiama il metodo che risolve il problema delle ceste.
	 * O(c), con c-ceste
	 */
	public void stampaSoluzione() {
		if(prodotti.size()<numSoci) {
			System.out.println("NO"); //non ci sono abbastanza prodotti per riempire tutte le ceste
		} else {
            for(int i=0; i<ceste.length; i++) { 
				ceste[i] = prodotti.get(i); //quando le ceste sono vuote, le riempie con i primi n-prodotti
											//(già ordinata, quindi inserisce il prodotto con valore maggiore possibile) 
			}
            risolvi();
		}
	}
	
	/*
	 * Ricerca la cesta con valore minimo tramite un algoritmo divide-et-impera.
	 * 2T(c/2) + c2, se c>1
	 * = O(c), con c-ceste
	 */
	public int min(int ceste[], int i, int j){
		if(i>j) {
			return 0;
		} else if(i == j) {
			cestaValMin = ceste[i];
			return cestaValMin;
		} else {
			int m = (i+j)/2;
			int min1 = min(ceste, i, m);
			int min2 = min(ceste, m+1, j);
			if(min1 < min2) {
				return min1;
			} else {
				return min2;
			} 
		}
	}
	
	/*
	 * Chiama il metodo min che ritorna la cesta di valore minimo.
	 * Il suo valore viene sommato col successivo elemento della lista dei prodotti
	 * (già ordinata, quindi somma il prodotto con valore maggiore possibile). 
	 * Stampa, se possibile, i valori della cesta minima e massima.
	 * O(p)*O(c)*O(c) + O(c) = O(p*c^2), con c-ceste e p-prodotti.
	 */
	public void risolvi() {
		for(int i=ceste.length; i<prodotti.size(); i++) { //i primi n-elementi sono già stati inseriti 
			int indiceMin = -1; //indice della cesta con valore minimo
			cestaValMin = min(ceste, 0, numSoci-1);
			for(int j=0; (j<ceste.length && indiceMin==-1); j++) { //si ferma alla prima occorenza della cesta con valore minimo
				if(ceste[j]==cestaValMin) {
					indiceMin = j; 
				}
			}
			ceste[indiceMin] = cestaValMin + prodotti.get(i); //somma il valore della cesta con il valore del nuovo prodotto inserito
		}
		cestaMin = ceste[0];
		//cesta di valore massimo e minimo alla fine del riempimento
		for(int k=0; k<ceste.length; k++) {
			if(ceste[k] > cestaMax) {
				cestaMax = ceste[k];  
			} else if(ceste[k] < cestaMin) {
				cestaMin = ceste[k]; 
			}
		}
		//stampa soluzione
		if (cestaMax-cestaMin <= soglia) {
			System.out.println(cestaMin + " " + cestaMax);
		} else {
			System.out.println("NO"); //la differenza tra cesta minima e massima non rientra nella soglia
		}
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
            System.err.println("Indicare sulla riga di comando il nome di un file di input");
            System.exit(1);
        } else {
        	Esercizio2 es2 = new Esercizio2(args[0]);
        	es2.stampaSoluzione();
        }
	}
}
