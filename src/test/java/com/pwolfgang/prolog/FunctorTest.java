/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwolfgang.prolog;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paul Wolfgang
 */
public class FunctorTest {
    
    public FunctorTest() {
    }

    @Test
    public void testEquals() {
        Const c = new Const("socrates");
        Variable v = new Variable("X");
        v.bind(c);
        Functor f1 = new Functor("mortal", Arrays.asList(c));
        Functor f2 = new Functor("mortal", Arrays.asList(v));
        assertEquals(f1, f2);
    }
   
}
