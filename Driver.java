package p2;

import java.util.*;

public class Driver {

	public static void main(String[] args) {

		// Instantiate ArrayList of Operation objects for
		// memory management algorithms
		List<Operation> opList = new ArrayList<Operation>();

		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);

		// Parse through every line of standard input
		while (s.hasNextLine()) {
			String line = s.nextLine();

			// Hit enter to create an extra line and end
			// standard input to the program
			if (line.length() == 0)
				break;

			// Convert text to tokenized numbers in an array
			String[] numstrs = line.split("\\s+");
			int[] nums = new int[numstrs.length];
			for (int i = 0; i < nums.length; i++)
				nums[i] = Integer.parseInt(numstrs[i]);

			int reference = nums[0];
			int operation = nums[1];
			int argument = nums[2];

			// Create a new Process object
			Operation op = new Operation(reference, operation, argument);

			// Place new Process object into the list of all processes
			opList.add(op);

		}

		// Perform First-fit algorithm
		System.out.println("\nFirst-fit simulation:");
		First first = new First(opList);
		first.run();

		// Perform Best-fit algorithm
		System.out.println("\nBest-fit simulation:");
		Best best = new Best(opList);
		best.run();

		// Perform Worst-fit algorithm
		System.out.println("\nWorst-fit simulation:");
		Worst worst = new Worst(opList);
		worst.run();

		System.out.println("");
	}
}
