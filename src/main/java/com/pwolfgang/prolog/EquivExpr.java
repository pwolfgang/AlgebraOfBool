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
package com.pwolfgang.prolog;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class EquivExpr implements Expr {

    private Expr left;
    private Expr right;

    public EquivExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    public boolean containsUnboundVariables() {
        return left.containsUnboundVariables() || right.containsUnboundVariables();
    }

    public String toString() {
        return left.toString() + " \u2261 " + right.toString();
    }

}
