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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class TermTest {
    
    Variable a;
    Variable b;
    Variable c;
    Variable d;
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
        Expression aPLUSbTIMEScPLUSd = aPLUSb.times(cPLUSd);
        List<Expression> factors = ((Term)aPLUSbTIMEScPLUSd).factors;
        assertEquals(List.of(ac, ad, bc, bd), factors);    
    }
    
    @Test
    public void aPLUSbTIMESbPLUSc() {
        Expression aPLUSbTIMESbPLUSc = aPLUSb.times(bPLUSc);
        List<Expression> factors = ((Term)aPLUSbTIMESbPLUSc).factors;
        assertEquals(List.of(ab, ac, b, bc), factors);
    }
    
    @Test
    public void aPLUSbPLUSabTIMESonePLUSb() {
        Expression aPLUSbPLUSabTIMESonePLUSb = aPLUSb.plus(ab).times(ONE.plus(b));
        assertEquals(ab.plus(a), aPLUSbPLUSabTIMESonePLUSb);
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testToString() {
    }
    
}
