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

grammar PropLogic;

prog:   prem LINE NL conc;

prem: stat+;

conc: stat+;

stat: blank* expr NL;

blank: NL;

expr: op='\u00AC' expr        #notop
    | expr op='\u2227' expr   #andop
    | expr op='\u2228' expr   #orop
    | expr op='\u2192' expr   #implop
    | expr op='\u2261' expr   #equivop
    | ID                      #id
    | '(' expr ')'            #parens
    ;

fragment LETTER: [A-Z];
fragment DIGIT: [0-9];
LINE: '_'+;
ID: LETTER DIGIT*;
NL: '\r\n';
WS: [ \t\uFEFF]+ -> channel(HIDDEN);
COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
