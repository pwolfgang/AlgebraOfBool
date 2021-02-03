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

import static com.pwolfgang.algebraofbool.Constant.ZERO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Paul
 */
public class Factor implements Expression {

    List<Expression> primatives;

    public Factor(Expression e1, Expression e2) {
        primatives = new ArrayList<>();
        primatives.add(e1);
        primatives.add(e2);

    }

    @Override
    public Expression plus(Expression e) {
        if (e instanceof Constant) {
            return e.plus(this);
        }
        if (e instanceof Variable) {
            return new Term(this, e);
        }
        if (e instanceof Factor) {
            if (this.equals(e)) {
                return ZERO;
            } else {
                return new Term(this, e);
            }
        }
        return e.plus(this);
    }

    @Override
    public Expression times(Expression e) {
        if (e instanceof Constant) {
            return e.times(this);
        }
        if (e instanceof Variable) {
            if (primatives.contains(e)) {
                return this;
            } else {
                primatives.add(e);
                return this;
            }
        }
        if (e instanceof Factor) {
            Factor f = (Factor)e;
            for (Expression p : f.primatives) {
                if (!primatives.contains(p)) {
                    primatives.add(p);
                }
            }
            return this;
        }
        return e.times(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() == this.getClass()) {
            Factor other = (Factor)o;
            return primatives.equals(other.primatives);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.primatives);
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        primatives.forEach(p -> stb.append(p));
        return stb.toString();
    }
}
