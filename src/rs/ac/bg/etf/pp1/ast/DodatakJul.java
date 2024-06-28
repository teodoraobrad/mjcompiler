// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class DodatakJul extends DesignatorStatement {

    private Designator Designator;
    private AssignOp AssignOp;
    private Expr Expr;
    private Designator Designator1;
    private OpConditionJul OpConditionJul;

    public DodatakJul (Designator Designator, AssignOp AssignOp, Expr Expr, Designator Designator1, OpConditionJul OpConditionJul) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.AssignOp=AssignOp;
        if(AssignOp!=null) AssignOp.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.OpConditionJul=OpConditionJul;
        if(OpConditionJul!=null) OpConditionJul.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public AssignOp getAssignOp() {
        return AssignOp;
    }

    public void setAssignOp(AssignOp AssignOp) {
        this.AssignOp=AssignOp;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public OpConditionJul getOpConditionJul() {
        return OpConditionJul;
    }

    public void setOpConditionJul(OpConditionJul OpConditionJul) {
        this.OpConditionJul=OpConditionJul;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(AssignOp!=null) AssignOp.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(OpConditionJul!=null) OpConditionJul.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(AssignOp!=null) AssignOp.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(OpConditionJul!=null) OpConditionJul.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(AssignOp!=null) AssignOp.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(OpConditionJul!=null) OpConditionJul.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DodatakJul(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AssignOp!=null)
            buffer.append(AssignOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpConditionJul!=null)
            buffer.append(OpConditionJul.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DodatakJul]");
        return buffer.toString();
    }
}
