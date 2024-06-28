// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class OpStatementss extends OpStatements {

    private OpStatements OpStatements;
    private Statement Statement;

    public OpStatementss (OpStatements OpStatements, Statement Statement) {
        this.OpStatements=OpStatements;
        if(OpStatements!=null) OpStatements.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public OpStatements getOpStatements() {
        return OpStatements;
    }

    public void setOpStatements(OpStatements OpStatements) {
        this.OpStatements=OpStatements;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpStatements!=null) OpStatements.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpStatements!=null) OpStatements.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpStatements!=null) OpStatements.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OpStatementss(\n");

        if(OpStatements!=null)
            buffer.append(OpStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OpStatementss]");
        return buffer.toString();
    }
}
