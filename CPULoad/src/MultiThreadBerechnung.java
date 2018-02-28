

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;


/**
 * Eigentliche Berechnungsklasse, die für Multi-Threading geeignet ist. Hier
 * wird die rechenaufwendige Sinus-Kosinus und Multiplikations-Operation
 * ausgeführt.
 * 
 * 
 * @author Ben Meier (https://gist.github.com/AstromechZA)
 * @author Florian Riedlinger
 * 
 */
public class MultiThreadBerechnung implements Runnable {

	private final Random rng;
	private final LongAdder berechnungenDurchgefuehrt;
	private boolean angehalten;
	private double store;

	/** Konstruktor und Initialisierung d. Variablen 
		 * 
		 * @param LongAdder Anzahl der durchgeführten Berechnungen 
		 * */
		public MultiThreadBerechnung(LongAdder berechnungenDurchgefuehrt) {
			this.rng = new Random();
			this.angehalten = false;
			this.store = 1;
			this.berechnungenDurchgefuehrt = berechnungenDurchgefuehrt;
		}

	public void stop() {
		angehalten = true;
	}

	/**
	 * Startet Berechnung mit festgelegter bzw. erkannter Anzahl an Threads Führt
	 * eine CPU-aufwendige Sinus-Cosinus-Berechnung einer Zufallszahl mit
	 * anschließender Multiplikation durch und erhöht den Berechnungszähler um 1
	 */
	@Override
	public void run() {
		while (!angehalten) {
			double r = rng.nextFloat();
			double v = Math.sin(Math.cos(Math.sin(Math.cos(r)))); // generates synthetic Load on CPU
			this.store *= v;
			this.berechnungenDurchgefuehrt.add(1);
		}
	}
}
