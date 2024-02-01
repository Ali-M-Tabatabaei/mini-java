// Generated from /home/alimtt/Documents/GitHub/mini-java/minijavaCompiler/grammar/MiniJava.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaParser}.
 */
public interface MiniJavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MiniJavaParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MiniJavaParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(MiniJavaParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(MiniJavaParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void enterMainMethod(MiniJavaParser.MainMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void exitMainMethod(MiniJavaParser.MainMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MiniJavaParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MiniJavaParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MiniJavaParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MiniJavaParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(MiniJavaParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(MiniJavaParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MiniJavaParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MiniJavaParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanType}
	 * labeled alternative in {@link MiniJavaParser#javaType}.
	 * @param ctx the parse tree
	 */
	void enterBooleanType(MiniJavaParser.BooleanTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanType}
	 * labeled alternative in {@link MiniJavaParser#javaType}.
	 * @param ctx the parse tree
	 */
	void exitBooleanType(MiniJavaParser.BooleanTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(MiniJavaParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(MiniJavaParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessModifier(MiniJavaParser.AccessModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#accessModifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessModifier(MiniJavaParser.AccessModifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterNestedStatement(MiniJavaParser.NestedStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitNestedStatement(MiniJavaParser.NestedStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifElseStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfElseStatement(MiniJavaParser.IfElseStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifElseStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MiniJavaParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MiniJavaParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code printStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(MiniJavaParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code printStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(MiniJavaParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code localVarDeclaration}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code localVarDeclaration}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressioncall}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressioncall(MiniJavaParser.ExpressioncallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressioncall}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressioncall(MiniJavaParser.ExpressioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void enterIfBlock(MiniJavaParser.IfBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#ifBlock}.
	 * @param ctx the parse tree
	 */
	void exitIfBlock(MiniJavaParser.IfBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void enterElseBlock(MiniJavaParser.ElseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#elseBlock}.
	 * @param ctx the parse tree
	 */
	void exitElseBlock(MiniJavaParser.ElseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void enterWhileBlock(MiniJavaParser.WhileBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#whileBlock}.
	 * @param ctx the parse tree
	 */
	void exitWhileBlock(MiniJavaParser.WhileBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLtExpression(MiniJavaParser.LtExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLtExpression(MiniJavaParser.LtExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPowExpression(MiniJavaParser.PowExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPowExpression(MiniJavaParser.PowExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(MiniJavaParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(MiniJavaParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(MiniJavaParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(MiniJavaParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(MiniJavaParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(MiniJavaParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpression(MiniJavaParser.AddExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpression(MiniJavaParser.AddExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpression(MiniJavaParser.ThisExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpression(MiniJavaParser.ThisExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayLengthExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayLengthExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intarrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intarrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpression(MiniJavaParser.SubExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpression(MiniJavaParser.SubExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpression(MiniJavaParser.MulExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpression(MiniJavaParser.MulExpressionContext ctx);
}