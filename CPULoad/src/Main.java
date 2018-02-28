import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		/**
		 * @Params: anzahlThreads sets the number
		 * of logical CPU cores that will be used for
		 * calculations. 
		 * For example: 2/4 cores will result in about 50% load
		 */
		CPUStrain cps = new CPUStrain();
		Scanner reader = new Scanner(System.in);
		int anzahlThreads;
		int rechnungsDurchlaeufe;
		
		//User-Input: Number of logical CPU cores that should be used
		System.out.printf("Bitte Anzahl der zu verwendenden logischen CPU-Kerne eingeben: ");
		anzahlThreads = reader.nextInt();
		System.out.printf("%nBitte Durchführungszeitraum (in Sekunden) festlegen: ");
		rechnungsDurchlaeufe = reader.nextInt();
		reader.close();
		
		
		//Setting parameters (20 Seconds of full CPU load here)
		cps.setThreads(anzahlThreads);
		cps.setRuns(rechnungsDurchlaeufe);
		
		cps.start();
		
	}

}
