    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie2.geneticAlgo;

import com.bases.satclasses.Sat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hassaine
 */
public class DNA {

    private int[] gene;
    private int fitness;

    public DNA(int size) {
        gene = new int[size];
        for (int i = 0; i < size; i++) {

            gene[i] = ThreadLocalRandom.current().nextInt(0, 2);

        }

    }

    public DNA(int[] gene) {
        this.gene=gene;

    }

    public DNA crossover(DNA partner) {
        int crossOverPoint = ThreadLocalRandom.current().nextInt(3, this.gene.length - 3);
       // System.out.println("crossOverPoint: "+crossOverPoint);
      // crossOverPoint = 10;
        int[] child = new int[this.gene.length];
        //System.out.println(child.length);
        for (int i = 0; i < this.gene.length; i++) {
            if (i < crossOverPoint) {
                child[i] = this.gene[i];
            } else {
                child[i] = partner.getGene(i);
            }

        }
         DNA childDna=  new DNA(child);
        // childDna.toString();
        return childDna;

    }

    public void mutate() {
        int mutationPoint = ThreadLocalRandom.current().nextInt(0, this.gene.length);
        this.gene[mutationPoint] = this.gene[mutationPoint] == 0 ? 1 : 0;
        
    }
    
    public void calculateFittness(Sat satInstance){
        
        this.fitness=satInstance.satisfaitClauseNumber(this.gene);
        
        
    }
            

    public int getGene(int i) {
        return this.gene[i];
    }
    public int getFitness(){
        return this.fitness;
    }
     
    @Override
    public String toString(){
        String output="";
        for (int i = 0; i < this.gene.length; i++) {
            output+=","+this.gene[i];
           
            
        }
        return output;
    }

}
