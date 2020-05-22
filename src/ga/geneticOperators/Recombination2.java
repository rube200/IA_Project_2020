package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Recombination2<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    public Recombination2(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        int cut2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());

        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }


        int[] child1 = recombineParent(ind1, ind2, cut1, cut2);
        int[] child2 = recombineParent(ind1, ind1, cut1, cut2);


        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }

    private int[] recombineParent(I ind1, I ind2, int cut1, int cut2)
    {
        int[] child = new int[ind1.getNumGenes()];
        for (int i = cut1; i <= cut2; i++)
            child[i] = ind1.getGene(i);


        int currentParentIndex = 0;
        for (int i = cut2 + 1; i != cut1; i++)
        {
            if (i == ind1.getNumGenes())
            {
                if (cut1 == 0)
                    break;

                i = 0;
            }


            boolean duplicated;
            do {
                duplicated = false;
                for (int value : child) {
                    if (ind2.getGene(currentParentIndex) == value) {
                        currentParentIndex++;
                        duplicated = true;
                        break;
                    }
                }
            } while (duplicated);


            child[i] = ind2.getGene(currentParentIndex);
            currentParentIndex++;
        }

        return child;
    }

    @Override
    public String toString() {
        return "OX1";
    }
}