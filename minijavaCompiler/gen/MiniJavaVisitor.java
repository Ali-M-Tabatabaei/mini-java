// Generated from /home/alimtt/Documents/GitHub/mini-java/minijavaCompiler/grammar/MiniJava.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniJavaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniJavaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MiniJavaParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#mainClass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainClass(MiniJavaParser.MainClassContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#mainMethod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainMethod(MiniJavaParser.MainMethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#localDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MiniJavaParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MiniJavaParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(MiniJavaParser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniJavaParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanType}
	 * labeled alternative in {@link MiniJavaParser#javaType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanType(MiniJavaParser.BooleanTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MiniJavaParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#accessModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessModifier(MiniJavaParser.AccessModifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedStatement(MiniJavaParser.NestedStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifElseStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MiniJavaParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code printStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(MiniJavaParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAssignmentStatement}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code localVarDeclaration}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressioncall}
	 * labeled alternative in {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressioncall(MiniJavaParser.ExpressioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(MiniJavaParser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#elseBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElseBlock(MiniJavaParser.ElseBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJavaParser#whileBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileBlock(MiniJavaParser.WhileBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ltExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLtExpression(MiniJavaParser.LtExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code objectInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExpression(MiniJavaParser.PowExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(MiniJavaParser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpression(MiniJavaParser.ParenExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullLitExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(MiniJavaParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpression(MiniJavaParser.AddExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpression(MiniJavaParser.ThisExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldCallExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayLengthExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intarrayInstantiationExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpression(MiniJavaParser.SubExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulExpression}
	 * labeled alternative in {@link MiniJavaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpression(MiniJavaParser.MulExpressionContext ctx);
}