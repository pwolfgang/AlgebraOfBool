package com.pwolfgang.prolog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Paul Wolfgang
 */
public class ResolverTest {
    
    public ResolverTest() {
    }

    @Test
    public void test1() {
        Const p = new Const("p");
        Const notP = new Const("p", true);
        Const q = new Const("q");
        Const notQ = new Const("q", true);
        Const r = new Const("r");
        Const notR = new Const("r", true);
        Const notS = new Const("s", true);
        Const t = new Const("t");
        Const notT = new Const("t", true);
        Clause c1 = new Clause();
        c1.add(p);
        Clause c2 = new Clause();
        c2.add(notP);
        c2.add(notQ);
        c2.add(r);
        Clause c3 = new Clause();
        c3.add(notS);
        c3.add(q);
        Clause c4 = new Clause();
        c4.add(notT);
        c4.add(q);
        Clause c5 = new Clause();
        c5.add(t);
        Clause c6 = new Clause();
        c6.add(notR);
        List<Clause> clauses = Arrays.asList(c1, c2, c3, c4, c5);
        Resolver resolver = new Resolver(clauses);
        assertTrue(resolver.doResolution(c6));
    }
    
    @Test
    public void test2() {
        Const p = new Const("p");
        Const notP = new Const("p", true);
        Const q = new Const("q");
        Const notQ = new Const("q", true);
        Const r = new Const("r");
        Const notR = new Const("r", true);
        Const s = new Const("s");
        Const notS = new Const("s", true);
        Clause c1 = new Clause(Arrays.asList(notR));
        Clause c2 = new Clause(Arrays.asList(notP, q));
        Clause c3a = new Clause(Arrays.asList(p, r));
        Clause c3b = new Clause(Arrays.asList(p, s));
        Resolver resolver = new Resolver(Arrays.asList(c1, c2, c3a, c3b));
        assertTrue(resolver.doResolution(new Clause(Arrays.asList(notQ))));
    }
    
    @Test
    public void testUnify1() {
        Const socratees = new Const("socratees");
        Variable x = new Variable("X");
        Functor t1 = new Functor("man", Arrays.asList(socratees));
        Functor t2 = new Functor("man", Arrays.asList(x));
        Map<Term, Term> binding = new HashMap<>();
        boolean result = Resolver.unify(t1, t2, binding);
        assertTrue(result);
        Map<Term, Term> expectedBinding = new HashMap<>();
        expectedBinding.put(x, socratees);
        System.out.println(binding);
        assertEquals(expectedBinding, binding);
    }
    
}
