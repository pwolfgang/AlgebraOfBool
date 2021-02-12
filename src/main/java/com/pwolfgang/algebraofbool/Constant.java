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

/**
 * There are two constants ZERO and ONE.
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public enum Constant implements Primative  {
    ONE, ZERO;
    /**
     * The multiply operator. ZERO times anything is ZERO and ONE times
     * anything is that thing.
     * @param e The other expression.
     * @return The result of the muliply.
     */
    @Override
    public Expression times(Expression e) {
        switch (this) {
            case ZERO:
                return ZERO;
            case ONE:
                return e;
            default:
                throw new IllegalArgumentException(this + " is not a valid constant");
        }
    }

    /**
     * The addition operator. ZERO plus anything is that thing. ONE plus adds
     * one. This has the effect of negating the other operand. Ne that ONE plus
     * ONE is ZERO.  When ONE is added to a term there must be a check to see
     * if the term currently contains a ONE, in which case the ONE's cancel.
     * @param e The other expression.
     * @return The result of the addition.
     */
    @Override
    public Expression plus(Expression e) {
        switch (this) {
            case ZERO:
                return e;
            case ONE:
                if (e == ZERO) {
                    return this;
                }
                if (e == ONE) {
                    return ZERO;
                } else {
                    if (e instanceof Term) {
                        return e.plus(this);
                    } else {
                        return new Term(ONE, e);
                    }
                }
            default:
                throw new IllegalArgumentException(this + " is not a valid constant");
        }
    }
    
    /**
     * Create a String representation.
     * @return 0 for ZERO and 1 for ONE.
     */
    @Override
    public String toString() {
        switch (this) {
            case ONE:
                return "1";
            case ZERO:
                return "0";
            default:
                throw new IllegalArgumentException(this + " is not a valid constant");
        }
    }
}