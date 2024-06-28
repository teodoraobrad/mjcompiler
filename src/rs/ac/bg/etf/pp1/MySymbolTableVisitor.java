package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class MySymbolTableVisitor extends SymbolTableVisitor {

	private StringBuilder output = new StringBuilder();

	private int tab = 0;
	private int spaces = 2; //po tabu
	private String stringToInsert = "";

	private boolean detailed = true; //detaljno ili ne
	
	public MySymbolTableVisitor() {
		super();
	}

	public MySymbolTableVisitor(boolean op) {
		this.detailed = op;
	}

	@Override
	public void visitObjNode(Obj objToVisit) {

		// kind name: getAdr getLevel
		output.append(objKindToString(objToVisit.getKind()));
		output.append(" ");
		output.append(objToVisit.getName());
		output.append(": ");
		objToVisit.getType().accept(this);
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel());
		output.append(" ");

		if (detailed && (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth)) {
			output.append("{ \n");
			addTab();

			for (Obj localObj : objToVisit.getLocalSymbols()) {
				output.append(stringToInsert);
				localObj.accept(this);
				output.append("\n");
			}

			removeTab();
			output.append(stringToInsert);
			output.append("}");
		}

	}

	@Override
	public void visitScopeNode(Scope scopeToVisit) {
		for (Obj o : scopeToVisit.values()) {
			o.accept(this);
			output.append("\n");
		}

	}

	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.Array:
			output.append("Arr of " + strTypeToString(structToVisit.getElemType()));
			break;
		default:
			output.append(strTypeToString(structToVisit));
			break;
		}

	}

	@Override
	public String getOutput() {
		return output.toString();

	}

	private String objKindToString(int objKind) {
		switch (objKind) {
		case Obj.Con:
			return "Con";
		case Obj.Var:
			return "Var";
		case Obj.Type:
			return "Type";
		case Obj.Meth:
			return "Meth";
		case Obj.Fld:
			return "Fld";
		case Obj.Elem:
			return "Elem";
		case Obj.Prog:
			return "Prog";
		}
		return "";
	}

	private String strTypeToString(Struct struct) {
		switch (struct.getKind()) {
		case Struct.None:
			return "none";
		case Struct.Int:
			return "int";
		case Struct.Char:
			return "char";
		case Struct.Array:
			return "arr";
		case Struct.Class:
			return "class";
		case Struct.Bool:
			return "bool";
		case Struct.Enum:
			return "enum";
		case Struct.Interface:
			return "interface";
		}
		return "";
	}

	private void addTab() {
		tab++;
		for (int i = 0; i < spaces; i++)
			stringToInsert += " ";
	}

	private void removeTab() {
		tab--;
		stringToInsert = stringToInsert.substring(0, tab * spaces);
	}

}
