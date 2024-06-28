// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class OpVarDeclLists extends OpVarDeclList {

    private OpVarDeclList OpVarDeclList;
    private VarDecl VarDecl;

    public OpVarDeclLists (OpVarDeclList OpVarDeclList, VarDecl VarDecl) {
        this.OpVarDeclList=OpVarDeclList;
        if(OpVarDeclList!=null) OpVarDeclList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public OpVarDeclList getOpVarDeclList() {
        return OpVarDeclList;
    }

    public void setOpVarDeclList(OpVarDeclList OpVarDeclList) {
        this.OpVarDeclList=OpVarDeclList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpVarDeclList!=null) OpVarDeclList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpVarDeclList!=null) OpVarDeclList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpVarDeclList!=null) OpVarDeclList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OpVarDeclLists(\n");

        if(OpVarDeclList!=null)
            buffer.append(OpVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OpVarDeclLists]");
        return buffer.toString();
    }
}
