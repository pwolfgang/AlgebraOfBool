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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Term implements Expression {

    LinkedHashSet<Expression> factors;

    public Term(Expression e1, Expression e2) {
        factors = new LinkedHashSet<>();
        factors.add(e1);
        factors.add(e2);
    }
    
    @Override
    public Expression plus(Expression e) {
        if (factors.contains(e)) {
            factors.remove(e);
            if (factors.isEmpty()) {
                return ZERO;
            } else if (factors.size() == 1) {
                var itr = factors.iterator();
                return itr.next();
            } else {
                return this;
            }
        }
        if (e == ONE) {
            LinkedHashSet<Expression> newFactors = new LinkedHashSet<>();
            newFactors.add(ONE);
            newFactors.addAll(factors);
            factors = newFactors;
            return this;
        }
        if (e == ZERO) {
            return this;
        }
        if (e instanceof Term) {
            LinkedHashSet<Expression> facts = ((Term)e).factors;
            facts.forEach(f -> {
                if (factors.contains(f)) {
                    factors.remove(f);
                } else {
                    factors.add(f);
                }
            });
            if (factors.isEmpty()) {
                return ZERO;
            } else if (factors.size()==1) {
                var itr = factors.iterator();
                return itr.next();
            } else {
                return this;
            }
        }
        factors.add(e);
        return this;
    }

    @Override
    public Expression times(Expression e) {
        if (e instanceof Constant) {
            return e.times(this);
        }
        if (e instanceof Variable || e instanceof Factor) {
            List<Expression> newFactors = new ArrayList<>();
            factors.forEach(f -> {
                newFactors.add(e.times(f));
            });
            return createTermFromList(newFactors);
        }
        if (e instanceof Term) {
            Term otherTerm = (Term)e;
            LinkedHashSet<Expression> otherFactors = otherTerm.factors;
            List<Expression> newFactors = new ArrayList<>();
            factors.forEach(f -> {
                otherFactors.forEach(of -> {
                    newFactors.add(f.times(of));
                });
            });
            return createTermFromList(newFactors);
        }
        throw new RuntimeException("Type of e not recognized " + e.getClass());
    }
    
    private Expression createTermFromList(List<Expression> newFactors) {
        if (newFactors.isEmpty()) {
            return ZERO;
        } else if (newFactors.size() == 1) {
            var itr = newFactors.iterator();
            return itr.next();
        } else {
            var itr = newFactors.iterator();
            var e1 = itr.next();
            var e2 = itr.next();
            var newTerm = e1.plus(e2);
            while (itr.hasNext()) {
                newTerm = newTerm.plus(itr.next());
            }
            return newTerm;
        }
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
