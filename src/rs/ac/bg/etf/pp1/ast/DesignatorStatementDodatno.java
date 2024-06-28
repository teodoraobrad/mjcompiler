// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementDodatno extends DesignatorStatement {

    private OpMultipleDesignators OpMultipleDesignators;
    private Designator Designator;
    private AssignOp AssignOp;
    private Designator Designator1;

    public DesignatorStatementDodatno (OpMultipleDesignators OpMultipleDesignators, Designator Designator, AssignOp AssignOp, Designator Designator1) {
        this.OpMultipleDesignators=OpMultipleDesignators;
        if(OpMultipleDesignators!=null) OpMultipleDesignators.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.AssignOp=AssignOp;
        if(AssignOp!=null) AssignOp.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
    }

    public OpMultipleDesignators getOpMultipleDesignators() {
        return OpMultipleDesignators;
    }

    public void setOpMultipleDesignators(OpMultipleDesignators OpMultipleDesignators) {
        this.OpMultipleDesignators=OpMultipleDesignators;
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

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpMultipleDesignators!=null) OpMultipleDesignators.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(AssignOp!=null) AssignOp.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpMultipleDesignators!=null) OpMultipleDesignators.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(AssignOp!=null) AssignOp.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpMultipleDesignators!=null) OpMultipleDesignators.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(AssignOp!=null) AssignOp.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementDodatno(\n");

        if(OpMultipleDesignators!=null)
            buffer.append(OpMultipleDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementDodatno]");
        return buffer.toString();
    }
}
