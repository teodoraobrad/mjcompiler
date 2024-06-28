// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationListDef extends ConstDecl {

    private Type Type;
    private MultipleConstList MultipleConstList;

    public ConstDeclarationListDef (Type Type, MultipleConstList MultipleConstList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.MultipleConstList=MultipleConstList;
        if(MultipleConstList!=null) MultipleConstList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public MultipleConstList getMultipleConstList() {
        return MultipleConstList;
    }

    public void setMultipleConstList(MultipleConstList MultipleConstList) {
        this.MultipleConstList=MultipleConstList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(MultipleConstList!=null) MultipleConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultipleConstList!=null) MultipleConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultipleConstList!=null) MultipleConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationListDef(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleConstList!=null)
            buffer.append(MultipleConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationListDef]");
        return buffer.toString();
    }
}
