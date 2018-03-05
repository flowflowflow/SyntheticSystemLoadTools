import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		IOStrain ios = new IOStrain();
		Scanner reader = new Scanner(System.in);
		int fileAmount;
		long durationInSeconds;
		
		//Config IOStrain
		System.out.printf("Bitte angeben, wie viele Dateien erstellt werden sollen (50 ist ein guter Wert): %n");
		System.out.printf("Please type in desired amount of files to create (50 is advised): %n");
		fileAmount = reader.nextInt();
		System.out.printf("%nBitte angeben, wie lange der Belastungstest laufen soll (in Sekunden) [60 ist ein guter Wert]: %n");
		System.out.printf("Please set the length of the synthetic IO load test (in Seconds) [60 is advised]: %n");
		durationInSeconds = reader.nextLong();
		reader.close();
		
		ios.setAmountOfFilesToCreate(fileAmount);
		ios.setDurationInSeconds(durationInSeconds);
		ios.start();
	}
}
