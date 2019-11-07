/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie2.geneticAlgo;

import com.bases.satclasses.Sat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hassaine
 */
public class Population {

    private ArrayList<DNA> population;
    private float crossOverRate;
    private float mutationRate;
    private int target;
    private int generation;

    public Population(int populationSize, int solutionSize,int target ,float CR,float MR) {
        this.crossOverRate = CR;
        this.population = new ArrayList<>();
        this.generation = 0;
        this.mutationRate=MR;
        this.target=target;
        for (int i = 0; i < populationSize; i++) {
            this.population.add(new DNA(solutionSize));

        }
    }

    public void evaluate(Sat satInstance) {

        for (DNA dna : population) {
            dna.calculateFittness(satInstance);

        }
    }

    public void naturelSelection() {
        this.population.sort((o1, o2) -> {

            return o2.getFitness() - o1.getFitness();
        });
    }

    public void generate() {
        int subpopSize = (int) (crossOverRate * this.population.size());

        int lastIndex = this.population.size() - 1;
   
        for (int i = 0; i < subpopSize; i++) {
            int categorie = ThreadLocalRandom.current().nextInt(0, 3);
            int firstParent = 0;
            int secondParent = 0;
            if (categorie > 0) {
                firstParent = ThreadLocalRandom.current().nextInt(0, subpopSize );
                secondParent = ThreadLocalRandom.current().nextInt(0, subpopSize );
            } else if (categorie == 0) {

                firstParent = ThreadLocalRandom.current().nextInt(subpopSize , population.size());
                secondParent = ThreadLocalRandom.current().nextInt(subpopSize , population.size());
            }

            DNA child = this.population.get(firstParent).crossover(this.population.get(secondParent));
            this.population.remove(lastIndex);
            this.population.add(lastIndex, child);
            lastIndex--;         
        }
        this.generation++;
    }

    public int getGeneration() {
        return this.generation;
    }

    public void mutation() {
        int subpopSize = (int) (this.mutationRate * this.population.size());
        int maskArray[] = new int[this.population.size()];
        Arrays.fill(maskArray, 0);

        for (int i = 0; i < subpopSize; i++) {

            int mutateElement = ThreadLocalRandom.current().nextInt(0, this.population.size());
            while (maskArray[mutateElement] == 1) {
                mutateElement = ThreadLocalRandom.current().nextInt(0, this.population.size());

            }
            maskArray[mutateElement] = 1;
            this.population.get(mutateElement).mutate();

        }

    }

    public DNA getBest() {
        return this.population.get(0);
    }

    public boolean isFinished() {
        return this.getBest().getFitness() == this.target;

    }

    public int getPopulationSize() {
        return this.population.size();
    }
    
    public String getPopulationToString(int size){
        
        String popToString="";
        int i=0;
        for (DNA dna : population) {
           if(i++==size)
               break;
            popToString+=dna.toString()+"\n";
            
        }
        return popToString;
        
    }

}
