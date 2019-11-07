/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bases.satclasses;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Hassaine ,Sloughi
 */
public class Node {

    private Node precedent;
    private short profondeur;
    private ArrayList<Litteral> valeur;

    public void setPrecedent(Node precedent) {
        this.precedent = precedent;
    }

    public Node(ArrayList<Litteral> valeur, short profondeur) {
        precedent = null;
        this.profondeur = profondeur;
        this.valeur = valeur;

    }

    public Node(ArrayList<Litteral> valeur, short profondeur, Node precedent) {
        this.precedent = precedent;
        this.profondeur = profondeur;
        this.valeur = valeur;

    }

    public Node getPrecedent() {
        return precedent;
    }

    public short getProfondeur() {
        return profondeur;
    }

    public ArrayList<Litteral> getValeur() {
        return valeur;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node tmp = (Node) o;
            ArrayList<Litteral> solution = tmp.getValeur();

            for (int i = 0; i < solution.size(); i++) {
                if (solution.get(i).getVal() != this.valeur.get(i).getVal()) {
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }

    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.valeur);
        return hash;
    }

}
