

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class RAMFiller extends Thread {
	
	/*
	 * fillRAMWithHashMap() variables
	 * 
	 */
	@Deprecated
	HashMap<Integer, long[]> bigHM = new HashMap<Integer, long[]>();
	Iterator it = bigHM.entrySet().iterator();
	private int runDurationInMS;
	private int RUN_COUNT;
	private long prevTotal;
	private long prevFree; 
	
	/*
	 * run() variables
	 */
	Runtime rt;
	Vector v = new Vector();
	int index = 0;

	public RAMFiller() {
		
	}

	// ===== Public methods =====
	
	/** Adds a byte-Array of the size x (here 1000000) to the Vector v
	 * every time the while-loop iterates. After that it prints out the 
	 * current free memory of the Java Runtime. The process is repeated until 
	 * all the JVM-allocated memory is used.
	 * 
	 * When the Runtime throws an OutOfMemoryError it gets handled by keeping
	 * the Vector in the memory through referencing. This keeps going until the
	 * application gets terminated.
	 * 
	 * @author Florian Riedlinger
	 * 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Runtime rt;
		Vector v = new Vector();
		int index = 0;
		
		try {
			while (true) {
				byte b[] = new byte[1000000];
				v.add(b);
				rt = Runtime.getRuntime();
				System.out.println("free memory: " + (rt.freeMemory() / 1_000_000) + " MB");
			}
		} catch (OutOfMemoryError e) {
			System.out.println("OutOfMemoryError occured");
			Thread.sleep(3000);
			long longIndex = 0;
			Iterator<Byte> it2 = v.iterator();

			for (int i = 0; i < v.size(); ++i) {
				Object vecEntry = v.elementAt(i);
				++longIndex;
				System.out.println("Keeping reference in memory: " + longIndex);

				if (i == v.size() - 2) {
					i = 0;
				}
			}
		}
	}

	
	@Deprecated
	public void fillRAMWithHashMap() {
		try {
			for (int i = 0; i < RUN_COUNT; ++i) {
				bigHM.put(i, new long[200]);
				long total = rt.totalMemory();
				long free = rt.freeMemory();
				long used = (total - free);
				long diff = (total - prevTotal);
				System.out.println("#" + i + ", Total: " + total + ", Used: " + used + ", Difference: " + free);
				prevTotal = total;
				prevFree = free;
				index = i;
			}
		} catch (OutOfMemoryError e) {
			while (System.currentTimeMillis() < System.currentTimeMillis() + runDurationInMS) {
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
				}
			}
		}
	}
	
	

}
