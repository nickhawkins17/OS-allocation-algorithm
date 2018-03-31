package p2;

import java.util.*;

public class Best {

	List<Operation> operationsList;
	static final int NUM_BYTES = 1024;
	List<Integer> memory = new ArrayList<Integer>();

	public Best(List<Operation> operations) {
		operationsList = operations;
	}

	/*
	 * run implements the Best-fit allocation algorithm
	 */
	public void run() {
		// Set the memory to empty bytes
		for (int ii = 0; ii < 1024; ii++)
			memory.add(0);

		// Iterate through the list of all operations
		for (int ii = 0; ii < operationsList.size(); ii++) {

			// Set the current operation for convenience
			Operation current = operationsList.get(ii);

			// Allocate
			if (current.getOp() == 1) {

				// Call to findIndex method returns the starting index
				int startIndex = findIndex(memory, current.getArg());

				// There is space for memory allocation
				if (startIndex != -1) {

					// Allocate memory with the operation
					for (int jj = startIndex; jj < current.getArg() + startIndex; jj++) {
						memory.set(jj, current.getRef());
					}
				}
				// There is not enough space for memory allocation
				else {

					// Calculate external fragmentation (empty bytes of memory)
					int externalFragmentation = 0;
					for (int kk = 0; kk < NUM_BYTES; kk++) {
						if (memory.get(kk) == 0)
							externalFragmentation++;
					}

					// Output statistics of operation failure
					System.out.println("Failed.");
					System.out.print("Request " + current.getRef() + " failed trying to allocate ");
					System.out.println(current.getArg() + " bytes.");
					System.out.println("External Fragmentation is " + externalFragmentation + " bytes.");

					return;

				}
			}
			// De-allocate
			else if (current.getOp() == 2) {

				// Iterate through memory and de-allocate the bytes
				// of the argument by setting them to 0
				for (int jj = 0; jj < NUM_BYTES; jj++) {
					if (memory.get(jj) == current.getArg())
						memory.set(jj, 0);
				}
			}
		}

		// Memory operation was successful
		System.out.println("Success.");
	}

	/*
	 * findIndex takes in a list of integers and an integer arg, which is the
	 * number of bytes of memory being requested for allocation. findIndex finds
	 * the smallest gap of empty memory that would fit the length of the arg,
	 * and returns the first index of that empty memory.
	 */
	public static int findIndex(List<Integer> mem, int arg) {

		boolean good = true;
		int index = 0;
		int count = 0;
		int lowestCount = 1025;
		int lowestCountIndex = 0;
		int tempLowestCountIndex = 0;
		boolean found = false;

		while (index < NUM_BYTES) {

			// If the byte is not allocated, continue by
			// counting the following contiguous empty bytes
			if (mem.get(index) == 0) {
				good = true;
				count = 0;
				tempLowestCountIndex = index;

				// Count the number of contiguous empty bytes
				while (good && index < NUM_BYTES) {
					if (mem.get(index++) != 0) {
						good = false;
						break;
					}
					count++;
				}

				// If the current stretch of non-allocated memory
				// is smaller than the current smallest hole but
				// still big enough to allocate the operation, this
				// is now currently the smallest hole found.
				if ((count < lowestCount) && (count >= arg)) {
					found = true;
					lowestCount = count;
					lowestCountIndex = tempLowestCountIndex;
				}
			}

			// Increment index to continue search for memory
			index++;

		}

		// If a hole was found, return the index of its first byte
		if (found)
			return lowestCountIndex;

		// A hole at least the size of arg was not found
		return -1;

	}

}
