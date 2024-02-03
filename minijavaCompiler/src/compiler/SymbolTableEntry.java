package compiler;

public class SymbolTableEntry{
    public String key;
    public String value;
    public SymbolTableEntry(String key, String value){
        this.key = key;
        this.value = value;
    }

    public void print(){
        System.out.print(key + "\t|\t");
        System.out.println(value);
    }
}
