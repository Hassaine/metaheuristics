/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bases.satclasses;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Hassaine
 */
public class Litteral {

    private int index;
    private int val;

    public Litteral(int index) {
        this.index = index;
        val = -1;
    }

    public Litteral(Litteral l) {
        this.index = l.index;
        this.val = l.val;
    }

    public int getVal() {
        return this.val;
    }

    public int getIndex() {
        return this.index;
    }

    public void setVal(int x) {
        this.val = x;
    }

    public static Litteral randomSelection(ArrayList<Litteral> litteraux) {
        Random rand = new Random();
        int litteral = rand.nextInt(litteraux.size());
        Litteral l = litteraux.get(litteral);
        return l;
    }

    public static Litteral getContraire(ArrayList<Litteral> litteraux, Litteral x) {
        for (int i = 0; i < litteraux.size(); i++) {
            if (litteraux.get(i).getIndex() == -x.getIndex()) {
                return litteraux.get(i);
            }
        }
        return null;
    }

    public static void removeChoisi(ArrayList<Litteral> litteraux, Litteral x) {
        int i = 0;

        while (i < litteraux.size()) {
            if ((litteraux.get(i).getIndex() == x.getIndex()) || (litteraux.get(i).getIndex() == -x.getIndex())) {
                litteraux.remove(i);

            } else {
                i++;
            }

        }
    }
//    public double frequence(Sat s){
//		int k =0;
//		for(int i=0;i<s.getClauses().size();i++){
//			for(int j=0;j<s.getClauses().get(i).getListeLitteraux().size();j++){
//				if(this.getVar()==s.getClauses().get(i).getListeLitteraux().get(j).getVar()){
//					k++;			
//				}
//			}
//		}
//		return (double)k/s.getClauses().size();
//	}

}
