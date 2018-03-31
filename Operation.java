package p2;

public class Operation {

	int reference; // constant
	int operation; // constant
	int argument; // constant

	// Operation object constructor
	public Operation(int reference, int operation, int argument) {
		this.reference = reference;
		this.operation = operation;
		this.argument = argument;

	}

	// Get reference
	public int getRef() {
		return reference;
	}

	// Get operation
	public int getOp() {
		return operation;
	}

	// Get argument
	public int getArg() {
		return argument;
	}

}
