package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

public class Mutation3<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation3(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        for (int i = 0; i < ind.getNumGenes(); i++)
        {
            float prob = GeneticAlgorithm.random.nextFloat();
            if (prob >= probability)
            {
                continue;
            }


            int randomIndex = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
            permute(ind, i, randomIndex);
        }
    }

    private void permute(I ind, int cut1, int cut2) {
        int aux = ind.getGene(cut2);
        ind.setGene(cut2, ind.getGene(cut1));
        ind.setGene(cut1, aux);
    }

    @Override
    public String toString() {
        return "PSM";
    }
}