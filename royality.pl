man(haakonVII).
man(olavV).
man(haraldV).
man(haakon).
woman(martha).
woman(mette).
woman(maud).
woman(sonja).
parent(haakonVII, olavV).
parent(maud, olavV).
parent(olavV, haraldV).
parent(martha, haraldV).
parent(haraldV, haakon).
parent(sonja, haakon).
parent(X, Y) ∧ man(X) → father(X,Y).
parent(X, Y) ∧ woman(X) → mother(X,Y).
parent(X, Y) ∨ parent(X, Z) ∧ ancestor(Z, Y) → ancestor(X, Y).
__________
father(haraldV, haakon).
