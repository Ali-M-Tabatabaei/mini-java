package compiler;


import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import gen.*;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import compiler.ProgramPrinter;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) {
        CharStream stream;
        {
            try {
                stream = CharStreams.fromFileName("./sample/test.txt");
                MiniJavaLexer lexer = new MiniJavaLexer(stream);
                TokenStream tokens = new CommonTokenStream(lexer);
                MiniJavaParser parser = new MiniJavaParser(tokens);
                parser.setBuildParseTree(true);
                ParseTree tree = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                MiniJavaListener listener = new ProgramPrinter();

                walker.walk(listener, tree);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
