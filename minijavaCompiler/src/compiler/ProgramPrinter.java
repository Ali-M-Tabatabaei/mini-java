package compiler;

import gen.MiniJavaListener;
import gen.MiniJavaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ProgramPrinter implements MiniJavaListener {
    private static int indent = 0;
    private boolean nestedBlockForStatement = false;
    private final Stack<Boolean> nestedBlockStack = new Stack<>();
    private SymbolTableGraph stg;
    private DirectedGraph graph = new DirectedGraph();

    private String changeType(String type){
        String str = type;
        if (str != null) str = (str.contains("number")) ? str.replace("number", "int") : str;
        return str;
    }

    private void printTab(int tabCount){
        for (int i=0; i<tabCount; i++)
            System.out.print("\t");
    }

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        System.out.println("program start: \n");
        this.stg = new SymbolTableGraph();
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {
        this.stg.printSymbolTable();
        detectAllErrorsInCode();
    }

    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
        // convert to java
        String className = ctx.className.getText();
        System.out.println("class " + ctx.className.getText() + "{\n");
        indent ++;

        // Symbol table entry
        String key =  "Key = MainClass_" + className;
        String value = "MainClass: (name: " + ctx.className.getText() + ")";
        stg.addEntry(key, value);

        // symbol table creation
        int lineNumber = ctx.getStart().getLine();
        stg.enterBlock(className, lineNumber);
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        // convert to java
        System.out.println("}\n");
        indent --;

        // changing scope
        stg.exitBlock();
    }

    @Override
    public void enterMainMethod(MiniJavaParser.MainMethodContext ctx) {
        // convert to java
        indent ++;
        String output = "\tpublic static void main (";
        output = output.concat(changeType(ctx.type().getText()) + " " + ctx.Identifier().getText() + ") {\n");
        System.out.println(output);

        // Symbol table entry
        String key = "Key = method_main";
        String value = "Value = Method: (name: main) (returnType: void) (accessModifier: public) (parametersType: [array of [classType = String, isDefined = true] , index: 1] )";
        stg.addEntry(key, value);
        // symbol table creation
        stg.enterBlock("main", ctx.getStart().getLine());
    }

    @Override
    public void exitMainMethod(MiniJavaParser.MainMethodContext ctx) {
        // convert to java
        indent --;
        System.out.print("\t}\n");
        // changing scope
        stg.exitBlock();
    }

    private void classSymbolTableCreation (MiniJavaParser.ClassDeclarationContext ctx, String parent, String implementations){
        // create symbol entry
        String className = ctx.className.getText();
        String classNameSymbol = "class_" + className;
        String key = "key = " + classNameSymbol;
        String value = "Value = Class: (name: " + className + ") (extends: " + parent + ")";
        graph.addEdge(className , parent);
        if(!implementations.isEmpty()) {
            value = value.concat(" (implements: " + implementations + ")");
            miniJavaClassDetail.addImplemente(className , implementations);
        }

        int lineNumber = ctx.getStart().getLine();
        stg.addEntry(key, value);

        // symbol table creation
        stg.enterBlock(className, lineNumber);
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        // convert to java
        String output = "class " + ctx.className.getText();
        String parent = ctx.parentClass.getText();

        int hasParent = 1;
        if(!parent.isEmpty()){
            output = output.concat(" extends " + parent);
            hasParent ++;
        } else
            parent = "Object";

        String stringToConcat = "";
        if(ctx.getText().contains("implements")){
            for (int i = hasParent; i < ctx.Identifier().size(); i++) {
                if (i == ctx.Identifier().size() -1)
                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText());
                else
                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText() + ", ");
            }
            output = output.concat(" implements " + stringToConcat);
        }
        indent ++;
        classSymbolTableCreation(ctx, parent, stringToConcat);
        System.out.println(output + " {\n");
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        System.out.print("}\n");
        indent -= 1;
        stg.exitBlock();
    }

    @Override
    public void enterInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        // convert to java
        String output = "interface " + ctx.Identifier().getText() + " {\n";
        System.out.println(output);
        indent ++;

        // creat symbol entry
        int lineNumber = ctx.getStart().getLine();
        String className = ctx.Identifier().getText();
        String key = "Key =  interface_" + className;
        String value = "Value = interface: (name: " + className + ")" ;
        stg.addEntry(key, value);

        stg.enterBlock(className, lineNumber);
    }

    @Override
    public void exitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        System.out.print("}\n");
        indent -= 1;
        stg.exitBlock();
    }

    @Override
    public void enterInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {
        // convert to java
        String output = "\t";
        ArrayList<String> params = new ArrayList<>();
        String accessModifier = "public";
        if(!ctx.accessModifier().isEmpty()){
            output = output.concat(ctx.accessModifier().getText()+ " ");
            accessModifier = ctx.accessModifier().getText();
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
                params.add(ctx.parameterList().parameter().get(i).type().getText());
            }
        }else {
            output = output.concat(") {\n");

        }
        int lineNumber = ctx.getStart().getLine();
        System.out.println(output);

        // create symbol table entry
        String methodName = ctx.Identifier().getText();
        String key = "key = method_" + methodName;
        String value = "Value = Method: (name: " + methodName + ")" + "(returnType: " + ctx.returnType().getText() + ") (accessModifier: ACCESS_MODIFIER_" + accessModifier.toUpperCase();

        if(ctx.parameterList() != null){
            int i = 0;
            int paramCount = ctx.parameterList().parameter().size();
            value += " (parametersType: ";
            for (;i < paramCount; i ++) {
                if (ctx.parameterList().parameter(i).type().javaType() != null) {
                    value += "[" + ctx.parameterList().parameter(i).type().getText() + ", " + "index: " + (i + 1) + "]";
                } else {
                    value += "[ classType = " + ctx.parameterList().parameter(i).type().Identifier().getText() + ", " + "index: " + (i + 1) + "]";
                }
            }
        }
        value += ")";
        miniJavaClassDetail.addMethod(stg.getCurentNodeName(), methodName);
        stg.addEntry(key, value);
    }

    @Override
    public void exitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {

    }

    @Override
    public void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {
        // convert to java
        String output = "\t";
        if(ctx.accessModifier() != null){
            output = output.concat(ctx.accessModifier().getText() + " ");
        }
        if (ctx.Final() != null) {
            output = output.concat(ctx.Final().getText() + " ");
        }
        output = output.concat(changeType(ctx.type().getText()) + " " + ctx.Identifier().getText() + " ");
        if (ctx.EQ() != null){
            output = output.concat(ctx.EQ().getText() + " " + this.expressionHandler(ctx.expression()) + " ;\n");
        }else{
            output = output.concat(";\n");
        }
        System.out.print(output);

        // create symbol table entry
        String key = "key = var_" + ctx.Identifier().getText();
        String value = "Value = Field: (name: " + ctx.Identifier().getText() + ")";
        if(ctx.type().LSB() != null){
            value += " (type: array of " ;
        }
        else {
            value += " (type: ";
        }
        if(ctx.type().Identifier() != null){
            value += "[ classType: " + ctx.type().Identifier().getText() + " ])";
        }
        else {
            value += ctx.type().javaType().getText() + ")";
        }
        if(ctx.accessModifier() != null){
            value += " (accesModifier: " + ctx.accessModifier().getText() + ")";
        }
        stg.addEntry(key, value);
    }



    @Override
    public void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void enterLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {
//        convert to java
        printTab(indent);
        System.out.println(changeType(ctx.type().getText()) + " " + ctx.Identifier() + ";");

        // create symbol table entry
        String key = ctx.Identifier().getText();
        String value = "Value = Localvar: (name: " + ctx.Identifier().getText() + ")";

        if(ctx.type().LSB() != null){
            value += " (type: array of ";
        }
        else {
            value += " (type: ";
        }

        if(ctx.type().javaType() != null){
            value += ctx.type().javaType().getText() + ")";
        }
        else {
            value += "[ classType: " + ctx.type().Identifier().getText() + " ])";
        }
        stg.addEntry(key, value);
    }

    @Override
    public void exitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {

    }

    private void createMethodEntry(MiniJavaParser.MethodDeclarationContext ctx){
        String accessModifier = "public";
        if(!ctx.accessModifier().isEmpty())
            accessModifier = ctx.accessModifier().getText();

        String methodName = ctx.Identifier().getText();
        miniJavaClassDetail.addMethod(stg.getCurentNodeName(), methodName);
        String key = "key = method_" + methodName;
        StringBuilder value = new StringBuilder("Value = Method: (name: " + methodName + ")" + "(returnType: " + ctx.returnType().getText() + ") (accessModifier: ACCESS_MODIFIER_" + accessModifier.toUpperCase());

        if(ctx.parameterList() != null){
            int i = 0;
            int paramCount = ctx.parameterList().parameter().size();
            value.append(" (parametersType: ");
            for (;i < paramCount; i ++) {
                if (ctx.parameterList().parameter(i).type().javaType() != null) {
                    value.append("[").append(ctx.parameterList().parameter(i).type().getText()).append(", ").append("index: ").append(i + 1).append("]");
                } else {
                    value.append("[ classType = ").append(ctx.parameterList().parameter(i).type().Identifier().getText()).append(", ").append("index: ").append(i + 1).append("]");
                }
            }
        }
        value.append(")");
        stg.addEntry(key, value.toString());
        stg.enterBlock(methodName, ctx.getStart().getLine());
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        // convert to java
        String output = "\t";
        if( ctx.Override() != null) {
            output = output.concat(ctx.Override().getText() + "\n\t");
        }
        if (!ctx.accessModifier().isEmpty()) {
            output = output.concat(ctx.accessModifier().getText() + " ");
        }
        if (ctx.returnType() != null) {
            output = output.concat(changeType(ctx.returnType().getText()) + " ");
        }

        output = output.concat(ctx.Identifier().getText() + " (");
        if (ctx.parameterList() != null){
            for (int i = 0; i < ctx.parameterList().parameter().size(); i++) {
                if(!(i == ctx.parameterList().parameter().size() - 1)) {
                    output = output.concat(ctx.parameterList().parameter().get(i).type().getText() + " " + ctx.parameterList().parameter().get(i).Identifier() + ", ");
                }
                else {
                    output = output.concat(ctx.parameterList().parameter().get(i).type().getText() + " " + ctx.parameterList().parameter().get(i).Identifier() + " ) {\n");
                }
            }
        }else {
            output = output.concat(") {\n");

        }
        if(ctx.Override() != null)
            System.out.println(ctx.Override().getText());
        System.out.println(output);
        indent ++;

        // create symbol table entry and symbol table
        createMethodEntry(ctx);

    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        indent --;
        if (ctx.methodBody().RETURN() != null)
            System.out.println("\t\treturn " + expressionHandler(ctx.methodBody().expression()) + ";");
        System.out.print("\t}\n");

        stg.exitBlock();
    }

    @Override
    public void enterParameterList(MiniJavaParser.ParameterListContext ctx) {

    }

    @Override
    public void exitParameterList(MiniJavaParser.ParameterListContext ctx) {

    }

    @Override
    public void enterParameter(MiniJavaParser.ParameterContext ctx) {
        String key = "Key = var_" + ctx.Identifier().getText();
        String value = "value = Parameter: (name: " + ctx.Identifier().getText() + ")";
        if(ctx.type().LSB() != null){
            value += " (type: array of ";
        }
        else {
            value += " (type: ";
        }

        if(ctx.type().javaType() != null){
            value += ctx.type().javaType().getText() + ")";
        }
        else {
            value += "[ classType: " + ctx.type().Identifier().getText() + "])";
        }
        stg.addEntry(key, value);
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
        if (!nestedBlockForStatement) {
            printTab(indent);
            indent ++;
        }
        nestedBlockStack.push(nestedBlockForStatement);
        nestedBlockForStatement = false;
        System.out.println("{");
    }

    @Override
    public void exitNestedStatement(MiniJavaParser.NestedStatementContext ctx) {
        boolean status = nestedBlockStack.pop();
        if (! status) {
            indent -= 1;
            printTab(indent);
        }else{
            printTab(indent - 1);
        }
        System.out.println("}");
    }

    @Override
    public void enterIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {
        printTab(indent);
        System.out.print("if (" + expressionHandler(ctx.expression()) + ") ");
        indent ++;
    }

    @Override
    public void exitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {

    }

    @Override
    public void enterWhileStatement(MiniJavaParser.WhileStatementContext ctx) {
        printTab(indent);
        String output = "while ( " + expressionHandler(ctx.expression()) + " ) ";
        if (! ctx.whileBlock().getText().startsWith("{")) {
            output = output.concat(" {");
            System.out.println(output);
        }else{
            nestedBlockForStatement = true;
            System.out.print(output);
        }
        indent ++;
    }

    @Override
    public void exitWhileStatement(MiniJavaParser.WhileStatementContext ctx) {
        indent --;
        if (! ctx.whileBlock().getText().startsWith("{")){
            printTab(indent);
            System.out.println("}");
        }
    }

    @Override
    public void enterPrintStatement(MiniJavaParser.PrintStatementContext ctx) {
        printTab(indent);
        String output = "System.out.println ( " + expressionHandler(ctx.expression()) + " );" ;
        System.out.println(output);
    }

    @Override
    public void exitPrintStatement(MiniJavaParser.PrintStatementContext ctx) {

    }

    @Override
    public void enterVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {
        printTab(indent);
        String output = expressionHandler(ctx.expression().get(0)) + " = " + this.expressionHandler(ctx.expression().get(1)) + ";" ;
        System.out.println(output);
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
        printTab(indent);
        System.out.println(expressionHandler(ctx.expression()) + ";");
    }

    @Override
    public void exitExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {

    }

    @Override
    public void enterIfBlock(MiniJavaParser.IfBlockContext ctx) {
        // convert to java
        if (! ctx.getText().startsWith("{")) {
            System.out.println("{");
        }else{
            nestedBlockForStatement = true;
        }

        // create symbol table
        stg.enterBlock("if", ctx.getStart().getLine());
    }

    @Override
    public void exitIfBlock(MiniJavaParser.IfBlockContext ctx) {
        indent -= 1;
        if (! ctx.getText().endsWith("}")){
            printTab(indent);
            System.out.println("}");
        }
        stg.exitBlock();
    }

    @Override
    public void enterElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        printTab(indent);
        indent += 1;
        System.out.print("else");
        if (! ctx.getText().startsWith("{")) {
            System.out.println("{");
        }else{
            nestedBlockForStatement = true;
        }
        stg.enterBlock("else", ctx.getStart().getLine());
    }

    @Override
    public void exitElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        indent -= 1;
        if (! ctx.getText().endsWith("}")){
            printTab(indent);
            System.out.println("}");
        }
        stg.exitBlock();
    }

    @Override
    public void enterWhileBlock(MiniJavaParser.WhileBlockContext ctx) {
        stg.enterBlock("while", ctx.getStart().getLine());

    }

    @Override
    public void exitWhileBlock(MiniJavaParser.WhileBlockContext ctx) {
        stg.exitBlock();
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


    private void detectAllErrorsInCode(){
        invalidInheritance();
        try {
            miniJavaClassDetail.checkImplementagtion();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    private void invalidInheritance(){
        List<String> cycle = graph.findAllCycleNodes();
        if (cycle.size() > 0) {
            String errorMessage = "Error410 : Invalid inheritance ";
            for(int i = 0 ; i < cycle.size() ; i++){
                errorMessage += cycle.get(i);
                if (i < cycle.size() - 1)
                    errorMessage += " -> ";
            }
            System.err.println(errorMessage);
        }
    }

    private String expressionHandler(MiniJavaParser.ExpressionContext expressionNode){
        if (expressionNode instanceof MiniJavaParser.ArrayInstantiationExpressionContext){
            String t = expressionNode.getChild(1).getText();
            if (t.equals("number")){
                t = "int";
            }
            return "new " + t + "[ " + expressionHandler(((MiniJavaParser.ArrayInstantiationExpressionContext) expressionNode).expression()) + "]" ;
        }else if(expressionNode instanceof MiniJavaParser.ObjectInstantiationExpressionContext){
            return expressionNode.getText().replace("new", "new ");
        }else if(expressionNode instanceof MiniJavaParser.ArrayAccessExpressionContext){
            return expressionHandler(((MiniJavaParser.ArrayAccessExpressionContext) expressionNode).expression(0)) + "[" + expressionHandler(((MiniJavaParser.ArrayAccessExpressionContext) expressionNode).expression(1)) + "]";
        }else if(expressionNode instanceof MiniJavaParser.ArrayLengthExpressionContext){
            return expressionHandler(((MiniJavaParser.ArrayLengthExpressionContext) expressionNode).expression()) + ((MiniJavaParser.ArrayLengthExpressionContext) expressionNode).DOTLENGTH();
        }else if(expressionNode instanceof MiniJavaParser.MethodCallExpressionContext){
            String s = ((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression(0).getText() + '.' + ((MiniJavaParser.MethodCallExpressionContext) expressionNode).Identifier().getText() + "(";
            for (int i=1; i<((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression().size(); i++){
                s = s.concat(expressionHandler(((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression(i)));
                if (i!=((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression().size()-1){
                    s = s.concat(", ");
                }else{
                    s = s.concat(")");
                }
            }
            return s;
        }else if(expressionNode instanceof MiniJavaParser.FieldCallExpressionContext){
            return expressionHandler(expressionNode) + "." + ((MiniJavaParser.FieldCallExpressionContext) expressionNode).Identifier() ;
        }else if(expressionNode instanceof MiniJavaParser.NotExpressionContext){
            return "! " + expressionHandler(((MiniJavaParser.NotExpressionContext) expressionNode).expression());
        }else if(expressionNode instanceof MiniJavaParser.PowExpressionContext){
            return "Math.pow( " + expressionHandler(((MiniJavaParser.PowExpressionContext) expressionNode).expression(0))+ " , " + expressionHandler(((MiniJavaParser.PowExpressionContext) expressionNode).expression(1)) + " )" ;
        }else if(expressionNode instanceof MiniJavaParser.AddExpressionContext) {
            return expressionHandler(((MiniJavaParser.AddExpressionContext) expressionNode).expression(0)) + " + " + expressionHandler(((MiniJavaParser.AddExpressionContext) expressionNode).expression(1))  ;
        }else if(expressionNode instanceof MiniJavaParser.SubExpressionContext) {
            return expressionHandler(((MiniJavaParser.SubExpressionContext) expressionNode).expression(0)) + " - " + expressionHandler(((MiniJavaParser.SubExpressionContext) expressionNode).expression(1)) ;
        }else if(expressionNode instanceof MiniJavaParser.MulExpressionContext) {
            return expressionHandler(((MiniJavaParser.MulExpressionContext) expressionNode).expression(0)) + " * " + expressionHandler(((MiniJavaParser.MulExpressionContext) expressionNode).expression(1)) ;
        }else if(expressionNode instanceof MiniJavaParser.ParenExpressionContext) {
            return "(" +  expressionHandler(((MiniJavaParser.ParenExpressionContext) expressionNode).expression()) + ")";
        }else if(expressionNode instanceof MiniJavaParser.AndExpressionContext) {
            return expressionHandler(((MiniJavaParser.AndExpressionContext) expressionNode).expression(0)) + " && " + expressionHandler(((MiniJavaParser.AndExpressionContext) expressionNode).expression(1)) ;
        }else if(expressionNode instanceof MiniJavaParser.LtExpressionContext) {
            return expressionHandler(((MiniJavaParser.LtExpressionContext) expressionNode).expression(0)) + " < " + expressionHandler(((MiniJavaParser.LtExpressionContext) expressionNode).expression(1)) ;
        }else {
            return expressionNode.getText();
        }
    }
}
