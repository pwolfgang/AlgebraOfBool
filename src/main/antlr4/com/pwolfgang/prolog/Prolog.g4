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

grammar Prolog;

prog:   prem LINE conc;

prem: stat+;

conc: stat+;

stat:   (fact | expr) '.';

fact: atom;

expr: op='\u00AC' expr        #notop
    | expr op='\u2227' expr   #andop
    | expr op='\u2228' expr   #orop
    | expr op='\u2192' expr   #implop
    | expr op='\u2261' expr   #equivop
    | name                    #const
    | functor                 #predicate
    | '(' expr ')'            #parens
    ;

atom:   name
    |   functor
    ;

term:   name                    #nameTerm
    |   variable                #variableTerm
    |   functor                 #functorTerm
    ;

functor:    name'('term (',' term)*')';

name:   LOWER_CASE;

variable: UPPER_CASE;

fragment UC_LETTER: [A-Z];
fragment LC_LETTER: [a-z];
fragment DIGIT: [0-9];
LINE: '_'+;
UPPER_CASE:    UC_LETTER (UC_LETTER | LC_LETTER | DIGIT)*;
LOWER_CASE:    LC_LETTER (UC_LETTER | LC_LETTER | DIGIT)*;
WS: [ \r\n\t\uFEFF]+ -> skip;
COMMENT: '/*' .*? '*/'  -> skip;
