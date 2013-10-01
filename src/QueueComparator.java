import java.util.Comparator;

/*
 * Used by the PriorityQueue to know how to order the states.
 */
public class QueueComparator implements Comparator<StateNode>{

	@Override
	public int compare(StateNode o1, StateNode o2) {
		// TODO Auto-generated method stub
		return o1.getF() - o2.getF();
	}

}
