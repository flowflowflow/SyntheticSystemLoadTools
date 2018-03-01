
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.util.Random;

public class IOMultiThreadedWorkload extends Thread {

	final String fs = File.separator;
	
	int x = 1;
	long durationInMilliSeconds = 0;
	long elapsedTimeInSeconds = 0;
	long startTime = System.currentTimeMillis();
	boolean isActive = false;
	RandomAccessFile raf;
	String tempPath = new String(System.getProperty("java.io.tmpdir"));
	String defaultPath = tempPath;
	String fileName = "dummyfile_" + x + ".txt";
	String pathAndFileName = tempPath + fs + fileName;
	String mode;
	
	
	
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
			setMode("rw");
			setFileName("dummyfile");
			setDurationInSeconds(durationInSeconds);
			isActive = true;
			System.out.println("Creating and filling file now: " + this.pathAndFileName);
			raf = new RandomAccessFile(pathAndFileName, mode);
		} catch (IOException ioE) {
			System.err.println(ioE);
			System.exit(1);
		}
	}

	public IOMultiThreadedWorkload(String fileName, int x, long durationInSeconds) throws IOException {
		try {
			setX(x + 1);
			setMode("rw");
			setFileName(fileName);
			setDurationInSeconds(durationInSeconds);
			isActive = true;
			System.out.println("Creating and filling file now: " + this.pathAndFileName);
			raf = new RandomAccessFile(pathAndFileName, mode);
		} catch (IOException ioE) {
			System.err.println(ioE);
			System.exit(1);
		}
	}

	/*
	 * Set- & Get-Methods
	 */
	
	//Elapsed time
	public long getElapsedTime() {
		long endTime = System.currentTimeMillis();
		elapsedTimeInSeconds = (endTime - startTime) / 1000;
		return elapsedTimeInSeconds;
	}
	
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
			System.out.println("Fehlerhafte Durchlaufszeitangabe");
			System.out.println("Setze Durchlaufszeit auf 10 Minuten");
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
			this.pathAndFileName = tempPath + fileName;
		} else {
			this.pathAndFileName = defaultPath + fileName;
		}
	}

	public String getPathAndFileName() {
		return pathAndFileName;
	}
	
	public void progressDots() {
		while(isActive) {
			try {
			System.out.print(".");
			Thread.sleep(500);
			System.out.print(".");
			Thread.sleep(500);
			System.out.print(".");
			Thread.sleep(500);
			System.out.print("\b\b\b");
			System.out.print("   ");
			System.out.print("\b\b\b");
			} catch(InterruptedException iex) {
				System.err.println(iex);
				System.exit(1);
			}
		}
	}
	
	// === Private methods ===

	private void writeIntoFile() throws IOException {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + durationInMilliSeconds;
		long counter = 0;
		
		while(System.currentTimeMillis() < endTime) {
			raf.getFilePointer();
			raf.writeUTF(generateString());
			counter++;
			if(counter > 2000) {
				raf.seek(0);
				counter = 0;
			}
		}
		raf.close();
		isActive = false;
		deleteGeneratedFiles();	
		//System.out.println("Der Durchgang ging: " + (endTime - startTime) / 1000 + " Sekunden.");
	}

	private String generateString() {
		byte[] array = new byte[32000];
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		return generatedString;
	}
	
	
	//Not yet working
	private void deleteGeneratedFiles() {
		try {
			Files.deleteIfExists(Paths.get(pathAndFileName, ""));
		} catch (IOException ioe) {
			System.err.println(ioe);
			System.exit(1);
		}
	}
}
