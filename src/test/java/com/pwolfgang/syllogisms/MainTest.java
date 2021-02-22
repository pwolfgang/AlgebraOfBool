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

import com.pwolfgang.algebraofbool.Expression;
import com.pwolfgang.algebraofbool.Variable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class MainTest {
    
    public MainTest() {
    }

    @Test
    public void testEvalSyllogism() {
    }

    @Test
    public void testTranslateStatement() {
        String s = "M a P";
        Expression expected = (Variable.of("M").and(Variable.of("P").not())).not();
        assertEquals(Main.translateStatement(s), expected);        
    }

    @Test
    public void testTranslatePeQ() {
        String s = "M e P";
        Expression expected = (Variable.of("M").and(Variable.of("P"))).not();
        assertEquals(Main.translateStatement(s), expected);        
    }

    @Test
    public void testTranslatePiQ() {
        String s = "M i P";
        Expression expected = (Variable.of("M").and(Variable.of("P")));
        assertEquals(Main.translateStatement(s), expected);        
    }

    @Test
    public void testTranslatePoQ() {
        String s = "M o P";
        Expression expected = (Variable.of("M").and(Variable.of("P").not()));
        assertEquals(Main.translateStatement(s), expected);        
    }
    
    @Test
    public void testSingelton() {
        String s = "S	There is an S";
        Expression expected = Variable.of("S");
        assertEquals(Main.translateStatement(s), expected);                
    }
    
}
