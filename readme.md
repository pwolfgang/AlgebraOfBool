# Algebra of Bool

This project was inspired by a series of You-Tube videos by N J Wildberger. Specifically, MathFoundations 254 through 280. Wildberer based his lectures on the book _The Mathematical Analysis of Logic, being an essay towards a calculus of deductive reasoning_ by George Bool which was originally published in 1847 and is currently available from Project Gutenberg. As Wildberger points out the Algebra of Bool as originally described by Bool is not Boolean Algebra. The difference is in Bool&#39;s original the addition and multiplication operations are over a field of size 2 namely {0, 1}. Thus the addition operation corresponds to an exclusive or while Boolean Algebra uses an inclusive or.

## Class Hierarchy

![](RackMultipart20210215-4-120dxum_html_ec354632fdfdc4c9.png)

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
