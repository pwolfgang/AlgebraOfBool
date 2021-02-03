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
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
enum Constant implements Primative  {
    ONE, ZERO;
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

    @Override
    public Expression plus(Expression e) {
        switch (this) {
            case ZERO:
                return e;
            case ONE:
                if (e == ONE) {
                    return ZERO;
                } else {
                    return e.plus(this);
                }
            default:
                throw new IllegalArgumentException(this + " is not a valid constant");
        }
    }
    
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