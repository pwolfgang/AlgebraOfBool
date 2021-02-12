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
    
    Primative p;
    Primative q;
    Primative r;
    
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
    public void lewisCaroll() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Puzzle by Lewis Carol");
        var i = Variable.of("I");
        var m = Variable.of("M");
        var a = Variable.of("A");
        var y = Variable.of("Y");
        var s = Variable.of("S");
        System.out.println("No interesting poems are unpopular.");
        var X1 = i.impl(p);
        System.out.println(X1);
        System.out.println("No modern poetry is free from affectation.");
        var X2 = m.impl(a);
        System.out.println(X2);
        System.out.println("All your poems are on soap-bubbles");
        var X3 = y.impl(s);
        System.out.println(X3);
        System.out.println("No affected poetry is popular.");
        var X4 = a.impl(p.not());
        System.out.println(X4);
        System.out.println("No ancient poem is on soap-bubbles");
        var X5 = m.not().impl(s.not());
        System.out.println(X5);
        System.out.println("Conclusion: Your poems are not popular");
        var Y = y.impl(i.not());
        System.out.println(Y);
        System.out.println("Combined premices");
        var premice = X1.and(X2).and(X3).and(X4).and(X5);
        System.out.println(premice);
        var result = premice.impl(Y);
        System.out.println(result);
        assertEquals(ONE, result);
    }
    
    @Test
    public void example() {
        var a = Variable.of("A");
        var b = Variable.of("B");
        var c = Variable.of("C");
        var X1 = a.or(b).impl(c.equiv(a));
        var X2 = a.equiv(b).impl((a.not().or(b)).and(c));
        var X3 = (a.and(b).or(c.not())).and(b.impl(c));
        System.out.println("X1: " + X1);
        System.out.println("X2: " + X2);
        System.out.println("X3: " + X3);
        var result = X1.and(X2).and(X3).impl(c.impl(b));
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
        var pIMPLq = p.impl(q);
        assertEquals(ONE.plus(p).plus(q).plus(ONE.plus(p).times(q)), p.impl(q));
    }
    
    @Test
    public void holyGrail() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Search for the Holy Grail");
        var g = Variable.of("G");
        var m = Variable.of("M");
        var s = Variable.of("S");
        var X = (g.and(s.impl(m))).not();
        var Y = (g.not().and(s.not())).not();
        var Z = (g.and(m.not())).not();
        System.out.println("X = \u00ac(G \u2227 (S\u2192M)): " + X);
        System.out.println("Y = \u00ac(\u00acG \u2227 \u00acS): " + Y);
        System.out.println("Z = \u00ac(G \u2227 \u00acM): " + Z);
        var XandYandZ = X.and(Y).and(Z);
        System.out.println("X \u2227 Y \u2227 Z = " + XandYandZ);
        System.out.println("X \u2227 Y \u2227 Z \u2192 G = " + XandYandZ.impl(g));
        System.out.println("X \u2227 Y \u2227 Z \u2192 M = " + XandYandZ.impl(m));
        System.out.println("X \u2227 Y \u2227 Z \u2192 S = " + XandYandZ.impl(s));
    }

}