
public class Stack<Type> { 
	
	// top of the stack value
	private Type payload;
	// the values 'under' the top of the stack
	private Stack<Type> prev;
	
	// initalizes a stack to empty
	public Stack() {
		payload = null;
		prev = null;
		
	}
	
	// pushes value onto the stack
	public void push(Type val) {
		// initalizes a new Stack that will represent what currently exists on the stack
		Stack<Type> old_top = new Stack<Type>();
		// saves the old top of the stack 
		old_top.payload = payload;
		old_top.prev = prev;
		
		// makes the top of the stack the pushed value and sets what is below it to the 
		// old stack
		this.payload = val;
		this.prev = old_top;
		
	}
	
	// pops value from top of the stack
	public Type pop() {
		// if empty return null;
		if(payload == null) return null;
		
		// remembers the top of stack currently
		Type top_stack = payload;
		
		// replaces current top of stack with the stack directly below it
		payload = prev.payload;
		prev = prev.prev;
		
		// returns the now old top of the stack
		return top_stack;
	}
	
	public boolean isEmpty() {
		return payload == null;
	}
	
	// For debugging visualization
	public String toString() {
		return "\ncurrent: " + payload + "\nPrev is: "+ prev;
	}
	
	

}
