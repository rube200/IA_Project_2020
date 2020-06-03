package ga;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class Population<I extends Individual, P extends Problem<I>>{

    private final Individual[] individuals;
    private I best;

    public Population(int size) {
        individuals = new Individual[size];
    }

    public Population(int size, P problem) {
        individuals = new Individual[size];
        for (int i = 0; i < size; i++) {
            individuals[i] = problem.getNewIndividual();
        }
    }

    public I evaluate() {
        Lock lock = new ReentrantLock();
        best = getIndividual(0);
        IntStream.range(0, individuals.length).parallel().forEach(i ->
        {
            individuals[i].computeFitness();
            lock.lock();
            if (individuals[i].compareTo(best) > 0) {
                best = (I)individuals[i];
            }
            lock.unlock();
        });
        return best;
    }

    public double getAverageFitness() {
        double fitnessSum = 0;
        for (Individual individual : individuals) {
            fitnessSum += individual.getFitness();
        }
        return fitnessSum / individuals.length;
    }

    public void replaceWorst(I substitute) {
        int i = 0, worstIndex = 0;
        I worst = getIndividual(0);
        for (Individual individual : individuals) {
            if (individual.compareTo(worst) < 0) {
                worstIndex = i;
                worst = (I)individual;
            }
            i++;
        }
        individuals[worstIndex] = substitute;
    }

    public void addIndividual(int index, I individual) {
        individuals[index] = individual;
    }

    public I getIndividual(int index) {
        return (I)individuals[index];
    }

    public I getBest() {
        return best;
    }

    public int getSize() {
        return individuals.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Individual individual : individuals) {
            sb.append(individual);
        }
        return sb.toString();
    }
}
