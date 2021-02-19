package com.pwolfgang.prolog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Paul Wolfgang
 */
public class Prolog {

    private static boolean errorOccured;

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        InputStream inStream;
        if (args.length > 0) {
            String inputFileName = args[0];
            int lastDot = inputFileName.lastIndexOf(".");
            String outputFileName;
            if (lastDot != -1) {
                outputFileName = inputFileName.substring(0, lastDot) + ".class";
            } else {
                outputFileName = inputFileName + ".class";
            }
            inStream = new FileInputStream(inputFileName);
        } else {
            inStream = System.in;
        }
        CharStream input = CharStreams.fromStream(inStream);
        PrologLexer lexer = new PrologLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PrologParser parser = new PrologParser(tokens);
        ParseTree tree = parser.prog();
        MyVisitor myVisitor = new MyVisitor();
        myVisitor.visit(tree);
        myVisitor.premices.forEach(System.out::println);
        myVisitor.conclusions.forEach(System.out::println);
        Unifier unifier = new Unifier(myVisitor.premices);
        List<Expr> result = unifier.doUnification(myVisitor.conclusions.get(0));
        System.out.println("After Unification");
        result.forEach(System.out::println);

    }

    public static void error(Token t, String msg) {
        errorOccured = true;
        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),
                msg);
    }

    public static void error(ParserRuleContext ctx, String msg) {
        error(ctx.getStart(), msg);
    }

}
