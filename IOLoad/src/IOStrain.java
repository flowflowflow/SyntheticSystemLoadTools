

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
			System.out.printf("%n%nIOLoad beendet!");
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
		/*if(amount == 0) {
			this.filesToCreate = 1;
		}
		else if(amount >= 1) {
			this.filesToCreate = amount;
		}*/
		
		this.filesToCreate = amount;
	}
	
	public void setDurationInSeconds(long durationInSeconds) {
		//Duplicate: checks for same parameters in IOMultiThreadedWorkload
		/*if(durationInSeconds > 1 && durationInSeconds < 10800) {
			this.durationInSeconds = durationInSeconds;
		}
		else {
			System.out.println("Fehlerhafte Eingabe. Setze Durchlaufzeit auf 10 Sekunden!");
			this.durationInSeconds = 10;
		}*/
		
		this.durationInSeconds = durationInSeconds;
	}
}
