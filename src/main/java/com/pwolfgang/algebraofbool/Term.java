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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Term implements Expression {

    List<Expression> factors;

    public Term(Expression e1, Expression e2) {
        factors = new ArrayList<>();
        factors.add(e1);
        factors.add(e2);
    }

    @Override
    public Expression plus(Expression e) {
        if (factors.contains(e)) {
            factors.remove(e);
            return this;
        }
        if (e == ONE) {
            factors.add(0, ONE);
            return this;
        }
        if (e == ZERO) {
            return this;
        }
        if (e instanceof Term) {
            factors.addAll(((Term) e).factors);
            return this;
        }
        factors.add(e);
        return this;
    }

    @Override
    public Expression times(Expression e) {
        return null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() == this.getClass()) {
            Term other = (Term)o;
            return factors.equals(other.factors);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.factors);
        return hash;
    }
    
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("+", "(", ")");
        factors.forEach(f -> sj.add(f.toString()));
        return sj.toString();
    }
}
