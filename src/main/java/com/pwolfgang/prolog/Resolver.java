package com.pwolfgang.prolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Resolver {

    List<Clause> clauses;

    public Resolver(List<Clause> clauses) {
        this.clauses = new LinkedList<>(clauses);
    }

    public boolean doResolution(Clause clause) {
        LinkedList<Clause> result = new LinkedList<>(clauses);
        result.addFirst(clause);
        boolean unificationPerformed = true;
        while (unificationPerformed) {
            unificationPerformed = false;
            System.out.println("Begin unification pass");
            result.forEach(System.out::println);
            Iterator<Clause> itr1 = result.iterator();
            Clause c1 = null;
            while (itr1.hasNext()) {
                c1 = itr1.next();
                if (c1.size() == 1) {
                    Term term1 = c1.getTerms().get(0);
                    Iterator<Clause> itr2 = result.listIterator();
                    while (itr2.hasNext()) {
                        Clause c2 = itr2.next();
                        if (!c1.equals(c2)) {
                            if (c1.containsUnboundVariables() || c2.containsUnboundVariables()) {
                                System.out.println("Trying to unify " + term1 + " and " + c2);
                                List<Term> terms = c2.getTerms();
                                for (Term t : terms) {
                                    Map<Term, Term> bindings = new HashMap<>();
                                    if (unify(term1, t, bindings)) {
                                        if (!bindings.isEmpty()) {
                                            System.out.println("Unified " + bindings);
                                            bindings.forEach((k, v) -> term1.bind(k));
                                            System.out.println("After binding: " + term1);
                                            for (Term t2 : terms) {
                                                bindings.forEach((k, v) -> t2.bind(k));
                                            }
                                            System.out.println("After binding: " + c2);
                                            unificationPerformed = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        boolean resolutionPossible = true;
        while (resolutionPossible) {
            System.out.println("\n\nBegin resolution pass");
            result.forEach(System.out::println);
            System.out.println();
            resolutionPossible = false;
            List<Clause> newClauses = new ArrayList<>();
            List<Clause> removedClauses = new ArrayList<>();
            Iterator<Clause> itr1 = result.iterator();
            Clause c1 = null;
            while (itr1.hasNext()) {
                c1 = itr1.next();
                if (c1.size() == 1) {
                    Term term1 = c1.getTerms().get(0);
                    Clause c2 = null;
                    ListIterator<Clause> itr2 = result.listIterator();
                    while (itr2.hasNext()) {
                        c2 = itr2.next();
                        if (!c1.equals(c2) && c1.canResolve(c2)) {
                            System.out.println("Resolving " + c1 + " and " + c2);
                            Clause c3 = Clause.resolve(c1, c2);
                            System.out.println("Result: " + c3);
                            if (!c3.isEmpty()) {
                                newClauses.add(c3);
                                removedClauses.add(c2);
                                resolutionPossible = true;
                            } else {
                                return true;
                            }
                        }
                    }
                }
                if (resolutionPossible) {
                    result.remove(c1);
                    result.removeAll(removedClauses);
                    result.addAll(newClauses);
                    break;
                }
            }
        }
        return false;
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
