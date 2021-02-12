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

import static com.pwolfgang.algebraofbool.Constant.ZERO;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class FactorTest {
    
    Factor ab;
    Factor bc;
    Factor cd;
    Primative a;
    Primative b;
    Primative c;
    Primative d;
    
    public FactorTest() {
    }
    
    @BeforeEach
    public void init() {
        a = Variable.of("A");
        b = Variable.of("B");
        c = Variable.of("C");
        d = Variable.of("D");
        ab = new Factor(a, b);
        bc = new Factor(b, c);
        cd = new Factor(c, d);       
    }

    @Test
    public void abTimesA() {
        assertEquals(ab.times(a), ab);
    }
    
    @Test
    public void abTimesC() {
        var f = ab.times(c);
        assertTrue(f instanceof Factor);
        assertEquals(((Factor)f).primatives, Set.of(a, b, c));
    }
    
    @Test
    public void abTimesCD() {
        var f = ab.times(cd);
        assertTrue(f instanceof Factor);
        assertEquals(((Factor)f).primatives, Set.of(a, b, c, d));
    }
    
    @Test
    public void abTimesBC() {
        var f = ab.times(bc);
        assertTrue(f instanceof Factor);
        assertEquals(((Factor)f).primatives, Set.of(a, b, c));
    }

    @Test
    public void abPlusab() {
        assertEquals(ab.plus(ab), ZERO);
    }
    
    @Test
    public void abPlusBC() {
        assertEquals(ab.plus(bc), new Term(ab, bc));
    }
    
    @Test
    public void abPlusC() {
        assertEquals(ab.plus(c), new Term(ab, c));
    }
       
}
