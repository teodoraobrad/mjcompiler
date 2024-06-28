package rs.ac.bg.etf.pp1;

import java.util.*;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	// int printCallCount = 0;
	int constCount = 0; // brojanje deklar konstanti
	int varCount = 0; // brojanje deklar promenljiva lvl 0, globalna
	int localvarCount = 0; // brojanje deklar promenljiva lvl> 0, lok
	int globalArrCount = 0; // brojanje deklar promenljiva tipa niza lvl 0, globalna
	int localArrCount = 0; // brojanje deklar promenljiva tipa niza lvl>0, lok

	int countLocalVarUsage = 0;
	int countGlobalVarUsage = 0;
	int constUsageCount = 0;
	int countConstUsage = 0;

	int methodCount = 0;
	int funcCallCount = 0;
	int elemCount = 0;
	int paramCount = 0;

	int declaredParams = 0;
	int numActPars = 0;
	int forLevel = 0;

	Struct currentType = MyTabWithBool.noType;
	Obj currentMethod = MyTabWithBool.noObj;
	Obj mainMethod = MyTabWithBool.noObj;
	String nameToInsert = null;
	Obj outer = null;

	boolean returnFound = false;

	boolean errorDetected = false;
	boolean mainFound = false;
	int nVars = 0;

	ArrayList<Obj> currentMethodParams = new ArrayList<Obj>();
	// Stack<ArrayList<Struct>> formParsTypeStack;;//

	Logger log = Logger.getLogger(getClass());

	public void visit(ProgName progName) {
		progName.obj = MyTabWithBool.insert(Obj.Prog, progName.getName(), MyTabWithBool.noType);
		MyTabWithBool.openScope();
	}

	public void visit(Program program) {
		nVars = MyTabWithBool.currentScope.getnVars();
		// outer=program.getProgName().obj;
		MyTabWithBool.chainLocalSymbols(program.getProgName().obj);
		MyTabWithBool.closeScope();
	}

	public void visit(NumConst constNum) {
		// constNum.struct=MyTabWithBool.intType;
		constNum.obj = new Obj(Obj.Con, "Default name", MyTabWithBool.intType);
		constNum.obj.setAdr(constNum.getVal());
	}

	public void visit(CharConst constChar) {
		// constChar.struct=MyTabWithBool.charType;
		constChar.obj = new Obj(Obj.Con, "Default name", MyTabWithBool.charType);
		constChar.obj.setAdr(constChar.getVal());
	}

	public void visit(BoolConst constBool) {
		// constBool.struct=MyTabWithBool.boolType;
		constBool.obj = new Obj(Obj.Con, "Default name", MyTabWithBool.boolType);

		if (constBool.getVal() == true)
			constBool.obj.setAdr(1);
		if (constBool.getVal() == false)
			constBool.obj.setAdr(0);
	}

	public void visit(SingleConst singleConst) {

		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * singleConst.getName(); } else nameToInsert = singleConst.getName();
		 */
		nameToInsert = singleConst.getName();
		if (!singleConst.getConst().obj.getType().equals(currentType))
		/// || singleConst.getConst().obj.getType().getKind() != currentType.getKind()
		{
			report_error("Tip dodeljene konstante nije ekvivalentan tipu iz deklaracije ", singleConst);
		} else if (MyTabWithBool.currentScope().findSymbol(nameToInsert) != null) {
			report_error("Konstanta vec postoji u tabeli simbola: " + nameToInsert, singleConst);
		} else {
			constCount++;

			Obj temp = MyTabWithBool.insert(Obj.Con, nameToInsert, currentType);
			temp.setAdr(singleConst.getConst().obj.getAdr());
			singleConst.obj=temp;

			report_info("Simbolicka konstanta deklarisana " + nameToInsert, singleConst);
			writeObj(temp);

		}
		nameToInsert = null;
	}

	public void visit(ConstDeclarationListDef endofconst) {
		currentType = MyTabWithBool.noType;
	}

	public void visit(TypeIdent type) {
		Obj typeNode = MyTabWithBool.find(type.getName());
		if (typeNode == MyTabWithBool.noObj) {
			report_error("Nije pronadjen tip " + type.getName() + " u tabeli simbola! ", null);
			type.struct = MyTabWithBool.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getName() + " ne predstavlja tip!", type);
				type.struct = MyTabWithBool.noType;

			}
			currentType = type.struct;
		}
	}

	public void visit(GlobalVarDeclDefNoBrace varDecl) {

		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * varDecl.getName(); } else nameToInsert = varDecl.getName();
		 */
		nameToInsert = varDecl.getName();
		if (MyTabWithBool.currentScope().findSymbol(nameToInsert) != null) {
			report_error("Promenljiva vec postoji u tabeli simbola u trenutnom opsegu: " + nameToInsert, varDecl);
		} else {

			Obj temp = MyTabWithBool.insert(Obj.Var, nameToInsert, currentType);
			
			if (temp.getLevel() == 0) {
				varCount++;
				temp.setAdr(Code.dataSize++);
			}
			varDecl.obj=temp;
			report_info("Glo promenljiva " + nameToInsert, varDecl);
			writeObj(temp);

		}
		nameToInsert = null;
	}

	public void visit(GlobalVarDeclDefBrace varDecl) {

		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * varDecl.getName(); } else nameToInsert = varDecl.getName();
		 */
		nameToInsert = varDecl.getName();

		if (MyTabWithBool.currentScope().findSymbol(nameToInsert) != null) {
			report_error("Promenljiva vec postoji u tabeli simbola: " + nameToInsert, varDecl);
		} else {
			Struct arrayStruct = new Struct(Struct.Array, currentType);
			Obj temp = MyTabWithBool.insert(Obj.Var, nameToInsert, arrayStruct);
			/*
			 * if(MyTabWithBool.find("array of "+currentVarType.getKind())!=null) {
			 * arrayStruct =
			 * MyTabWithBool.find("array of "+currentVarType.getKind()).getType(); } else {
			 * Tab.insert(Obj.Type, "array of"+currentVarType.getKind(), arrayStruct); if
			 * (currenrNamespace != null) {
			 */
			if (temp.getLevel() == 0) {
				globalArrCount++;
				temp.setAdr(Code.dataSize++);
			}
			varDecl.obj=temp;
			report_info("Glo promenljiva niz  " + nameToInsert, varDecl);
			writeObj(temp);
		}
		nameToInsert = null;
	}

	public void visit(GlobalVarDeclaration endofglobals) {
		currentType = MyTabWithBool.noType;
	}

	public void visit(VarDeclNoBrace varDecl) {
		/*
		 * if(currenrNamespace!=null) {
		 * nameToInsert=currenrNamespace+"::"+varDecl.getName(); }else
		 */
		nameToInsert = varDecl.getName();

		if (MyTabWithBool.currentScope().findSymbol(nameToInsert) != null) {
			report_error("Promenljiva vec postoji u tabeli simbola za trenutni opseg: " + nameToInsert, varDecl);
		} else {
			Obj temp = MyTabWithBool.insert(Obj.Var, nameToInsert, currentType);
			// varDecl.obj.setAdr(varCount);
			if (temp.getLevel() > 0) {
				localvarCount++;
				temp.setAdr(Code.dataSize++);
			}
			varDecl.obj=temp;
			report_info("Lok romenljiva " + nameToInsert, varDecl);
			writeObj(temp);

		}
		nameToInsert = null;
	}

	public void visit(VarDeclBrace varDecl) {
		/*
		 * if(currenrNamespace!=null) {
		 * nameToInsert=currenrNamespace+"::"+varDecl.getName(); }else
		 */
		nameToInsert = varDecl.getName();

		if (MyTabWithBool.currentScope().findSymbol(nameToInsert) != null) {
			report_error("Promenljiva vec postoji u tabeli simbola za trenurni opseg: " + nameToInsert, varDecl);
		} else {
			Struct arrayStruct = new Struct(Struct.Array, currentType);

			Obj temp = MyTabWithBool.insert(Obj.Var, nameToInsert, arrayStruct);
			// varDecl.obj.setAdr(varCount);
			if (temp.getLevel() > 0) {
				localArrCount++;
				temp.setAdr(Code.dataSize++);
			}
//report_info(""+currentVarType.getKind(),null);
			varDecl.obj=temp;
			report_info("Lok promenljiva niz  " + nameToInsert, varDecl);
			writeObj(temp);

		}
		nameToInsert = null;
		/*
		 * if(MyTabWithBool.find("array of "+currentVarType.getKind())!=null) {
		 * arrayStruct =
		 * MyTabWithBool.find("array of "+currentVarType.getKind()).getType(); } else {
		 * Tab.insert(Obj.Type, "array of"+currentVarType.getKind(), arrayStruct); if
		 * (currenrNamespace != null) {
		 */
	}

	public void visit(VarDeclOk endofconst) {
		currentType = MyTabWithBool.noType;
	}

	public void visit(VoidRetType methodReturnTypeVoid) {
		methodReturnTypeVoid.struct = MyTabWithBool.noType;
		currentMethodParams.clear();
		currentMethodParams = new ArrayList<>();
	}

	public void visit(ReturnTypeIdent methodReturnTypeType) {
		methodReturnTypeType.struct = methodReturnTypeType.getType().struct;
		currentMethodParams.clear();
		currentMethodParams = new ArrayList<>();
	}

	public void visit(MethodSignature methodSignature) {
		currentMethod = MyTabWithBool.noObj;

		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * methodSignature.getName(); } else
		 */
		nameToInsert = methodSignature.getName();

		if (MyTabWithBool.currentScope().findSymbol(nameToInsert) == null) {
			createMethod(nameToInsert, methodSignature.getReturnType().struct);
			report_info("Deklarisana metoda " + nameToInsert, methodSignature);
			writeObj(currentMethod);
		} else {
			report_error("Ime metoda " + nameToInsert + " vec postoji u trenutnom opsegu.", methodSignature);
		}

		if (currentMethod.equals(MyTabWithBool.noObj)) {
			createMethod("Oporavak " + nameToInsert, methodSignature.getReturnType().struct);
		}

		methodSignature.obj = currentMethod;
		nameToInsert = null;
	}

	private void createMethod(String methodName, Struct methodType) {

		currentMethod = MyTabWithBool.insert(Obj.Meth, methodName, methodType);
		MyTabWithBool.openScope();

		if (methodName.equalsIgnoreCase("main")) {
			if (mainFound) {
				report_error("Dva puta deklarisan main.", null);
			}

			mainFound = true;
			mainMethod = currentMethod;

			if (!currentMethod.getType().equals(MyTabWithBool.noType)) {
				report_error("Main metod ne sme imati povratnu vrednost.", null);
			}
			if (!currentMethodParams.isEmpty()) {
				report_error("Main metod ne sme imati parametre", null);
			}
		}

		insertFormPar();
	}

	public void insertFormPar() {
		for (int i = 0; i < currentMethodParams.size(); i++) {
			Obj curFormParObj = currentMethodParams.get(i);
			Obj newFormParObj = MyTabWithBool.insert(curFormParObj.getKind(), curFormParObj.getName(),
					curFormParObj.getType());
			newFormParObj.setFpPos(i + 1);
		}
		currentMethod.setLevel(currentMethodParams.size());

	}

	public void visit(MethodDecl methodDecl) {
		methodCount++;

		if (!returnFound && !currentMethod.getType().equals(MyTabWithBool.noType)) {
			report_error("U telu metode ne postoji return naredba ", methodDecl);
		}

		MyTabWithBool.chainLocalSymbols(currentMethod);
		MyTabWithBool.closeScope();

		currentType = MyTabWithBool.noType;
		currentMethodParams.clear();
		currentMethod = MyTabWithBool.noObj; // null
		returnFound = false;
	}

	public void visit(ParamNormal formParsElementVar) {
		insertFormParam(formParsElementVar.getParamName(), formParsElementVar.getType().struct);
		// count
		// println
		currentType = MyTabWithBool.noType;
	}

	public void visit(ParamArray formParsElementArr) {
		insertFormParam(formParsElementArr.getParamName(),
				new Struct(Struct.Array, formParsElementArr.getType().struct));
		currentType = MyTabWithBool.noType;
	}

	private void insertFormParam(String formParName, Struct formParType) {
		boolean existsFormParObjWithSameName = currentMethodParams.stream().filter(o -> o.getName().equals(formParName))
				.findFirst().isPresent();

		if (!existsFormParObjWithSameName) {
			Obj temp = new Obj(Obj.Var, formParName, formParType);
			currentMethodParams.add(temp);
			declaredParams++;
		} else {
			report_error("Vec postoji parametar sa ovim imenom" + formParName, null);
		}

	}

	public void visit(FactorNewVar ass) {
		currentType = MyTabWithBool.noType;
	}
	public void visit(FactorNewVarNoActPars ass) {
		currentType = MyTabWithBool.noType;
	}

	public void visit(DesignatorAssignOpExprOk ass) {
		//ass.struct = ass.getExpr().struct;
		ass.obj=new Obj(Obj.NO_VALUE,"des",ass.getExpr().struct);
		//ass.obj = ass.getExpr().obj;
	}

	public void visit(OnErrorAssignOp ass) {
		ass.obj = MyTabWithBool.noObj;
	}

	public void visit(DesignatorStatementAssign desStmtAssign) {
		Obj desObj = desStmtAssign.getDesignator().obj;
		writeObj(desObj);
		/*
		 * if(desObj.getKind()==Obj.Var) { report_info("Arr of " +
		 * desObj.getType().getKind(),null);
		 * 
		 * if(desObj.getType().getElemType()!=null) report_info("elem " +
		 * desObj.getType().getElemType().getKind(),null); else
		 * report_info("elem je null" ,null);
		 * 
		 * 
		 * } // report_info("Arr of2 " +
		 * desStmtAssign.getDesignatorAssignOpExpr().struct.getKind(),null);
		 * if(desStmtAssign.getDesignatorAssignOpExpr().struct.getKind()==Struct.Array)
		 * { if(desStmtAssign.getDesignatorAssignOpExpr().struct.getElemType()!=null)
		 * report_info("elem " +
		 * desStmtAssign.getDesignatorAssignOpExpr().struct.getElemType().getKind(),null
		 * ); else report_info("elem je null" ,null); } else report_info("nije arr"+ ""
		 * ,null);
		 */

		if (!(desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Fld || desObj.getKind() == Obj.Elem)) {
			report_error("Pogresan tip objekta koriscen u DesignatorStatementAssign.", desStmtAssign);
		}

		if (desStmtAssign.getDesignatorAssignOpExpr() == null
				|| !desStmtAssign.getDesignatorAssignOpExpr().obj.getType().compatibleWith(desObj.getType())) {
			report_error("Nekompatibilni tipovi prilikom dodele.", desStmtAssign);
			
		}
		//desStmtAssign.obj=desObj;
	}

	public void visit(DesignatorStatementInc desstmt) {
		Obj desObj = desstmt.getDesignator().obj;
		if (!(desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Fld || desObj.getKind() == Obj.Elem)) {
			report_error("Pogresan tip objekta koriscen u DesignatorStatementInc.", desstmt);
		}
		if (!(desObj.getType().getKind() == MyTabWithBool.intType.getKind())) {
			report_error("Designator mora biti tipa int u DesignatorStatementInc.", desstmt);

		}
	}

	public void visit(DesignatorStatementDec desstmt) {
		Obj desObj = desstmt.getDesignator().obj;
		if (!(desObj.getKind() == Obj.Var || desObj.getKind() == Obj.Fld || desObj.getKind() == Obj.Elem)) {
			report_error("Pogresan tip objekta koriscen u DesignatorStatemenDec.", desstmt);
		}
		if (!(desObj.getType().getKind() == MyTabWithBool.intType.getKind())) {
			report_error("Designator mora biti tipa int u DesignatorStatementDec.", desstmt);

		}
	}

	public void visit(StmtBreak statementBreak) {
		if (forLevel == 0) {
			report_error("Break ne sme biti van for petlje", statementBreak);
		}
	}

	public void visit(StmtContinue statementContinue) {
		if (forLevel == 0) {
			report_error("Continue  ne sme biti van for petlje", statementContinue);
		}
	}

	public void visit(ForCondition forenter) {
		forLevel++;
	}

	public void visit(NoForCondition forenter) {
		forLevel++;
		// forenter.obj=new Obj(Obj.Var,"ifcond",MyTabWithBool.boolType);
		// forenter.obj.setAdr(1);
	}

	public void visit(StmtFor forenter) {
		OpForCondition opCondition = forenter.getOpForCondition();

		if (opCondition instanceof ForCondition) {
			ForCondition opConditionFull = (ForCondition) opCondition;

			if (!opConditionFull.getCondFact().struct.equals(MyTabWithBool.boolType)) {
				report_error("Uslov u for iskazu mora biti bool tips", forenter);
			}
		}

		forLevel--;

	}

	public void visit(StmtReturn statementReturn) {
		if (currentMethod.equals(MyTabWithBool.noObj)) {
			report_error("Nema okruzujuce metode za ovaj return", statementReturn);
		} else {

			if (!currentMethod.getType().equals(MyTabWithBool.noType)) {
				report_error("Metoda nije void a nema povratne vrednosti.", statementReturn);
			}
			returnFound = true;
		}

	}

	public void visit(StmtReturnExpr statementReturn) {
		/*if (currentMethod.equals(MyTabWithBool.noObj)) {
			report_error("Nema okruzujuce metode ", statementReturn);
		} else {
			if (currentMethod.getType() == MyTabWithBool.noType) {
				report_error("Return naredba u funkciji koja nema povratnu vrednost", statementReturn);
				return;
			}
			//Struct returnType = statementReturn.getExpr().struct;
			Struct returnType = statementReturn.getExpr().obj.getType();
			
			if (!currentMethod.getType().equals(returnType)) {// ili compatible? ili assing?
				report_error("Povratne vrednosti se ne poklapaju", statementReturn);
			}

			returnFound = true;

		}*/
		
		
		if (currentMethod.equals(MyTabWithBool.noObj)) {
			report_error("Nema okruzujuce metode ", statementReturn);
		} else {
			if (currentMethod.getType() == MyTabWithBool.noType) {
				report_error("Return naredba u funkciji koja nema povratnu vrednost", statementReturn);
				return;
			}
			Struct returnType = statementReturn.getExpr().struct;

			if (!currentMethod.getType().equals(returnType)) {// ili compatible? ili assing?
				report_error("Povratne vrednosti se ne poklapaju", statementReturn);
			}

			returnFound = true;

		}
	}

	public void visit(StmtRead stmtRead) {
		// funcCallCount

		Obj designator = stmtRead.getDesignator().obj;

		if (!(designator.getKind() == Obj.Var || designator.getKind() == Obj.Fld || designator.getKind() == Obj.Elem)) {
			report_error("Neispravan tip designatora u read funkciji.", stmtRead);
		}
		if (!(designator.getType().equals(MyTabWithBool.intType) || designator.getType().equals(MyTabWithBool.charType)
				|| designator.getType().equals(MyTabWithBool.boolType))) {
			report_error("Neispravan tip parametra kod read funkcije.", stmtRead);
		}
	}

	public void visit(StmtPrint stmtPrint) {
		/*Obj designator = stmtPrint.getExpr().obj;

		if (!(designator.getType().getKind() == MyTabWithBool.intType.getKind() || designator.getType().getKind() == Struct.Bool
				|| designator.getType().getKind() == Struct.Char || designator.getType().getKind() == Struct.Array)) {
			report_error("Neispravan tip expr u print funkciji.", stmtPrint);
		}*/
		Struct designator = stmtPrint.getExpr().struct;

		if (!(designator.getKind() == MyTabWithBool.intType.getKind() || designator.getKind() == Struct.Bool
				|| designator.getKind() == Struct.Char||designator.getKind()==Struct.Array)) {
			report_error("Neispravan tip expr u print funkciji.", stmtPrint);
		}
	}

	public void visit(StmtPrintNumConst stmtPrint) {
		/*Obj designator = stmtPrint.getExpr().obj;

		if (!(designator.getType().getKind() == MyTabWithBool.intType.getKind() || designator.getType().getKind() == Struct.Bool
				|| designator.getType().getKind() == Struct.Char)) {
			report_error("Neispravan tip expr u print funkciji.", stmtPrint);
		}*/
		Struct designator = stmtPrint.getExpr().struct;

		if (!(designator.getKind() == MyTabWithBool.intType.getKind() || designator.getKind() == Struct.Bool
				|| designator.getKind() == Struct.Char)) {
			report_error("Neispravan tip expr u print funkciji.", stmtPrint);
		}
	}

	public void visit(IfCond ifCond) {
		if (!ifCond.getCondition().struct.equals(MyTabWithBool.boolType)) {
			report_error("Uslov u if izrazu mora biti boolean.", ifCond);
		}
	}

	public void visit(DesignatorIdent designatorIdent) {

		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * designatorIdent.getName(); } else
		 */
		nameToInsert = designatorIdent.getName();

		designatorIdent.obj = MyTabWithBool.find(nameToInsert);
		
		

		if (designatorIdent.obj.equals(MyTabWithBool.noObj)) {

			report_error("Promenljiva nije nadjena: " + nameToInsert, designatorIdent);

		} else {
			if (designatorIdent.obj.getLevel() == 0) {
			countGlobalVarUsage++;
		}
		if (designatorIdent.obj.getLevel() > 0) {
			countLocalVarUsage++;
		}
		if (designatorIdent.obj.getKind() == Obj.Con) {
			countConstUsage++;
		}
			report_info("Designator : " + nameToInsert, designatorIdent);
			writeObj(designatorIdent.obj);
		}
		nameToInsert = null;
	}

	public void visit(ArrHelp designatorIdent) {
		designatorIdent.obj=MyTabWithBool.find(designatorIdent.getN());
	}
	public void visit(DesignatorArray designatorIdent) {
		/*
		 * if (currenrNamespace != null) { nameToInsert = currenrNamespace + "::" +
		 * designatorIdent.getName(); } else
		 */
		nameToInsert = designatorIdent.getArrHelp().getN();

		designatorIdent.obj = MyTabWithBool.find(nameToInsert);
//designatorIdent.getDesignatorCore().obj;

		if (designatorIdent.obj.equals(Tab.noObj)) {

			/*
			 * designatorIdent.obj = MyTabWithBool.find(designatorIdent.getName()); if
			 * (designatorIdent.obj.equals(Tab.noObj))
			 */
			report_error("Promenljiva nije nadjena: " + nameToInsert, designatorIdent);
		} else {

			if (designatorIdent.obj.getType().getKind() != Struct.Array) {
				report_error("Promenljiva nije niz a pokusano je indeksiranje : " + nameToInsert,
						designatorIdent);
				designatorIdent.obj = MyTabWithBool.noObj;
			} else {

				//if (!designatorIdent.getExpr().obj.getType().equals(MyTabWithBool.intType)) {
				if (!designatorIdent.getExpr().struct.equals(MyTabWithBool.intType)) {
					report_error("pokusano je indeksiranje sa neint expr : " + nameToInsert,
							designatorIdent);
				}
				if (designatorIdent.obj.getLevel() == 0) {
					countGlobalVarUsage++;
				}
				if (designatorIdent.obj.getLevel() > 0) {
					countLocalVarUsage++;
				}
				if (designatorIdent.obj.getKind() == Obj.Con) {
					countConstUsage++;
				}
				designatorIdent.obj = new Obj(Obj.Elem, nameToInsert + "_element",
						designatorIdent.obj.getType().getElemType());
				writeObj(designatorIdent.obj);

				/*
				 * if (!designatorIdent.getExpr().struct.equals(Tab.intType)) {
				 * report_error("Indeks niza mora bit int.", designatorIdent); }
				 */

				report_info("Designator : " + nameToInsert, designatorIdent);
				writeObj(designatorIdent.obj);
			}
		}

	}

	public void visit(YesDesignator designatorIdent) {
		//designatorIdent.obj = designatorIdent.getDesignator().obj;
	}

	public void visit(ActParsMulti a) {
		numActPars++;
	}

	public void visit(ActParsSingle a) {
		numActPars++;
	}

	public void visit(PreActPars a) {
		numActPars = 0;
	}

	public void visit(FunctionCallPars function) {
		Obj desObj = function.getDesignator().obj;
		Scope s, g, p;
		for (s = MyTabWithBool.currentScope, g = s, p = s; s != null; p = g, g = s, s = s.getOuter()) {
		}

		Obj finded = p.findSymbol(desObj.getName());

		if (finded == null) {
			finded = g.findSymbol(desObj.getName());
			if (finded == null)
				report_error("Funkcija nije globalna! " + desObj.getName(), function);
		}

		Obj func = function.getDesignator().obj;
		if (Obj.Meth == func.getKind()) {
			funcCallCount++;
			report_info("Pronadjen poziv funkcije '" + func.getName() + "'", function);
			function.obj = func;
			writeObj(function.obj);
			if (func.getLevel() != numActPars) {
				report_error("Argumenti funkcije nisu odgovarajuci po broju " + func.getLevel() + " != " + numActPars,
						function);
			}

		} else {
			report_error("Pozvana " + func.getName() + " koja nije funkcija", function);
			function.obj = MyTabWithBool.noObj;
		}

		numActPars = 0;
	}

	public void visit(FunctionCallNoPars function) {
		Obj func = function.getDesignator().obj;
		
		Obj desObj = function.getDesignator().obj;
		Scope s, g, p;
		for (s = MyTabWithBool.currentScope, g = s, p = s; s != null; p = g, g = s, s = s.getOuter()) {
		}

		Obj finded = p.findSymbol(desObj.getName());

		if (finded == null) {
			finded = g.findSymbol(desObj.getName());
			if (finded == null)
				report_error("Funkcija nije globalna! " + desObj.getName(), function);
		}
		if (Obj.Meth == func.getKind()) {
			funcCallCount++;
			report_info("Pronadjen poziv funkcije '" + func.getName() + "'", function);
			function.obj = func;
			if (func.getLevel() != 0) {
				report_error("Argumenti funkcije nisu odgovarajuci " + func.getLevel() + " != " + 0, function);
			}

		} else {
			report_error("Pozvana " + func.getName() + " koja nije funkcija", function);
			function.obj = MyTabWithBool.noObj;
		}

	}

	public void visit(NoExprTerm exprTerm) {
		//exprTerm.obj = MyTabWithBool.noObj;
		  exprTerm.struct = MyTabWithBool.intType;
	}

	public void visit(AddOperation exprTerm) {
	/*	if (exprTerm.getOpExprTerm().obj==(MyTabWithBool.noObj)) {
			exprTerm.obj = exprTerm.getTerm().obj;
			// report_info("ovde" ,exprTerm);

		} else {

			if (!exprTerm.getTerm().obj.getType().compatibleWith(exprTerm.getOpExprTerm().obj.getType())) {
				report_error("Nisu kompatibilni tipovi", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;
				
			}
			if (!exprTerm.getTerm().obj.getType().equals(MyTabWithBool.intType)
					|| !exprTerm.getOpExprTerm().obj.getType().equals(MyTabWithBool.intType)) {
				report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;
				
			} else {
				exprTerm.obj = new Obj(Obj.Con,"expr", new Struct(Struct.Int));
				//dodaj+ -
				
			}

		}*/
		
		if (exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.noType)) {
    		exprTerm.struct=exprTerm.getTerm().struct;
    		//report_info("ovde" ,exprTerm);
			
    		
    	}else {
    		
        	if (!exprTerm.getTerm().struct.compatibleWith(exprTerm.getOpExprTerm().struct)) {
            report_error("Nisu kompatibilni tipovi", exprTerm);
            exprTerm.struct=MyTabWithBool.noType;
            report_info("ovde1" ,exprTerm);
        	}
        	if (!exprTerm.getTerm().struct.equals(MyTabWithBool.intType) || !exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.intType)) {
            report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
            exprTerm.struct=MyTabWithBool.noType;
            report_info("oassssvde1" ,exprTerm);}
        	else {
        		exprTerm.struct = MyTabWithBool.intType; report_info("ovddddddde1" ,exprTerm);
        	}
        	
        }

	}

	public void visit(MultiExpr exprTerm) {
		if (exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.noType)) {
			exprTerm.struct = exprTerm.getTerm().struct;

		} else {
			if (!exprTerm.getTerm().struct.compatibleWith(exprTerm.getOpExprTerm().struct)) {
				report_error("Nisu kompatibilni tipovi", exprTerm);
				exprTerm.struct = MyTabWithBool.noType;
			}
			if (!exprTerm.getTerm().struct.equals(MyTabWithBool.intType)
					|| !exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.intType)) {
				report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
				exprTerm.struct = MyTabWithBool.noType;
			} else
				exprTerm.struct = MyTabWithBool.intType;
		}

		if (!exprTerm.struct.equals(MyTabWithBool.intType)) {
			report_error("Term izraz u expr moraja biti int tipa da bi bio negiran", exprTerm);

		} else
			exprTerm.struct = MyTabWithBool.intType;
			/*
		
		if (exprTerm.getOpExprTerm().obj==MyTabWithBool.noObj) {
			exprTerm.obj = exprTerm.getTerm().obj;
		} else {
			if (!exprTerm.getTerm().obj.getType().compatibleWith(exprTerm.getOpExprTerm().obj.getType())) {
				report_error("Nisu kompatibilni tipovi", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;
			}
			if (!exprTerm.getTerm().obj.getType().equals(MyTabWithBool.intType)
					|| !exprTerm.getOpExprTerm().obj.getType().equals(MyTabWithBool.intType)) {
				report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;

			} else
				exprTerm.obj = new Obj(Obj.Con,"neg expr", new Struct(Struct.Int));
		}*/
	}

	public void visit(SingleTerm termFactor) {
		//termFactor.obj = termFactor.getFactor().obj;
		termFactor.struct = termFactor.getFactor().struct;
	}

	public void visit(DoubleTerm termMul) {
		if (!termMul.getTerm().struct.equals(MyTabWithBool.intType)
				|| !termMul.getFactor().struct.equals(MyTabWithBool.intType)) {
			report_error("Oba operanda u term izrazu moraju biti int tipa", termMul);
			termMul.struct = MyTabWithBool.noType;
		} else
			termMul.struct = MyTabWithBool.intType;
		/*if (!termMul.getTerm().obj.getType().equals(MyTabWithBool.intType)
				|| !termMul.getFactor().obj.getType().equals(MyTabWithBool.intType)) {
			report_error("Oba operanda u term izrazu moraju biti int tipa", termMul);
			termMul.obj = MyTabWithBool.noObj;
		} else
			termMul.obj = termMul.getFactor().obj;*/
		
	}

	public void visit(FactorNewArr factorNewArr) {
		/*if (!factorNewArr.getExpr().obj.getType().equals(MyTabWithBool.intType)) {
			report_error("Expr kod new za niz mora biti int tipa.", factorNewArr);
		}

		factorNewArr.obj = new Obj(Obj.Var,"new", new Struct(Struct.Array, factorNewArr.getType().struct)) ;// 4444444444444
		*/
		if (!factorNewArr.getExpr().struct.equals(MyTabWithBool.intType)) {
            report_error("Expr kod new za niz mora biti int tipa.", factorNewArr);
        }

        factorNewArr.struct = new Struct(Struct.Array,factorNewArr.getType().struct);
        currentType =  MyTabWithBool.noType;
	}

	public void visit(FactorDesignator factorDesignator) {

		Obj desObj = factorDesignator.getDesignator().obj;
		
        factorDesignator.struct = desObj.getType();
		//factorDesignator.obj = desObj;
	}

	public void visit(FactorFuncCall factorFunction) {
		//factorFunction.obj = factorFunction.getFunctionCall().obj;
		factorFunction.struct = factorFunction.getFunctionCall().obj.getType();
	}

	public void visit(FactorConst factorConst) {
		constUsageCount++;
		factorConst.struct = factorConst.getConst().obj.getType();
		//factorConst.obj = factorConst.getConst().obj;
	}

	public void visit(FactorParen factorParen) {
		//factorParen.obj = factorParen.getExpr().obj;
		factorParen.struct = factorParen.getExpr().struct;
	}

	public void visit(PlusExpr exprTerm) {
		if (!exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.noType)) {
			exprTerm.struct = exprTerm.getTerm().struct;
		} else {
			if (!exprTerm.getTerm().struct.compatibleWith(exprTerm.getOpExprTerm().struct)) {
				report_error("Nisu kompatibilni tipovi", exprTerm);
				exprTerm.struct = MyTabWithBool.noType;
			}
			if (!exprTerm.getTerm().struct.equals(MyTabWithBool.intType)
					|| !exprTerm.getOpExprTerm().struct.equals(MyTabWithBool.intType)) {
				report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
				exprTerm.struct = MyTabWithBool.noType;

			} else
				exprTerm.struct = MyTabWithBool.intType;
		}
		/*if (exprTerm.getOpExprTerm().obj==MyTabWithBool.noObj) {
			exprTerm.obj = exprTerm.getTerm().obj;
		} else {
			if (!exprTerm.getTerm().obj.getType().compatibleWith(exprTerm.getOpExprTerm().obj.getType())) {
				report_error("Nisu kompatibilni tipovi", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;
			}
			if (!exprTerm.getTerm().obj.getType().equals(MyTabWithBool.intType)
					|| !exprTerm.getOpExprTerm().obj.getType().equals(MyTabWithBool.intType)) {
				report_error("Oba operanda u expr izrazu moraju biti int tipa", exprTerm);
				exprTerm.obj = MyTabWithBool.noObj;

			} else
				exprTerm.obj = new Obj(Obj.Con,"expr", new Struct(Struct.Int));
		}*/
	}

	public void visit(SingleCondFact condFactExpr) {
		//condFactExpr.struct = condFactExpr.getExpr().obj.getType();
		condFactExpr.struct = condFactExpr.getExpr().struct;
	}

	public void visit(DoubleCondFact condFactExpr) {
		//Struct type1 = condFactExpr.getExpr().obj.getType(), type2 = condFactExpr.getExpr1().obj.getType();
		Struct type1 = condFactExpr.getExpr().struct, type2 = condFactExpr.getExpr1().struct;

		if (!type1.compatibleWith(type2)) {
			report_error("Expr moraju biti kompatibilni u CondFact", condFactExpr);
			condFactExpr.struct = Tab.noType;
		} else {

			if ((type1.getKind() == Struct.Class) || (type2.getKind() == Struct.Class)
					|| (type1.getKind() == Struct.Array) || (type2.getKind() == Struct.Array)) {
				if (!(condFactExpr.getRelOp() instanceof EQOp || condFactExpr.getRelOp() instanceof NOEQOp)) {
					report_error("Nedozvoljena operacija kod nizova i klasa", condFactExpr);
					condFactExpr.struct = MyTabWithBool.noType;
				} else {
					condFactExpr.struct = MyTabWithBool.boolType;
				}

			} else {
				condFactExpr.struct = MyTabWithBool.boolType;
			}
		}
	}

	public void visit(DoubleCondTerm condTerm) {
		condTerm.struct = condTerm.getCondTerm().struct;
		// provera
	}

	public void visit(SingleCondTerm condTerm) {
		condTerm.struct = condTerm.getCondFact().struct;
		// provera
	}

	public void visit(MultipleCondition condition) {
		condition.struct = condition.getCondition().struct;
		// provera
	}

	public void visit(SingleCondition condition) {
		condition.struct = condition.getCondTerm().struct;
		// provera
	}

	public void visit(FactorRange condition) {
		if(!(condition.getExpr().struct.getKind()==MyTabWithBool.intType.getKind())) {
        	report_error("Argument funkcije range mora biti int", condition);
        }
       // condition.setR(condition.getExpr().);
        condition.struct=new Struct(Struct.Array, MyTabWithBool.intType );
      //provera
		
		/*if (!(condition.getExpr().obj.getType().getKind() == MyTabWithBool.intType.getKind())) {
			report_error("Argument funkcije range mora biti int", condition);
		}
		// condition.setR(condition.getExpr().);
		condition.obj = new Obj(Obj.Con,"range", new Struct(Struct.Array, MyTabWithBool.intType));
		condition.obj.setAdr(condition.getExpr().obj.getAdr());
		// provera
		 *
		 */
	}

	public void visit(DodatakJul condition) {

		// provera
	}

	public boolean passed() {
		return !errorDetected;
	}

	public int getnVars() {
		return nVars;
	}

	public boolean ok() {
		return mainFound && !errorDetected;
	}

	public boolean mainFound() {
		return mainFound;
	}

	/*
	 * public Obj getOuterScope() {
	 * 
	 * return this.outer; }
	 */

	private void writeObj(Obj obj) {
		report_info("Objektni cvor tipa " + obj.getKind() + ", imena " + obj.getName() + ", adr " + obj.getAdr()
				+ ", lvl " + obj.getLevel(), null);
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
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

}
/*
 * //String currenrNamespace = null; //Struct currentConstType =
 * MyTabWithBool.noType, currentVarType = MyTabWithBool.noType; string, obj
 * //boolean bezimeni = true;
 * 
 * public void visit(NamespaceName namespace) { currenrNamespace =
 * namespace.getName(); }
 * 
 * public void visit(NamespaceDef namespace) { currenrNamespace = null; }
 * 
 * public void visit(ConstHeader constDecl) { currentConstType =
 * constDecl.getType().struct; } public void visit(TypeNamespace type) { String
 * name = type.getNs() + "::" + type.getName(); Obj typeNode =
 * MyTabWithBool.find(name); if (typeNode == MyTabWithBool.noObj) {
 * report_error("Nije pronadjen tip " + name + " u tabeli simbola! ", null);
 * type.struct = MyTabWithBool.noType; } else { if (Obj.Type ==
 * typeNode.getKind()) { type.struct = typeNode.getType(); } else {
 * report_error("Greska: Ime " + name + " ne predstavlja tip!", type);
 * type.struct = MyTabWithBool.noType; } } }
 * 
 * public void visit(GlobalVarHeader varDecl) { currentVarType =
 * varDecl.getType().struct; }
 * 
 * 
 */
