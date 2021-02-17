package com.pwolfgang.prolog;

import java.util.Objects;

public class Const extends Term {
    
    private final String name;
    
    public Const(String name) {
        super(false);
        this.name = name;
    }
    
    public Const(String name, boolean negated) {
        super(negated);
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (Const.class == o.getClass()) {
            Const other = (Const) o;
            return other.name.equals(this.name);
        } else if (o instanceof Variable) {
            return this.equals(((Variable)o).getBinding());
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return (isNegated() ? "\u00ac" : "") + name;
    }
    
    @Override
    public boolean containsUnboundVariables() {return false;}
    
    @Override
    public void bind(Term binding) {
        // do nothing
    }
    
}
