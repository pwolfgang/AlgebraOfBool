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
    
    public ExpressionTest() {
    }
    
    @BeforeEach
    public void setUp() {
        p = Variable.of("P");
        q = Variable.of("Q");
    }
    
    @Test
    public void modusPones() throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Expression X1 = p;
        Expression X2 = p.impl(q);
        Expression Y = q;
        System.out.println("P: " + X1);
        System.out.println("P → Q: " + X2);
        Expression X1andX2 = X1.and(X2);
        System.out.println("P \u2227 (P → Q): " + X1andX2);
        Expression X1andX2implY = X1andX2.impl(Y);
        System.out.println("(P \u2227 (P → Q)) → Q: " + X1andX2implY);;
        assertEquals(ONE, X1andX2implY);
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

}