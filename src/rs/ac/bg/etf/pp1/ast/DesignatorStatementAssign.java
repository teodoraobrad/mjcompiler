// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssign extends DesignatorStatement {

    private Designator Designator;
    private DesignatorAssignOpExpr DesignatorAssignOpExpr;

    public DesignatorStatementAssign (Designator Designator, DesignatorAssignOpExpr DesignatorAssignOpExpr) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorAssignOpExpr=DesignatorAssignOpExpr;
        if(DesignatorAssignOpExpr!=null) DesignatorAssignOpExpr.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorAssignOpExpr getDesignatorAssignOpExpr() {
        return DesignatorAssignOpExpr;
    }

    public void setDesignatorAssignOpExpr(DesignatorAssignOpExpr DesignatorAssignOpExpr) {
        this.DesignatorAssignOpExpr=DesignatorAssignOpExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorAssignOpExpr!=null) DesignatorAssignOpExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorAssignOpExpr!=null) DesignatorAssignOpExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorAssignOpExpr!=null) DesignatorAssignOpExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementAssign(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorAssignOpExpr!=null)
            buffer.append(DesignatorAssignOpExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssign]");
        return buffer.toString();
    }
}
