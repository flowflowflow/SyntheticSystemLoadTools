

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * Klasse zur Auslastung der f�r die JVM bereitgestellten 
 * Prozessorkerne (physisch und logisch) durch wiederholte
 * Ausf�hrung rechenintensiver Operationen
 * 
 * @author Florian Riedlinger
 */

public class CPUStrain extends Thread {
	
	int anzahlThreads;
	private int rechnungsDurchlaeufe = 60; // ca. Zeit in Sekunden
	LongAdder zaehler = new LongAdder();
	Runtime rt = Runtime.getRuntime();
	List<MultiThreadBerechnung> laufendeMultiThreadBerechnungen = new ArrayList<>();
	List<Thread> laufendeThreads = new ArrayList<>();
	
	public CPUStrain() {
		anzahlThreads = rt.availableProcessors();
		rechnungsDurchlaeufe = 120;
		System.out.printf("%n========  CPULoad v1.0 ========%n%n");
		System.out.printf("%d logische CPU-Kerne erkannt", anzahlThreads);
		System.out.printf("%n%n===============================%n%n");
	}
	
	public CPUStrain(int anzahlThreads) {
		if(anzahlThreads < 1 || anzahlThreads > rt.availableProcessors()) {
			this.anzahlThreads = 1;
			System.out.println("ERROR: Anzahl der Threads fehlerhaft.%n Setze auf Standardwert: 1 Thread");
		}
		else {
			this.anzahlThreads = anzahlThreads;
		}
		System.out.printf("%%d logische CPU-Kerne werden verwendet%n%n", anzahlThreads);
	}
	
	public void setRuns(int rechnungsDurchlaeufe) {
		this.rechnungsDurchlaeufe = rechnungsDurchlaeufe;
	}
	
	public String getRuns() {
		return "%nEs sind momentan " + rechnungsDurchlaeufe + " Durchl�ufe eingestellt. (" + rechnungsDurchlaeufe + " Sekunden)%n";
	}
	
	public void setThreads(int anzahlThreads) {
		this.anzahlThreads = anzahlThreads;
	}
	
	public String getThreads() {
		return "Es werden momentan " + this.anzahlThreads + " logische CPU-Kerne verwendet.";
	}
	
	
	public void run() {
		
		System.out.printf(getRuns());
		System.out.printf("%nStarte Berechnung mit %d Threads:%n%n", anzahlThreads);
		
		/*
		 * Initialisiert und startet eine zuvor festgelegte Anzahl
		 * von Threads (abh�ngig von verf�gbaren logischen CPU-Kernen)
		 */
		for(int i = 0; i < anzahlThreads; ++i) {
			MultiThreadBerechnung ber = new MultiThreadBerechnung(zaehler);
			Thread t = new Thread(ber);
			laufendeMultiThreadBerechnungen.add(ber);
			laufendeThreads.add(t);
			t.start();
		}
		
		/* 
		 * Setzt den Z�hler zur�ck, um einen neuen Lauf zu starten
		 * und h�lt den Thread f�r eine Sekunde an, um Statistik
		 * auszugeben
		*/
		for(int i=0; i < rechnungsDurchlaeufe; ++i) {
			zaehler.reset();
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ie) {
				System.out.println(ie);
				break;
			}
			System.out.printf(
					"[%d. Durchlauf] Berechnungen pro Sekunde: %d (%.2f pro Thread)\n"
					, i+1, zaehler.longValue(), (double) (zaehler.longValue()) / anzahlThreads);
		}
		
		/* F�hrt stop()-Methode der MultiThreadBerechnung-Klasse in Instanz i aus
		 *  und wartet auf Beendigung des Threads mit Instanz i
		 */
		for(int i=0; i < laufendeMultiThreadBerechnungen.size(); ++i) {
			laufendeMultiThreadBerechnungen.get(i).stop();
			try {
				laufendeThreads.get(i).join();
			} catch(InterruptedException ie) {
				System.err.println("Exiting application: " + ie);
				System.exit(2);
			}
		}
	}
	
	
}

