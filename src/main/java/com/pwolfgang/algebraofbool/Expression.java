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
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public interface Expression {
    Expression times(Expression e);
    Expression plus(Expression e);
    default Expression and(Expression e) {
        return this.times(e);
    }
    default Expression or(Expression e) {
        return this.plus(e).plus(this.times(e));
    }
    default Expression not() {
        return ONE.plus(this);
    }
    default Expression impl(Expression e) {
        return (this.not()).or(e);
    }
}
