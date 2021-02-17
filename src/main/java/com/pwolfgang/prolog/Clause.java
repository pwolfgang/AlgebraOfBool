package com.pwolfgang.prolog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A Clause is a list of terms that are ored together.
 * @author Paul Wolfgang
 */
public class Clause extends Fact {
    
    private final Map<Term, Term> terms;
    
    public Clause() {
        terms = new LinkedHashMap<>();
    }
    
    public Clause(Map<Term, Term> terms) {
        this.terms = new LinkedHashMap<>(terms);
    }
    
    public Clause(List<Term> terms) {
        this.terms = new LinkedHashMap<>();
        terms.forEach(t -> this.terms.put(t, t));
    }
    
    public void add(Term term) {
        terms.put(term, term);
    }
    
    public Clause(Clause c1, Clause c2) {
        this.terms = new LinkedHashMap<>(c1.terms);
        c2.terms.forEach((k, v) -> this.terms.put(k, v));
    }
    
    boolean isEmpty() {
        return terms.isEmpty();
    }
    
    public List<Term> getTerms() {
        return new ArrayList<>(terms.keySet());
    }
    
    int size() {
        return terms.size();
    }
    
    public void remove(Term term) {
        terms.remove(term);
    }
    
    public Term get(Term term) {
        return terms.get(term);
    }
    
    public boolean contains(Term term) {
        return terms.containsKey(term);
    }
    
    public static Clause resolve(Clause c1, Clause c2) {
        Clause result = new Clause(c1.terms);
        c2.terms.forEach((t, v) -> {
            if (result.contains(t) && result.get(t).isNegated() != t.isNegated()) {
                result.remove(t);
            } else {
                result.add(t);
            }
        });
        return result;
    }
    
    public boolean canResolve(Clause c) {
        for (Map.Entry<Term, Term> term : c.terms.entrySet()) {
            Term t = term.getKey();
            if (this.terms.containsKey(t) && t.isNegated() != this.terms.get(t).isNegated()) {
                return true;
            }
        }
        return false;
    }
    
    List<Term> getTermsAsList() {
        List<Term> result = new ArrayList<>();
        terms.forEach((k, v) -> result.add(k));
        return result;
    }
    
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" \u2228 ");
        terms.forEach((t, v) -> sj.add(t.toString()));
        return sj.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Clause.class == o.getClass()) {
            Clause other = (Clause) o;
            for (Map.Entry<Term, Term> term : terms.entrySet()) {
                Term t1 = term.getKey();
                if (!other.contains(t1) || t1.isNegated() != other.get(t1).isNegated()){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (Map.Entry<Term, Term> term : terms.entrySet()) {
            int h = term.getKey().hashCode();
            if (term.getKey().isNegated()) {
                h *= 3;
            }
            hash += 79 * hash + h;
        }
        return hash;
    }
    
    public boolean containsUnboundVariables() {
        for (Term term : terms.keySet()) {
            if (term.containsUnboundVariables()) {
                return true;
            }
        }
        return false;
    }
    
}
