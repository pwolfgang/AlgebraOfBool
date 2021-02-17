package com.pwolfgang.prolog;

public abstract class Term extends Fact {
    private boolean negated;
    
    public Term(boolean negated) {
        this.negated = negated;
    }
    
    public boolean isNegated() {
        return negated;
    }
    
    public void negate() {
        negated = !negated;
    }
    
    /**
     * Terms are equal if both are constants and they are equal constants,
     * if both are variables and they have the same name and current binding
     * if one is a constant and the other a variable and the variable binding is
     * equal to the constant, both are functors and they have the same number
     * of arguments and the bindings are equal.
     * @param o
     * @return 
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Term) {
            return this.equals(o);
        } else {
            return false;
        }
    }
    
    public abstract boolean containsUnboundVariables();
    
    public abstract void bind(Term binding);
}
