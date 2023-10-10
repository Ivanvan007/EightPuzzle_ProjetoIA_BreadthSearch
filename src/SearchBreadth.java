import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class SearchBreadth
{
    private Fringe f;
    private Set<IState> closedStates;
    private int nodeCounter;

    public SearchBreadth(IState i)
    {
        closedStates = new HashSet<IState>();
        f = new Fringe(new BreadthFirst());
        f.add(new Node(i, null, 0));
        nodeCounter = 0;
    }

    public Fringe getFringe()
    {
        return f;
    }

    public int getNodeCounter()
    {
        return nodeCounter;
    }

    public Node solve()
    {
        Node node = f.getFirst();
        while (node != null)
        {
            ArrayList<Node> successors = node.getSuc();
            closedStates.add(node.getState());
            for (Node nodeSuc : successors)
            {
                if (nodeSuc.getState().goal())
                {
                    return nodeSuc;
                }
                if (closedStates.contains(nodeSuc.getState()))
                {
                    continue;
                }
                Node copia = f.containsState(nodeSuc);
                if (copia == null)
                {
                    // Calcula o custo usando a função h() da classe EightPuzzle
                    double custo = nodeSuc.getState().h();
                    // Adiciona o novo nó com base no estado anterior e no custo
                    f.add(new Node(nodeSuc.getState(), node, custo));
                }

            }
            node = f.getFirst();
            // statistics
            nodeCounter++;
            if (nodeCounter % 1000 == 0) {  //every 1000 nodes print the situation
                System.out.println(node);
                System.out.println(" expanded nodes: " + String.format("%1$,10d", nodeCounter) + " fringe: "
                        + String.format("%1$,5d", f.getCounter()));
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        // SearchBreadth p = new SearchBreadth(Baldes49.getInitialState());
//		 SearchBreadth p = new SearchBreadth(Baldes34.getInitialState());
//		 SearchBreadth p = new SearchBreadth(MissCan.getInitialState());
//		 SearchBreadth p = new SearchBreadth(EightPuzzle.getInitialState());
//		SearchBreadth p = new SearchBreadth(LightsOff.getInitialState());
        // SearchBreadth p = new SearchBreadth(PuzzleSix.getInitialState());
        // SearchBreadth p = new SearchBreadth(MouseCheese.getInitialState());
        // SearchBreadth p = new SearchBreadth(ND6.getInitialState());
        // SearchBreadth p = new SearchBreadth(Solitario.getInitialState());

        SearchBreadth p = new SearchBreadth(EightPuzzle.getInitialState());
        Calendar c = Calendar.getInstance();
        long t = c.getTimeInMillis();
        System.out.println("#########################################################");
        Node no = p.solve();
        System.out.println("===========================");
        if (no != null) {
            no.printAncestors();
        } else {
            System.out.println("No solution");
        }
        System.out.println("        expanded nodes: " + String.format("%1$,d", p.getNodeCounter()) + "    fringe: "
                + String.format("%1$,d", p.getFringe().getCounter()));
        System.out.println("~~~~~~~~ END ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        c = Calendar.getInstance();
        System.out.println("Time: " + (c.getTimeInMillis() - t) + " ms");
    }

    private class BreadthFirst implements IAlgorithm
    {
        public void insert(List<Node> lista, Node no)
        {
            lista.add(no);
        }
    }
}
