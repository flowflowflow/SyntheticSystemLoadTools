

import java.io.IOException;
/** 
 * This class is needed so the main-Class can run all three performance
 * tests in simultaneous threads. It starts the synthetic IO load Class 
 * (IOMultiThreadedWorkload.java) and creates MAX_FILES_CREATED amount of files
 * that will be filled with random strings nearly simultaneously.
 * 
 * @author Florian Riedlinger
 *
 */

public class IOStrain extends Thread{

	int filesToCreate;
	long durationInSeconds;
	long elapsedTimeInSeconds = 0;
	IOMultiThreadedWorkload iomtw;
	Thread t;
	
	public void run() {
		try {
			for(int i=0; i < filesToCreate; i++)	{ 
				iomtw = new IOMultiThreadedWorkload(i, durationInSeconds);
				t = new Thread(iomtw);
				t.start();
			}
			iomtw.progressDots();
			t.join();
			elapsedTimeInSeconds = iomtw.getElapsedTime();
			System.out.printf("%n%nIOLoad beendet! (Dauer: " + elapsedTimeInSeconds + " Sekunden)");
			System.out.printf("%n%nIOLoad finished! (Elapsed time: " + elapsedTimeInSeconds + " Seconds)");
			System.exit(0);
			//iomtw.deleteTemporaryFiles();
		} catch (IOException iex) {
			System.exit(2);
		} catch (InterruptedException inte) {
			System.err.println(inte);
			System.exit(2);
		}
	}
	
	public void setAmountOfFilesToCreate(int amount) {
		if(amount < 1 || amount > 1000) {
			this.filesToCreate = 1;
			System.err.printf("Error: invalid amount entered.%nSetting amount to " + this.filesToCreate);
		}
		else {
			this.filesToCreate = amount;
		}
	}
	
	public void setDurationInSeconds(long durationInSeconds) {
		if(durationInSeconds > 0 && durationInSeconds < 10800) {
			this.durationInSeconds = durationInSeconds;
		}
		else {
			this.durationInSeconds = 600;
			System.out.printf("Fehlerhafte Zeitangabe%nSetze Durchlaufszeit auf 10 Minuten");
		}
	}
}
