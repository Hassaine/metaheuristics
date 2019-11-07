/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.partie1;

import com.bases.satclasses.Litteral;
import com.bases.satclasses.Sat;
import com.bases.satclasses.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Hassaine ,Sloughi
 */
public class Resolution {

    private Node root;
    private Sat satInstance;

    public Resolution(Node root, Sat satInstance) {
        this.root = root;
        this.satInstance = satInstance;
    }

    public void largeurDabord() {
        // crée une file overt
        SimpleQueue overt = new SimpleQueue();
        // crée une file fermet
        ArrayList<Node> fermet = new ArrayList<Node>();
        // ajouter le node initial a overt
        overt.add(root);
        // tantque overt n'est pas vide
        while (!overt.isEmpty()) {
            // retirer le premier element de overt FIFO
            Node actuel = overt.remove();

            //si l'instance existe dans l'ensemble fermet on ignore le traitment 
            if (fermet.contains(actuel)) {

                continue;
            }

            // tester la satisfaisabilité de la solution
            boolean satisfaible = this.satInstance.test(actuel.getValeur());
            //afficher la profondeur
            System.out.println(actuel.getProfondeur());
            // si la solution est satisfiable
            if (satisfaible) {
                // afficher la solution
                System.out.println("solution trouver");
                ArrayList<Litteral> solution = actuel.getValeur();
                for (Litteral litteral : solution) {
                    System.out.println(litteral.getIndex() + "=" + litteral.getVal());

                }
                //sinon on génère 20 fils pour le node 
                // ************ EXEMPLE *************
                /*
                begin
                  Si on a une instance     de 3 elements   0 1 0  on vas génère les instance comme suit
                          0 1 0
                
                   /        |       \
                
                  1 1 0   0 0 0    0 1 1
                
                pusque dans notre cas on 20 element dans la solution de X1 jusqu'a X20  donc on doit genere 20 fils
                end
                 */
                break;

            } else {

                int[] solution = new int[actuel.getValeur().size()];
                for (int i = 0; i < solution.length; i++) {

                    solution[i] = actuel.getValeur().get(i).getVal();
                }

                for (int i = 0; i < actuel.getValeur().size(); i++) {
                    int tmp = solution[i] == 1 ? 0 : 1;

                    ArrayList<Litteral> solutionFils = new ArrayList<Litteral>();
                    for (int j = 0; j < actuel.getValeur().size(); j++) {
                        solutionFils.add(new Litteral(actuel.getValeur().get(j)));
                        if (j == i) {
                            solutionFils.get(j).setVal(tmp);
                        } else {
                            solutionFils.get(j).setVal(actuel.getValeur().get(j).getVal());
                        }

                    }
                    Node fils = new Node(solutionFils, (short) (actuel.getProfondeur() + 1));
                    fils.setPrecedent(actuel);
                    overt.add(fils);

                }

                fermet.add(actuel);

            }

        }

    }

    public void profondeurDabord() {
        Stack<Node> overt = new Stack<Node>();
        ArrayList<Node> fermet = new ArrayList<Node>();
        overt.add(root);
        while (!overt.isEmpty()) {

            Node actuel = overt.pop();
            //si l'instance existe dans l'ensemble fermet on ignore le traitment 
            if (fermet.contains(actuel)) {

                continue;
            }
            //afficher la profondeur
            System.out.println(actuel.getProfondeur());

            //tester la satisfaibilité de la solution
            boolean satisfaible = this.satInstance.test(actuel.getValeur());
            //si la solution est satisfaible 
            if (satisfaible) {
                System.out.println("solution trouver");
                ArrayList<Litteral> solution = actuel.getValeur();
                for (Litteral litteral : solution) {
                    System.out.println(litteral.getIndex() + "=" + litteral.getVal());

                }
                break;

                //sinon on génère 20 fils pour le node 
                // ************ EXEMPLE *************
                /*
                begin
                  Si on a une instance     de 3 elements   0 1 0  on vas génère les instance comme suit
                          0 1 0
                
                   /        |       \
                
                  1 1 0   0 0 0    0 1 1
                
                pusque dans notre cas on 20 element dans la solution de X1 jusqu'a X20  donc on doit genere 20 fils
                end
                 */
            } else {

                // recuperer la solution actuel est la mettre dans un tableau
                //begin    
                int[] solution = new int[actuel.getValeur().size()];
                for (int i = 0; i < solution.length; i++) {

                    solution[i] = actuel.getValeur().get(i).getVal();
                }
                //end

                // pour chque element dans la solution on genere un fils qui port l'inverse de cet element dans sa position voire EXEMPLE
                for (int i = 0; i < actuel.getValeur().size(); i++) {
                    //inverser l'element de la position actuel
                    int tmp = solution[i] == 1 ? 0 : 1;
                    //crée un fils        
                    ArrayList<Litteral> solutionFils = new ArrayList<Litteral>();

                    //cloné le père dans le fils sauf l'element de la position actuel
                    for (int j = 0; j < actuel.getValeur().size(); j++) {
                        solutionFils.add(new Litteral(actuel.getValeur().get(j)));

                        // mettre l'inverse de l'element dans le fils 
                        if (j == i) {

                            solutionFils.get(j).setVal(tmp);
                        } else {
                            //sinon on copy just l'element du père
                            solutionFils.get(j).setVal(actuel.getValeur().get(j).getVal());
                        }

                    }
                    //crée un node dans l'arbre de recherche
                    Node fils = new Node(solutionFils, (short) (actuel.getProfondeur() + 1));
                    //mettre la liaison entre le père est le fils
                    fils.setPrecedent(actuel);
                    //ajouter le fils a l'ensemble overt
                    overt.add(fils);

                }
                //ajouter le père a l'ensemble fermet
                fermet.add(actuel);

            }

        }
    }

