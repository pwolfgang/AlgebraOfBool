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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A factor represents the conjunction of one or more Expressions
 * @author Paul
 */
public class Factor implements Expression {

    /**
     * The components of this Factor.
     */
    Set<Expression> primatives;

    /**
     * Create a Factor from two expressions.
     * @param e1 The first Expression
     * @param e2 The Second Expression
     */
    public Factor(Expression e1, Expression e2) {
        primatives = Set.of(e1, e2);
    }
    
    /**
     * Create a Factor from a Set of Primitives. This is a package private
     * constructor.
     * @param newPrimatives 
     */
    Factor(Set<Expression> newPrimatives) {
        primatives = Collections.unmodifiableSet(newPrimatives);
    }

    /**
     * Add this Expression to another Expression. Addition of two equal 
     * Factors is ZERO.
     * @param e
     * @return 
     */
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

    /**
     * Multiply this Expression by another Expression. There can only be one
     * instance of a Primitive in a Factor.
     * @param e
     * @return 
     */
    @Override
    public Expression times(Expression e) {
        var newPrimatives = new LinkedHashSet<Expression>(primatives);
        if (e instanceof Constant) {
            var result = e.times(this);
            return result;
        }
        if (e instanceof Variable) {
            if (primatives.contains(e)) {
                return this;
            } else {
                newPrimatives.add(e);
                Expression result = new Factor(newPrimatives);
                return result;
            }
        }
        if (e instanceof Factor) {
            Factor f = (Factor)e;
            for (Expression p : f.primatives) {
                if (!newPrimatives.contains(p)) {
                    newPrimatives.add(p);
                }
            }
            Expression result = new Factor(newPrimatives);
            return result;
        }
        return e.times(this);
    }

    /**
     * Determine if an other Object is equal to this. Two Factors are equal
     * if they contain the same Primitives.
     * @param o The other object
     * @return True if the other Object is a Factor that contains the same Primitives.
     */
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
