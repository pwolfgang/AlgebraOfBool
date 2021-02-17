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
package com.pwolfgang.algebraofbool;

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
