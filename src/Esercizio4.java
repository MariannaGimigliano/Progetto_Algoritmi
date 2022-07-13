/*
 * Marianna Gimigliano
 * Matricola: 0000915343
 * marianna.gimigliano@studio.unibo.it
 * 
 * Compilare con: javac Esercizio4.java
 * Eseguire con: java -cp . Esercizio4 comandi_input
 *
 * Il programma risolve il problema delle biglie attraverso un algoritmo divide-et-impera.
 * Si tiene traccia di un vettore "movimenti". All'interno di un metodo ricorsivo,
 * ad ogni biforcazione, viene salvato nel vettore 0 se la biglia è caduta a sinistra, 1 se è caduta a destra. 
 * Tramite il conteggio di quante volte la biglia è andata a destra si risale all'urna in cui è caduta.
 * 
 * Costo computazionale: O(u) + O(b*m) = O(b*m)
 */

import java.util.Locale;
import java.util.Random;

public class Esercizio4 {
	
	static int numUrne; 
	static double prob; //probabilità che ogni biglia cada verso destra
	static int numBiglie; 
	static int[] urne; //ogni elemento del vettore rappresenta un'urna
	static int[] movimenti; //vettore delle cadute (destra o sinistra) della biglia
	Random rnd = new Random(915343L);
	
	/*
	 * Stampa un intero che indica l'urna e il valore che indica la 
	 * frazione delle biglie che è caduta in quell'urna.
	 * O(u), con u-urne.
	 */
	public void stampaSoluzione() {
		for(int i=0; i<urne.length; i++) {
			if(urne[i]!=0) {
				System.out.printf(i + " " + "%.6f", (float)urne[i]/numBiglie);
				System.out.println();
			} else {
				System.out.printf(i + " " + "%.6f", (float)urne[i]);
				System.out.println();
			}
		}
	}
	
	/*
	 * Simula la caduta di una biglia e assegna al vettore dei movimenti
	 * 0 se la biglia è caduta a sinistra, 1 se è caduta a destra.
	 * O(m), con m-movimenti.
	 */
	public int[] lancia(int[] movimenti, int i) {
		if (i == movimenti.length) {
			return movimenti;
		} else {
			if(rnd.nextDouble()<prob) { //vai a destra
				movimenti[i] = 1;
			} else { //vai a sinistra
				movimenti[i] = 0;
			}
			return lancia(movimenti, ++i);
		}
	}
	
	/*
	 * Per ogni biglia simula la sua caduta, conta quante volte è andata a destra
	 * e inserisce tale biglia nell'urna corrispondente.
	 * (es. la biglia cade 4 volte a destra su 7 biforcazioni, allora finisce nell'urna 
	 * alla posizione 4 (quinta urna)).
	 * O(b)*O(m) + O(m) = O(b*m), con b-biglie e m-movimenti
	 */
	public void esegui() {
		for(int i=0; i<numBiglie; i++) {
			int cont = 0; //contatore dei movimenti a destra della biglia
			movimenti = lancia(movimenti, 0);
			for(int j=0; j<movimenti.length; j++) {
				if(movimenti[j] == 1) {
					cont++;
				}
			}
			urne[cont] += 1;                          
		}
		stampaSoluzione();
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		if(args.length == 0) {
            System.err.println("Indicare sulla riga di comando i dati necessari");
            System.exit(0);
        } else {
        	numUrne = Integer.parseInt(args[0]);
            prob = Double.parseDouble(args[1]);
            numBiglie = Integer.parseInt(args[2]);
            urne = new int[numUrne]; 
            movimenti = new int[numUrne-1]; //per n urne finali, ci sono n-1 biforcazioni possibili
			Esercizio4 es4 = new Esercizio4();
			es4.esegui();
        }
	}
}
