// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class OpDesignatorStatementMultiples extends OpDesignatorStatementMultiple {

    private OpDesignatorStatementMultiple OpDesignatorStatementMultiple;
    private DesignatorStatement DesignatorStatement;

    public OpDesignatorStatementMultiples (OpDesignatorStatementMultiple OpDesignatorStatementMultiple, DesignatorStatement DesignatorStatement) {
        this.OpDesignatorStatementMultiple=OpDesignatorStatementMultiple;
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
    }

    public OpDesignatorStatementMultiple getOpDesignatorStatementMultiple() {
        return OpDesignatorStatementMultiple;
    }

    public void setOpDesignatorStatementMultiple(OpDesignatorStatementMultiple OpDesignatorStatementMultiple) {
        this.OpDesignatorStatementMultiple=OpDesignatorStatementMultiple;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpDesignatorStatementMultiple!=null) OpDesignatorStatementMultiple.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OpDesignatorStatementMultiples(\n");

        if(OpDesignatorStatementMultiple!=null)
            buffer.append(OpDesignatorStatementMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OpDesignatorStatementMultiples]");
        return buffer.toString();
    }
}
