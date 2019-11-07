/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso_algorithm;

import com.bases.satclasses.Clause;
import com.bases.satclasses.Litteral;
import com.bases.satclasses.Sat;
import com.bases.satclasses.SatFileManager;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author User
 */
public class PSO_Algorithm {

    protected static final Random rnd = new Random();
    private static final int PARTICLE_COUNT = 20;
    private static final int V_MAX = 20; // Maximum velocity change allowed.
// Range: 0 >= V_MAX < CITY_COUNT
    private static final double C1 = 2.0, C2 = 2.0;
    private static final double wCoef = 0.4;
    private static final double Rid = rnd.nextDouble(), rid = rnd.nextDouble();
    private static final int MAX_ITER = 500;
    private static int[] gene;
    private static int Target;
    private static ArrayList<Particle> Population = null;
    private static int[] Gbest;
    private static boolean done = false;
    private static int Xid;
    private static long startTime, endTime, time;
    private static int last_indice = 0;
    private static int[] tab1, tab2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //creer population de PARTICLE_COUNT particle
    
       
        Population = new ArrayList<Particle>();
        String sat20 = ".\\resource\\uf20-91\\uf20-01.cnf";
         String test = ".\\resource\\test.cnf";
        String sat75 = ".\\resource\\uf75-325\\uf75-05.cnf";
        String sat100 = ".\\resource\\uf100-430\\uf100-05.cnf";
        String sat125 = ".\\resource\\uf125-538\\uf125-03.cnf";
        String sat150 = ".\\resource\\uf150-645\\uf150-09.cnf";
        String sat250 = ".\\resource\\uf250-1065\\uf250-04.cnf";
        SatFileManager satFileManager = new SatFileManager(sat250);
        //System.out.println(satFileManager.getClauses().size()+"\n");
        // recuperation les litteraux
        ArrayList<Litteral> litteraux = satFileManager.getLitteraux();
        // recuperation les clause
        ArrayList<Clause> clauses = satFileManager.getClauses();
        Sat sat = new Sat(litteraux, clauses);
        Target = clauses.size();
        tab1 = new int[litteraux.size()];
        //initialisation du temp 
     startTime = System.currentTimeMillis();
        Gbest = new int[litteraux.size()];//un vecteur qui contient le best du group
        //initialiser le best group
        for (int i = 0; i < litteraux.size(); i++) {
            Gbest[i] = 0;
        }
        
        
        
        Particle P;
        
        //creation de population
        for (int j = 0; j < PARTICLE_COUNT; j++) {
            P = new Particle(litteraux.size(),V_MAX);
            for (int i = 0; i < litteraux.size(); i++) {
                P.solution[i] = ThreadLocalRandom.current().nextInt(0, 2);

            }
            // initialiser le best d'un particule par ca position initial
            P.Pbest = P.solution;
            Population.add(P);
         
            //initialiser le gbest par la meilleur position : cherhcer la meilleur position et la prendre
            if ((int) sat.satisfaitClauseNumber(P.Pbest) > (int) sat.satisfaitClauseNumber(Gbest)) {
                Gbest = P.Pbest;
            }
        }
      