/*
 * public void visit(DesignatorIdentNamespace designatorIdent) {
 * designatorIdent.obj = new Obj(Obj.Var, designatorIdent.getName(), new
 * Struct(Struct.None));
 * 
 * }
 * 
 * public void visit(DesignatorArrayNamespace designatorIdent) {
 * designatorIdent.obj = new Obj(Obj.Elem, designatorIdent.getName(), new
 * Struct(Struct.None)); /* designatorIdent.obj= newObj(Obj.Elem,
 * designatorIdent.getDesignatorCoreNamespace().obj.getName(), new
 * Struct(Struct.None));
 */
/*
 * }
 * 
 * public void visit(DesignatorNamespace designatorIdent) { Obj test =
 * designatorIdent.getDesignatorCoreNamespace().obj;
 * 
 * String name = designatorIdent.getNs() + "::" + test.getName();
 * designatorIdent.obj = MyTabWithBool.find(name);
 * 
 * if (designatorIdent.obj.equals(Tab.noObj)) {
 * report_error("Promenljiva nije nadjena: " + name, designatorIdent); } else {
 * 
 * if (test.getKind() == Obj.Elem && designatorIdent.obj.getType().getKind() ==
 * Struct.Array) { designatorIdent.obj = new Obj(Obj.Elem, name + "_element",
 * designatorIdent.obj.getType().getElemType());
 * report_info("Designator u ns u vidu niza: " + name, designatorIdent);
 * 
 * writeObj(designatorIdent.obj); } else if (test.getKind() == Obj.Var) {
 * report_info("Designator u ns: " + name, designatorIdent);
 * 
 * writeObj(designatorIdent.obj); } else {
 * report_error("Promenljiva nije nadjena: " + name, designatorIdent);
 * 
 * } } }
 */
