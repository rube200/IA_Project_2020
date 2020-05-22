package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.IntVectorIndividual;
import ga.Problem;
import java.util.Arrays;

public class Recombination3<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {
    public Recombination3(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        boolean startWithParent1 = GeneticAlgorithm.random.nextBoolean();
        int[] child1 = recombineParent(ind1, ind2, startWithParent1);
        int[] child2 = recombineParent(ind1, ind1, !startWithParent1);
        System.out.println("end1 " + Arrays.toString(child1));
        System.out.println("end1 " + Arrays.toString(child2));

        for (int i = 0; i < ind1.getNumGenes(); i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }


    private int[] recombineParent(I ind1, I ind2, boolean startWithParent1)
    {
        int lengt = ind1.getNumGenes();
        int[] child = new int[lengt];


        int currentIndex = 0;
        child[currentIndex] = startWithParent1 ? ind1.getGene(currentIndex) : ind2.getGene(currentIndex);
        int value = !startWithParent1 ? ind1.getGene(currentIndex) : ind2.getGene(currentIndex);


        boolean alreadyExists;
        do {
            for (int i = 0; i < lengt; i++)
            {
                int gene = startWithParent1 ? ind1.getGene(i) : ind2.getGene(i);
                if (value == gene)
                {
                    currentIndex = i;
                    value = !startWithParent1 ? ind1.getGene(i) : ind2.getGene(i);
                    break;
                }
            }


            alreadyExists = false;
            for (int i = 0; i < lengt; i++)
            {
                if (child[i] == value)
                {
                    alreadyExists = true;
                    break;
                }
            }


            if (alreadyExists)
                break;


            child[currentIndex] = startWithParent1 ? ind1.getGene(currentIndex) : ind2.getGene(currentIndex);
        } while (true);


        int parentIndex = 0;
        for (int i = 0; i < lengt; i++)
        {
            if (child[i] != 0)
                continue;


            for (; parentIndex < lengt; parentIndex++)
            {
                boolean duplicated = false;
                value = !startWithParent1 ? ind1.getGene(parentIndex) : ind2.getGene(parentIndex);
                for (int j = 0; j < lengt; j++)
                {
                    if (child[j] == value) {
                        duplicated = true;
                        break;
                    }
                }


                if (!duplicated)
                    break;
            }


            child[i] = value;
        }


        return child;
    }


    @Override
    public String toString() {
        return "CX";
    }
}