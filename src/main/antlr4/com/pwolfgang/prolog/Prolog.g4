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

prog:   prem LINE NL conc;

prem: stat+;

conc: stat+;

stat:   expr NL;

expr: op='\u00AC' expr        #notop
    | expr op='\u2227' expr   #andop
    | expr op='\u2228' expr   #orop
    | expr op='\u2192' expr   #implop
    | expr op='\u2261' expr   #equivop
    | functor                 #predicate
    | NAME                    #name
    | '(' expr ')'            #parens
    ;

functor: NAME '(' (VARIABLE | NAME) (',' (VARIABLE | NAME))* ')'; 

fragment UCLETTER: [A-Z];
fragment LCLETTER: [a-z];
fragment DIGIT: [0-9];
LINE: '_'+;
NAME: LCLETTER (LCLETTER | UCLETTER | DIGIT)*;
CONSTANT: UCLETTER (LCLETTER | UCLETTER | DIGIT)*;
NL: '\r'?'\n';
WS: [ \t\r\n\uFEFF]+ -> skip;
