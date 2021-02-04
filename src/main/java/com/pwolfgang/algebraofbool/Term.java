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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Term implements Expression {

    Set<Expression> factors;

    public Term(Expression e1, Expression e2) {
        var f = new LinkedHashSet<Expression>();
        f.add(e1);
        f.add(e2);
        factors = Collections.unmodifiableSet(f);
    }
    
    Term(LinkedHashSet<Expression> newFactors) {
        factors = Collections.unmodifiableSet(newFactors);
    }
    
    @Override
    public Expression plus(Expression e) {
        var newFactors = new LinkedHashSet<Expression>(factors);
        if (newFactors.contains(e)) {
            newFactors.remove(e);
            if (newFactors.isEmpty()) {
                return ZERO;
            } else if (newFactors.size() == 1) {
                var itr = newFactors.iterator();
                var result = itr.next();
                return result;
            } else {
                Expression result = new Term(newFactors);
                return result;
            }
        }
        if (e == ONE) {
            newFactors = new LinkedHashSet<>();
            newFactors.add(ONE);
            newFactors.addAll(factors);
            Expression result = new Term(newFactors);
            return this;
        }
        if (e == ZERO) {
            System.out.println(this);
            return this;
        }
        if (e instanceof Term) {
            Set<Expression> facts = ((Term)e).factors;
            newFactors = new LinkedHashSet<>(factors);
            for (var f : facts) {
                if (newFactors.contains(f)) {
                    newFactors.remove(f);
                } else {
                    newFactors.add(f);
                }
            }
            if (newFactors.isEmpty()) {
                return ZERO;
            } else if (newFactors.size()==1) {
                var itr = factors.iterator();
                var result = itr.next();
                return result;
            } else {
                return new Term(newFactors);
            }
        }
        newFactors.add(e);
        Expression result = new Term(newFactors);
        return result;
    }

    @Override
    public Expression times(Expression e) {
        if (e instanceof Constant) {
            var result = e.times(this);
            System.out.println(result);
            return result;
        }
        if (e instanceof Variable || e instanceof Factor) {
            List<Expression> newFactors = new ArrayList<>();
            factors.forEach(f -> {
                newFactors.add(e.times(f));
            });
            var result = createTermFromList(newFactors);
            return result;
        }
        if (e instanceof Term) {
            Term otherTerm = (Term)e;
            Set<Expression> otherFactors = otherTerm.factors;
            List<Expression> newFactors = new ArrayList<>();
            factors.forEach(f -> {
                otherFactors.forEach(of -> {
                    newFactors.add(f.times(of));
                });
            });
            var result = createTermFromList(newFactors);
            return result;
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
