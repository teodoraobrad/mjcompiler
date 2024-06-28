// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class MultipleConstListDef extends MultipleConstList {

    private MultipleConstList MultipleConstList;
    private ConstDef ConstDef;

    public MultipleConstListDef (MultipleConstList MultipleConstList, ConstDef ConstDef) {
        this.MultipleConstList=MultipleConstList;
        if(MultipleConstList!=null) MultipleConstList.setParent(this);
        this.ConstDef=ConstDef;
        if(ConstDef!=null) ConstDef.setParent(this);
    }

    public MultipleConstList getMultipleConstList() {
        return MultipleConstList;
    }

    public void setMultipleConstList(MultipleConstList MultipleConstList) {
        this.MultipleConstList=MultipleConstList;
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
        if(MultipleConstList!=null) MultipleConstList.accept(visitor);
        if(ConstDef!=null) ConstDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultipleConstList!=null) MultipleConstList.traverseTopDown(visitor);
        if(ConstDef!=null) ConstDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultipleConstList!=null) MultipleConstList.traverseBottomUp(visitor);
        if(ConstDef!=null) ConstDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleConstListDef(\n");

        if(MultipleConstList!=null)
            buffer.append(MultipleConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDef!=null)
            buffer.append(ConstDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleConstListDef]");
        return buffer.toString();
    }
}
