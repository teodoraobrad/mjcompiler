// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclListDef extends OpMethodDeclList {

    private OpMethodDeclList OpMethodDeclList;
    private MethodDecl MethodDecl;

    public MethodDeclListDef (OpMethodDeclList OpMethodDeclList, MethodDecl MethodDecl) {
        this.OpMethodDeclList=OpMethodDeclList;
        if(OpMethodDeclList!=null) OpMethodDeclList.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public OpMethodDeclList getOpMethodDeclList() {
        return OpMethodDeclList;
    }

    public void setOpMethodDeclList(OpMethodDeclList OpMethodDeclList) {
        this.OpMethodDeclList=OpMethodDeclList;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpMethodDeclList!=null) OpMethodDeclList.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpMethodDeclList!=null) OpMethodDeclList.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpMethodDeclList!=null) OpMethodDeclList.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclListDef(\n");

        if(OpMethodDeclList!=null)
            buffer.append(OpMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclListDef]");
        return buffer.toString();
    }
}
