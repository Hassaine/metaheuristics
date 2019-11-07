/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bases.satclasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hassaine ,Sloughi
 */
public class SatFileManager {

    private ArrayList<Litteral> litteraux = new ArrayList<Litteral>();
    private ArrayList<Clause> clauses = new ArrayList<Clause>();
    private String filePath;
    private int litterauxNumber;
    private int clauseNumber;

    public int getLitterauxNumber() {
        return litterauxNumber;
    }

    public int getClauseNumber() {
        return clauseNumber;
    }

    public void setLitterauxNumber(int litterauxNumber) {
        this.litterauxNumber = litterauxNumber;
    }

    public void setClauseNumber(int clauseNumber) {
        this.clauseNumber = clauseNumber;
    }

    public SatFileManager(String filePath) throws FileNotFoundException, IOException {
        this.filePath = filePath;

        BufferedReader br = new BufferedReader(new FileReader(this.filePath));
        String line;
        String[] vars;
        while ((line = br.readLine()) != null) {

            if (line.matches(".*cnf.*([0-9]).*")) {
                vars = line.split(" ");
               
              
                System.out.print("vars:" + vars[2] + ",");
              
               this.setLitterauxNumber(Integer.parseInt(vars[2]));
                   System.out.println("clauses:" + Integer.parseInt(vars[4]));
               this.setClauseNumber(Integer.parseInt(vars[4]));
                
                break;

            }
//        Pattern pattern = Pattern.compile("([0-9]+)-.*\\.cnf");
//        Matcher matcher = pattern.matcher(this.filePath);
//        if (matcher.find()) {
//            this.litterauxNumber = Integer.parseInt(matcher.group(1));
//
//        }
//
        }
        br.close();
        for (int i = 1; i <= litterauxNumber; i++) {
            
                litteraux.add(new Litteral(i));
           
//
        }

    }

    public ArrayList<Litteral> getLitteraux() {
        return litteraux;
    }

    public ArrayList<Clause> getClauses() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(this.filePath));
        String line;
        while ((line = br.readLine()) != null) {

            ArrayList<Litteral> litteraux_tmp = new ArrayList<Litteral>();

            if (line.length() > 0 && line.charAt(0) != 'c' && line.charAt(0) != 'p' && line.charAt(0) != '%'
                    && line.charAt(0) != '0') {

                StringTokenizer tokens = new StringTokenizer(line);
                while (tokens.hasMoreTokens()) {
                    int litteral = Integer.parseInt(tokens.nextToken());
                    if(litteral!=0)
                    litteraux_tmp.add(new Litteral(litteral));

                }
                this.clauses.add(new Clause(litteraux_tmp));
            }
        }
        br.close();
        return this.clauses;

    }

}
