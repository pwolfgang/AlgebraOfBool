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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Unifier {
    
    List<Expr> clauses;
    List<Expr> result;
    
    public Unifier(List<Expr> clauses) {
        this.clauses = clauses;
    }
    
    public List<Expr> doUnification(Expr conc) {
        var result = new LinkedList<>(clauses);
        result.addFirst(conc);
        var unificationPerformed = new UnificationPerformed();
        unificationPerformed.set(true);
        while (unificationPerformed.isTrue()) {
            unificationPerformed.set(false);
            System.out.println("Begin unification pass");
            result.forEach(System.out::println);
            Iterator<Expr> itr1 = result.iterator();
            Expr c1 = null;
            while (itr1.hasNext()) {
                c1 = itr1.next();
                if (c1 instanceof Term) {
                    Term term1 = (Term)c1;
                    Iterator<Expr> itr2 = result.iterator();
                    while (itr2.hasNext()) {
                        Expr c2 = itr2.next();
                        if (!c1.equals(c2)) {
                            if (c1.containsUnboundVariables() || c2.containsUnboundVariables()) {
                                System.out.println("Trying to unify " + term1 + " and " + c2);
                                c2.stream().takeWhile((t)->unificationPerformed.isFalse()).forEach(t -> {
                                    Map<Term, Term> bindings = new HashMap<>();
                                    if (unify(term1, t, bindings)) {
                                        if (!bindings.isEmpty()) {
                                            System.out.println("Unified " + bindings);
                                            bindings.forEach((k, v) -> term1.bind(k));
                                            System.out.println("After binding: " + term1);
                                            c2.stream().forEach(t2 -> {
                                                bindings.forEach((k, v) -> t2.bind(k));
                                            });
                                            System.out.println("After binding: " + c2);
                                            unificationPerformed.set(true);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    private static class UnificationPerformed {
        private boolean value = true;
        
        public void set(boolean value) {
            this.value = value;
        }
        
        public boolean isFalse() {
            return !value;
        }
        
        public boolean isTrue() {
            return value;
        }
    }

    public static boolean unify(Term term1, Term term2, Map<Term, Term> bindings) {
        if (term1 instanceof Variable) {
            return unifyVariable((Variable) term1, term2, bindings);
        }
        if (term2 instanceof Variable) {
            return unifyVariable((Variable) term2, term1, bindings);
        }
        if (term1 instanceof Const) {
            return term1.equals(term2);
        }
        if (term1 instanceof Functor && term2 instanceof Functor) {
            Functor f1 = (Functor) term1;
            Functor f2 = (Functor) term2;
            if (!f1.getName().equals(f2.getName())) {
                return false;
            }
            Iterator<Term> itr1 = f1.getArgs().iterator();
            Iterator<Term> itr2 = f2.getArgs().iterator();
            while (itr1.hasNext() && itr2.hasNext()) {
                Term t1 = itr1.next();
                Term t2 = itr2.next();
                if (!unify(t1, t2, bindings)) {
                    return false;
                }
            }
            return !(itr1.hasNext() || itr2.hasNext());
        }
        return false;
    }
    
    public static boolean unifyVariable(Variable v, Term t, Map<Term, Term> bindings) {
        if (t instanceof Variable) {
            return v.equals(t);
        }
        if (bindings.containsKey(v)) {
            return false;
        }
        if (t instanceof Const) {
            v.bind(t);
            bindings.put(v, t);
            return true;
        }
        if (t instanceof Functor) {
            if (!((Functor) t).containsVariable(v)) {
                v.bind(t);
                bindings.put(v, t);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
