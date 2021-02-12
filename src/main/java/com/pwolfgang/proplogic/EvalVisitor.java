/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwolfgang.proplogic;

import static com.pwolfgang.algebraofbool.Constant.ONE;
import static com.pwolfgang.algebraofbool.Constant.ZERO;
import com.pwolfgang.algebraofbool.Expression;
import com.pwolfgang.algebraofbool.Variable;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class EvalVisitor extends PropLogicBaseVisitor<Expression> {
    
    Expression prem;
    
    @Override
    public Expression visitProg(PropLogicParser.ProgContext ctx) {
        prem = visit(ctx.prem());
        return visit(ctx.conc());
    }
    
    private Expression evalConclusion(PropLogicParser.StatContext ctx) {
        Expression conc = visit(ctx.expr());
        System.out.println("__________");
        System.out.print(ctx.getText());
        System.out.println(conc);
        var result = prem.impl(conc);
        System.out.println(result);
        if (result == ONE) {
            System.out.println("QED");
        }
        return result;
        
    }
    
    @Override
    public Expression visitConc(PropLogicParser.ConcContext ctx) {
        var statList = ctx.stat();
        Expression finalResult = ZERO;
        for (var stat : statList) {
            var result = evalConclusion(stat);
            if (result == ONE) {
                finalResult = ONE;
            }
        }
        return finalResult;
    }

    @Override
    public Expression visitPrem(PropLogicParser.PremContext ctx) {
        var statements = ctx.stat();
        var itr = statements.iterator();
        var premice = visit(itr.next());
        System.out.println(premice);
        while (itr.hasNext()) {
            var stmt = visit(itr.next());
            System.out.println(stmt);
            premice = premice.and(stmt);
        }
        System.out.println(premice);
        return premice;
    }

    @Override
    public Expression visitStat(PropLogicParser.StatContext ctx) {
        System.out.print(ctx.getText());
        return visit(ctx.expr());
        
    }

    @Override
    public Expression visitOrop(PropLogicParser.OropContext ctx) {
        return visit(ctx.expr(0)).or(visit(ctx.expr(1)));
    }

    @Override
    public Expression visitImplop(PropLogicParser.ImplopContext ctx) {
        return visit(ctx.expr(0)).impl(visit(ctx.expr(1)));
    }
    
    @Override
    public Expression visitEquivop(PropLogicParser.EquivopContext ctx) {
        return visit(ctx.expr(0)).equiv(visit(ctx.expr(1)));
    }

    @Override
    public Expression visitParens(PropLogicParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Expression visitAndop(PropLogicParser.AndopContext ctx) {
        return visit(ctx.expr(0)).and(visit(ctx.expr(1)));
    }

    @Override
    public Expression visitNotop(PropLogicParser.NotopContext ctx) {
        return visit(ctx.expr()).not();
    }

    @Override
    public Expression visitId(PropLogicParser.IdContext ctx) {
        return Variable.of(ctx.ID().getText());
    }

}
