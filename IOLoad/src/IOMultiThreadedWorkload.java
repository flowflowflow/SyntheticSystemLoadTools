
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.io.File;
import java.util.Random;

public class IOMultiThreadedWorkload extends Thread {

	int x = 1;
	long durationInMilliSeconds = 1000;
	final int RUNS_AMOUNT = 1000;
	boolean isActive = false;
	RandomAccessFile raf;
	String tempPath = new String(System.getProperty("java.io.tmpdir"));
	String defaultPath = tempPath;
	String fileName = "randomtext_" + x + ".txt";
	String pathAndFileName = tempPath + "\\" + fileName;
	String mode;
	File fileObject;

	public void run() {
		try {
			writeIntoFile();
		} catch (IOException ie) {
			System.err.println("Fehler: " + ie);
			System.exit(0);
		} /* catch(InterruptedException intexc) { System.exit(1); }
		   */
	}

	public IOMultiThreadedWorkload(int x, long durationInSeconds) throws IOException {
		try {
			setX(x + 1);
			setMode("rws");
			setFileName("randomtext");
			setDurationInSeconds(durationInSeconds);
			System.out.println("Creating and filling file now: " + this.pathAndFileName);
			raf = new RandomAccessFile(pathAndFileName, mode);
		} catch (IOException ioE) {
			System.err.println(ioE);
			System.exit(0);
		}
	}

	public IOMultiThreadedWorkload(String fileName, int x, long durationInSeconds) throws IOException {
		try {
			setX(x + 1);
			setMode("rws");
			setFileName(fileName);
			setDurationInSeconds(durationInSeconds);
			System.out.println("Creating and filling file now: " + this.pathAndFileName);
			raf = new RandomAccessFile(pathAndFileName, mode);
		} catch (IOException ioE) {
			System.err.println(ioE);
			System.exit(0);
		}
	}

	/*
	 * Set- & Get-Methods
	 */
	
	//x variable
	public void setX(int x) {
		this.x = x;
	}

	//Duration in seconds 
	public void setDurationInSeconds(long durationInSeconds) {
		if(durationInSeconds > 0 && durationInSeconds < 10800) {
			this.durationInMilliSeconds = durationInSeconds*1000;
		}
		else {
			this.durationInMilliSeconds = 600 * 1000;
		}
	}
	
	//RandomAccessFile mode
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	
	// File name
	public void setFileName(String fileName) {
		this.fileName = fileName + "_" + x + ".txt";
		setPathAndFileName();
	}

	public String getFileName() {
		return fileName;
	}

	// Default path
	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
		setPathAndFileName();
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	// Path and File name
	public void setPathAndFileName() {
		if (defaultPath == tempPath) {
			this.pathAndFileName = tempPath + "\\" + fileName;
		} else {
			this.pathAndFileName = defaultPath + "\\" + fileName;
		}
	}

	public String getPathAndFileName() {
		return pathAndFileName;
	}
	
	public void progressDots() {
		while(isActive) {
			try {
			System.out.print(".");
			Thread.sleep(1000);
			System.out.print(".");
			Thread.sleep(1000);
			System.out.print(".");
			Thread.sleep(1000);
			System.out.print("\b\b\b");
			System.out.print("   ");
			System.out.print("\b\b\b");
			} catch(InterruptedException intex) {
				System.err.println(intex);
				System.exit(4);
			}
		}
	}
	
	// === Private methods ===

	private void writeIntoFile() throws IOException {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + durationInMilliSeconds;
		long resetTimerInMillis = 20000;
		isActive = true;
		
		while(System.currentTimeMillis() < endTime) {
			raf.getFilePointer();
			raf.writeUTF(generateString());
			//System.out.println("Current time: " + System.currentTimeMillis());
			//System.out.println("End time: " + endTime);
			if(System.currentTimeMillis() - startTime >= resetTimerInMillis) {
				raf.seek(0);
			}
		}
		//System.out.println("Der Durchgang ging: " + (endTime - startTime) / 1000 + " Sekunden.");
		isActive = false;
	}

	private String generateString() {
		byte[] array = new byte[32000];
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
	}
}
