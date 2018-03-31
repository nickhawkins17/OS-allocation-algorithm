package p2;

import java.util.*;

public class First {

	List<Operation> operationsList;
	static final int NUM_BYTES = 1024;
	List<Integer> memory = new ArrayList<Integer>();

	public First(List<Operation> operations) {
		operationsList = operations;
	}

	/*
	 * run implements the First-fit allocation algorithm
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
		System.out.println(memory.toString());

	}

	/*
	 * findIndex takes in a list of integers and an integer arg, which is the
	 * number of bytes of memory being requested for allocation. findIndex finds
	 * the first gap of empty memory at least the length of the arg, and returns
	 * the beginning index of that empty memory in the list of integers.
	 */
	public static int findIndex(List<Integer> mem, int arg) {

		boolean good = true;
		int index = 0;

		// Iterate through the memory ArrayList
		for (int ii = 0; ii < NUM_BYTES - arg + 1; ii++) {

			// Determine if the memory is empty at this index
			if (mem.get(ii) == 0) {
				good = true;
				index = ii;

				// Determine if the next arg number of
				// indexes are empty
				for (int jj = ii; jj < arg + ii; jj++) {
					if (mem.get(jj) != 0) {
						good = false;
					}
				}

				// If a hole at least the size of arg
				// is found, we're done.
				if (good) {
					return index;
				}
			}
		}

		// A hole at least the size of arg was not found
		return -1;

	}

}
