import java.util.ArrayList;
public class Node
{
    private int depth;
    private IState state;
    private double g;
    private double f;
    private Node parent;
    private String oper;

    public Node( IState state, Node parent, double cost)
    {
        this.state = state;
        this.parent = parent;
        if (parent == null) {
            g = cost;
            depth = 0;
            f = g() + h();
        }
        else {
            g = parent.g() + cost;
            depth = parent.getDepth() + 1;
            f = g() + h();
            if (parent.f() > f)
                f = parent.f();
        }
        this.oper = "";
    }

    public Node( IState state, Node parent, double cost, String oper)
    {
        this( state, parent, cost);
        this.oper = oper;
    }

    public int getDepth()
    {
        return depth;
    }

    public double g()
    {
        return g;
    }

    public double h()
    {
        return state.h();
    }

    public double f()
    {
        return f;
    }

    public Node getParent()
    {
        return parent;
    }

    public IState getState()
    {
        return state;
    }

    public ArrayList<Node> getSuc()
    {
        ArrayList<Node> suc = new ArrayList<Node>();
        ArrayList<Action> s = state.suc(); // successor states of the state
        for (Action r : s) {	// for each successor state create a successor node
            Node n = new Node( r.getState(), this, r.getCost(), r.getDescr());
            suc.add( n);
        }
        return suc;
    }

    public boolean loop( Node n) {
        if (n.getState().equals(state))
            return true;
        Node p = parent;
        while ( p != null) {
            if (n.getState().equals( p.getState())) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    public String toString() {
        return (oper!=null&&!oper.equals("")?("  "+oper):"")+ getState()+"   g= "+g()+"   h= "+String.format("%6.2f",h())+"   f= "+String.format("%6.2f",f())+"   depth= "+getDepth();
    }

    public String repr() {
        return "[" + getState()+"]";
    }

    public void printAncestors() {
        if (parent != null)
            parent.printAncestors();
        System.out.println( this);
    }
}
