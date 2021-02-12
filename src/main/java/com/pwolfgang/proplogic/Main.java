/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwolfgang.proplogic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Main {
    
    public static void main(String... args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        InputStream is;
        if (args.length > 0) {
            is = new FileInputStream(args[0]);
        } else {
            is = System.in;
        }
        CharStream input = CharStreams.fromStream(is);
        var lexer = new PropLogicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        var parser = new PropLogicParser(tokens);
        ParseTree tree = parser.prog();
        var visitor = new EvalVisitor();
        visitor.visit(tree);
        
    }
    
}
