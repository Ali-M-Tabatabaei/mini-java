package compiler;

import gen.MiniJavaListener;
import gen.MiniJavaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.lang.Math;

import java.util.*;

public class ProgramOptimizer implements MiniJavaListener {
    Stack<OptimizationSymbolTable> currentScope;
    Queue<OptimizationSymbolTable> scopes;
    int nested = 0;
    int id = 0;
    int indent_level = 0;
    boolean print_enable ;  // This is for second code walk
    Iterator scopeIterator ; // Iterate for second phase
    boolean block_print_disable = false;
    int disable_block_id = 0;
    String if_condition = null;

    public ProgramOptimizer() {
        this.currentScope = new Stack<OptimizationSymbolTable>();
        this.scopes = new LinkedList<OptimizationSymbolTable>();
        this.print_enable = false;
    }

    private void printResult() {
        Iterator it = this.scopes.iterator();
        while (it.hasNext()){
            OptimizationSymbolTable s = ((OptimizationSymbolTable)it.next());
            s.print();
        }
    }

    private void tabPrint(int tabCount){
        for (int i=0; i<tabCount; i++)
            System.out.print("\t");
    }

    private void setVariablesSeen(ArrayList<String> visitedVariables){
        Stack<OptimizationSymbolTable> checkScope = new Stack<OptimizationSymbolTable>();;
        for(int i=0; i<visitedVariables.size(); i++){
            String visitedVariableName = visitedVariables.get(i);
            while(currentScope.peek().parentId != 0){
                // System.out.println("Check for " + visitedVariableName + " in " + currentScope.peek().name);
                if (currentScope.peek().symbolTable.containsKey(visitedVariableName)) {
                    currentScope.peek().symbolTable.replace(visitedVariableName, new OptimizationSymbolTableEntry(visitedVariableName, true));
                }
                checkScope.push(currentScope.pop());
            }
            while (!checkScope.empty()){
                currentScope.push(checkScope.pop());
            }
        }
    }


    private void printTree(ParserRuleContext context, String stopLexeme){
        String treeString = "" ;
        for (int i=0; i<context.getChildCount(); i++){
            ParseTree c = context.getChild(i);
            if (c instanceof MiniJavaParser.ParameterListContext){
                for (int j=0; j<((MiniJavaParser.ParameterListContext) c).parameter().size(); j++){
                    treeString += ((MiniJavaParser.ParameterListContext) c).parameter(j).type().getText() + " " +
                            ((MiniJavaParser.ParameterListContext) c).parameter(j).Identifier().getText();

                    if (j != ((MiniJavaParser.ParameterListContext) c).parameter().size()-1)
                        treeString += "," ;
                }
            }else if(c instanceof MiniJavaParser.ObjectInstantiationExpressionContext){
                treeString += "new " + ((MiniJavaParser.ObjectInstantiationExpressionContext) c).Identifier().getText() + "()" ;
            }else {
                treeString += c.getText() + " ";
            }
            if (context.getChild(i).getText().equals(stopLexeme))
                break;
        }
        tabPrint(indent_level);
        System.out.println(treeString);
    }

    private String getSubTree(ParserRuleContext context, String stopLexeme){
        String treeString = "" ;
        for (int i=0; i<context.getChildCount(); i++){
            treeString += context.getChild(i).getText() + " ";
            if (context.getChild(i).getText().equals(stopLexeme))
                break;
        }
        return treeString;
    }

