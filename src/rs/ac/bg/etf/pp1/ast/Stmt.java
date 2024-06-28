// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class Stmt extends Statement {

    private OpStatements OpStatements;

    public Stmt (OpStatements OpStatements) {
        this.OpStatements=OpStatements;
        if(OpStatements!=null) OpStatements.setParent(this);
    }

    public OpStatements getOpStatements() {
        return OpStatements;
    }

    public void setOpStatements(OpStatements OpStatements) {
        this.OpStatements=OpStatements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpStatements!=null) OpStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpStatements!=null) OpStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpStatements!=null) OpStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Stmt(\n");

        if(OpStatements!=null)
            buffer.append(OpStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Stmt]");
        return buffer.toString();
    }
}
