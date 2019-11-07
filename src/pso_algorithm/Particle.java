/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso_algorithm;

import com.bases.satclasses.Clause;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author User
 */
public class Particle {

    public int [] solution;
    public int[] Pbest;
    public double Vid;

    public Particle(int size) {
        solution = new int[size];
        Pbest = new int[size];
        Vid = 0;
    }
     public Particle(int size,int V_MAX) {
        solution = new int[size];
        Pbest = new int[size];
        Vid = ThreadLocalRandom.current().nextInt(0, V_MAX);
    }

    public int[] getSolution() {
        return solution;
    }

    

    public int[] getPbest() {
        return Pbest;
    }

    public void setVid(int val) {
        Vid = val;
    }

    public double getVid() {
        return Vid;
    }

   
    /* public int compareTo(Particle that)
	    {
	    	if(this.Pbest < that.Pbest){
	    		return -1;
	    	}else if(this.Pbest > that.Pbest){
	    		return 1;
	    	}else{
	    		return 0;
	    	}
	    }*/
     }