    private ArrayList<String> getExpressionUsedVariables(MiniJavaParser.ExpressionContext expressionContext, ArrayList<String> usedVars){
        // This function get an expression tree and return all used variables in the expression in a ArrayList
        if (expressionContext == null)
            return usedVars;
        for (int i = 0; i < expressionContext.getChildCount(); i++) {  // Iterate over all child of expression
            ParseTree child = expressionContext.getChild(i);
            // Check type of child and extract used variables from it
            if (child instanceof MiniJavaParser.ArrayAccessExpressionContext) {
                usedVars.add(child.getChild(0).getText());
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.ArrayAccessExpressionContext) child).index, usedVars).clone();
            }else if(child instanceof MiniJavaParser.ArrayLengthExpressionContext){
                usedVars.add(child.getChild(0).getText());
            }else if(child instanceof MiniJavaParser.MethodCallExpressionContext){
                usedVars.add(child.getChild(0).getText());
                for (int j = 0; j < ((MiniJavaParser.MethodCallExpressionContext) child).expression().size(); j++){
                    usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.MethodCallExpressionContext) child).expression().get(j), usedVars).clone();
                }
            }else if(child instanceof MiniJavaParser.FieldCallExpressionContext){
                usedVars.add(child.getChild(0).getText());
            }else if(child instanceof MiniJavaParser.NotExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.NotExpressionContext) child).expression(), usedVars).clone();
            }else if(child instanceof MiniJavaParser.ArrayInstantiationExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.ArrayInstantiationExpressionContext) child).expression(), usedVars).clone();
            }else if(child instanceof MiniJavaParser.ArrayInstantiationExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.ArrayInstantiationExpressionContext) child).expression(), usedVars).clone();
            }else if(child instanceof MiniJavaParser.PowExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.PowExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.PowExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.MulExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.MulExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.MulExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.AddExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.AddExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.AddExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.SubExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.SubExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.SubExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.LtExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.LtExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.LtExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.AndExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.AndExpressionContext) child).expression(0), usedVars).clone();
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.AndExpressionContext) child).expression(1), usedVars).clone();
            }else if(child instanceof MiniJavaParser.ParenExpressionContext){
                usedVars = (ArrayList<String>) getExpressionUsedVariables(((MiniJavaParser.ParenExpressionContext) child).expression(), usedVars).clone();
            }else if(child instanceof MiniJavaParser.IdentifierExpressionContext){
                usedVars.add(child.getText());
            }
        }
        return usedVars;
    }

    private int constantFolding(MiniJavaParser.ExpressionContext expressionContext) {
        // This function get an expression tree and evaluate it
        // If it has letter in its string, constant folding can not be applied and Integer.MAX_VALUE will be returned
        for (char c : expressionContext.getText().toCharArray()) {
            if (Character.isLetter(c)) {
                return Integer.MAX_VALUE;
            }
        }
        // Evaluate supported operations in Mini-Java
        if (expressionContext instanceof MiniJavaParser.PowExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.PowExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.PowExpressionContext) expressionContext).expression(1));
            return (int)Math.pow(operand1, operand2);
        } else if (expressionContext instanceof MiniJavaParser.MulExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.MulExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.MulExpressionContext) expressionContext).expression(1));
            return operand1*operand2;
        } else if (expressionContext instanceof MiniJavaParser.AddExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.AddExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.AddExpressionContext) expressionContext).expression(1));
            return operand1+operand2;
        } else if (expressionContext instanceof MiniJavaParser.SubExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.SubExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.SubExpressionContext) expressionContext).expression(1));
            return operand1-operand2;
        } else if (expressionContext instanceof MiniJavaParser.LtExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.LtExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.LtExpressionContext) expressionContext).expression(1));
            if (operand1 < operand2){
                return 1;
            }else{
                return 0;
            }
        } else if (expressionContext instanceof MiniJavaParser.AndExpressionContext) {
            int operand1 = constantFolding(((MiniJavaParser.AndExpressionContext) expressionContext).expression(0));
            int operand2 = constantFolding(((MiniJavaParser.AndExpressionContext) expressionContext).expression(1));
            if (operand1 != 0 && operand2 != 0){
                return 1;
            }else{
                return 0;
            }
        } else if (expressionContext instanceof MiniJavaParser.NotExpressionContext){
            int operand1 = constantFolding(((MiniJavaParser.NotExpressionContext) expressionContext).expression());
            if (operand1 != 0){
                return 0;
            }else{
                return 1;
            }
        } else if (expressionContext instanceof MiniJavaParser.ParenExpressionContext) {
            return constantFolding(((MiniJavaParser.ParenExpressionContext) expressionContext).expression());
        } else if(expressionContext instanceof MiniJavaParser.IntLitExpressionContext){
            return Integer.parseInt(expressionContext.getText());
        } else if(expressionContext instanceof MiniJavaParser.BooleanLitExpressionContext){
            if (expressionContext.getText().equals("true")){
                return 1;
            }else{
                return 0;
            }
        }
        // Return Integer.MIN_VALUE for unsupported operations
        return Integer.MIN_VALUE;
    }

    private String getFolded(MiniJavaParser.ExpressionContext expressionNode){
        int constantFoldingValue = constantFolding(expressionNode);
        if (constantFoldingValue == Integer.MAX_VALUE){
            return getSubTree(expressionNode, expressionNode.getChild(expressionNode.getChildCount()-1).getText());
        }else{
            return String.valueOf(constantFoldingValue);
        }
    }

    private String getExpression(MiniJavaParser.ExpressionContext expressionNode) {
        if (expressionNode instanceof MiniJavaParser.ArrayInstantiationExpressionContext) {
            return getSubTree(expressionNode, "[") + getExpression(((MiniJavaParser.ArrayInstantiationExpressionContext) expressionNode).expression()) + "]" ;
        } else if (expressionNode instanceof MiniJavaParser.ObjectInstantiationExpressionContext) {
            return expressionNode.getText().replace("new", "new ");
        } else if (expressionNode instanceof MiniJavaParser.ArrayAccessExpressionContext) {
            return getExpression(((MiniJavaParser.ArrayAccessExpressionContext) expressionNode).expression(0)) + "[" + getExpression(((MiniJavaParser.ArrayAccessExpressionContext) expressionNode).expression(1)) + "]";
        } else if (expressionNode instanceof MiniJavaParser.ArrayLengthExpressionContext) {
            return getExpression(((MiniJavaParser.ArrayLengthExpressionContext) expressionNode).expression()) + ((MiniJavaParser.ArrayLengthExpressionContext) expressionNode).DOTLENGTH();
        } else if (expressionNode instanceof MiniJavaParser.MethodCallExpressionContext) {
            String s = ((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression(0).getText() + '.' + ((MiniJavaParser.MethodCallExpressionContext) expressionNode).Identifier().getText() + "(";
            for (int i = 1; i < ((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression().size(); i++) {
                s = s.concat(getExpression(((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression(i)));
                if (i != ((MiniJavaParser.MethodCallExpressionContext) expressionNode).expression().size() - 1) {
                    s = s.concat(", ");
                } else {
                    s = s.concat(")");
                }
            }
            return s;
        } else if (expressionNode instanceof MiniJavaParser.FieldCallExpressionContext) {
            return getExpression(expressionNode) + "." + ((MiniJavaParser.FieldCallExpressionContext) expressionNode).Identifier();
        } else if (expressionNode instanceof MiniJavaParser.NotExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.PowExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.AddExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.SubExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.MulExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.ParenExpressionContext) {
            return "(" + getExpression(((MiniJavaParser.ParenExpressionContext) expressionNode).expression()) + ")";
        } else if (expressionNode instanceof MiniJavaParser.AndExpressionContext) {
            return getFolded(expressionNode);
        } else if (expressionNode instanceof MiniJavaParser.LtExpressionContext) {
            return getFolded(expressionNode);
        } else {
            return expressionNode.getText();
        }
    }

    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        indent_level = 0;
        if (!print_enable) {
            OptimizationSymbolTable s = new OptimizationSymbolTable("Program", id++, 0);
            this.currentScope.push(s);
            this.scopes.add(s);
        }else{
            scopeIterator = this.scopes.iterator();
            currentScope.clear();
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {
        if (! print_enable) {
            this.printResult();
            print_enable = true;
        }
    }

    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
        if (print_enable) {
            printTree(ctx, "{");
            indent_level += 1;
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else{
            // created this scopes Symbol table
            String name = "MainClass_" + ctx.className.getText();
            int parentId =this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
        }
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        if (print_enable) {
            indent_level -= 1;
            System.out.println(ctx.getChild(ctx.getChildCount() - 1));
        }
        this.currentScope.pop();
    }

    @Override
    public void enterMainMethod(MiniJavaParser.MainMethodContext ctx) {
        if (print_enable) {
            printTree(ctx, "{");
            indent_level += 1;
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else{
            // created this scopes Symbol table
            String name = "method_main";
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
        }

    }

    @Override
    public void exitMainMethod(MiniJavaParser.MainMethodContext ctx) {
        if (print_enable) {
            indent_level -= 1;
            tabPrint(indent_level);
            System.out.println(ctx.getChild(ctx.getChildCount() - 1));
        }
        this.currentScope.pop();
    }

    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        if (print_enable) {
            printTree(ctx, "{");
            indent_level += 1;
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else{
            // created this scopes Symbol table
            String name = "Class_" + ctx.className.getText();
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
        }
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        if (print_enable) {
            indent_level -= 1;
            tabPrint(indent_level);
            System.out.println(ctx.getChild(ctx.getChildCount() - 1));
        }
        this.currentScope.pop();
    }

    @Override
    public void enterInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        if (print_enable) {
            printTree(ctx, "{");
            indent_level += 1;
        }else{
            // created this scope's Symbol table
            String name = "interface_" + ctx.Identifier().getText();
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
        }
    }

    @Override
    public void exitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        if (print_enable) {
            indent_level -= 1;
            tabPrint(indent_level);
            System.out.println(ctx.getChild(ctx.getChildCount() - 1));
        }else{
            this.currentScope.pop();
        }
    }

    @Override
    public void enterInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {
        if (print_enable)
            printTree(ctx, ";");
    }

    @Override
    public void exitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {

    }

    @Override
    public void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {
        String key = ctx.Identifier().getText();

        if (print_enable && !block_print_disable) {
            if ((currentScope.peek().symbolTable.containsKey(key) && currentScope.peek().symbolTable.get(key).value) || currentScope.peek().name.contains("interface")){
                if (ctx.expression() != null) {
//                    int constantFoldingResult = constantFolding(ctx.expression());
//                    if (constantFoldingResult == Integer.MAX_VALUE) {
//                        printTree(ctx, ";");
//                    } else {
//                        tabPrint(indent_level);
//                        System.out.println(getSubTree(ctx, "=") + constantFoldingResult + " ;");
//                    }
                    System.out.println(getSubTree(ctx, "=") + getExpression(ctx.expression()) + " ;");
                }else{
                    printTree(ctx, ";");
                }
            }
        }else{
            // created this line's Symbol table entry
            OptimizationSymbolTableEntry entry = new OptimizationSymbolTableEntry(key, false);
            this.currentScope.peek().symbolTable.put(key, entry);
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
        }
    }

    @Override
    public void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void enterLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {
        String key = ctx.Identifier().getText();
        if (print_enable && !block_print_disable){
            if (currentScope.peek().symbolTable.containsKey(key) && currentScope.peek().symbolTable.get(key).value){
                printTree(ctx, ";");
            }
        }else {
            // created this line's Symbol table entry
            OptimizationSymbolTableEntry entry = new OptimizationSymbolTableEntry(key, false);
            this.currentScope.peek().symbolTable.put(key, entry);
        }
    }

    @Override
    public void exitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {

    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        if(print_enable){
            printTree(ctx, "{");
            indent_level += 1;
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else {
            // created this scope's Symbol table
            String name = "method_" + ctx.Identifier().getText();
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
        }
    }

    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.methodBody().expression(), new ArrayList<String>()));
        }else{
            if (ctx.methodBody().expression() != null) {
//                int constantFoldingResult = constantFolding(ctx.methodBody().expression());
//                if (constantFoldingResult == Integer.MAX_VALUE) {
//                    tabPrint(indent_level);
//                    System.out.println("ret " + ctx.methodBody().expression().getText() + " ;");
//
//                } else {
//                    tabPrint(indent_level);
//                    System.out.println("ret " + constantFoldingResult + " ;");
//                }
                tabPrint(indent_level);
                System.out.println("ret " + getExpression(ctx.methodBody().expression()) + " ;");
            }
            indent_level -= 1;
            tabPrint(indent_level);
            System.out.println("}");
        }
        this.currentScope.pop();
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
        if (! print_enable) {
            this.nested++;
        }else{
            if (!block_print_disable) {
                tabPrint(indent_level);
                System.out.println("{");
            }
            indent_level += 1;
        }
    }

    @Override
    public void exitNestedStatement(MiniJavaParser.NestedStatementContext ctx) {
        if (! print_enable) {
            this.nested--;
        }else{
            indent_level -= 1;
            if (! block_print_disable) {
                tabPrint(indent_level);
                System.out.println("}");
            }
        }
    }

    @Override
    public void enterIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {
        if (print_enable && !block_print_disable){
//            int constantFoldingResult = constantFolding(ctx.expression());
//            if (constantFoldingResult == Integer.MAX_VALUE) {
//                if (ctx.expression().getText().equals("false")) {
//                    block_print_disable = true;
//                    disable_block_id = currentScope.peek().id ;
//                    if_condition = getSubTree(ctx, ")");
//                }else {
//                    printTree(ctx, ")");
//                }
//            } else {
//                if (constantFoldingResult == 0) {
//                    block_print_disable = true;
//                    disable_block_id = currentScope.peek().id ;
//                    if_condition = getSubTree(ctx, ")");
//                }else {
//                    tabPrint(indent_level);
//                    System.out.println(getSubTree(ctx, "( ") + constantFoldingResult + " )");
//                }
//            }
            String exp = getExpression(ctx.expression());
            if (exp.equals("false") || exp.equals("0")){
                block_print_disable = true;
                disable_block_id = currentScope.peek().id ;
                if_condition = getSubTree(ctx, ")");
            }else{
                tabPrint(indent_level);
                System.out.println(getSubTree(ctx, "(") + exp + " )");
            }
        }else {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
        }
    }

    @Override
    public void exitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {

    }

    @Override
    public void enterWhileStatement(MiniJavaParser.WhileStatementContext ctx) {
        if (print_enable && !block_print_disable){
//            int constantFoldingResult = constantFolding(ctx.expression());
//            if (constantFoldingResult == Integer.MAX_VALUE) {
//                if (ctx.expression().getText().equals("false")){
//                    block_print_disable = true;
//                    disable_block_id = this.currentScope.peek().id ;
//                }else {
//                    printTree(ctx, ")");
//                }
//            } else {
//                if (constantFoldingResult == 0){
//                    block_print_disable = true;
//                    disable_block_id = this.currentScope.peek().id ;
//                }else {
//                    tabPrint(indent_level);
//                    System.out.println(getSubTree(ctx, "(") + constantFoldingResult + " )");
//                }
//            }
            String exp = getExpression(ctx.expression());
            if (exp.equals("false") || exp.equals("0")){
                block_print_disable = true;
                disable_block_id = currentScope.peek().id ;
                if_condition = getSubTree(ctx, ")");
            }else{
                tabPrint(indent_level);
                System.out.println(getSubTree(ctx, "(") + exp + " )");
            }
        }else {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
        }
    }

    @Override
    public void exitWhileStatement(MiniJavaParser.WhileStatementContext ctx) {

    }

    @Override
    public void enterPrintStatement(MiniJavaParser.PrintStatementContext ctx) {
        if (print_enable && !block_print_disable){
//            int constantFoldingResult = constantFolding(ctx.expression());
//            if (constantFoldingResult == Integer.MAX_VALUE) {
//                printTree(ctx, ";");
//            } else {
//                tabPrint(indent_level);
//                System.out.println(getSubTree(ctx, "(") + constantFoldingResult + " ) ;");
//            }
            tabPrint(indent_level);
            System.out.println(getSubTree(ctx, "(") + getExpression(ctx.expression()) + " ) ;");
        }else {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
        }
    }

    @Override
    public void exitPrintStatement(MiniJavaParser.PrintStatementContext ctx) {

    }

    @Override
    public void enterVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {
        if (print_enable && !block_print_disable){
//            int constantFoldingResult = constantFolding(ctx.expression(1));
//            if (constantFoldingResult == Integer.MAX_VALUE) {
//                printTree(ctx, ";");
//            }else{
//                tabPrint(indent_level);
//                System.out.println(ctx.expression(0).getText() + " = " + constantFoldingResult + " ;");
//            }
            tabPrint(indent_level);
            System.out.println(getExpression(ctx.expression(0)) + " = " + getExpression(ctx.expression(1)));
        }else {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
    }

    @Override
    public void exitVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {

    }

    @Override
    public void enterArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx) {
        if (print_enable && !block_print_disable){
//            int constantFoldingResult1 = constantFolding(ctx.expression(0));
//            int constantFoldingResult2 = constantFolding(ctx.expression(1));
//            if (constantFoldingResult1 == Integer.MAX_VALUE && constantFoldingResult2 == Integer.MAX_VALUE) {
//                printTree(ctx, ")");
//            } else if (constantFoldingResult1 != Integer.MAX_VALUE && constantFoldingResult2 == Integer.MAX_VALUE){
//                tabPrint(indent_level);
//                System.out.println(getSubTree(ctx, "[") + constantFoldingResult1 + "] = " + ctx.expression(1).getText());
//            }else if (constantFoldingResult1 == Integer.MAX_VALUE && constantFoldingResult2 != Integer.MAX_VALUE){
//                tabPrint(indent_level);
//                System.out.println(getSubTree(ctx, "=") + constantFoldingResult2);
//            }else{
//                tabPrint(indent_level);
//                System.out.println(getSubTree(ctx, "[") + constantFoldingResult1 + "] = " + constantFoldingResult2);
//            }
            tabPrint(indent_level);
            System.out.println(getSubTree(ctx, "[") + getExpression(ctx.expression(0)) + "] = " + getExpression(ctx.expression(1)));
        }else {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
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
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
        }else{
//            int constantFoldingValue = constantFolding(ctx.expression());
//            if (constantFoldingValue == Integer.MAX_VALUE){
//                printTree(ctx, ";");
//            }else {
//                tabPrint(indent_level);
//                System.out.println(constantFoldingValue + " ;");
//            }
            tabPrint(indent_level);
            System.out.println(getExpression(ctx.expression()) + " ;");
        }
    }

    @Override
    public void exitExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {

    }

    @Override
    public void enterIfBlock(MiniJavaParser.IfBlockContext ctx) {
        if (print_enable){
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else {
            String name;
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            if (this.nested > 0) {
                name = "nested_if";
            } else {
                name = "if";
            }

            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
            this.nested++;
        }
    }

    @Override
    public void exitIfBlock(MiniJavaParser.IfBlockContext ctx) {
        if (! print_enable)
            this.nested--;
        this.currentScope.pop();
        if (print_enable && block_print_disable && this.currentScope.peek().id == disable_block_id)
            block_print_disable = false;
    }

    @Override
    public void enterElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        if (print_enable && ! block_print_disable) {
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
            tabPrint(indent_level);
            if(if_condition == null) {
                System.out.println("else");
            }else{
                System.out.println("if ( <> " + if_condition.substring(if_condition.indexOf("(")+1));
                if_condition = null;
            }
        }else{
            String name;
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            if (this.nested > 0) {
                name = "nested_else";
            } else {
                name = "else";
            }

            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
            this.nested++;
        }
    }

    @Override
    public void exitElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        if (! print_enable)
            this.nested--;
        this.currentScope.pop();
        if (print_enable &&  block_print_disable &&this.currentScope.peek().id == disable_block_id)
            block_print_disable = false;
    }

    @Override
    public void enterWhileBlock(MiniJavaParser.WhileBlockContext ctx) {
        if (print_enable) {
            currentScope.push((OptimizationSymbolTable)scopeIterator.next());
        }else{
            String name;
            int parentId = this.currentScope.peek().id;
            int line = ctx.getStart().getLine();
            if (this.nested > 0) {
                name = "nested_while";
            } else {
                name = "while";
            }

            OptimizationSymbolTable table = new OptimizationSymbolTable(name, id++, parentId, line);
            this.currentScope.push(table);
            this.scopes.add(table);
            this.nested++;
        }
    }

    @Override
    public void exitWhileBlock(MiniJavaParser.WhileBlockContext ctx) {
        if (! print_enable)
            this.nested--;
        this.currentScope.pop();
        if (print_enable && block_print_disable && this.currentScope.peek().id == disable_block_id)
            block_print_disable = false;
    }

    @Override
    public void enterLtExpression(MiniJavaParser.LtExpressionContext ctx) {
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
        setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
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
        if (print_enable && !block_print_disable){
//            int constantFoldingResult = constantFolding(ctx.expression());
//            if (constantFoldingResult == Integer.MAX_VALUE) {
//                printTree(ctx, ";");
//            }else{
//                tabPrint(indent_level);
//                System.out.println(getSubTree(ctx, "[") + constantFoldingResult + "] ;");
//            }
        }
    }

    @Override
    public void exitArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterPowExpression(MiniJavaParser.PowExpressionContext ctx) {
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
        setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
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
        if (! print_enable) {
            for (int i = 0; i < ctx.expression().size(); i++) {
                setVariablesSeen(getExpressionUsedVariables(ctx.expression(i), new ArrayList<String>()));
            }
        }
    }

    @Override
    public void exitMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx) {

    }

    @Override
    public void enterNotExpression(MiniJavaParser.NotExpressionContext ctx) {
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
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
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
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
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
    }

    @Override
    public void exitAndExpression(MiniJavaParser.AndExpressionContext ctx) {

    }

    @Override
    public void enterArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
    }

    @Override
    public void exitArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {

    }

    @Override
    public void enterAddExpression(MiniJavaParser.AddExpressionContext ctx) {
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
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
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
    }

    @Override
    public void exitFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx) {

    }

    @Override
    public void enterArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx) {
        if (! print_enable)
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(), new ArrayList<String>()));
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
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
    }

    @Override
    public void exitSubExpression(MiniJavaParser.SubExpressionContext ctx) {

    }

    @Override
    public void enterMulExpression(MiniJavaParser.MulExpressionContext ctx) {
        if (! print_enable) {
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(0), new ArrayList<String>()));
            setVariablesSeen(getExpressionUsedVariables(ctx.expression(1), new ArrayList<String>()));
        }
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


class OptimizationSymbolTable{
    public String name;
    public int id;
    public int parentId;
    public int line = 1;
    public Map<String, OptimizationSymbolTableEntry> symbolTable;

    public OptimizationSymbolTable(String name, int id, int parentId){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.id = id;
        this.parentId = parentId;
    }
    public OptimizationSymbolTable(String name, int id, int parentId, int line){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.id = id;
        this.parentId = parentId;
        this.line = line;
    }

    public void print(){
        System.out.println("-------------- " + this.name + ": " + this.line + " --------------");
        if (!this.symbolTable.isEmpty()){
            for(Map.Entry<String, OptimizationSymbolTableEntry> entry : this.symbolTable.entrySet()){
                entry.getValue().print();
            }
        }
        System.out.println("--------------------------------------------------------\n");

    }
}

class OptimizationSymbolTableEntry{
    public String key;
    public Boolean value;
    public OptimizationSymbolTableEntry(String key, Boolean value){
        this.key = key;
        this.value = value;
    }

    public void print(){
        System.out.print(key + "\t|\t");
        System.out.println(value);
    }
}