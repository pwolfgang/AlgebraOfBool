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

import java.util.stream.Stream;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public abstract class Expr {
    
    protected Expr left;
    protected Expr right;
    
    public Expr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
    
    public abstract boolean  containsUnboundVariables();
    
    public Stream<Term> stream() {
        if (left != null && right != null) {
            Stream<Term> leftStream;
            Stream<Term> rightStream;
            if (left instanceof Term) {
                leftStream = Stream.of((Term)left);
            } else {
                leftStream = left.stream();
            }
            if (right instanceof Term) {
                rightStream = Stream.of((Term)right);
            } else {
                rightStream = right.stream();
            }
            return Stream.concat(leftStream, rightStream);
        } else if (left != null) {
            if (left instanceof Term) {
                return Stream.of((Term) left);
            } else {
                return left.stream();
            }
        }
        return Stream.empty();
    }
}
