# Algebra of Bool

This project was inspired by a series of You-Tube videos by N J Wildberger. Specifically, MathFoundations 254 through 280. Wildberer based his lectures on the book _The Mathematical Analysis of Logic, being an essay towards a calculus of deductive reasoning_ by George Bool which was originally published in 1847 and is currently available from Project Gutenberg. As Wildberger points out the Algebra of Bool as originally described by Bool is not Boolean Algebra. The difference is in Bool&#39;s original the addition and multiplication operations are over a field of size 2 namely {0, 1}. Thus the addition operation corresponds to an exclusive or while Boolean Algebra uses an inclusive or.

## Class Hierarchy

![Class Diagram](https://github.com/pwolfgang/algebraofbool/blob/main/ClassDiagram.svg?raw=true)

Expression

The Expression interface is at the top of the hierarchy. It defines the following methods:

| Method | Description |
| --- | --- |
| Expression times(Expression e) | Create a new Expression that is the product of this Expression and Expression e. |
| Expression plus(Expression e) | Create a new Expression that is the sum of this Expression and Expression e. |
| default Expression and(Expression e) | Create a new Expression that is the Propositional Logic of this Expression **and** Expression e. |
| default Expression or(Expression e) | Create a new Expression that is the Propositional Logic of this Expression **or** Expression e. |
| default Expression not() | Create a new Expression that is the Propositional Logic negation of **not** this Expression. |
| default Expression impl(Expression e) | Create a new Expression that is the Propositional Logic of this Expression **implies** Expression e. |
| default Expression equiv(Expression e) | Create a new Expression that is the Propositional Logic of this Expression is **equivalent to** Expression e. |

### Primative

This is a tagging interface.

### Constant

This is an enumeration that implements Primative. It defines the constants ZERO and ONE and implements the times and plus methods.

### Variable

A Variable is a String. This class also implements Primative and the times and plus methods.

### Factor

A Factor is the product of one or more Primatives. It implements the times and plus methods.

### Term

A Term is the sum of one or more Factors. It implements the times and plus methods.

## Arithmetic Operations.

All mathematical operations are over the field {0, 1}, essentially arithmetic modulo 2.

### Addition

0 + _x_ = _x_

1 + x = 1 if _x_=0
 0 if _x_ = 1

_x_ + _x_ = 0

### Multiplication

0 × _x_ = 0

1 × _x_ = _x_

_x_ × _x_ = _x_

## Relation to Propositional Logic

| Propositional Logic | Algebra of Bool |
| --- | --- |
| ¬_x_ | 1 + _x_ |
| _x_∧_y_ | _x_ × _y_ |
| _x_∨_y_ | _x_ + _y_ + _x_×_y_ |
| _x_ → _y_ | 1 + _x_ + _x_×_y_ |
| _x_ ≡ _y_ | 1 + _x_ + _y_ |

### Grammar

The project includes an ANTLR4 grammar for Propositional Logic. ANTLR generates a parser and tree visitor. The class EvalVisitor translates each Propositional Logic statement into the corresponding Algebra of Bool Expression. The grammar defines a program (PROG) that consists of a sequence of statements (premises) followed by one or more conclusions. The premises are separated from the conclusions by a line (10 underscore characters). All of the premises are multiplied together. The resulting product is then combined via implication to each of the conclusions. If the result is 1 then the conclusion is validated.

### Example

Wildberger in this MathFoundations 279 video gives this example.

A logic problem for Sir Galahad:

Knight of the golden road: This road leads to the grail. Also if the stones take you there, so does the marble road.

Knight of the marble road: Neither the gold or stone roads lead to the grail.

Knight of the stone road: Follow the gold and you&#39;ll reach the grail, follow the marble and you&#39;ll be lost.

All knights are liars. Which road to take?

The above statements are expressed in Propositional Logic as follows:

¬(G ∧ (S → M))

¬(¬G ∧ ¬S)

¬(G ∧ ¬M)

Where G represents the gold road, S the stone road, and M the marble road.

Output from the program is as follows:

¬(G∧(S→M))

(1+SMG+G+GS)

¬(¬G∧¬S)

(S+G+GS)

¬(G∧¬M)

(1+GM+G)

(S+SG)

\_\_\_\_\_\_\_\_\_\_

G

G

(1+S+SG)

\_\_\_\_\_\_\_\_\_\_

M

M

(1+S+SG+MS+SGM)

\_\_\_\_\_\_\_\_\_\_

S

S

1

QED

The conclusion is that the stone road leads to the grail.