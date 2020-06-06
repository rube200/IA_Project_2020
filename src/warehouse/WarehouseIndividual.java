package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {
    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);
        for (int i = 0; i < genome.length; i++) {
            boolean flag;
            do {
                flag = false;
                genome[i] = GeneticAlgorithm.random.nextInt(size) + 1;
                for (int j = 0; j < i; j++) {
                    if (genome[i] == genome[j]) {
                        flag = true;
                        break;
                    }
                }
            } while(flag);
        }
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    public static int getShelfPos(int[] genome, int value) {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }

        return -1;
    }

    @Override
    public double computeFitness(boolean parallelWork) {
        fitness = 0;
        List<Request> requests = WarehouseAgentSearch.getRequests();


        if (parallelWork)
        {
            Lock lock = new ReentrantLock();
            requests.parallelStream().forEach(req -> {
                int[] products = req.getRequest();
                int lastIndex = products.length - 1;
                int currentFitness = computeFitnessPerRequest(products, lastIndex);


                lock.lock();
                fitness += currentFitness;
                lock.unlock();
            });
        }
        else
        {
            for (Request request : requests) {
                int[] products = request.getRequest();
                int lastIndex = products.length - 1;
                fitness += computeFitnessPerRequest(products, lastIndex);
            }
        }


        return fitness;
    }

    private int computeFitnessPerRequest(int[] products, int lastIndex)
    {
        int currentFitness = 0;


        for (int i = 0; i <= lastIndex; i++) {
            int product = products[i];
            if (i == 0) {
                currentFitness += distance(WarehouseAgentSearch.getCellAgent(), getShelfCell(product));
            }


            if (i == lastIndex) {
                currentFitness += distance(getShelfCell(product), WarehouseAgentSearch.getExit());
            } else {
                currentFitness += distance(getShelfCell(product), getShelfCell(products[i + 1]));
            }
        }


        return currentFitness;
    }

    public int getProductInShelf(int line, int column) {
        List<Cell> shelves = WarehouseAgentSearch.getShelves();
        for (int i = 0; i < shelves.size(); i++) {
            Cell shelf = shelves.get(i);
            if (shelf.getLine() == line && shelf.getColumn() == column) {
                if (genome[i] > problem.agentSearch.getNumProducts())
                    return 0;

                return genome[i];
            }
        }

        return 0;
    }

    public Cell getShelfCell(int product) {
        return WarehouseAgentSearch.getShelves().get(getShelfPos(genome, product));
    }

    public double distance(Cell cell1, Cell cell2) {
        Pair p = new Pair(cell1, cell2);
        Integer distance = problem.pairs.get(p);
        if (distance != null)
            return distance;


        p = new Pair(cell2, cell1);//reverter pair cause by hash
        distance = problem.pairs.get(p);
        if (distance != null)
            return distance;


        throw new IllegalArgumentException("Invalid cells");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int i = 0; i < genome.length; i++) {
            sb.append(genome[i]).append(" ");
            //this method might require changes
        }

        return sb.toString();
    }

    /**
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(WarehouseIndividual i) {
        return Double.compare(i.getFitness(), this.fitness);
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);
    }
}
