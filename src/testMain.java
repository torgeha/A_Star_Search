import java.util.ArrayList;


public class testMain {
	
	public static void easyRushHour(int searchType) {
		
		RushHourState initialState = new RushHourState();
		initialState.addCar(0, 2, 2, 2);
		initialState.addCar(0, 0, 4, 3);
		initialState.addCar(0, 3, 4, 2);
		initialState.addCar(0, 4, 1, 2);
		initialState.addCar(1, 2, 0, 2);
		initialState.addCar(1, 4, 2, 2);
		
		StateNode initial = new StateNode(initialState, null);
		
		StateNode result = Search.search(initial, searchType);
		
		printResult(result);
	}
	
	public static void mediumRushHour(int searchType) {
		RushHourState initialState = new RushHourState();
		initialState.addCar(0, 1, 2, 2);
		initialState.addCar(0, 0, 5, 3);
		initialState.addCar(0, 1, 3, 2);
		initialState.addCar(0, 3, 0, 2);
		initialState.addCar(1, 0, 2, 3);
		initialState.addCar(1, 2, 0, 2);
		initialState.addCar(1, 3, 1, 2);
		initialState.addCar(1, 3, 3, 3);
		initialState.addCar(1, 4, 2, 2);
		initialState.addCar(1, 5, 0, 2);
		initialState.addCar(1, 5, 2, 2);
		
		StateNode initial = new StateNode(initialState, null);
		
		StateNode result = Search.search(initial, searchType);
		
		printResult(result);
	}
	
	public static void hardRushHour(int searchType) {
		RushHourState initialState = new RushHourState();
		initialState.addCar(0, 2, 2, 2);
		initialState.addCar(0, 0, 4, 2);
		initialState.addCar(0, 0, 5, 2);
		initialState.addCar(0, 2, 5, 2);
		initialState.addCar(0, 4, 0, 2);
		initialState.addCar(1, 0, 0, 3);
		initialState.addCar(1, 1, 1, 3);
		initialState.addCar(1, 2, 0, 2);
		initialState.addCar(1, 3, 0, 2);
		initialState.addCar(1, 4, 2, 2);
		initialState.addCar(1, 4, 4, 2);
		initialState.addCar(1, 5, 3, 3);
		
		StateNode initial = new StateNode(initialState, null);
		
		StateNode result = Search.search(initial, searchType);
		
		printResult(result);
	}
	
	public static void expertRushHour(int searchType) {
		RushHourState initialState = new RushHourState();
		initialState.addCar(0, 0, 2, 2);
		initialState.addCar(0, 0, 1, 3);
		initialState.addCar(0, 0, 5, 2);
		initialState.addCar(0, 1, 0, 2);
		initialState.addCar(0, 2, 3, 2);
		initialState.addCar(0, 3, 4, 2);
		initialState.addCar(1, 0, 3, 2);
		initialState.addCar(1, 2, 4, 2);
		initialState.addCar(1, 3, 0, 3);
		initialState.addCar(1, 4, 0, 2);
		initialState.addCar(1, 4, 2, 2);
		initialState.addCar(1, 5, 2, 2);
		initialState.addCar(1, 5, 4, 2);
		
		StateNode initial = new StateNode(initialState, null);
		
		StateNode result = Search.search(initial, searchType);
		
		printResult(result);
	}
	
	public static void printResult(StateNode result) {
		StateNode tempRes = result;
		if (result == null) {
			System.out.println("FAIL");
		}
		else {
			while (result.getParent() != null) {
				System.out.println(result.getParent());
				System.out.println("G: " + result.getParent().getG());
				System.out.println("H: " + result.getParent().getH());
				System.out.println("F: " + result.getParent().getF());
				result = result.getParent();
				System.out.println("----------------------");
			}
			System.out.println("#############");
			System.out.println(tempRes);
			System.out.println("G: " + tempRes.getG());
			System.out.println("H: " + tempRes.getH());
			System.out.println("F: " + tempRes.getF());
			System.out.println("Expanded nodes: " + Search.expandedNodes);
			
		}
	}
	
	//Insert the puzzle instance to be tested and specify which search
	public static void main(String[] args) {
		/*
		 * Supported arguments:
		 * OpenList.A_STAR_SEARCH
		 * OpenList.DEPTH_FIRST_SEARCH
		 * OpenList.BREDTH_FIRST_SEARCH
		 */
		expertRushHour(OpenList.A_STAR_SEARCH);
	}

}
