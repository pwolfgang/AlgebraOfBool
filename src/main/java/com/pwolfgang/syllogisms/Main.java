/*
 * Copyright (C) 2021 Paul Wolfgang <paul@pwolfgang.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pwolfgang.syllogisms;

import static com.pwolfgang.algebraofbool.Constant.ONE;
import com.pwolfgang.algebraofbool.Expression;
import com.pwolfgang.algebraofbool.Variable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Program to test the Syllogisms
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String line;
        while (true) {
        List<String> syllogism = new ArrayList<>();
        while ((line = in.readLine())!=null && !line.isEmpty()) {
            syllogism.add(line);
        }
        if (line == null) break;
            String name = syllogism.get(0);
            System.out.println(name);
            String[] sArray = new String[syllogism.size()-1];
            for (int i = 0; i < syllogism.size()-1; i++) {
                sArray[i] = syllogism.get(i+1);
                System.out.println(sArray[i]);
            }
            if (evalSyllogism(sArray)) {
                System.out.println("Success");
            } else {
                System.out.println("************FAIL**********");
            }
            System.out.println();
        }
    }

    /**
     * Method to evaluate a Syllogisme
     */
    public static boolean evalSyllogism(String[] syllogism) {
        Expression[] premices = new Expression[syllogism.length-1];
        Expression conclusion;
        for (int i = 0; i < syllogism.length-1; i++) {
            premices[i] = translateStatement(syllogism[i]);
        }
        System.out.println("Converted Premices");
        for (var p : premices) {
            System.out.println(p);
        }
        conclusion = translateStatement(syllogism[syllogism.length-1]);
        System.out.println("Conclusion");
        System.out.println(conclusion);
        Expression andedPremices = ONE;
        for (var p:premices) {
            andedPremices = andedPremices.and(p);
        }
        System.out.println("Anded Premices");
        System.out.println(andedPremices);
        Expression result = andedPremices.impl(conclusion);
        System.out.println("Result");
        System.out.println(result);
        return result.equals(ONE);
    }
    
    /**
     * Method to translate a statement
     * @param statement
     * @return 
     */
    public static Expression translateStatement(String statement) {
        String[] tokens = statement.split("\\s+");
        if (tokens.length > 2) {
            String p = tokens[0];
            String op = tokens[1];
            String q = tokens[2];
            switch (op) {
                case "a": return translatePaQ(p, q);
                case "e": return translatePeQ(p, q);
                case "i": return translatePiQ(p, q);
                case "o": return translatePoQ(p, q);
                default: return Variable.of(p);
            }
        } else {
            return Variable.of(tokens[0]);
        }
    }
    
    /**
     * Method to translate P a Q (All P are Q)
     */
    public static Expression translatePaQ(String p, String q) {
        var P = Variable.of(p);
        var Q = Variable.of(q);
        return (P.and(Q.not())).not();
    }
    
    /**
     * Method to translate P e Q (No P is a Q)
     */
    public static Expression translatePeQ(String p, String q) {
        var P = Variable.of(p);
        var Q = Variable.of(q);
        return (P.and(Q)).not();
    }
    
    /**
     * Method to translate P i Q (Some P is a Q)
     */
    public static Expression translatePiQ(String p, String q) {
        var P = Variable.of(p);
        var Q = Variable.of(q);
        return P.and(Q);
    }
    
    /**
     * Method to translate P o Q (Some P is not a Q)
     */
    public static Expression translatePoQ(String p, String q) {
        var P = Variable.of(p);
        var Q = Variable.of(q);
        return P.and(Q.not());
    }
}
