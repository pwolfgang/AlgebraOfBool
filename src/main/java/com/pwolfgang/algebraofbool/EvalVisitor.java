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
     * @param ctx The node in the parse tree containing the program node.
     * @return The resulting expression.
     */
    @Override
    public Expression visitProg(PropLogicParser.ProgContext ctx) {
        prem = visit(ctx.prem());
        return visit(ctx.conc());
    }
    
    /**
     * Method to evaluate a conclusion. The conclusion node is visited resulting
     * in an Algebra of Bool expression. An implication between the premises
     * and this conclusion is formed and evaluated. If the result is ONE then
     * QED is output.
     * @param ctx
     * @return 
     */
    private Expression evalConclusion(PropLogicParser.StatContext ctx) {
        Expression conc = visit(ctx.expr());
        System.out.println("__________");
        System.out.print(ctx.getText());
        System.out.println(conc);
        var result = prem.impl(conc);
        System.out.println("Premices imply Conclusion");
        System.out.println(result);
        if (result == ONE) {
            System.out.println("QED");
        } else {
            System.out.println("Not Proven");
        }
        return result;
        
    }
    
    /**
     * Method to evaluate the conclusions. Each conclusion is evaluated.
     * @param ctx The expression tree containing the conclusions node.
     * @return 
     */
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

    /**
     * Method to evaluate the premises. Each premise is converted to Algebra
     * of Bool form and then anded with the the other premises.
     * @param ctx The node containing the premises.
     * @return The result of anding the premises.
     */
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
        System.out.println("Combined Premices");
        System.out.println(premice);
        return premice;
    }

    /**
     * Method to evaluate a statement. A statement consists of a single
     * expression.
     * @param ctx The node containing the statement.
     * @return The result
     */
    @Override
    public Expression visitStat(PropLogicParser.StatContext ctx) {
        ctx.children.forEach(child->{
            if (child instanceof PropLogicParser.ExprContext) {
                System.out.println(child.getText());
            }
        });
        return visit(ctx.expr());
        
    }

    /**
     * Method to visit a Or operation. The left and right operands are
     * evaluated and then the or operator is applied.
     * @param ctx The parse tree node.
     * @return The result
     */
    @Override
    public Expression visitOrop(PropLogicParser.OropContext ctx) {
        return visit(ctx.expr(0)).or(visit(ctx.expr(1)));
    }

    /**
     * Method to visit a Implication operation. The left and right operands are
     * evaluated and then the implication operator is applied.
     * @param ctx The parse tree node.
     * @return The result.
     */
    @Override
    public Expression visitImplop(PropLogicParser.ImplopContext ctx) {
        return visit(ctx.expr(0)).impl(visit(ctx.expr(1)));
    }
    
    /**
     * Method to visit a Equivalence operation. The left and right operands are
     * evaluated and then the equivalence operator is applied.
     * @param ctx The parse tree node.
     * @return The result.
     */
    @Override
    public Expression visitEquivop(PropLogicParser.EquivopContext ctx) {
        return visit(ctx.expr(0)).equiv(visit(ctx.expr(1)));
    }

    /**
     * Method to visit an expression surrounded by parentheses. The enclosed
     * expression is evaluated.
     * @param ctx The parse tree node.
     * @return The result.
     */
    @Override
    public Expression visitParens(PropLogicParser.ParensContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * Method to visit a And operation. The left and right operands are
     * evaluated and then the and operator is applied.
     * @param ctx The parse tree node
     * @return The result
     */
    @Override
    public Expression visitAndop(PropLogicParser.AndopContext ctx) {
        return visit(ctx.expr(0)).and(visit(ctx.expr(1)));
    }

    /**
     * Method to visit a Not operation. The expression is evaluated and then
     * the not method is applied.
     * @param ctx The parse tree node.
     * @return The result.
     */
    @Override
    public Expression visitNotop(PropLogicParser.NotopContext ctx) {
        return visit(ctx.expr()).not();
    }

    /**
     * Method to visit an ID. A new variable is created.
     * @param ctx The parse tree node.
     * @return The result.
     */
    @Override
    public Expression visitId(PropLogicParser.IdContext ctx) {
        return Variable.of(ctx.ID().getText());
    }
    
}
