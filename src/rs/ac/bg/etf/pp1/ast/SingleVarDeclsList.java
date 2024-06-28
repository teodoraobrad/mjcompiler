// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDeclsList extends VarDeclsList {

    private VarDeclDef VarDeclDef;

    public SingleVarDeclsList (VarDeclDef VarDeclDef) {
        this.VarDeclDef=VarDeclDef;
        if(VarDeclDef!=null) VarDeclDef.setParent(this);
    }

    public VarDeclDef getVarDeclDef() {
        return VarDeclDef;
    }

    public void setVarDeclDef(VarDeclDef VarDeclDef) {
        this.VarDeclDef=VarDeclDef;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclDef!=null) VarDeclDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclDef!=null) VarDeclDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclDef!=null) VarDeclDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDeclsList(\n");

        if(VarDeclDef!=null)
            buffer.append(VarDeclDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDeclsList]");
        return buffer.toString();
    }
}
