package com.pwolfgang.prolog;

import com.pwolfgang.algebraofbool.Expression;
import java.util.Objects;
import java.util.stream.Stream;

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
       
    public Expression toExpression() {
        return com.pwolfgang.algebraofbool.Variable.of(name);
    }
    
    public String getBoundName() {
        return name;
    }
}
