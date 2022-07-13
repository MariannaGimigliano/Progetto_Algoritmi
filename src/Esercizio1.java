/*
 * Marianna Gimigliano
 * Matricola: 0000915343
 * marianna.gimigliano@studio.unibo.it
 * 
 * Compilare con: javac Esercizio1.java
 * Eseguire con: java -cp . Esercizio1 <file_comandi>
 *
 * La memorizzazione dei prodotti finanziari e dei portafogli dei clienti viene gestita attraverso
 * HashMap con associazioni chiave-valore.
 * 
 * Costo dato dalle op di ricerca, inserimento, rimozione.
 * Ricerca: O(n/m), con n-elementi e m-capienza della tabella hash.
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Esercizio1 {
	
	Map<String, Integer> prodotto = new HashMap<>(); //coppia <id prodotto, valore prodotto>
	Map<String, List<String>> portafoglio = new HashMap<>(); // coppia <matricola cliente, lista di id prodotti>
 
	/*
	 * Legge il file in input con la sequenza di comandi e chiama i metodi 
	 * opportuni tramite lo switch.
	 */
	public void esegui(String file){
        try {
            Scanner scan = new Scanner(new FileReader(file));
            while(scan.hasNext()) {
            	String comando = scan.next();
            	switch(comando.toLowerCase()) {
            		case "m":
            			modificaProdotto(scan.next(), Integer.parseInt(scan.next()));
            			break;
            		case "p":
            			aggiungiProdotto(scan.next(), Integer.parseInt(scan.next()));
            			break;
            		case "c":
            			aggiungiCliente(scan.next());
            			break;
            		case "a":
            			inserisciProdPortafoglio(scan.next(), scan.next(), Integer.parseInt(scan.next()));
            			break;
            		case "r":
            			rimuoviProdPortafoglio(scan.next(), scan.next(), Integer.parseInt(scan.next()));
            			break;
            		case "k":
            			String cod = scan.next();
            			if(cod.startsWith("P")) { 
            				rimuoviProdotto(cod);
            			} else if (cod.startsWith("C")) { 
            				rimuoviCliente(cod);
            			} else {
            				System.out.println("Identificativo non valido");
            			}
            			break;
            		case "v":
            			visualizzaPortafoglio(scan.next());
            			break;
            	}
            } scan.close();
        } catch ( IOException ex ) {
            System.err.println(ex);
            System.exit(1);
        }
    }
	
	/*
	 * Aggiunge una coppia id-valore alla mappa dei prodotti.
	 */
	public void aggiungiProdotto(String id, int valore) {
		if(!id.startsWith("P")){
			System.out.println ("Identificativo non valido");
		} else 	{
			if(!prodotto.containsKey(id)) {
				prodotto.put(id, valore);
			} else {
				System.out.println("Identificativo già presente");
			}
		}
	}
	
	/*
	 * Rimuove una coppia id-valore alla mappa dei prodotti.
	 * Rimuove un id dalla mappa dei portafogli se il prodotto dovesse essere
	 * presente nei portafogli di alcuni clienti.
	 */
	public void rimuoviProdotto(String id) {
		if(prodotto.containsKey(id)) {
			prodotto.remove(id);
			for (String matricola : portafoglio.keySet()) {
				while(portafoglio.get(matricola).contains(id)) {
					portafoglio.get(matricola).remove(id); 
				}
			}
		} else {
			System.out.println("Identificativo non trovato");
		}
	}
	
	/*
	 * Modifica un valore nella mappa dei prodotti.
	 */
	public void modificaProdotto(String id, int valore) {
		if(prodotto.containsKey(id)) {
			prodotto.replace(id, valore);
		} else {
			System.out.println("Identificativo non trovato");
		}
	}
	
	/*
	 * Aggiunge una coppia matricola-lista vuota alla mappa dei portafogli.
	 */
	public void aggiungiCliente(String matricola) {
		if(!matricola.startsWith("C")) {
			System.out.println ("Identificativo non valido");
		} else {
			if(!portafoglio.containsKey(matricola)) {
				portafoglio.put(matricola, List.of());
			} else {
				System.out.println("Identificativo già presente");
			}
		}
	}
	
	/*
	 * Rimuove una coppia matricola-lista di id prodotto alla mappa dei portafogli.
	 */
	public void rimuoviCliente(String matricola) {
		if(portafoglio.containsKey(matricola)) {
			portafoglio.remove(matricola);
		}  else {
			System.out.println("Identificativo non trovato");
		}
	}
	
	/*
	 * Salva la lista dei prodotti nel portafogli di un determinato cliente, aggiunge n volte 
	 * un id prodotto alla lista e sotituisce la precedente con la nuova.
	 */
	public void inserisciProdPortafoglio(String matricola, String id, int n) {
		if(prodotto.containsKey(id) && portafoglio.containsKey(matricola)) {
			List<String> prodottiPortafoglio = new LinkedList<>(portafoglio.get(matricola));
			for(int i = 0; i < n; i++) {
				prodottiPortafoglio.add(id);
			}
			portafoglio.replace(matricola, prodottiPortafoglio);
		} else {
			System.out.println("Identificativo non trovato");
		}	
	}

	/*
	 * Salva la lista dei prodotti nel portafogli di un determinato cliente, rimuove n volte 
	 * un id prodotto dalla lista e sotituisce la precedente con la nuova.
	 */
	public void rimuoviProdPortafoglio(String matricola, String id, int n) {
		if(prodotto.containsKey(id) && portafoglio.containsKey(matricola)) {
			List<String> prodottiPortafoglio = new LinkedList<>(portafoglio.get(matricola));
			for(int i = 0; i < n; i++) {
				prodottiPortafoglio.remove(id);
			}
			portafoglio.replace(matricola, prodottiPortafoglio);
		} else {
			System.out.println("Identificativo non trovato");
		}		
	}
	
	/*
	 * Stampa i prodotti nel portafogli di un determinato cliente.
	 * Stampa le coppie <id prodotti dal portafogli del cliente, valore corrispondente
	 * dalla mappa dei prodotti>.
	 */
	public void visualizzaPortafoglio(String matricola){
		if(portafoglio.containsKey(matricola)) {
			System.out.println(matricola);
			for (String idProdotto : portafoglio.get(matricola)) {
				System.out.println(idProdotto + " " + prodotto.get(idProdotto));
			}
		} else {
			System.out.println("Identificativo non trovato");
		}
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
            System.err.println("Indicare sulla riga di comando il nome di un file di input");
            System.exit(1);
        } else {
			Esercizio1 es1 = new Esercizio1();
			es1.esegui(args[0]);
        }
	}
}
