grammar PropLogic;

prog:   prem LINE NL conc;

prem: stat+;

conc: stat+;

stat:   expr NL;

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
LINE: '__________';
ID: LETTER DIGIT*;
NL: '\r\n';
WS: [ \t\r\n\uFEFF]+ -> skip;
