package warehouse;

import ga.Problem;

import java.util.HashMap;


public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {
    protected HashMap<Pair, Integer> pairs;
    protected WarehouseAgentSearch agentSearch;
    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.agentSearch = agentSearch;
        this.pairs = new HashMap<>();
        for (Object o : agentSearch.getPairs()) {
            if (!(o instanceof Pair))
                continue;

            Pair pair = (Pair)o;
            pairs.put(pair, pair.getValue());
        }
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, WarehouseAgentSearch.getShelves().size());
    }

    @Override
    public String toString() {
        return "agentSearch: " + agentSearch.toString();
    }
}
