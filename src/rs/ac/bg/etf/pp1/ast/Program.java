// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ProgName ProgName;
    private OpDeclList OpDeclList;
    private OpMethodDeclList OpMethodDeclList;

    public Program (ProgName ProgName, OpDeclList OpDeclList, OpMethodDeclList OpMethodDeclList) {
        this.ProgName=ProgName;
        if(ProgName!=null) ProgName.setParent(this);
        this.OpDeclList=OpDeclList;
        if(OpDeclList!=null) OpDeclList.setParent(this);
        this.OpMethodDeclList=OpMethodDeclList;
        if(OpMethodDeclList!=null) OpMethodDeclList.setParent(this);
    }

    public ProgName getProgName() {
        return ProgName;
    }

    public void setProgName(ProgName ProgName) {
        this.ProgName=ProgName;
    }

    public OpDeclList getOpDeclList() {
        return OpDeclList;
    }

    public void setOpDeclList(OpDeclList OpDeclList) {
        this.OpDeclList=OpDeclList;
    }

    public OpMethodDeclList getOpMethodDeclList() {
        return OpMethodDeclList;
    }

    public void setOpMethodDeclList(OpMethodDeclList OpMethodDeclList) {
        this.OpMethodDeclList=OpMethodDeclList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgName!=null) ProgName.accept(visitor);
        if(OpDeclList!=null) OpDeclList.accept(visitor);
        if(OpMethodDeclList!=null) OpMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgName!=null) ProgName.traverseTopDown(visitor);
        if(OpDeclList!=null) OpDeclList.traverseTopDown(visitor);
        if(OpMethodDeclList!=null) OpMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgName!=null) ProgName.traverseBottomUp(visitor);
        if(OpDeclList!=null) OpDeclList.traverseBottomUp(visitor);
        if(OpMethodDeclList!=null) OpMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(ProgName!=null)
            buffer.append(ProgName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpDeclList!=null)
            buffer.append(OpDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpMethodDeclList!=null)
            buffer.append(OpMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
