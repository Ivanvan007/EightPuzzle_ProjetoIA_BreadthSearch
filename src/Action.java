public class Action
{
    private IState state;
    private double cost;
    private String descr;

    public Action( IState state, double cost)
    {
        this.state = state;
        this.cost = cost;
        this.descr = "";
    }

    public Action( IState state, double cost, String descr) {
        this.state = state;
        this.cost = cost;
        this.descr = descr;
    }

    public IState getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    public String getDescr() {
        return descr;
    }
}
