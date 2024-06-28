// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class NoMultipleConstList extends MultipleConstList {

    private ConstDef ConstDef;

    public NoMultipleConstList (ConstDef ConstDef) {
        this.ConstDef=ConstDef;
        if(ConstDef!=null) ConstDef.setParent(this);
    }

    public ConstDef getConstDef() {
        return ConstDef;
    }

    public void setConstDef(ConstDef ConstDef) {
        this.ConstDef=ConstDef;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDef!=null) ConstDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDef!=null) ConstDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDef!=null) ConstDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoMultipleConstList(\n");

        if(ConstDef!=null)
            buffer.append(ConstDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoMultipleConstList]");
        return buffer.toString();
    }
}
