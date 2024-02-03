package compiler;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable{
    public String name;
    public int id;
    public SymbolTable parent;
    public int line = 1;
    public Map<String, SymbolTableEntry> symbolTable;
    private List<SymbolTable> children;

    public SymbolTable(String name, int id, SymbolTable parent){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    public SymbolTable(String name, int id, SymbolTable parent, int line){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.line = line;
        this.children = new ArrayList<>();
    }

    public void print(){
        System.out.println("-------------- " + this.name + ": " + this.line + " --------------");
        if (!this.symbolTable.isEmpty()){
            for(Map.Entry<String, SymbolTableEntry> entry : this.symbolTable.entrySet()){
                entry.getValue().print();
            }
        }
        System.out.println("--------------------------------------------------------\n");

    }
    public SymbolTable addChild(SymbolTable child) {
        children.add(child);
        return child;
    }
}