    public void Aetoil(Comparator hirustique) {
        // crée une list de priorté en se basent sur le Comparator<Node> "hirustique"
        // défini par
        // le devleopper
        PriorityQueue<Node> overt = new PriorityQueue<>(hirustique);
        overt.add(root);
        ArrayList<Node> fermet = new ArrayList<Node>();

        while (!overt.isEmpty()) {

            Node actuel = overt.poll();
            //si l'instance existe dans l'ensemble fermet on ignore le traitment 
            if (fermet.contains(actuel)) {

                continue;
            }
            System.out.println(actuel.getProfondeur());
            boolean satisfaible = this.satInstance.test(actuel.getValeur());

            if (satisfaible) {
                System.out.println("solution trouver");
                ArrayList<Litteral> solution = actuel.getValeur();
                for (Litteral litteral : solution) {
                    System.out.println(litteral.getIndex() + "=" + litteral.getVal());

                }
                //sinon on génère 20/75.. fils pour le node 
                // ************ EXEMPLE *************
                /*
                begin
                  Si on a une instance     de 3 elements   0 1 0  on vas génère les instance comme suit
                          0 1 0
                
                   /        |       \
                
                  1 1 0   0 0 0    0 1 1
                
                pusque dans notre cas on 20 element dans la solution de X1 jusqu'a X20  donc on doit genere 20 fils
                end
                 */
                break;
            } else {

                // recuperer la solution actuel est la mettre dans un tableau
                //begin    
                int[] solution = new int[actuel.getValeur().size()];
                for (int i = 0; i < solution.length; i++) {

                    solution[i] = actuel.getValeur().get(i).getVal();
                }
                //end

                // pour chque element dans la solution on genere un fils qui port l'inverse de cet element dans sa position voire EXEMPLE
                for (int i = 0; i < actuel.getValeur().size(); i++) {
                    //inverser l'element de la position actuel
                    int tmp = solution[i] == 1 ? 0 : 1;
                    //crée un fils        
                    ArrayList<Litteral> solutionFils = new ArrayList<Litteral>();

                    //cloné le père dans le fils sauf l'element de la position actuel
                    for (int j = 0; j < actuel.getValeur().size(); j++) {
                        solutionFils.add(new Litteral(actuel.getValeur().get(j)));

                        // mettre l'inverse de l'element dans le fils 
                        if (j == i) {

                            solutionFils.get(j).setVal(tmp);
                        } else {
                            //sinon on copy just l'element du père
                            solutionFils.get(j).setVal(actuel.getValeur().get(j).getVal());
                        }

                    }
                    //crée un node dans l'arbre de recherche
                    Node fils = new Node(solutionFils, (short) (actuel.getProfondeur() + 1));
                    //mettre la liaison entre le père est le fils
                    fils.setPrecedent(actuel);
                    //ajouter le fils a l'ensemble overt
                    overt.add(fils);

                }
                //ajouter le père a l'ensemble fermet
                fermet.add(actuel);

            }

        }

    }

}
