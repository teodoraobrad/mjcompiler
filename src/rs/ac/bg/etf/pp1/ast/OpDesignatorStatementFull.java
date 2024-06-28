// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class OpDesignatorStatementFull extends OpDesignatorStatementList {

    private DesignatorStatement DesignatorStatement;
    private OpDesignatorStatementMultiple OpDesignatorStatementMultiple;

    public OpDesignatorStatementFull (DesignatorStatement DesignatorStatement, OpDesignatorStatementMultiple OpDesignatorStatementMultiple) {
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
        this.OpDesignatorStatementMultiple=OpDesignatorStatementMultiple;
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.setParent(this);
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public OpDesignatorStatementMultiple getOpDesignatorStatementMultiple() {
        return OpDesignatorStatementMultiple;
    }

    public void setOpDesignatorStatementMultiple(OpDesignatorStatementMultiple OpDesignatorStatementMultiple) {
        this.OpDesignatorStatementMultiple=OpDesignatorStatementMultiple;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OpDesignatorStatementFull(\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpDesignatorStatementMultiple!=null)
            buffer.append(OpDesignatorStatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OpDesignatorStatementFull]");
        return buffer.toString();
    }
}
