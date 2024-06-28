package rs.ac.bg.etf.pp1;

import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc = -1;
	boolean error = false;

	Logger log = Logger.getLogger(getClass());

	private Stack<Integer> addOpStack = new Stack<>();
	private Stack<Integer> mulOpStack = new Stack<>();
	// private Obj outerScope = null;

	public CodeGenerator() {

		super();
		/*
		 * Obj ordMethod = MyTabWithBool.find("ord"); Obj chrMethod =
		 * MyTabWithBool.find("chr"); ordMethod.setAdr(Code.pc);
		 * chrMethod.setAdr(Code.pc); Code.put(Code.enter); Code.put(1); Code.put(1);
		 * Code.put(Code.load_n); Code.put(Code.exit); Code.put(Code.return_);
		 * 
		 * Obj lenMethod = MyTabWithBool.find("len"); lenMethod.setAdr(Code.pc);
		 * Code.put(Code.enter); Code.put(1); Code.put(1); Code.put(Code.load_n);
		 * Code.put(Code.arraylength); Code.put(Code.exit); Code.put(Code.return_);
		 */
	}

	// ------------------------------------------------------

	public int getMainPc() {
		return mainPc;
	}

	public void report_error(String message, SyntaxNode info) {
		error = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public boolean ok() {
		return !error;
	}
	// ------------------------------------------------------

	public void visit(MethodSignature methodSignature) {
		Obj methodObj = methodSignature.obj;
		methodObj.setAdr(Code.pc);

		if (methodObj.getName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}

		Code.put(Code.enter);
		Code.put(methodObj.getLevel());
		Code.put(methodObj.getLocalSymbols().size());

	}

	public void visit(MethodDecl methodDecl) {
		Struct methodReturnType = methodDecl.getMethodSignature().obj.getType();

		if (methodReturnType.equals(MyTabWithBool.noType)) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		} else {
			Code.put(Code.trap);
			Code.put(1);
		}
	}

	public void visit(StmtReturnExpr statementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(StmtReturn statementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// ------------------------------------------------------

	public void visit(FactorConst factorConst) {
		Code.load(factorConst.getConst().obj);
	}

	public void visit(FactorDesignator factorvar) {
		Code.load(factorvar.getDesignator().obj);
	}

	public void visit(ArrHelp factorDesignator) {
		Code.load(factorDesignator.obj);

	}

	public void visit(DesignatorStatementAssign designatorStatementAssign) {
		Code.store(designatorStatementAssign.getDesignator().obj);
	}

	/*
	 * public void visit(DesignatorIdent factorDesignator) {
	 * Code.load(factorDesignator.obj);
	 *
	 * }
	 */

	public void visit(FactorNewArr factorNewArr) {
		Code.put(Code.newarray);
		Code.put(factorNewArr.getType().struct.equals(MyTabWithBool.charType) ? 0 : 1);
	}

	public void visit(AddOperation dt) {
		// expr
		if (dt.getAddOp() instanceof PlusOp) {
			Code.put(Code.add);
		}
		if (dt.getAddOp() instanceof MinusOp) {
			Code.put(Code.sub);
		}

	}

	public void visit(MultiExpr dt) {
		Code.put(Code.neg);
	}

	public void visit(DoubleTerm dt) {
		// expr
		if (dt.getMulOp() instanceof MultOp) {
			Code.put(Code.mul);
		}
		if (dt.getMulOp() instanceof ProcOp) {
			Code.put(Code.rem);
		}
		if (dt.getMulOp() instanceof DivOp) {
			Code.put(Code.div);
		}
	}

	public void visit(StmtPrint statementPrint) {

		if (statementPrint.getExpr().struct.getKind() == Struct.Array) {

			if (statementPrint.getExpr().struct.getElemType().equals(MyTabWithBool.charType)) {
				
				Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

				Code.put(Code.dup);			//a  a	
				Code.put(Code.arraylength); //a  d
				Code.loadConst(0);		//a  d	1
				Code.put(Code.dup);			//a  d	1	1
				Code.store(i);				//a  d	1
				int adrif = Code.pc;
				Code.putFalseJump(Code.ne, 0);//a
				
				Code.put(Code.dup);			//a  a	
				Code.load(i);				//a  a	i
				Code.put(Code.aload);		//a  ai
				
				Code.put(Code.const_1);		//a  ai	1
				Code.put(Code.bprint);		//a  
				
				Code.put(Code.dup);			//a  a
				Code.put(Code.arraylength); //a  d
				Code.load(i);				//a	 d	i
				Code.put(Code.const_1);		//a  d	i	1
				Code.put(Code.add);			//a  d	1+1
				Code.put(Code.dup);			//a  d	i+1 i+1
				Code.store(i);				//a  d	1+i
				Code.putJump(adrif);
				// int adrfix=Code.pc;
				Code.fixup(adrif+1);
				Code.put(Code.pop);

			} else {
				if (statementPrint.getExpr().struct.getElemType().equals(MyTabWithBool.intType)) {
					Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

					
					Code.put(Code.dup);			//a  a	
					Code.put(Code.arraylength); //a  d
					Code.loadConst(0);		//a  d	1
					Code.put(Code.dup);			//a  d	1	1
					Code.store(i);				//a  d	1
					int adrif = Code.pc;
					Code.putFalseJump(Code.ne, 0);//a
					
					Code.put(Code.dup);			//a  a	
					Code.load(i);				//a  a	i
					Code.put(Code.aload);		//a  ai
					
					Code.loadConst(5);	//a  ai	1
					Code.put(Code.print);		//a  
					
					Code.put(Code.dup);			//a  a
					Code.put(Code.arraylength); //a  d
					Code.load(i);				//a	 d	i
					Code.put(Code.const_1);		//a  d	i	1
					Code.put(Code.add);			//a  d	1+1
					Code.put(Code.dup);			//a  d	i+1 i+1
					Code.store(i);				//a  d	1+i
					Code.putJump(adrif);
					// int adrfix=Code.pc;
					Code.fixup(adrif+1);
					Code.put(Code.pop);
					
					
				} else // if(statementPrint.getExpr().struct == MyTabWithBool.boolType)
				{

					Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

					
					Code.put(Code.dup);			//a  a	
					Code.put(Code.arraylength); //a  d
					Code.loadConst(0);		//a  d	1
					Code.put(Code.dup);			//a  d	1	1
					Code.store(i);				//a  d	1
					int adrif = Code.pc;
					Code.putFalseJump(Code.ne, 0);//a
					
					Code.put(Code.dup);			//a  a	
					Code.load(i);				//a  a	i
					Code.put(Code.aload);		//a  ai
					
					Code.loadConst(5);	//a  ai	1
					Code.put(Code.print);		//a  
					
					Code.put(Code.dup);			//a  a
					Code.put(Code.arraylength); //a  d
					Code.load(i);				//a	 d	i
					Code.put(Code.const_1);		//a  d	i	1
					Code.put(Code.add);			//a  d	1+1
					Code.put(Code.dup);			//a  d	i+1 i+1
					Code.store(i);				//a  d	1+i
					Code.putJump(adrif);
					// int adrfix=Code.pc;
					Code.fixup(adrif+1);
					Code.put(Code.pop);
			}
			}}
			 else {

			if (statementPrint.getExpr().struct.equals(MyTabWithBool.charType)) {

				Code.loadConst(1);
				Code.put(Code.bprint);
			} else {
				if (statementPrint.getExpr().struct.equals(MyTabWithBool.intType)) {

					Code.loadConst(5);
					Code.put(Code.print);
				} else // if(statementPrint.getExpr().struct == MyTabWithBool.boolType)
				{

					Code.loadConst(5);
					Code.put(Code.print);
				}
//				if(statementPrint.getExpr().struct.getKind()==Struct.Array) {
//					
//					(FactorRange)statementPrint.getExpr().
//					Code.loadConst(5);
//					Code.put(Code.print);
//				}

			}

		}
	}

	public void visit(StmtPrintNumConst statementPrint) {

		if (statementPrint.getExpr().struct.getKind() == Struct.Array) {
			if (statementPrint.getExpr().struct.getElemType().equals(MyTabWithBool.charType)) {

				Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

				Code.put(Code.dup); // a a
				Code.put(Code.arraylength); // a d
				Code.loadConst(0); // a d 1
				Code.put(Code.dup); // a d 1 1
				Code.store(i); // a d 1
				int adrif = Code.pc;
				Code.putFalseJump(Code.ne, 0);// a

				Code.put(Code.dup); // a a
				Code.load(i); // a a i
				Code.put(Code.aload); // a ai

				Code.loadConst(statementPrint.getN()); // a ai 1
				Code.put(Code.bprint); // a

				Code.put(Code.dup); // a a
				Code.put(Code.arraylength); // a d
				Code.load(i); // a d i
				Code.put(Code.const_1); // a d i 1
				Code.put(Code.add); // a d 1+1
				Code.put(Code.dup); // a d i+1 i+1
				Code.store(i); // a d 1+i
				Code.putJump(adrif);
				// int adrfix=Code.pc;
				Code.fixup(adrif + 1);
				Code.put(Code.pop);

			} else {
				if (statementPrint.getExpr().struct.getElemType().equals(MyTabWithBool.intType)) {
					Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

					Code.put(Code.dup); // a a
					Code.put(Code.arraylength); // a d
					Code.loadConst(0); // a d 1
					Code.put(Code.dup); // a d 1 1
					Code.store(i); // a d 1
					int adrif = Code.pc;
					Code.putFalseJump(Code.ne, 0);// a

					Code.put(Code.dup); // a a
					Code.load(i); // a a i
					Code.put(Code.aload); // a ai

					Code.loadConst(statementPrint.getN()); // a ai 1
					Code.put(Code.print); // a

					Code.put(Code.dup); // a a
					Code.put(Code.arraylength); // a d
					Code.load(i); // a d i
					Code.put(Code.const_1); // a d i 1
					Code.put(Code.add); // a d 1+1
					Code.put(Code.dup); // a d i+1 i+1
					Code.store(i); // a d 1+i
					Code.putJump(adrif);
					// int adrfix=Code.pc;
					Code.fixup(adrif + 1);
					Code.put(Code.pop);

				} else // if(statementPrint.getExpr().struct == MyTabWithBool.boolType)
				{

					Obj i = MyTabWithBool.insert(Obj.Var, "j", MyTabWithBool.intType);

					Code.put(Code.dup); // a a
					Code.put(Code.arraylength); // a d
					Code.loadConst(0);; // a d 1
					Code.put(Code.dup); // a d 1 1
					Code.store(i); // a d 1
					int adrif = Code.pc;
					Code.putFalseJump(Code.ne, 0);// a

					Code.put(Code.dup); // a a
					Code.load(i); // a a i
					Code.put(Code.aload); // a ai

					Code.loadConst(statementPrint.getN()); // a ai 1
					Code.put(Code.print); // a

					Code.put(Code.dup); // a a
					Code.put(Code.arraylength); // a d
					Code.load(i); // a d i
					Code.put(Code.const_1); // a d i 1
					Code.put(Code.add); // a d 1+1
					Code.put(Code.dup); // a d i+1 i+1
					Code.store(i); // a d 1+i
					Code.putJump(adrif);
					// int adrfix=Code.pc;
					Code.fixup(adrif + 1);
					Code.put(Code.pop);
				}
			}
		} else {

			if (statementPrint.getExpr().struct.equals(MyTabWithBool.charType)) {

				Code.loadConst(statementPrint.getN());
				Code.put(Code.bprint);
			} else if (statementPrint.getExpr().struct == MyTabWithBool.boolType) {
				Code.loadConst(statementPrint.getN());
				Code.put(Code.print);
			} else {
				Code.loadConst(statementPrint.getN());
				Code.put(Code.print);
			}
//		if(statementPrint.getExpr().struct.getKind()==Struct.Array) {
//		
//		(FactorRange)statementPrint.getExpr().
//		Code.loadConst(5);
//		Code.put(Code.print);
//	}
		}
	}

	public void visit(StmtRead stmtRead) {
		Obj readObj = stmtRead.getDesignator().obj;

		if (readObj.getType().equals(MyTabWithBool.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}

		Code.store(readObj);

	}

	public void visit(DesignatorStatementInc designatorStatementInc) {
		boolean isArrayElement = designatorStatementInc.getDesignator().obj.getKind() == Obj.Elem;
		if (isArrayElement) {
			Code.put(Code.dup2);
		}

		Code.load(designatorStatementInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);

		Code.store(designatorStatementInc.getDesignator().obj);
	}

	public void visit(DesignatorStatementDec designatorStatementDec) {

		if (designatorStatementDec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatementDec.getDesignator().obj);
	}

	public void visit(FactorRange factorRange) {
		Obj ad = MyTabWithBool.insert(Obj.Var, "duz", MyTabWithBool.intType);// new Obj()load 255 PROB;LEM BIO LEN
		Obj a = MyTabWithBool.insert(Obj.Var, "adr", MyTabWithBool.intType);
		Obj i = MyTabWithBool.insert(Obj.Var, "i", MyTabWithBool.intType);

		Code.put(Code.dup);
		Code.put(Code.dup);
		Code.store(ad);

		Code.put(Code.newarray);
		Code.put(1);
		Code.store(a);

		// Code.load(a);

		Code.loadConst(0);;
		Code.put(Code.dup);
		Code.store(i);
		int adrif = Code.pc;
		Code.putFalseJump(Code.ne, 0);
		// Code.put(Code.jcc);Code.put(Code.eq);
		int adrelse = Code.pc - 2;

		Code.load(a);
		Code.load(i);
		Code.load(i);
		Code.put(Code.astore);
		Code.load(ad);
		Code.load(i);
		Code.put(Code.const_1);
		Code.put(Code.add);// Code.put(Code.inc);
		// Greska: Na levoj strani dodele mora biti promenljiva!
		// Greska: nelegalan operand u Code.load
		Code.put(Code.dup);
		Code.store(i);
		// Code.put(Code.jmp);
		// Code.put2(adrif);
		Code.putJump(adrif);
		// int adrfix=Code.pc;
		Code.fixup(adrelse);

		Code.load(a);

		/*
		 * for(int i=1;i<ad.getAdr();i++) { Code.load(a); Code.put(Code.const_n);
		 * Code.put(i); Code.put(Code.const_n); Code.put(i); Code.put(Code.aload);
		 * 
		 * }
		 * 
		 * Code.load(a);
		 * 
		 * 
		 * 
		 * 
		 */

	}

}