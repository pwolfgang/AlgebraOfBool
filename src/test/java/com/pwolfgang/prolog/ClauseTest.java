/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwolfgang.prolog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Paul Wolfgang
 */
public class ClauseTest {
    
    Clause c1;
    Clause c2;
    Clause c3;
    Clause c4;
    
    @BeforeEach
    public void setup() {
        Const p = new Const("p");
        Const notP = new Const("p", true);
        Const q = new Const("q");
        Const notQ = new Const("q", true);
        Const s = new Const("s");
        Const notS = new Const("s", true);
        Const t = new Const("t");
        Const notT = new Const("t", true);
        Const r = new Const("r");
        c1 = new Clause();
        c1.add(notP);
        c1.add(notQ);
        c1.add(r);
        c2 = new Clause();
        c2.add(notS);
        c2.add(q);
        c3 = new Clause();
        c3.add(notT);
        c3.add(q);
        c4 = new Clause();
        c4.add(t);
    }
    
    public ClauseTest() {
    }

    @Test
    public void testResolve() {
        assertEquals("q", Clause.resolve(c3, c4).toString());
    }

    @Test
    public void testToString() {
        assertEquals("t", c4.toString());
        assertEquals("\u00act \u2228 q", c3.toString());
    }
    
}
