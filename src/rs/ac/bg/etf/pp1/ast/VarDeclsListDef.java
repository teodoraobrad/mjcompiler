// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class VarDeclsListDef extends VarDeclsList {

    private VarDeclsList VarDeclsList;
    private VarDeclDef VarDeclDef;

    public VarDeclsListDef (VarDeclsList VarDeclsList, VarDeclDef VarDeclDef) {
        this.VarDeclsList=VarDeclsList;
        if(VarDeclsList!=null) VarDeclsList.setParent(this);
        this.VarDeclDef=VarDeclDef;
        if(VarDeclDef!=null) VarDeclDef.setParent(this);
    }

    public VarDeclsList getVarDeclsList() {
        return VarDeclsList;
    }

    public void setVarDeclsList(VarDeclsList VarDeclsList) {
        this.VarDeclsList=VarDeclsList;
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
        if(VarDeclsList!=null) VarDeclsList.accept(visitor);
        if(VarDeclDef!=null) VarDeclDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclsList!=null) VarDeclsList.traverseTopDown(visitor);
        if(VarDeclDef!=null) VarDeclDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclsList!=null) VarDeclsList.traverseBottomUp(visitor);
        if(VarDeclDef!=null) VarDeclDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclsListDef(\n");

        if(VarDeclsList!=null)
            buffer.append(VarDeclsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclDef!=null)
            buffer.append(VarDeclDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclsListDef]");
        return buffer.toString();
    }
}
