/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie1;

import com.project.partie1.huristiques.ClausesSatisfaitHuristique;
import com.project.partie1.huristiques.FrequenceHuristique;
import com.bases.satclasses.Sat;
import com.bases.satclasses.Litteral;
import com.bases.satclasses.SatFileManager;
import com.bases.satclasses.Clause;
import com.bases.satclasses.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hassaine ,Sloughi
 */
public class Main {

    public static ArrayList<Litteral> RandomSolution(ArrayList<Litteral> litteraux) {

        ArrayList<Litteral> randomSolution = (ArrayList<Litteral>) litteraux.clone();
        Collections.shuffle(randomSolution);
       
        for (Litteral litteral : randomSolution) {
            litteral.setVal(ThreadLocalRandom.current().nextInt(0, 2));
            
            
            
        }
        return randomSolution;

    }

    public static void main(String[] args) {
        
        ArrayList<Litteral> litteraux = new ArrayList<Litteral>();
        ArrayList<Clause> clauses = new ArrayList<Clause>();
        try {
             String sat20=".\\resource\\uf20-91\\uf20-01.cnf";
              String sat75=".\\resource\\uf75-325\\uf75-01.cnf";
            SatFileManager satFileManager = new SatFileManager(sat75);
            
            // recuperation les litteraux
            litteraux = satFileManager.getLitteraux();
            
            // recuperation les clause
            clauses = satFileManager.getClauses();
            
            // generé une solution aléatoire
            ArrayList<Litteral> randomSolution = RandomSolution(litteraux);
            
            long startTime, endTime, time;
            

                  
            // crée une instance du SAT
            Sat sat = new Sat(litteraux, clauses);
            // crée la racine de l'arbre de recherche
            Node root = new Node(randomSolution, (short) 0);

          
//            // crée une instance du resolution
           Resolution resoudre = new Resolution(root, sat);
           //profondeur dabord
          // resoudre.profondeurDabord();
           
           
           //largeur dabord
          // resoudre.largeurDabord();



//            // crée une huristique FrequenceHuristique
            ClausesSatisfaitHuristique huristique = new ClausesSatisfaitHuristique(sat);
               startTime = System.currentTimeMillis();
            resoudre.Aetoil(huristique);
            //resoudre.profondeurDabord();
            endTime = System.currentTimeMillis();
            time = endTime - startTime;
            System.out.println("time :"+time+"ms");
        } catch (IOException iOException) {
            
            iOException.printStackTrace();

        }

    }

}
