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
 * @author Hassaine
 */
public class Clause {

    private ArrayList<Litteral> ListeLitteraux;

    public Clause(ArrayList<Litteral> Liste) {
        ListeLitteraux = Liste;

    }

    public Clause(Clause c) {
        this.ListeLitteraux = (ArrayList<Litteral>) c.ListeLitteraux.clone();

    }

    // si le clause est satisfait par la solution
    public int clauseSat(ArrayList<Litteral> solution) {
        for (Litteral litteral_solution : solution) {
            for (Litteral litteral_clause : this.ListeLitteraux) {
                if (Math.abs(litteral_clause.getIndex()) == litteral_solution.getIndex()) {
                    if (litteral_clause.getIndex() < 0 && litteral_solution.getVal() == 0) {
                        return 1;
                    }
                    if (litteral_clause.getIndex() > 0 && litteral_solution.getVal() == 1) {
                        return 1;
                    }

                }
            }
        }
        return 0;
    }

    public int clauseSat(int[] solution) {
        for (Litteral litteral_clause : this.ListeLitteraux) {
            if (solution[Math.abs(litteral_clause.getIndex())-1] == 0 && litteral_clause.getIndex() < 0) {
                return 1;
            }
            if (solution[Math.abs(litteral_clause.getIndex())-1] == 1 && litteral_clause.getIndex() > 0) {
                return 1;
            }

        }

        return 0;
    }
    

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");

        for (int i = 0; i < this.ListeLitteraux.size(); i++) {
            s.append(this.ListeLitteraux.get(i).getIndex()).append(" ");
        }
        return s.toString();
    }
}
