package com.pwolfgang.prolog;

import java.util.Objects;
import java.util.stream.Stream;

public class Variable extends Expr implements Term {

    private final String name;
    private Term binding;

    public Variable(String name) {
        super(null, null);
        this.name = name;
    }

    @Override
    public void bind(Term binding) {
        if (binding instanceof Variable) {
            Variable vb = (Variable) binding;
            if (vb.name.equals(name)) {
                this.binding = vb.binding;
            }
        } else {
            this.binding = binding;
        }
    }

    public Term getBinding() {
        return binding;
    }

    @Override
    public boolean containsUnboundVariables() {
        return binding == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (Variable.class == o.getClass()) {
            Variable other = (Variable) o;
            return other.name.equals(this.name) && Objects.equals(other.binding, this.binding);
        } else if (o instanceof Const) {
            return ((Const) o).equals(this);
        } else if (o instanceof Functor) {
            return this.binding.equals(o);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        return name + "\\" + binding;
    }

    public Stream<Term> stream() {
        return Stream.of(this);
    }
}
