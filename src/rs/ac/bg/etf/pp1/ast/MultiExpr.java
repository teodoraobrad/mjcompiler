// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class MultiExpr extends Expr {

    private Term Term;
    private OpExprTerm OpExprTerm;

    public MultiExpr (Term Term, OpExprTerm OpExprTerm) {
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
        this.OpExprTerm=OpExprTerm;
        if(OpExprTerm!=null) OpExprTerm.setParent(this);
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public OpExprTerm getOpExprTerm() {
        return OpExprTerm;
    }

    public void setOpExprTerm(OpExprTerm OpExprTerm) {
        this.OpExprTerm=OpExprTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Term!=null) Term.accept(visitor);
        if(OpExprTerm!=null) OpExprTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
        if(OpExprTerm!=null) OpExprTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Term!=null) Term.traverseBottomUp(visitor);
        if(OpExprTerm!=null) OpExprTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiExpr(\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OpExprTerm!=null)
            buffer.append(OpExprTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiExpr]");
        return buffer.toString();
    }
}
