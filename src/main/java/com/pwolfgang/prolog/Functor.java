package com.pwolfgang.prolog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Functor extends Expr implements Term {

    private final String name;
    private final int arity;
    private final List<Term> args;

    public Functor(String name, List<? extends Term> args) {
        super (null, null);
        this.name = name;
        this.arity = args.size();
        this.args = new ArrayList<>(args);
    }
    
    public String getName() {return name;}
    
    public List<Term> getArgs() {return args;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (Functor.class == o.getClass()) {
            Functor other = (Functor) o;
            return this.name.equals(other.name) && this.arity == other.arity
                    && Objects.equals(this.args, other.args);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.arity;
        return hash;
    }

    @Override
    public boolean containsUnboundVariables() {
        for (Term arg : args) {
            if (((Expr)arg).containsUnboundVariables()) {
                return true;
            }
        }
        return false;
    }
    
    public void bind(Term term, Term binding) {
        args.forEach(arg -> {
            arg.bind(binding);
        });
    }
    
    @Override
    public void bind(Term term) {
        if (term instanceof Functor) {
            Functor binding = (Functor) term;
            binding.args.forEach(arg->arg.bind(arg));
        }
        if (term instanceof Variable) {
            args.forEach(arg -> bind(arg, term));
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        args.forEach(arg -> sj.add(arg.toString()));
        return name + sj.toString();
    }
    
    public boolean containsVariable(Variable v) {
        if (args.contains(v)) return true;
        for (Term arg : args) {
            if (arg instanceof Functor && ((Functor)arg).containsVariable(v)) {
                return true;
            }
        }
        return false;
    }
    
    public Stream<Term> stream() {
        return args.stream();
    }
}