        int i = 0;
        double viD;
        while (i < MAX_ITER && done == false) {
            
             int gbestClauseSat=0;
            //nombre de population 40 ,done=true: solution trouvé -> sortie de la boucle
            for (int j = 0; j < PARTICLE_COUNT && done==false; j++) {
                //prendre la velocity initial(speed)
                viD = Population.get(j).Vid;
                
                
                
                //mettre a jour la velocity par la regle 1 
                Population.get(j).Vid = wCoef * viD + C1 * Rid * hamming_distance(Population.get(j).Pbest, Population.get(j).solution) 
                        + C2 * rid * hamming_distance(Gbest,Population.get(j).solution);
                
                
                
                if(Population.get(j).Vid >V_MAX)Population.get(j).Vid=V_MAX;
                
                
                
                
                //hamming_distance : une fonction qui retourne le nombre de bit diff de deux vecteur (compute the hamming distance)
                done= Update_Particle_cours(j, litteraux.size(), (int) Population.get(j).Vid, Target, sat);//flip GA
               
                
                int ActuelPositionClauseSat = (int) sat.satisfaitClauseNumber(Population.get(j).solution);
                int pbestClauseSat=(int) sat.satisfaitClauseNumber(Population.get(j).Pbest);
                if (pbestClauseSat <  ActuelPositionClauseSat) {
                  //mettre ajour le best particle
                    Population.get(j).Pbest = Population.get(j).solution;
                    pbestClauseSat=ActuelPositionClauseSat;
                   
                }
               
                
                if ((gbestClauseSat = sat.satisfaitClauseNumber(Gbest)) < pbestClauseSat) {
                    //mettre a jour le best group
                    Gbest = Population.get(j).Pbest;
                    gbestClauseSat=pbestClauseSat;
                    
                }
           
                if (gbestClauseSat== Target) {
                    //donc solution
                 System.out.print("(");
                    for (int f = 0; f < litteraux.size(); f++) {
                        if(f%10==0 && f!=0)System.out.println("\n");
                     System.out.print(Population.get(j).solution[f]==0?"-x"+f:"x"+f);
                      if(f!=litteraux.size()-1)  System.out.print(",");
            }
                      System.out.print(")");
                System.out.println("\n");
                    done = true;
                    break;
                }
            }
            i++;
            System.out.println(gbestClauseSat);
        }
        endTime = System.currentTimeMillis();
        time = (endTime - startTime);
        System.out.println("time:"+time+"ms");
        System.out.println("nombre iteration:"+i);
        if (done == false) {
            System.out.println("solution non trouvé!");
            System.out.println("Solution approché!");
              for (int f = 0; f < litteraux.size(); f++) {
                System.out.print(Gbest[f]);
             
            }
               System.out.println("\nle nombre de clause satisfait est : "+(int)sat.satisfaitClauseNumber(Gbest));
            
        }

    }
  public static void affichage(int [] a,Sat sat)
  {
      for(int i=0;i<a.length;i++)
      {
          System.out.print(a[i]);
      }
      System.out.print(" ** "+(int)sat.satisfaitClauseNumber(a));
      System.out.println("\n");
  }
    public static int hamming_distance(int[] P1, int[] P2) {
        int num = 0;
        for (int i = 0; i < P1.length; i++) {
            if (P1[i] != P2[i]) {
                num++;
            }

        }
        return num;
    }
 public static boolean Update_Particle_cours(int position,int size,int velo ,int Target,Sat sat)
 {
      double viD=PSO_Algorithm.Population.get(position).Vid;
      int [] tab=PSO_Algorithm.Population.get(position).getSolution();
      int pBest=(int) sat.satisfaitClauseNumber(PSO_Algorithm.Population.get(position).solution);
      int i=0;
      while(i<viD)
      {
          int indice=ThreadLocalRandom.current().nextInt(0, size);
          tab[indice]=(int)Math.abs(tab[indice]-1);
          int satis=sat.satisfaitClauseNumber(tab);
           
                if(satis - pBest < 0)
                {
                    tab[indice]=(int)Math.abs(tab[indice]-1);
                    i--;
                   
                    
                }else
                {
                    
                    pBest=satis;
                     if(pBest==Target)
                    {  
                        
                       PSO_Algorithm.Population.get(position).solution=tab;
                        return true;
                    }
                }
             i++;    
                
                
      }
     
     
     
     return false;
 }
 

 public static boolean Update_Particle_flip_GA(int position,int size,int velo ,int Target,Sat sat)
    {//sortie si prog reste =0 dans un parcour de solution de 0 à n ou bien si on a modifier la solution velo fois (velo parcour)
        int prog=0,flips=0,satis,sous;
        int pBest=(int) sat.satisfaitClauseNumber(PSO_Algorithm.Population.get(position).solution);
        int [] tab=PSO_Algorithm.Population.get(position).getSolution();
        double viD=PSO_Algorithm.Population.get(position).Vid;
        do{
           
            for(int i=0;i<size;i++)
            {   
     
                tab[i]=(int)Math.abs(tab[i]-1);
           
                
                satis=sat.satisfaitClauseNumber(PSO_Algorithm.Population.get(position).getSolution());
                sous=satis - pBest;
                if(sous > 0)
                {
                    prog+=sous ;
                    if(sat.satisfaitClauseNumber(tab)==Target)
                    {  
                        
                       PSO_Algorithm.Population.get(position).solution=tab;
                             
                
                        return true;
                    }
                }else
                {
                  tab[i]=(int)Math.abs(tab[i]-1);
                }
            }
            flips+=1;
        }while(prog==0 && flips < viD);
       
    
       
        return false;
   
    }
  

    private static double g_calcul(double d,int V_max) {
      if(d >= V_max)
          return V_max;
      else if(d <= 0.0)
            return 0.0;
           else
            return d;
          }
    
  
//    String stringToSplit="1,1,1,1,1,1,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1,1,0,1,0,1,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,0,1,1,0,0,1,1,0,0,1,1,0,0";
//        String[] tempArray;
//        tempArray = stringToSplit.split(",");
//        int []tab=new int[75];
//        for(int i=0;i<75;i++)
//        {
//            //System.out.println("hh");
//            tab[i]=Integer.parseInt(tempArray[i]);
//        }
//        int val=sat.satisfaitClauseNumber(tab);
//        System.out.println(val);
//    }
//

   }
