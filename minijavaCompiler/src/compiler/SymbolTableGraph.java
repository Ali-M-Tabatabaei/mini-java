package compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableGraph {
    private final SymbolTable rootNode;
    private SymbolTable currentNode;

    public SymbolTableGraph() {
        rootNode = new SymbolTable("program", null, 0);
        currentNode = rootNode;
    }

    public void addSymbolTable(String name, int line) {
        currentNode.addChild(new SymbolTable(name, currentNode, line));
    }

    public void enterBlock(String name, int lineNumber) {
        currentNode = currentNode.addChild(new SymbolTable(name, currentNode, lineNumber));
    }

    public void exitBlock() {
        currentNode = currentNode.getParent();
    }

    public void printSymbolTable() {
        printNode(rootNode);
    }

    private void printNode(SymbolTable node) {
        node.print();
        for (SymbolTable child : node.getChildren()) {
            printNode(child);
        }
    }

}


