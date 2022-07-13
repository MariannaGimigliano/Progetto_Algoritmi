/*
 * Marianna Gimigliano
 * Matricola: 0000915343
 * marianna.gimigliano@studio.unibo.it
 * 
 * Compilare con: javac Esercizio3.java
 * Eseguire con: java -cp . Esercizio3 <file_input>
 *
 * Il programma risolve il problema degli scacchi prendendo spunto da un algoritmo di visita 
 * in ampiezza, considerando la scacchiera come un grafo non orientato.
 * Ogni cella rappresenta un nodo e sono connesse alle altre celle raggiungibili tramite archi. 
 * Allora l'algoritmo visiterà tutti i nodi raggiungibili a distanze man mano crescenti dalla sorgente,
 * cioè tramite una sequenza di mosse.
 * 
 * Costo computazionale: O(r*c) + O(r*c) + O(n+m) = O(r*c)
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Esercizio3 {
	
	int n;
	int m;
	char[][] scacchiera;
	Coordinate cavallo; //coppia (i,j): posizione del cavallo sulla scacchiera durante l'esecuzione
	
	/*
	 * Utilizza una classe cooridinate per tenere traccia dei movimenti
	 * del cavallo sulla scacchiera.
	 */
	private class Coordinate{
		int x;
		int y;
		
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	/*
	 * Legge i dati in input da un file e crea un'istanza della classe.
	 * O(r*c), con r-righe e c-colonne.
	 */
	public Esercizio3(String file) {
		try {
            Scanner scan = new Scanner(new FileReader(file));
            n = scan.nextInt();
            m = scan.nextInt();
            scacchiera = new char[n][m];
            scan.nextLine();
            for(int i=0; i<n; i++) {
            	String riga = scan.nextLine();
				for(int j=0; j<m; j++) {
					scacchiera[i][j] = riga.charAt(j);
					if(scacchiera[i][j]=='C') {
						cavallo = new Coordinate(i, j); //memorizza le coordinate iniziali della posizione del cavallo
					}
				}
			}
            scan.close();
	    } catch(IOException ex) {
	        System.err.println(ex);
	        System.exit(1);
	    }
	}
	
	/*
	 * Stampa la scacchiera finale e la stringa true se il cavallo è in grado 
	 * di raggiungere tutte le caselle libere, false altrimenti.
	 * O(r*c), con r-righe e c-colonne.
	 */
	public void stampaSoluzione() {
		boolean completo = true;
		for(int i=0; i<scacchiera.length; i++) {
			for(int j=0; j<scacchiera[0].length; j++) {
				System.out.print(scacchiera[i][j]);
				if(scacchiera[i][j] == '.') {
					completo = false;
				}
			} System.out.println();
		} System.out.println(completo);
	}
	
	/*
	 * Prende in input una coppia di coordinate (x,y) e controlla che sia all'interno
	 * della scacchiera.
	 */
	public boolean controllo(int x, int y) {
		if(x<0 || y<0 || x>=n || y>=m) {
			return false;
		} 
		return true;
	}
	
	/*
	 * A partire dalla sorgente (posizione iniziale del cavallo) vengono visitate tutte le celle raggiungibili.
	 * Il cavallo ha 8 mosse possibili. Se una mossa ricade su una cella vuota, essa viene contrassegnata con
	 * una 'C' e inserita in fondo alla coda. Altrimenti, se la mossa ricade fuori dalla scacchiera o in una cella
	 * occupata, si procede al controllo della mossa successiva.
	 * O(n+2m) = O(n+m), con n-nodi e m-archi.
	 */
	public void visita() { 
		Queue<Coordinate> coda = new LinkedList<>(); //coda di coordinate, quindi di celle '.'
		coda.add(cavallo); 
		while(!coda.isEmpty()) {
			cavallo = coda.poll();
			if(controllo(cavallo.getX()-2, cavallo.getY()-1) && scacchiera[cavallo.getX()-2][cavallo.getY()-1]=='.') {
				scacchiera[cavallo.getX()-2][cavallo.getY()-1] = 'C';
				coda.add(new Coordinate(cavallo.getX()-2, cavallo.getY()-1));
			} 
			if(controllo(cavallo.getX()-2, cavallo.getY()+1) && scacchiera[cavallo.getX()-2][cavallo.getY()+1]=='.') {
				scacchiera[cavallo.getX()-2][cavallo.getY()+1] = 'C';
				coda.add(new Coordinate(cavallo.getX()-2, cavallo.getY()+1));
			} 
			if(controllo(cavallo.getX()-1, cavallo.getY()-2) && scacchiera[cavallo.getX()-1][cavallo.getY()-2]=='.') {
				scacchiera[cavallo.getX()-1][cavallo.getY()-2] = 'C';
				coda.add(new Coordinate(cavallo.getX()-1, cavallo.getY()-2));
			} 
			if(controllo(cavallo.getX()-1, cavallo.getY()+2) && scacchiera[cavallo.getX()-1][cavallo.getY()+2]=='.') {
				scacchiera[cavallo.getX()-1][cavallo.getY()+2] = 'C';
				coda.add(new Coordinate(cavallo.getX()-1, cavallo.getY()+2));
			} 
			if(controllo(cavallo.getX()+1, cavallo.getY()+2) && scacchiera[cavallo.getX()+1][cavallo.getY()+2]=='.') {
				scacchiera[cavallo.getX()+1][cavallo.getY()+2] = 'C';
				coda.add(new Coordinate(cavallo.getX()+1, cavallo.getY()+2));
			} 
			if(controllo(cavallo.getX()+1, cavallo.getY()-2) && scacchiera[cavallo.getX()+1][cavallo.getY()-2]=='.') {
				scacchiera[cavallo.getX()+1][cavallo.getY()-2] = 'C';
				coda.add(new Coordinate(cavallo.getX()+1, cavallo.getY()-2));
			} 
			if(controllo(cavallo.getX()+2, cavallo.getY()-1) && scacchiera[cavallo.getX()+2][cavallo.getY()-1]=='.') {
				scacchiera[cavallo.getX()+2][cavallo.getY()-1] = 'C';
				coda.add(new Coordinate(cavallo.getX()+2, cavallo.getY()-1));
			} 
			if(controllo(cavallo.getX()+2, cavallo.getY()+1) && scacchiera[cavallo.getX()+2][cavallo.getY()+1]=='.') {
				scacchiera[cavallo.getX()+2][cavallo.getY()+1] = 'C';
				coda.add(new Coordinate(cavallo.getX()+2, cavallo.getY()+1));
			} 
		}
		stampaSoluzione();
	}

	public static void main(String[] args) {
		if ( args.length != 1 ) {
            System.err.println("Indicare sulla riga di comando il nome di un file di input");
            System.exit(1);
        } else {
        	Esercizio3 es3 = new Esercizio3(args[0]);
        	es3.visita();
        }
	}
}
