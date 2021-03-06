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
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class TermTest {
    
    Primative a;
    Primative b;
    Primative c;
    Primative d;
    Factor ab;
    Factor bc;
    Factor ac;
    Factor bd;
    Factor ad;
    Term aPLUSb;
    Term bPLUSc;
    Term ONEplusAB;
    Term aPLUSc;
    Term cPLUSd;
    
    public TermTest() {
    }
    
    @BeforeEach
    public void setUp() {
        a = Variable.of("A");
        b = Variable.of("B");
        c = Variable.of("C");
        d = Variable.of("D");
        ab = new Factor(a, b);
        bc = new Factor(b, c);
        ac = new Factor(a, c);
        bd = new Factor(b, d);
        ad = new Factor(a, d);
        aPLUSb = new Term(a, b);
        bPLUSc = new Term(b, c);
        aPLUSc = new Term(a, c);
        cPLUSd = new Term(c, d);
        ONEplusAB = new Term(ONE, ab);
    }

    @Test
    public void ePLUSZEROisE() {
        assertEquals(aPLUSb, aPLUSb.plus(ZERO));
    }
    
    @Test
    public void onesCancel() {
        assertEquals(ab, ONEplusAB.plus(ONE));
    }
    
    @Test
    public void aPLUSbPLUSbPLUSc() {
        assertEquals(aPLUSc, aPLUSb.plus(bPLUSc));
    }

    @Test
    public void testTimes() {
        var aPLUSbTIMEScPLUSd = aPLUSb.times(cPLUSd);
        Set<Expression> factors = ((Term)aPLUSbTIMEScPLUSd).factors;
        assertEquals(Set.of(ac, ad, bc, bd), factors);    
    }
    
    @Test
    public void aPLUSbTIMESbPLUSc() {
        var aPLUSbTIMESbPLUSc = aPLUSb.times(bPLUSc);
        Set<Expression> factors = ((Term)aPLUSbTIMESbPLUSc).factors;
        assertEquals(Set.of(ab, ac, b, bc), factors);
    }
    
    @Test
    public void aPLUSbPLUSabTIMESonePLUSb() {
        var aPLUSbPLUSabTIMESonePLUSb = aPLUSb.plus(ab).times(ONE.plus(b));
        assertEquals(ab.plus(a), aPLUSbPLUSabTIMESonePLUSb);
    }
    
    @Test
    public void onePLUSpTimesQ() {
        assertEquals(b.plus(a.times(b)),ONE.plus(a).times(b));
    }
    
    @Test
    public void modusTollens() {
        var p = Variable.of("P");
        var q = Variable.of("Q");
        var onePLUSpPLUSqPlusPQ =
                ONE.plus(p).plus(q).plus(p.times(q));
        var onePLUSp = ONE.plus(p);
        var result = onePLUSpPLUSqPlusPQ.impl(onePLUSp);
        assertEquals(ONE, result);
    }
    
}
