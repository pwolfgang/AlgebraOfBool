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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Variable implements Primative {
    
    private final String id;
    private static final Map<String, Variable> POOL = new HashMap<>();
    private Variable(String id) {
        this.id = id;
    }
    public static Variable of(String id) {
        if (POOL.containsKey(id)) {
            return POOL.get(id);
        } else {
            Variable v = new Variable(id);
            POOL.put(id, v);
            return v;
        }
    }
    
    @Override
    public Expression times(Expression e) {
        if (e instanceof Variable) {
            if (this == e) {
                return this;
            } else {
                return new Factor(this, e);
            }
        } else if (e == ONE) {
            return this;
        } else if (e == ZERO) {
            return ZERO;
        } else {
            return e.times(this);
        }
    }
    
    @Override
    public Expression plus(Expression e) {
        if (e instanceof Variable) {
            if (this == e) {
                return ZERO;
            } else {
                return new Term(this, e);
            }
        } else if (e == ONE) {
            return new Term(e, this);
        } else if (e == ZERO) {
            return this;
        } else {
            return e.plus(this);
        }
    }
    
    public String toString() {
        return id;
    }
}
