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

import com.pwolfgang.algebraofbool.Expression;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class NotExpr extends Expr {
    
    
    public NotExpr(Expr expr) {
        super(expr, null);
    }
    
    public boolean containsUnboundVariables() {
        return left.containsUnboundVariables();
    }
    
    public String toString() {
        return '\u00ac' + left.toString();
    }
    
    public Expression toExpression() {
        return left.toExpression().not();
    }
    
}
