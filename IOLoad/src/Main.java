

import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		IOStrain ios = new IOStrain();
		Scanner reader = new Scanner(System.in);
		int fileAmount = 0;
		long durationInSeconds = 1000;
		
		//Config IOStrain
		System.out.println("Bitte angeben, wie viele Dateien erstellt werden sollen: ");
		fileAmount = reader.nextInt();
		System.out.println("Bitte angeben, wie lange der Belastungstestlaufen soll (in Sekunden): ");
		durationInSeconds = reader.nextLong();
		reader.close();
		
		ios.setAmountOfFilesToCreate(fileAmount);
		ios.setDurationInSeconds(durationInSeconds);
		ios.start();		
	}

}
