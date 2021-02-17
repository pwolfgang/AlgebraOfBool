package com.pwolfgang.prolog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.antlr.v4.runtime.ANTLRInputStream;
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
        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(inStream, "UTF-8"));
        PrologLexer lexer = new PrologLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PrologParser parser = new PrologParser(tokens);
        ParseTree tree = parser.program();
        MyVisitor myVisitor = new MyVisitor();
        myVisitor.visit(tree);
        
//        DefinitionVisitor defVisitor = new DefinitionVisitor();
//        defVisitor.visit(tree);
//        ParseTreeProperty<Scope> scopeMap = defVisitor.getScopeMap();
//        ReferenceVisitor refVisitor = new ReferenceVisitor(scopeMap);
//        refVisitor.visit(tree);
//        if (errorOccured) return;
//        ParseTreeProperty<Type> typeMap = refVisitor.getTypeMap();
//        CodeGenerator cg = new CodeGenerator();
//        CompileVisitor visitor = new CompileVisitor(scopeMap, typeMap, inputFileName);
//        visitor.visit(tree);
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
