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
import static com.pwolfgang.algebraofbool.Constant.ZERO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class VariableTest {

    public VariableTest() {
    }

    @Test
    public void ofCreatesUniqueInstances() {
        var a = Variable.of("A");
        assertEquals(a, Variable.of("A"));
        assertNotEquals(a, Variable.of("B"));
    }

    @Test
    public void aTimesAisA() {
        var a = Variable.of("A");
        assertEquals(a.times(a), a);
    }

    @Test
    public void aTimesBisAB() {
        var a = Variable.of("A");
        var b = Variable.of("B");
        assertEquals(a.times(b), new Factor(a, b));
    }

    @Test
    public void aTimesONEisA() {
        var a = Variable.of("A");
        assertEquals(a.times(ONE), a);
    }

    @Test
    public void aTimesZEROisZERO() {
        var a = Variable.of("A");
        assertEquals(a.times(ZERO), ZERO);
    }


    @Test
    public void aPlusAisZERO() {
        var a = Variable.of("A");
        assertEquals(a.plus(a), ZERO);
    }

    @Test
    public void aPlusBisAplusB() {
        var a = Variable.of("A");
        var b = Variable.of("B");
        assertEquals(a.plus(b), new Term(a, b));
    }
    
    @Test
    public void aPlusONEisONEplusA() {
        var a = Variable.of("A");
        assertEquals(a.plus(ONE), new Term(ONE, a));
    }
    
    @Test
    public void aPlusZEROisA() {
        var a = Variable.of("A");
        assertEquals(a.plus(ZERO), a);
    }

    @Test
    public void ToSring() {
        var a = Variable.of("A");
        assertEquals(a.toString(), "A");
    }
}
