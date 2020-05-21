package warehouse;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    //TODO this class might require the definition of additional methods and/or attributes


    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);
        for (int i = 0; i < genome.length; i++) {
            //algoritmo para n repetir valores (produtos) no genoma
            int continuar;
            do {
                continuar = 1;
                genome[i] = GeneticAlgorithm.random.nextInt(size) + 1;
                for (int j = 0; j < i; j++) {
                    if(genome[i] == genome[j]){
                        continuar = 0;
                    }
                }
            }while(continuar == 0);

        }
        System.out.println(Arrays.toString(genome));
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        Cell cell1, cell2;
        fitness = 0;
        problem.getAgentSearch().getPairs();
        for (Request request : problem.getAgentSearch().getRequests()) {
            for (int i = 0; i < request.getRequest().length; i++) {
                if (i==0){
                    cell1 = problem.getAgentSearch().getCellAgent();
                    cell2 = problem.getAgentSearch().getShelves().get(getShelfPos(genome, request.getRequest()[i]));
                    fitness += distance(cell1, cell2);
                }
                if (i==request.getRequest().length-1){
                    cell1 = problem.getAgentSearch().getExit();
                    cell2 = problem.getAgentSearch().getShelves().get(getShelfPos(genome, request.getRequest()[i]));
                    fitness += distance(cell1, cell2);
                }else{
                    cell1 = problem.getAgentSearch().getShelves().get(getShelfPos(genome, request.getRequest()[i]));
                    cell2 = problem.getAgentSearch().getShelves().get(getShelfPos(genome, request.getRequest()[i+1]));
                    fitness += distance(cell1, cell2);
                }
            }

        }
        return fitness;
    }

    public double distance(Cell cell1, Cell cell2) {
        LinkedList Pairs = problem.getAgentSearch().getPairs();
        for (Object obj : Pairs) {
            if (!(obj instanceof Pair))
                continue;

            Pair pair = (Pair) obj;
            if (cell1.equals(pair.getCell1()) && cell2.equals(pair.getCell2()))
                return pair.getValue();


            if (cell1.equals(pair.getCell2()) && cell2.equals(pair.getCell1()))
                return pair.getValue();
        }

        throw new IllegalArgumentException("Invalid cells");
    }


    public static int getShelfPos(int[] genome, int value) {
        for (int i = 0; i < genome.length; i++) {
            if(genome[i] == value){
                return i;
            }
        }
        return -1;
    }

    //Return the product Id if the shelf in cell [line, column] has some product and 0 otherwise
    public int getProductInShelf(int line, int column){
        for (int i = 0; i < WarehouseAgentSearch.getShelves().size(); i++) {
            if(WarehouseAgentSearch.getShelves().get(i).getLine() == line && WarehouseAgentSearch.getShelves().get(i).getColumn()== column && genome[i] > 0){
                return genome[i];
            }
        }
        return 0;
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
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);

    }
}
