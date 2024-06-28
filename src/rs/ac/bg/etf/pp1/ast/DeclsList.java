// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class DeclsList extends OpDeclList {

    private OpDeclList OpDeclList;
    private DeclList DeclList;

    public DeclsList (OpDeclList OpDeclList, DeclList DeclList) {
        this.OpDeclList=OpDeclList;
        if(OpDeclList!=null) OpDeclList.setParent(this);
        this.DeclList=DeclList;
        if(DeclList!=null) DeclList.setParent(this);
    }

    public OpDeclList getOpDeclList() {
        return OpDeclList;
    }

    public void setOpDeclList(OpDeclList OpDeclList) {
        this.OpDeclList=OpDeclList;
    }

    public DeclList getDeclList() {
        return DeclList;
    }

    public void setDeclList(DeclList DeclList) {
        this.DeclList=DeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpDeclList!=null) OpDeclList.accept(visitor);
        if(DeclList!=null) DeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpDeclList!=null) OpDeclList.traverseTopDown(visitor);
        if(DeclList!=null) DeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpDeclList!=null) OpDeclList.traverseBottomUp(visitor);
        if(DeclList!=null) DeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclsList(\n");

        if(OpDeclList!=null)
            buffer.append(OpDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DeclList!=null)
            buffer.append(DeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclsList]");
        return buffer.toString();
    }
}
