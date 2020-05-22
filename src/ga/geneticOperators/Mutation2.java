package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.util.Arrays;

public class Mutation2<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation2(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());

        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }


        do {
            permute(ind, cut1, cut2);
            cut1++;
            cut2--;
        } while (cut1 < cut2);
    }

    private void permute(I ind, int cut1, int cut2) {
        int aux = ind.getGene(cut2);
        ind.setGene(cut2, ind.getGene(cut1));
        ind.setGene(cut1, aux);
    }


    @Override
    public String toString() {
        return "RSM";
    }
}