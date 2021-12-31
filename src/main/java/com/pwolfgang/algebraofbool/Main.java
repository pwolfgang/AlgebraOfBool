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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This program reads a sequence of Boolean Algebra expressions (the premises)
 * followed by a sequence of Boolean Algebra expressions (the conclusions).
 * The premises and separated from the conclusions by a string of underscore
 * characters. Each Boolean Algebra expression is translated into the Algebra
 * of Bool and simplified. The premises are then anded together and then 
 * the implication with each conclusion is evaluated. If the result is True (1)
 * then QED is output.
 * @author Paul Wolfgang <paul@pwolfgang.com>
 */
public class Main {

    /**
     * Main method to process the input. Input consists of a single file as
     * as described above, or a directory containing one or more such files.
     * If not argument are provided, input is from standard input.
     * @param args args[0] contains either an input file or a directory.
     * @throws Exception 
     */
    public static void main(String... args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        InputStream is = null;
        if (args.length > 0) {
            File file = new File(args[0]);
            if (file.isDirectory()) {
                for (var name : file.list()) {
                    System.out.println(name);
                    File f = new File(file, name);
                    is = new FileInputStream(f);
                    process(is);
                    System.out.println();
                }
            } else {
                is = new FileInputStream(file);
                process(is);
            }
        } else {
            is = System.in;
            process(is);
        }
    }

    /**
     * Method to process an input stream. The input is parsed using an ANGLR
     * generated parser, and the resulting parse tree is processed by a
     * visitor.
     * @param is The input stream.
     * @throws IOException
     * @throws RecognitionException 
     */
    private static void process(InputStream is) throws IOException, RecognitionException {
        CharStream input = CharStreams.fromStream(is);
        var lexer = new PropLogicLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        var parser = new PropLogicParser(tokens);
        ParseTree tree = parser.prog();
        var visitor = new EvalVisitor();
        visitor.visit(tree);
    }

}
