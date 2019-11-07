/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie1.huristiques;

import com.bases.satclasses.Litteral;
import com.bases.satclasses.Sat;
import com.bases.satclasses.Node;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Hassaine, Sloughi 
 */

public class FrequenceHuristique implements Comparator<Node> {
    private Sat satInstance;

    public FrequenceHuristique(Sat instance) {
        this.satInstance = instance;
    }

    // definir l'huristique sur la quelle priorityQueue tri ses elements
    @Override
    public int compare(Node o1, Node o2) {

        ArrayList<Litteral> solution1 = o1.getValeur();
        ArrayList<Litteral> solution2 = o2.getValeur();
        int satisfaitClauseNumberS1 = satInstance.satisfaitClauseNumber(solution1);
        int satisfaitClauseNumberS2 = satInstance.satisfaitClauseNumber(solution2);
        return satisfaitClauseNumberS2 - satisfaitClauseNumberS1;

    }

}
