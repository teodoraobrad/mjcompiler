// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class YesMultipleDesignators extends OpMultipleDesignators {

    private OpMultipleDesignators OpMultipleDesignators;
    private OpDesignator OpDesignator;

    public YesMultipleDesignators (OpMultipleDesignators OpMultipleDesignators, OpDesignator OpDesignator) {
        this.OpMultipleDesignators=OpMultipleDesignators;
        if(OpMultipleDesignators!=null) OpMultipleDesignators.setParent(this);
        this.OpDesignator=OpDesignator;
        if(OpDesignator!=null) OpDesignator.setParent(this);
    }

    public OpMultipleDesignators getOpMultipleDesignators() {
        return OpMultipleDesignators;
    }

    public void setOpMultipleDesignators(OpMultipleDesignators OpMultipleDesignators) {
        this.OpMultipleDesignators=OpMultipleDesignators;
    }

    public OpDesignator getOpDesignator() {
        return OpDesignator;
    }

    public void setOpDesignator(OpDesignator OpDesignator) {
        this.OpDesignator=OpDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpMultipleDesignators!=null) OpMultipleDesignators.accept(visitor);
        if(OpDesignator!=null) OpDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpMultipleDesignators!=null) OpMultipleDesignators.traverseTopDown(visitor);
        if(OpDesignator!=null) OpDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpMultipleDesignators!=null) OpMultipleDesignators.traverseBottomUp(visitor);
        if(OpDesignator!=null) OpDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YesMultipleDesignators(\n");

        if(OpMultipleDesignators!=null)
            buffer.append(OpMultipleDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpDesignator!=null)
            buffer.append(OpDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YesMultipleDesignators]");
        return buffer.toString();
    }
}
