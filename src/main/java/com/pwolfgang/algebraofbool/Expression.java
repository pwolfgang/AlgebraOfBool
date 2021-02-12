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

/**
 * The interface to define an Expression.
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public interface Expression {
    /**
     * Multiply two Expressions.
     * @param e The other Expression.
     * @return The result of the multiply.
     */
    Expression times(Expression e);
    
    /**
     * Add two Expressions.
     * @param e The other Expression.
     * @return The result of the addition.
     */
    Expression plus(Expression e);
    
    /**
     * Create an Expression that is the logical and with this Expression.
     * @param e The other Expression.
     * @return this times e.
     */
    default Expression and(Expression e) {
        var result = this.times(e);
        return result;
    }
    
    /**
     * Create an Expression that is the inclusive or with this Expression.
     * @param e The other Expression.
     * @return this plus e plus this times e.
     */
    default Expression or(Expression e) {
        var result = this.plus(e).plus(this.times(e));
        return result;
    }
    
    /**
     * Create an Expression that is the logical not of this Expression
     * @return ONE plus this.
     */
    default Expression not() {
        var result = ONE.plus(this);
        return result;
    }
    
    /**
     * Create an Expression that represents this implies e
     * @param e The other Expression
     * @return ONE plus this plus this times e
     */
    default Expression impl(Expression e) {
        var result = ONE.plus(this.plus(this.and(e)));
        return result;
    }
    
    /**
     * Create an Expression that represents this is equivalent to e
     * @param e The other Expression.
     * @return ONE plus this plus e. 
     */
    default Expression equiv(Expression e) {
        var result = ONE.plus(this).plus(e);
        return result;
    }
}
