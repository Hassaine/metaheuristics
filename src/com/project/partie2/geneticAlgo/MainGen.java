/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie2.geneticAlgo;

import com.bases.satclasses.Sat;
import com.bases.satclasses.SatFileManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassaine
 */
public class MainGen {

    public static void main(String[] args) {
        try {

            long startTime, endTime, time;
            int maxIter = 10000;
            String sat20 = ".\\resource\\uf20-91\\uf20-01.cnf";
            String sat75 = ".\\resource\\uf75-325\\uf75-02.cnf";
            String sat100 = ".\\resource\\uf100-430\\uf100-01.cnf";
            
            
            SatFileManager satFileManager = new SatFileManager(sat75);
            
            
            Sat satInstance = new Sat(satFileManager.getLitteraux(), satFileManager.getClauses());

            int i = 0;
            startTime = System.currentTimeMillis();

            //create a new   Population(int populationSize, int solutionSize , int target ,float CR,float MR)
            Population pop = new Population(100, satFileManager.getLitterauxNumber(), satFileManager.getClauseNumber(), (float) 0.45, (float) 0.45);
            //evaluate the population
            pop.evaluate(satInstance);

            // select the best individual
            pop.naturelSelection();

            // while we didnt get the perferct solution and we didnt attend yet our maxiter
            while (!pop.isFinished() && i++ < maxIter) {
                //generate  a new generation 
                pop.generate();
                // mutate indivedual randomly 
                pop.mutation();
                //evaluate the new generation
                pop.evaluate(satInstance);
                 // select the best individual
                pop.naturelSelection();

            }
            endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            
            // if we did find the  bestSolution <=> find a solution that satisfait all the clauses
            if (pop.isFinished()) {
                System.out.println(pop.getBest().toString());
                System.out.println("iterations:" + i);
                System.out.println("time:" + time + "ms");

            } else {
                System.out.println("no solution funded");

            }

        } catch (IOException ex) {
            Logger.getLogger(MainGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
