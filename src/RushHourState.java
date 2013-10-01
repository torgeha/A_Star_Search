import java.util.ArrayList;

/*
 * Specialization for the Rush Hour puzzle
 */
public class RushHourState implements State{
	
	//constants used by the RushHourState, improves readability
	private static final int BOARDSIZE = 6;
	private static final int GOAL_COORDINATE = 4;
	private static final int GOAL_X = 5;
	private static final int GOAL_Y = 2;
	private static final int CAR_X = 1;
	private static final int CAR_Y = 2;
	private static final int CAR_ORIENTATION = 0;
	private static final int CAR_HORIZONTAL = 0;
	private static final int CAR_VERTICAL = 1;
	private static final int CAR_SIZE = 3;
	private static final int PATH_COST = 1;
	
	//Two representations of the puzzle state
	private boolean[][] legalMoves;
	private ArrayList<int[]> cars = new ArrayList<int[]>();
	
	//To calculate the heuristic function in A* search, the distance for car 0 from the goal + # of cars blocking the way is used.
	public int calculateHeuristic() {
		int heuristic = 0;
		int[] car0 = cars.get(0);
		int car0Pos = car0[CAR_X];
		heuristic += GOAL_COORDINATE - car0Pos;
		boolean[][] legalMoves = getLegalMoves();
		
		for (int x = car0Pos + 1; x < BOARDSIZE; x++) {
			if (legalMoves[x][GOAL_Y]) {
				heuristic ++;
			}
		}
		return heuristic;
	}
	
	public boolean isSolution() {
		int car0Pos = this.getCar(0)[CAR_X];
		if (car0Pos == GOAL_COORDINATE) {
			return true;
		}
		return false;
	}
	
	//Generates a 2d boolean array of the board from the car tuples. False means empty tile, true means occupied. 
	private boolean[][] getLegalMoves() {
		if(legalMoves == null) {
			legalMoves = new boolean[BOARDSIZE][BOARDSIZE];
			
			for(int i = 0; i < getCarsSize(); i++) {
				int[] car = this.getCar(i);
				legalMoves[car[CAR_X]][car[CAR_Y]] = true;
				
				if(car[CAR_ORIENTATION] == CAR_HORIZONTAL) {
					legalMoves[car[CAR_X ] + 1][car[CAR_Y]] = true;
					
					if(car[CAR_SIZE] == 3) {
						legalMoves[car[CAR_X ] + 2][car[CAR_Y]] = true;
					}
				}
				else if(car[CAR_ORIENTATION] == CAR_VERTICAL) {
					legalMoves[car[CAR_X]][car[CAR_Y] + 1] = true;
					
					if(car[CAR_SIZE] == 3) {
						legalMoves[car[CAR_X]][car[CAR_Y] + 2] = true;
					}
				}
			}
		}
		return legalMoves;
	}
	
	//Help method used by getSuccessors to generate a successor based on the parent state and directions provided.
	//It moves the car specified by carPos to the new coordinates.
	private static RushHourState generateSuccessor(RushHourState state, int carPos, int xNew, int yNew) {
		RushHourState newState = new RushHourState();
		for (int i = 0; i < state.getCarsSize(); i++) {
			if (i == carPos) {
				int[] car = state.getCar(i);
				newState.addCar(car[CAR_ORIENTATION], xNew, yNew, car[CAR_SIZE]);
			}
			else {
				int[] car = state.getCar(i);
				newState.addCar(car[CAR_ORIENTATION], car[CAR_X], car[CAR_Y], car[CAR_SIZE]);
			}
		}
		return newState;
	}
	
	//checks the boolean array at the specified coordinates to check if tile is occupied or moves outside the board.
	private static boolean isValidMove(boolean[][] legalMoves, int x, int y) {
		if (x < BOARDSIZE && x >= 0 && y < BOARDSIZE && y >=0) {
			if (!legalMoves[x][y]) {
				return true;
			}
		}
		return false;
	}
	
	//Returns a list with all possible successors to the parent, which is all states where the step cost has increased by one.
	//It loops through all cars, trying to move it in all directions based on its orientation. All valid moves translates to a successor state.
	public ArrayList<StateNode> getSuccessors(StateNode parent) {
		ArrayList<StateNode> successorList = new ArrayList<StateNode>();
		
		boolean[][] legalMoves = this.getLegalMoves();
		
		for(int i = 0; i < getCarsSize(); i ++) {
			int[] car = this.getCar(i);
				//horizontal check
				if (car[CAR_ORIENTATION] == CAR_HORIZONTAL) {
					//check left
					if(isValidMove(legalMoves, car[CAR_X] - 1, car[CAR_Y])) {
						StateNode successor = new StateNode(generateSuccessor(this, i, car[CAR_X ] - 1, car[CAR_Y]), parent);
						successorList.add(successor);
					}
					//check right
					if(isValidMove(legalMoves, car[CAR_X] + car[CAR_SIZE], car[CAR_Y])) {
						StateNode successor = new StateNode(generateSuccessor(this, i, car[CAR_X ] + 1, car[CAR_Y]), parent);
						successorList.add(successor);
					}
				}
				//vertical check
				else if (car[CAR_ORIENTATION] == CAR_VERTICAL) {
					//check up
					if(isValidMove(legalMoves, car[CAR_X], car[CAR_Y] - 1)) {
						StateNode successor = new StateNode(generateSuccessor(this, i, car[CAR_X ], car[CAR_Y]- 1), parent);
						successorList.add(successor);
					}
					//check down
					if(isValidMove(legalMoves, car[CAR_X], car[CAR_Y] + car[CAR_SIZE])) {
						StateNode successor = new StateNode(generateSuccessor(this, i, car[CAR_X ], car[CAR_Y] + 1), parent);
						successorList.add(successor);
					}
				}
		}
		return successorList;
	}
	
	//Uses coordinates to calculate a unique String ID for every State.
	public String calculateId() {
		String temp = "";
		for (int[] car : cars) {
			temp += car[1] + "" +  car[2] + "";
		}
		return temp;
	}
	
	public void addCar(int orientation, int x, int y, int size) {
		int[] car = {orientation, x, y, size};
		cars.add(car);
	}
	
	public int[] getCar(int i) {
		if (cars.get(i) != null) {
			return cars.get(i);
		}
		return null;
	}
	
	public int getCarsSize() {
		return cars.size();
	}
	
	//for output purposes
	public String toString() {
		String returnString = "";
		
		int[][] cars = new int[BOARDSIZE][BOARDSIZE];
		for(int t = 0; t<BOARDSIZE;t++) {
			for(int r=0;r<BOARDSIZE;r++) {
				cars[r][t] = -1;
			}
		}
		for (int i = 0; i < getCarsSize(); i++) {
			
			int[] car = getCar(i);
			if(car[CAR_ORIENTATION] == CAR_HORIZONTAL) {
				for(int x = car[CAR_X]; x<(car[CAR_SIZE]+car[CAR_X]); x++) {
					cars[x][car[CAR_Y]] = i;
				}
			}
			else{
				for(int y = car[CAR_Y]; y<(car[CAR_SIZE]+car[CAR_Y]); y++) {
					cars[car[CAR_X]][y] = i;
				}
			}
		}
		
		for(int i = 0; i < BOARDSIZE; i++) {
			for (int j = 0; j < BOARDSIZE; j++) {
				if (cars[j][i] == -1) {
					returnString += "#" + "\t";
				}
				else {
					returnString += cars[j][i] + "\t";
				}
			}
			returnString += "\n";
		}
		
		
		return returnString;
	}

	public int getPathCost() {
		return PATH_COST;
	}

}
