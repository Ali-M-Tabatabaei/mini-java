package compiler;


import gen.MiniJavaListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import gen.MiniJavaLexer;
import gen.MiniJavaParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.IOException;

public class Compiler {
    public static void main(String[] args) {
        CharStream stream;
        {
            try {
                stream = CharStreams.fromFileName("./sample/test.mj");
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
