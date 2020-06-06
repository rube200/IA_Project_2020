package ga.selectionMethods;

import ga.GeneticAlgorithm;
import ga.Individual;
import ga.Population;
import ga.Problem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Tournament <I extends Individual, P extends Problem<I>> extends SelectionMethod<I, P> {

    private boolean parallelWork;
    private int size;

    public Tournament(int popSize, boolean parallelWork) {
        this(popSize, 2, parallelWork);
    }

    public Tournament(int popSize, int size, boolean parallelWork) {
        super(popSize);
        this.parallelWork = parallelWork;
        this.size = size;
    }

    @Override
    public Population<I, P> run(Population<I, P> original) {
        Population<I, P> result = new Population<>(original.getSize());


        if (parallelWork)
        {
            int randomLengt = popSize * size;
            int[] myRandom = new int[randomLengt];
            for (int i = 0; i < popSize * size; i++)
                myRandom[i] = GeneticAlgorithm.random.nextInt(popSize);


            IntStream.range(0, popSize).parallel().forEach(i -> result.addIndividual(i, tournament(original, i, myRandom)));
        }
        else
        {
            for (int i = 0; i < popSize; i++) {
                result.addIndividual(i, tournament(original));
            }
        }


        return result;
    }

    private I tournament(Population<I, P> population) {
        I best = population.getIndividual(GeneticAlgorithm.random.nextInt(popSize));


        for (int i = 1; i < size; i++) {
            I aux = population.getIndividual(GeneticAlgorithm.random.nextInt(popSize));
            if (aux.compareTo(best) > 0) { //if aux is BETTER than best
                best = aux;
            }
        }


        return (I) best.clone();
    }

    private I tournament(Population<I, P> population, int index, int[] myRandom) {
        int initialRandomIndex = index * size;
        I best = population.getIndividual(myRandom[initialRandomIndex]);


        for (int i = 1; i < size; i++) {
            I aux = population.getIndividual(myRandom[initialRandomIndex + i]);
            if (aux.compareTo(best) > 0) { //if aux is BETTER than best
                best = aux;
            }
        }


        return (I) best.clone();
    }
    
    @Override
    public String toString(){
        return "Tournament(" + size + ")";
    }    
}