package com.pwolfgang.prolog;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Const extends Expr implements Term {
    
    private final String name;
    
    public Const(String name) {
        super(null, null);
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
        return name;
    }
    
    @Override
    public boolean containsUnboundVariables() {return false;}
    
    public void bind(Term binding) {
        // do nothing
    }
    
    public Iterator<Term> iterator() {
        return new Iterator<Term>() {
            private boolean called = false;
            
            public boolean hasNext() {
                return !called;
            }
            
            public Term next() {
                if (!called) {
                    called = true;
                    return Const.this;
                }
                throw new NoSuchElementException();
            }
        };
    }
       
}
