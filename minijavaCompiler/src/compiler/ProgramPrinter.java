package compiler;

import gen.MiniJavaListener;
import gen.MiniJavaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;


public class ProgramPrinter implements MiniJavaListener {
    private static int indent = 0;
    private boolean nestedBlockForStatement = false;
    private Stack<Boolean> nestedBlockStack = new Stack<Boolean>();

    private String changeType(String type){
        String str = type;

        if (str != null) {
            str = (str.contains("number")) ? str.replace("number", "int") : str;
        }
        return str;
    }

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        System.out.println("program start: \n");
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {

    }

    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
        System.out.println("class " + ctx.className.getText() + "{\n");
        indent ++;
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        System.out.println("}\n");
        indent --;
    }

    @Override
    public void enterMainMethod(MiniJavaParser.MainMethodContext ctx) {
        indent ++;
        String output = "\tpublic static void main (";
        output = output.concat(changeType(ctx.type().getText()) + " " + ctx.Identifier().getText() + ") {\n");
        System.out.println(output);
    }

    @Override
    public void exitMainMethod(MiniJavaParser.MainMethodContext ctx) {
        indent --;
        System.out.print("\t}\n");
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        String output = "class " + ctx.className.getText();
        String parent = ctx.parentClass.getText();
        int hasParent = 1;
        if(!parent.isEmpty()){
            output = output.concat(" extends " + parent);
            hasParent ++;
        }
        if(ctx.getText().contains("implements")){
            String stringToConcat = "";
            for (int i = hasParent; i < ctx.Identifier().size(); i++) {
                if (i == ctx.Identifier().size() -1)
                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText());
                else
                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText() + ", ");
            }

            output = output.concat(" implements " + stringToConcat);
        }
        indent ++;
        System.out.println(output + " {\n");
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        System.out.print("}\n");
        indent -= 1;
    }

    @Override
    public void enterInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        String output = "interface " + ctx.Identifier().getText() + " {\n";
        System.out.println(output);
        indent ++;
    }

    @Override
    public void exitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        System.out.print("}\n");
        indent -= 1;
    }

    @Override
    public void enterInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {
        String output = "\t";
        if(!ctx.accessModifier().isEmpty()){
            output = output.concat(ctx.accessModifier().getText()+ " ");
        }
        if(!ctx.returnType().isEmpty()){
            output = output.concat(changeType(ctx.returnType().getText()) + " ");
        }
        output = output.concat(ctx.Identifier().getText() + " ( ");
        if (!ctx.parameterList().isEmpty()){
            for (int i = 0; i < ctx.parameterList().parameter().size(); i++) {
                if(!(i == ctx.parameterList().parameter().size() - 1))
                    output = output.concat(ctx.parameterList().parameter().get(i).type().getText() + " " + ctx.parameterList().parameter().get(i).Identifier() + ", ");
                else
                    output = output.concat(ctx.parameterList().parameter().get(i).type().getText() + " " + ctx.parameterList().parameter().get(i).Identifier() + " );\n");
            }
        }
        System.out.println(output);
    }

    @Override
    public void exitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {

    }

    @Override
    public void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void enterLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {

    }

    @Override
    public void exitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {

    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {

    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {

    }

    @Override
    public void enterParameterList(MiniJavaParser.ParameterListContext ctx) {

    }

    @Override
    public void exitParameterList(MiniJavaParser.ParameterListContext ctx) {

    }

    @Override
    public void enterParameter(MiniJavaParser.ParameterContext ctx) {

    }

    @Override
    public void exitParameter(MiniJavaParser.ParameterContext ctx) {

    }

    @Override
    public void enterMethodBody(MiniJavaParser.MethodBodyContext ctx) {

    }

    @Override
    public void exitMethodBody(MiniJavaParser.MethodBodyContext ctx) {

    }

    @Override
    public void enterType(MiniJavaParser.TypeContext ctx) {

    }

    @Override
    public void exitType(MiniJavaParser.TypeContext ctx) {

    }

    @Override
    public void enterBooleanType(MiniJavaParser.BooleanTypeContext ctx) {

    }

    @Override
    public void exitBooleanType(MiniJavaParser.BooleanTypeContext ctx) {

    }

    @Override
    public void enterReturnType(MiniJavaParser.ReturnTypeContext ctx) {

    }

    @Override
    public void exitReturnType(MiniJavaParser.ReturnTypeContext ctx) {

    }

    @Override
    public void enterAccessModifier(MiniJavaParser.AccessModifierContext ctx) {

    }

    @Override
    public void exitAccessModifier(MiniJavaParser.AccessModifierContext ctx) {

    }

    @Override
    public void enterNestedStatement(MiniJavaParser.NestedStatementContext ctx) {

    }

    @Override
    public void exitNestedStatement(MiniJavaParser.NestedStatementContext ctx) {

    }

    @Override
    public void enterIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {

    }

    @Override
    public void exitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {

    }

    @Override
    public void enterWhileStatement(MiniJavaParser.WhileStatementContext ctx) {

    }

    @Override
    public void exitWhileStatement(MiniJavaParser.WhileStatementContext ctx) {

    }

    @Override
    public void enterPrintStatement(MiniJavaParser.PrintStatementContext ctx) {

    }

    @Override
    public void exitPrintStatement(MiniJavaParser.PrintStatementContext ctx) {

    }

    @Override
    public void enterVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {

    }

    @Override
    public void exitVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {

    }

    @Override
    public void enterArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx) {

    }

    @Override
    public void exitArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx) {

    }

    @Override
    public void enterLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx) {

    }

    @Override
    public void exitLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx) {

    }

    @Override
    public void enterExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {

    }

    @Override
    public void exitExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {

    }

    @Override
    public void enterIfBlock(MiniJavaParser.IfBlockContext ctx) {

    }

    @Override
    public void exitIfBlock(MiniJavaParser.IfBlockContext ctx) {

    }

    @Override
    public void enterElseBlock(MiniJavaParser.ElseBlockContext ctx) {

    }

    @Override
    public void exitElseBlock(MiniJavaParser.ElseBlockContext ctx) {

    }

    @Override
    public void enterWhileBlock(MiniJavaParser.WhileBlockContext ctx) {

    }

    @Override
    public void exitWhileBlock(MiniJavaParser.WhileBlockContext ctx) {

    }

    @Override
    public void enterLtExpression(MiniJavaParser.LtExpressionContext ctx) {

    }

    @Override
    public void exitLtExpression(MiniJavaParser.LtExpressionContext ctx) {

    }

    @Override
    public void enterObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterPowExpression(MiniJavaParser.PowExpressionContext ctx) {

    }

    @Override
    public void exitPowExpression(MiniJavaParser.PowExpressionContext ctx) {

    }

    @Override
    public void enterIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx) {

    }

    @Override
    public void exitIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx) {

    }

    @Override
    public void enterMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx) {

    }

    @Override
    public void exitMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx) {

    }

    @Override
    public void enterNotExpression(MiniJavaParser.NotExpressionContext ctx) {

    }

    @Override
    public void exitNotExpression(MiniJavaParser.NotExpressionContext ctx) {

    }

    @Override
    public void enterBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx) {

    }

    @Override
    public void exitBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx) {

    }

    @Override
    public void enterParenExpression(MiniJavaParser.ParenExpressionContext ctx) {

    }

    @Override
    public void exitParenExpression(MiniJavaParser.ParenExpressionContext ctx) {

    }

    @Override
    public void enterIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx) {

    }

    @Override
    public void exitIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx) {

    }

    @Override
    public void enterStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx) {

    }

    @Override
    public void exitStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx) {

    }

    @Override
    public void enterNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx) {

    }

    @Override
    public void exitNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx) {

    }

    @Override
    public void enterAndExpression(MiniJavaParser.AndExpressionContext ctx) {

    }

    @Override
    public void exitAndExpression(MiniJavaParser.AndExpressionContext ctx) {

    }

    @Override
    public void enterArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {

    }

    @Override
    public void exitArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {

    }

    @Override
    public void enterAddExpression(MiniJavaParser.AddExpressionContext ctx) {

    }

    @Override
    public void exitAddExpression(MiniJavaParser.AddExpressionContext ctx) {

    }

    @Override
    public void enterThisExpression(MiniJavaParser.ThisExpressionContext ctx) {

    }

    @Override
    public void exitThisExpression(MiniJavaParser.ThisExpressionContext ctx) {

    }

    @Override
    public void enterFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx) {

    }

    @Override
    public void exitFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx) {

    }

    @Override
    public void enterArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx) {

    }

    @Override
    public void exitArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx) {

    }

    @Override
    public void enterIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterSubExpression(MiniJavaParser.SubExpressionContext ctx) {

    }

    @Override
    public void exitSubExpression(MiniJavaParser.SubExpressionContext ctx) {

    }

    @Override
    public void enterMulExpression(MiniJavaParser.MulExpressionContext ctx) {

    }

    @Override
    public void exitMulExpression(MiniJavaParser.MulExpressionContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
