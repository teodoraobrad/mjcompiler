// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodSignature MethodSignature;
    private OpVarDeclList OpVarDeclList;
    private OpStatements OpStatements;

    public MethodDecl (MethodSignature MethodSignature, OpVarDeclList OpVarDeclList, OpStatements OpStatements) {
        this.MethodSignature=MethodSignature;
        if(MethodSignature!=null) MethodSignature.setParent(this);
        this.OpVarDeclList=OpVarDeclList;
        if(OpVarDeclList!=null) OpVarDeclList.setParent(this);
        this.OpStatements=OpStatements;
        if(OpStatements!=null) OpStatements.setParent(this);
    }

    public MethodSignature getMethodSignature() {
        return MethodSignature;
    }

    public void setMethodSignature(MethodSignature MethodSignature) {
        this.MethodSignature=MethodSignature;
    }

    public OpVarDeclList getOpVarDeclList() {
        return OpVarDeclList;
    }

    public void setOpVarDeclList(OpVarDeclList OpVarDeclList) {
        this.OpVarDeclList=OpVarDeclList;
    }

    public OpStatements getOpStatements() {
        return OpStatements;
    }

    public void setOpStatements(OpStatements OpStatements) {
        this.OpStatements=OpStatements;
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
        if(MethodSignature!=null) MethodSignature.accept(visitor);
        if(OpVarDeclList!=null) OpVarDeclList.accept(visitor);
        if(OpStatements!=null) OpStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodSignature!=null) MethodSignature.traverseTopDown(visitor);
        if(OpVarDeclList!=null) OpVarDeclList.traverseTopDown(visitor);
        if(OpStatements!=null) OpStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodSignature!=null) MethodSignature.traverseBottomUp(visitor);
        if(OpVarDeclList!=null) OpVarDeclList.traverseBottomUp(visitor);
        if(OpStatements!=null) OpStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodSignature!=null)
            buffer.append(MethodSignature.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpVarDeclList!=null)
            buffer.append(OpVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpStatements!=null)
            buffer.append(OpStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
