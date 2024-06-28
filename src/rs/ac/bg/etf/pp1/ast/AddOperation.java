// generated with ast extension for cup
// version 0.8
// 25/5/2024 13:7:3


package rs.ac.bg.etf.pp1.ast;

public class AddOperation extends OpExprTerm {

    private OpExprTerm OpExprTerm;
    private AddOp AddOp;
    private Term Term;

    public AddOperation (OpExprTerm OpExprTerm, AddOp AddOp, Term Term) {
        this.OpExprTerm=OpExprTerm;
        if(OpExprTerm!=null) OpExprTerm.setParent(this);
        this.AddOp=AddOp;
        if(AddOp!=null) AddOp.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public OpExprTerm getOpExprTerm() {
        return OpExprTerm;
    }

    public void setOpExprTerm(OpExprTerm OpExprTerm) {
        this.OpExprTerm=OpExprTerm;
    }

    public AddOp getAddOp() {
        return AddOp;
    }

    public void setAddOp(AddOp AddOp) {
        this.AddOp=AddOp;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OpExprTerm!=null) OpExprTerm.accept(visitor);
        if(AddOp!=null) AddOp.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OpExprTerm!=null) OpExprTerm.traverseTopDown(visitor);
        if(AddOp!=null) AddOp.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OpExprTerm!=null) OpExprTerm.traverseBottomUp(visitor);
        if(AddOp!=null) AddOp.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddOperation(\n");

        if(OpExprTerm!=null)
            buffer.append(OpExprTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOp!=null)
            buffer.append(AddOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddOperation]");
        return buffer.toString();
    }
}
