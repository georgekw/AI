package assignment4;

public abstract class MinimalConflicts {
	
	public abstract boolean isSolution();//Checks whether the csp is a solution. 
	public abstract Object minimumConflicts();//Minimize conflicts.
	public abstract Object getRandomConflictedVariable();//Randomly chosen conflicted variable of the csp.
	public abstract void setNewValue(Object value, Object variable);//Update the value of the variable.

	//Returns a solution or failure.
	public Board minConflicts(Board board, int maxSteps) {
		Board current = board;
		for (int i = 0; i < maxSteps; i++) {
			if (current.isSolution()) {
				return current;
			}
			else {
				Object variable = board.getRandomConflictedVariable();
				Object value = board.minimumConflicts();
				board.setNewValue(value, variable);
			}
		}
		return board;
	}
	
}
