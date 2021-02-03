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
public class ConstantTest {
    
    public ConstantTest() {
    }


    @Test
    public void ZEROtimeExpressionIsZero() {
        System.out.println("ZERO times A is ZERO");
        assertEquals(ZERO.times(Variable.of("A")), ZERO);
    }
    
    @Test
    public void ONEtimeONEisONE() {
        System.out.println("ONE times ONE is ONE");
        assertEquals(ONE.times(ONE), ONE);
    }
    
    public void ONEtimesExpressionIsExpression() {
        System.out.println("ONE times A is A");
        assertEquals(ONE.times(Variable.of("A")), Variable.of("A"));
    }
   
    @Test
    public void ZEROplusExpressionIsExpression() {
        System.out.println("ZERO plus A is A");
        assertEquals(ZERO.plus(Variable.of("A")), Variable.of("A"));
    }
    
    @Test
    public void ONEplusONEisZERO() {
        System.out.println("ONE plus ONE is ZERO");
        assertEquals(ONE.plus(ONE), ZERO);
    }
    
    @Test
    public void ToString() {
        System.out.println("testToString");
        assertEquals(ONE.toString(), "1");
        assertEquals(ZERO.toString(), "0");
    }

}
