/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwolfgang.prolog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paul Wolfgang
 */
public class ConstTest {
    
    public ConstTest() {
    }

    @Test
    public void testEquals() {
        Const c = new Const("foo");
        Variable v = new Variable("X");
        v.bind(c);
        assertEquals(c, v); 
    }

}
