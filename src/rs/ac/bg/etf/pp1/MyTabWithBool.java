package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class MyTabWithBool extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);

    public static void init() {
        Tab.init();
        MyTabWithBool.insert(Obj.Type, "bool", MyTabWithBool.boolType);
    }
   
	
}
