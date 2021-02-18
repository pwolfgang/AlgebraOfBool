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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class MyVisitor extends  PrologBaseVisitor<Expr> {
    
    private List<Expr> premices;
    private List<Expr> conclusions;
    
    public Expr visitProg(PrologParser.ProgContext ctx) {
        premices = new ArrayList<>();
        var premList = ctx.prem().stat();
        premList.forEach(p -> premices.add(visit(p)));
        conclusions = new ArrayList<>();
        var concList = ctx.conc().stat();
        concList.forEach(c -> conclusions.add(visit(c)));
        return null;
    }
    
    public Expr visitStat(PrologParser.StatContext ctx) {
        if (ctx.fact() != null) {
            return visit(ctx.fact());
        }
        return visit(ctx.expr());
    }
    
    public Expr visitFact(PrologParser.FactContext ctx) {
        return visit(ctx.atom());
    }
    
    public Expr visitOrop(PrologParser.OropContext ctx) {
        Expr left = visit(ctx.expr(0));
        Expr right = visit(ctx.expr(1));
        return new OrExpr(left, right);
    }
    
    public Expr visitImplop(PrologParser.ImplopContext ctx) {
        Expr left = visit(ctx.expr(0));
        Expr right = visit(ctx.expr(1));
        return new ImplExpr(left, right);
    }
    
    public Expr visitPredicate(PrologParser.PredicateContext ctx) {
        return visit(ctx.functor());
    }
    
    public Expr visitParens(PrologParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
    
    public Expr visitAndop(PrologParser.AndopContext ctx) {
        Expr left = visit(ctx.expr(0));
        Expr right = visit(ctx.expr(1));
        return new AndExpr(left, right);
    }
    
    public Expr visitConst(PrologParser.ConstContext ctx) {
        return visit(ctx.name());
    }
    
    public Expr visitNotop(PrologParser.NotopContext ctx) {
        return new NotExpr(visit(ctx.expr()));
    }
    
    public Expr visitEquivop(PrologParser.EquivopContext ctx) {
        Expr left = visit(ctx.expr(0));
        Expr right = visit(ctx.expr(1));
        return new EquivExpr(left, right);
    }
    
    public Expr visitAtom(PrologParser.AtomContext ctx) {
        if (ctx.name() != null) {
            return visit(ctx.name());
        }
        return visit(ctx.functor());
    }
    
    public Expr visitNameTerm(PrologParser.NameTermContext ctx) {
        return visit(ctx.name());
    }
    
    public Expr visitVariableTerm(PrologParser.VariableTermContext ctx) {
        return visit(ctx.variable());
    }
    
    public Expr visitFunctorTerm(PrologParser.FunctorTermContext ctx) {
        return visit(ctx.functor());
    }
    
    public Expr visitFunctor(PrologParser.FunctorContext ctx) {
        String name = ctx.name().getText();
        List<Term> args = new ArrayList<>();
        var termList = ctx.term();
        termList.forEach(term -> args.add((Term)visit(term)));
        return new Functor(name, args);
    }
    
    public Expr visitName(PrologParser.NameContext ctx) {
        return new Const(ctx.getText());
    }
    
    public Expr visitVariable(PrologParser.VariableContext ctx) {
        return new Variable(ctx.getText());
    }
    
}
