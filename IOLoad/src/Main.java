import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		IOStrain ios = new IOStrain();
		Scanner reader = new Scanner(System.in);
		int fileAmount;
		long durationInSeconds;
		
		//Config IOStrain
		System.out.println("Bitte angeben, wie viele Dateien erstellt werden sollen (50 ist ein guter Wert): ");
		fileAmount = reader.nextInt();
		System.out.println("Bitte angeben, wie lange der Belastungstest laufen soll (in Sekunden) [60 ist ein guter Wert]: ");
		durationInSeconds = reader.nextLong();
		reader.close();
		
		ios.setAmountOfFilesToCreate(fileAmount);
		ios.setDurationInSeconds(durationInSeconds);
		ios.start();		
	}
}
