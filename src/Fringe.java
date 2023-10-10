import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Fringe
{
    private ArrayList<Node> list;
    private Set<IState> closedStates;
    // alg is an object that knows how to insert new nodes into the fringe list
    // keeping the list ordered according to the strategy
    private IAlgorithm alg;
    // do we want to use a set of closed states (graph search)?
    private static final boolean USE_STATES_SET = true;

    public Fringe(IAlgorithm alg) {
        this.alg = alg;
        list = new ArrayList<Node>();
        if (USE_STATES_SET) {
            closedStates = new HashSet<IState>();
        }
    }

    public void add(Node node) {
        if (USE_STATES_SET) {
            if (!closedStates.contains(node.getState())) {
                alg.insert(list, node);	// depending on the strategy, it inserts the node into a different position
                closedStates.add(node.getState());
            }
        } else {
            alg.insert(list, node);
        }
    }

    public Node getFirst() {
        if (list.isEmpty()) {
            return null;
        } else {
            Node c = list.remove(0);
            if (USE_STATES_SET) {
                closedStates.remove(c.getState());
            }
            return c;
        }
    }

    // does the closed states set contain the state of node n?
    public Node containsState(Node n) {
        if (USE_STATES_SET) {
            if (closedStates.contains(n.getState())) {
                for (Node fno : list) {
                    if (fno.getState().equals(n.getState()))
                        return fno;
                }
            }
        } else {
            for (Node fno : list) {
                if (fno.getState().equals(n.getState()))
                    return fno;
            }
        }
        return null;
    }

    public void remove(Node n) {
        list.remove(n);
        if (USE_STATES_SET) {
            closedStates.remove(n.getState());
        }
    }

    public int getCounter() {
        return list.size();
    }

    public void print() {
        for (Node n : list) {
            System.out.print("  " + n.repr());
        }
        System.out.println();
    }
}
