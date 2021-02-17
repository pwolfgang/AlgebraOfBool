package com.pwolfgang.prolog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyVisitor extends PrologBaseVisitor<Fact> {
    
    public Fact visitName(NameContext ctx) {
        return new Const(ctx.LOWER_CASE().getText());
    }
    
    public Fact visitFunctor(FunctorContext ctx) {
        List<Term> terms = new ArrayList<>();
        ctx.term().forEach(t -> terms.add((Term)visit(t)));
        String name = ctx.name().getText();
        return new Functor(name, terms);
    }
    
    public Fact visitNameTerm(NameTermContext ctx) {
        return visit(ctx.name());
    }
    
    public Fact visitVariableTerm(VariableTermContext ctx) {
        return visit(ctx.variable());
    }
    
    public Fact visitFunctorTerm(FunctorTermContext ctx) {
        return visit(ctx.functor());
    }
    
    public Fact visitVariable(VariableContext ctx) {
        return new Variable(ctx.UPPER_CASE().getText());
    }
    
    public Fact visitPrule(PruleContext ctx) {
        Term lhs = (Term)visit(ctx.atom());
        List<Term> rhs = ((Clause)visit(ctx.atoms())).getTermsAsList();
        rhs.forEach(t -> t.negate());
        rhs.add(lhs);
        return new Clause(rhs);
    }
    
    public Fact visitFact(FactContext ctx) {
        return visit(ctx.atom());
    }
    
    public Fact visitSingle(SingleContext ctx) {
        Term t = (Term)visit(ctx.atom());
        return new Clause(Arrays.asList(t));
    }
    
    public Fact visitNegation(NegationContext ctx) {
        Term t = (Term)visit(ctx.atom());
        t.negate();
        return new Clause(Arrays.asList(t));
    }
    
    public Fact visitAnd(AndContext ctx) {
        Clause lhs = (Clause)visit(ctx.atoms(0));
        Clause rhs = (Clause)visit(ctx.atoms(1));
        return new Clause(lhs, rhs);
    }
    
    public Fact visitGoal(GoalContext ctx) {
        Clause goal = (Clause)visit(ctx.atoms());
        goal.getTerms().forEach(t-> t.negate());
        return goal;
    }
    
    public Fact visitProgram(ProgramContext ctx) {
        List<Clause> objects = new ArrayList<>();
        ctx.fact().forEach(factContext -> {
            Fact f = visit(factContext);
            if (f instanceof Clause) {
                objects.add((Clause) f);
            } else if (f instanceof Term) {
                objects.add(new Clause(Arrays.asList((Term)f)));
            }
        });
        ctx.prule().forEach(p -> objects.add((Clause)visit(p)));
        Resolver resolver = new Resolver(objects);
        Clause goal = ((Clause)visit(ctx.goal()));
        objects.forEach(System.out::println);
        System.out.println(goal);
        boolean result = resolver.doResolution(goal);
        System.out.println(result ? "SUCCESS" : "FAILURE");
        return null;
    }   
}
