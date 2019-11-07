/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso_algorithm;

import com.bases.satclasses.Clause;
import com.bases.satclasses.SatFileManager;
import com.project.partie2.geneticAlgo.MainGen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassaine
 */
public class Mainproblem {
    public static void main(String[] args) {
           try {
        int i=9;
          String sat75 = ".\\resource\\uf75-325\\uf75-03.cnf";
           SatFileManager satFileManager = new SatFileManager(sat75);
               ArrayList<Clause> clauses = satFileManager.getClauses(); 
               
               for (Clause clause : clauses) {
                   System.out.println(i+":"+clause.toString());
                   i++;
                   
               }
               
            
    
           
           }
           catch (IOException ex) {
            Logger.getLogger(MainGen.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
