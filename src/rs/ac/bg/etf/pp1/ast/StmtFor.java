// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class StmtFor extends Statement {

    private OpDesignatorStatementList OpDesignatorStatementList;
    private OpForCondition OpForCondition;
    private OpDesignatorStatementList OpDesignatorStatementList1;
    private Statement Statement;

    public StmtFor (OpDesignatorStatementList OpDesignatorStatementList, OpForCondition OpForCondition, OpDesignatorStatementList OpDesignatorStatementList1, Statement Statement) {
        this.OpDesignatorStatementList=OpDesignatorStatementList;
        if(OpDesignatorStatementList!=null) OpDesignatorStatementList.setParent(this);
        this.OpForCondition=OpForCondition;
        if(OpForCondition!=null) OpForCondition.setParent(this);
        this.OpDesignatorStatementList1=OpDesignatorStatementList1;
        if(OpDesignatorStatementList1!=null) OpDesignatorStatementList1.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public OpDesignatorStatementList getOpDesignatorStatementList() {
        return OpDesignatorStatementList;
    }

    public void setOpDesignatorStatementList(OpDesignatorStatementList OpDesignatorStatementList) {
        this.OpDesignatorStatementList=OpDesignatorStatementList;
    }

    public OpForCondition getOpForCondition() {
        return OpForCondition;
    }

    public void setOpForCondition(OpForCondition OpForCondition) {
        this.OpForCondition=OpForCondition;
    }

    public OpDesignatorStatementList getOpDesignatorStatementList1() {
        return OpDesignatorStatementList1;
    }

    public void setOpDesignatorStatementList1(OpDesignatorStatementList OpDesignatorStatementList1) {
        this.OpDesignatorStatementList1=OpDesignatorStatementList1;
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
        if(OpDesignatorStatementList!=null) OpDesignatorStatementList.accept(visitor);
        if(OpForCondition!=null) OpForCondition.accept(visitor);
        if(OpDesignatorStatementList1!=null) OpDesignatorStatementList1.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpDesignatorStatementList!=null) OpDesignatorStatementList.traverseTopDown(visitor);
        if(OpForCondition!=null) OpForCondition.traverseTopDown(visitor);
        if(OpDesignatorStatementList1!=null) OpDesignatorStatementList1.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpDesignatorStatementList!=null) OpDesignatorStatementList.traverseBottomUp(visitor);
        if(OpForCondition!=null) OpForCondition.traverseBottomUp(visitor);
        if(OpDesignatorStatementList1!=null) OpDesignatorStatementList1.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtFor(\n");

        if(OpDesignatorStatementList!=null)
            buffer.append(OpDesignatorStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpForCondition!=null)
            buffer.append(OpForCondition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpDesignatorStatementList1!=null)
            buffer.append(OpDesignatorStatementList1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtFor]");
        return buffer.toString();
    }
}
