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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public abstract class Expr {
    
    protected Expr left;
    protected Expr right;
    
    public Expr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }
    
    public abstract boolean  containsUnboundVariables();
    
    public Iterator<Term> iterator() {
        return new InOrderIterator(this);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected class InOrderIterator implements Iterator<Term> {
 
        Expr next;
        Iterator<Term> nextIterator;
        
        Deque<Expr> stack = new ArrayDeque<>();
        
        InOrderIterator(Expr root) {
            if (root != null) {
                findLeftMostChild(root);
            } else {
                next = null;
            }
        }
        
        private void findLeftMostChild(Expr root) {
            while (root.left != null) {
                stack.push(root);
                root = root.left;
            }
            next = root;
            nextIterator = next.iterator();
        }
        
        private void advance() {
            if (next.right != null) {
                findLeftMostChild(next.right);
            } else {
                if (stack.isEmpty()) {
                    next = null;
                } else {
                    next = stack.pop();
                    nextIterator = next.iterator();
                }
            }
        }
        
        @Override
        public boolean hasNext() {
            return next != null && nextIterator.hasNext();
        }

        @Override
        public Term next() {
            var returnValue = nextIterator.next();
            if (!nextIterator.hasNext()) {
                advance();
            }
            return returnValue;
        }     
    }
 
}
