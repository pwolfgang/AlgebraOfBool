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
package com.pwolfgang.algebraofbool;

import static com.pwolfgang.algebraofbool.Constant.ONE;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class ExpressionTest {
    
    Variable p;
    Variable q;
    Variable r;
    
    public ExpressionTest() {
    }
    
    @BeforeEach
    public void setUp() {
        p = Variable.of("P");
        q = Variable.of("Q");
        r = Variable.of("R");
    }
    
    @Test
    public void modusPones() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Modus pones");
        Expression X1 = p;
        Expression X2 = p.impl(q);
        Expression Y = q;
        System.out.println("P: " + X1);
        System.out.println("P → Q: " + X2);
        Expression X1andX2 = X1.and(X2);
        System.out.println("P \u2227 (P → Q): " + X1andX2);
        Expression X1andX2implY = X1andX2.impl(Y);
        System.out.println("(P \u2227 (P → Q)) → Q: " + X1andX2implY);
        System.out.println("QED");
        System.out.println();
        assertEquals(ONE, X1andX2implY);
    }

    @Test
    public void modusTollens() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Modus Tollens");
        Expression X1 = q.not();
        Expression X2 = p.impl(q);
        Expression Y = p.not();
        System.out.println("\u00acQ: " + X1);
        System.out.println("P → Q: " + X2);
        Expression X1andX2 = X1.and(X2);
        System.out.println("\u00acQ \u2227 (P → Q): " + X1andX2);
        Expression X1andX2implY = X1andX2.impl(Y);
        System.out.println("(\u00acQ \u2227 (P → Q)) → \u00acP: " + X1andX2implY);;
        assertEquals(ONE, X1andX2implY);
        System.out.println("QED");
        System.out.println();
    }
    
    @Test
    public void hypotheticalSyllogism() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Hypothetical Syllogism");
        Expression X1 = p.impl(q);
        Expression X2 = q.impl(r);
        Expression Y = p.impl(r);
        assertEquals(ONE, X1.and(X2).impl(Y));
    }
    
    @Test
    public void disjunctiveSyllogism() {
        Expression X1 = p.or(q);
        Expression X2 = p.not();
        Expression Y = q;
        Expression result = X1.and(X2).impl(Y);
        assertEquals(ONE, result);
    }
    
    @Test
    public void resolution() {
        Expression X1 = p.or(q);
        Expression X2 = p.not().or(r);
        Expression Y = q.or(r);
        Expression result = X1.and(X2).impl(Y);
        assertEquals(ONE, result);
    }
    
    @Test
    public void lewisCaroll() {
        Variable i = Variable.of("I");
        Variable m = Variable.of("M");
        Variable a = Variable.of("A");
        Variable y = Variable.of("Y");
        Variable s = Variable.of("S");
        Expression X1 = i.impl(p);
        Expression X2 = m.impl(a);
        Expression X3 = y.impl(s);
        Expression X4 = a.impl(p.not());
        Expression X5 = m.not().impl(s.not());
        Expression Y = y.impl(i.not());
        Expression premice = X1.and(X2).and(X3).and(X4).and(X5);
        System.out.println(premice);
        Expression result = premice.impl(Y);
        System.out.println(result);
        assertEquals(ONE, result);
    }
    
    @Test
    public void example() {
        Variable a = Variable.of("A");
        Variable b = Variable.of("B");
        Variable c = Variable.of("C");
        Expression X1 = a.or(b).impl(c.equiv(a));
        Expression X2 = a.equiv(b).impl((a.not().or(b)).and(c));
        Expression X3 = (a.and(b).or(c.not())).and(b.impl(c));
        System.out.println("X1: " + X1);
        System.out.println("X2: " + X2);
        System.out.println("X3: " + X3);
        Expression result = X1.and(X2).and(X3).impl(c.impl(b));
        assertEquals(ONE, result);
    }
    
    @Test
    public void indepotent() {
        assertEquals(p, p.or(p));
        assertEquals(p, p.and(p));
    }
    
    @Test
    public void testOR() {
        assertEquals(p.plus(q).plus(p.times(q)), p.or(q));
    }
    
    @Test
    public void testImplies()throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Expression pIMPLq = p.impl(q);
        assertEquals(ONE.plus(p).plus(q).plus(ONE.plus(p).times(q)), p.impl(q));
    }
    
    @Test
    public void holyGrail() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Search for the Holy Grail");
        Variable g = Variable.of("G");
        Variable m = Variable.of("M");
        Variable s = Variable.of("S");
        Expression X = (g.and(s.impl(m))).not();
        Expression Y = (g.not().and(s.not())).not();
        Expression Z = (g.and(m.not())).not();
        System.out.println("X = \u00ac(G \u2227 (S\u2192M)): " + X);
        System.out.println("Y = \u00ac(\u00acG \u2227 \u00acS): " + Y);
        System.out.println("Z = \u00ac(G \u2227 \u00acM): " + Z);
        Expression XandYandZ = X.and(Y).and(Z);
        System.out.println("X \u2227 Y \u2227 Z = " + XandYandZ);
        System.out.println("X \u2227 Y \u2227 Z \u2192 G = " + XandYandZ.impl(g));
        System.out.println("X \u2227 Y \u2227 Z \u2192 M = " + XandYandZ.impl(m));
        System.out.println("X \u2227 Y \u2227 Z \u2192 S = " + XandYandZ.impl(s));
    }

}