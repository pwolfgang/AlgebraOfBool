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

package com.pwolfgang.algebraofbool;

import static com.pwolfgang.algebraofbool.Constant.ONE;
import static com.pwolfgang.algebraofbool.Constant.ZERO;
import com.pwolfgang.algebraofbool.Expression;
import com.pwolfgang.algebraofbool.Variable;

/**
 * Visit the parse tree and convert it to an Expression.
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class EvalVisitor extends PropLogicBaseVisitor<Expression> {
    
    Expression prem;
    
    /**
     * A program consists of a series of premises followed by a series of
     * conclusions. The premises are each converted to Expressions and then
     * the product of all premises is created. Then a implication is formed
     * between the resulting product and each conclusion.
     * @param ctx
     * @return 
     */
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
