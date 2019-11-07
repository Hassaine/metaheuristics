/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bases.satclasses;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Hassaine ,Sloughi
 */
public class Sat {

    private ArrayList<Litteral> litteraux = new ArrayList<Litteral>();
    private ArrayList<Clause> clauses = new ArrayList<Clause>();

    public Sat(ArrayList<Litteral> litteraux, ArrayList<Clause> clauses) {
        this.litteraux = litteraux;
        this.clauses = clauses;
    }

    public boolean test(ArrayList<Litteral> solution) {
        // just to make sure that the solution has the same size as the litteals number
        if (solution.size() != litteraux.size()) {
            System.out.println("error in the size of the solution");
            return false;
        }
        // tester la satisfesabilité des clause par la solution donnée
        for (Clause clause : clauses) {
            if (clause.clauseSat(solution) == 0) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Litteral> getLitteraux() {

        return litteraux;
    }


    public short satisfaitClauseNumber(ArrayList<Litteral> solution) {
        if (solution.size() != litteraux.size()) {
            System.out.println("error in the size of the solution");
            return 0;
        }
        // compter le nombre de clause satisfait par une solution donnée
        short i = 0;
        for (Clause clause : clauses) {
            if (clause.clauseSat(solution) == 1) {
                i++;
            }
        }

        return i;

    }
    public int satisfaitClauseNumber(int[] solution) {
        if (solution.length != litteraux.size()) {
            System.out.println("error in the size of the solution");
            return 0;
        }
        // compter le nombre de clause satisfait par une solution donnée
        int i = 0;
        for (Clause clause : clauses) {
            if (clause.clauseSat(solution) == 1) {
                i++;
            }
        }

        return i;

    }
    public short satisfaitClauseNumber2(ArrayList solution) {
        if (solution.size() != litteraux.size()) {
            System.out.println("error in the size of the solution");
            return 0;
        }
        // compter le nombre de clause satisfait par une solution donnée
        short i = 0;
        for (Clause clause : clauses) {
            if (clause.clauseSat(solution) == 1) {
                i++;
            }
        }

        return i;

    }
}
