package compiler;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableGraph {
    private final SymbolNode rootNode;
    private SymbolNode currentNode;

    public SymbolTableGraph() {
        rootNode = new SymbolNode(null, null, null);
        currentNode = rootNode;
    }

    public void addSymbol(String name, String type, String val) {
        currentNode.addChild(new SymbolNode(name, type, val, currentNode));
    }

    public void addSymbolMainClass(String SymbolName, String name,  String parent, String val) {
        currentNode.addChild(new SymbolNode(SymbolName, name, parent, val, currentNode));
    }

    public void addSymbolClass(String SymbolName, String name,  String parent, String val, ArrayList<String> implementations) {
        currentNode.addChild(new SymbolNode(SymbolName, name, parent, val, currentNode, implementations));
    }

    public void addSymbolField(String SymbolName, String name, String type, Boolean isDefined, String val) {
        currentNode.addChild(new SymbolNode(SymbolName, name, type, isDefined, val, currentNode));
    }

    public void addSymbolMethod(String access, String symbolName, String name, String type, String returnType, List<String> parameterList, String val) {
        currentNode.addChild(new SymbolNode(access, symbolName, name, type, returnType, parameterList , val, currentNode));
    }

    public boolean containsSymbol(String name, String symbolName) {
        return findNode(name, symbolName) != null;
    }

    public String getSymbolType(String name, String symbolName) {
        SymbolNode node = findNode(name, symbolName);
        return node != null ? node.getType() : null;
    }

    public void enterBlock(String name, int lineNumber) {
        currentNode = currentNode.addChild(new SymbolNode(name, lineNumber, currentNode));
    }


    public void exitBlock() {
        currentNode = currentNode.getParent();
    }

    public void printSymbolTable() {
        printNode(rootNode, 1);
    }

    private void printNode(SymbolNode node, int depth) {
        String dashes = "----------";
        if (node.getLineNumber() != 0) {
            System.out.println(dashes + node.getName() + ": " + node.getLineNumber() + " " + dashes);
        }

//        for (int i = 0; i < depth; i++) {
//
//        }

        for (SymbolNode child : node.getChildren()) {

//            System.out.println(child.getSymbolName() + " " + child.getVal() + " " + child.val);
            printNode(child, depth + 1);
        }
    }

    public SymbolNode updateNode(String name, String symbolName) {
        return findNode(name, symbolName);
    }

    private SymbolNode findNode(String name, String symbolName) {
        SymbolNode node = currentNode;
        while (node != null) {
            for (SymbolNode child : node.getChildren()) {
                if (child.getName() != null && child.getName().equals(name) &&
                        child.getSymbolName() != null && child.getSymbolName().equals(symbolName)) {
                    return child;
                }
            }
            node = node.getParent();
        }
        return null;
    }

    public static class SymbolNode {
        private final String name;
        private String type;
        private Boolean isDefined;
        private String returnType;
        private String[] parameterList;
        private ArrayList<String> implementations;
        private String val;
        private int lineNumber = 0;
        private String symbolName;
        private String parentName;
        private final SymbolNode parent;
        private final List<SymbolNode> children;
        private String accessModifier ;

        public SymbolNode(String name, int lineNumber, SymbolNode parent) {
            this.name = name;
            this.lineNumber = lineNumber;
            this.parent = parent;
            children = new ArrayList<>();
        }



        public SymbolNode (String name, String type, SymbolNode parent) {
            this.name = name;
            this.type = type;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public SymbolNode(String name, String type, String val, SymbolNode parent) {
            this.name = name;
            this.type = type;
            this.val = val;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public SymbolNode(String symbolName, String name, String type, Boolean isDefined, String val, SymbolNode parent) {
            this.name = name;
            this.type = type;
            this.isDefined = isDefined;
            this.val = val;
            this.symbolName = symbolName;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public SymbolNode(String access, String symbolName, String name, String type, String returnType, List<String> parameterList, String val, SymbolNode parent) {
            this.name = name;
            this.accessModifier = access;
            this.type = type;
            this.returnType = returnType;
            this.parameterList = parameterList.toArray(new String[0]);
            this.val = val;
            this.symbolName = symbolName;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public SymbolNode(String symbolName, String name, String parentName, String val, SymbolNode parent) {
            this.name = name;
            this.symbolName = symbolName;
            this.parentName = parentName;
            this.val = val;
            this.parent = parent;
            children = new ArrayList<>();
        }

        public SymbolNode(String symbolName, String name, String parentName, String val, SymbolNode parent, ArrayList<String> implementList) {
            this.name = name;
            this.symbolName = symbolName;
            this.parentName = parentName;
            this.val = val;
            this.implementations = implementList;
            this.parent = parent;
            children = new ArrayList<>();

        }

        public void setDefined(Boolean defined) {
            isDefined = defined;
        }

        public String getSymbolName() {
            return symbolName;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public SymbolNode getParent() {
            return parent;
        }

        public List<SymbolNode> getChildren() {
            return children;
        }

        public Boolean getDefined() {
            return isDefined;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getReturnType() {
            return returnType;
        }

        public String getVal() {
            return val;
        }

        public String[] getParameterList() {
            return parameterList;
        }

        public String getParentName() {
            return parentName;
        }

        public SymbolNode addChild(SymbolNode child) {
            children.add(child);
            return child;
        }
    }

//    public void populateSymbolTable() {
//        // Add symbols for outer block
////        addSymbol("Human", "Class");
//
//        // Enter nested block
//        enterBlock("Human", "Class");
//
//        // Add symbols for nested block
//        addSymbol("nose", "Nose");
//        addSymbol("hand", "Hand");
//        addSymbol("leg", "Leg");
//        addSymbol("calories", "int");
//        addSymbol("isHungry", "bool");
//
////        addSymbol("Human", "Constructor");
//        enterBlock("Human", "Constructor");
//
//        addSymbol("parameters", "Nose n");
//
//        exitBlock();
//
////        addSymbol("speak", "Voice");
//        enterBlock("speak", "Voice");
//        addSymbol("voice", "Voice");
//        exitBlock();
//
////        addSymbol("eat", "void");
//        enterBlock("eat", "void");
//        addSymbol("parameters", "Food food, int c");
//        addSymbol("newFood", "Food");
//
//        enterBlock("while", "scope");
//        addSymbol("newFood", "Food");
//
//
//        enterBlock("if", "scope");
//        enterBlock("if", "scope");
//        exitBlock();
//        exitBlock();
//
//        exitBlock();
//        exitBlock();
//
//
//
//
//
//
//    }






}
