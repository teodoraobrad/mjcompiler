package rs.ac.bg.etf.pp1;

import java_cup.runtime.*;

import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.util.Log4JUtils;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.visitors.*;

/*
 * 
 * Lek | + + +
 * Sin | + + + +oporavak A,B
 * Sem | + + - +detektovanje A,B
 * Gen | + - - +dopunaA range
 * 		 A B C
 * 
 * 
 */



class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String args[]) throws Exception {

		Logger log = Logger.getLogger(Compiler.class);

		Reader br = null;
		try {
			if (args.length < 2) {//<ulaz>.mj <izlaz>.obj 2
				log.error("Nije prosledjeno dovoljno argumenata kroz komandnu liniju.");
				return;
			}
			
			File sourceCode = new File(args[0]);//"test/program.mj"
			if (!sourceCode.exists()) {
				log.error("Ulazni fajl [" + sourceCode.getAbsolutePath() + "] nije pronadjen!");
	       
				return;
			}
			log.info("Kompajliranje fajla: " + sourceCode.getAbsolutePath());

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			MJParser p = new MJParser(lexer);
			
			if (p.errorDetected) {
				log.info("Ulaz je sintaksno neispravan, parsiranje prekinuto!");
				return;
	        }
			
			Symbol s = p.parse(); // pocetak parsiranja
			if (p.errorDetected) {
				log.info("Ulaz je sintaksno neispravan, parsiranje prekinuto!");
				return;
	        }
			
			Program program = (Program) (s.value);
			
		
			log.info("**********SINTAKSNO STABLO*********");
			log.info(program.toString(""));
			log.info("===================================");
			
			MyTabWithBool.init();
			
			// ispis prepoznatih programskih konstrukcija
			 //RuleVisitor v = new RuleVisitor();
			SemanticAnalyzer seman = new SemanticAnalyzer();
			log.info("**********OBILAZAK STABLA*********");
			program.traverseBottomUp(seman);
			log.info(program.toString(""));
			log.info("===================================");
			log.info("**********DETEKTOVANJE KORISCENJA SIMBOLA*********");
			
			log.info("===================================");
			log.info(" Deklarisanih simblickih konstanti ima " + seman.constCount);
			log.info(" Deklarisane simbolicke konstante su koriscene " + seman.localvarCount+" puta");
			log.info(" Simbolicke konstante su koriscene " + seman.constUsageCount+" puta");
			log.info(" Deklarisanih globalnih promenljivih ima " + seman.varCount);
			log.info(" Deklarisanih globalnih u vidu niza ima " + seman.globalArrCount);
			log.info(" Deklarisanih lokalnih promenljivih ima " + seman.localvarCount);
			log.info(" Deklarisanih lokalnih promenljivih u vidu niza ima " + seman.localArrCount);
			log.info(" Globalne promenljive su koriscene " + seman.countGlobalVarUsage+" puta");
			log.info(" Lokalne promenljive su koriscene " + seman.localvarCount+" puta");
			log.info("===================================");
			log.info(" Deklaracija metoda ima " + seman.methodCount);
			log.info(" Poziva globalnih funkcij ima " + seman.funcCallCount);
			log.info(" Pristupa elementu niza ima " + seman.elemCount);
			log.info(" Formalnih argumenata funkcije ima " + seman.declaredParams);
			log.info(" Koriscenja formalnih argumenata funkcije ima " + seman.paramCount);
			log.info("===================================");
			log.info("***********ISPIS TABELE SIMBOLA***********");
			
			// MyTabWithBool.dump();
			tsdump();
			log.info("===================================");
			
			if (!p.errorDetected && seman.passed()) {
		
				log.info("Semanticka OK!");
				if (!seman.ok()) { //popraviiii
	                if (!seman.mainFound()) {
	                	log.error("Main metoda nije pronadjena.");
	                }
	                log.error("Detaljna semanticka analiza PROPALA.");
	            } else {
	            	log.info("Semanticka analiza uspesno zavrsena.");

	                CodeGenerator codeGen = new CodeGenerator();//seman.getOuterScope()
	                program.traverseBottomUp(codeGen);
	                Code.dataSize = seman.getnVars();
	                Code.mainPc = codeGen.getMainPc();

	                if (codeGen.ok()) {
	                	log.info("Generisanje koda uspesno zavrseno.");

	                    File outputObjFile = new File(args[1]);
	                    if (outputObjFile.exists())
	                    	outputObjFile.delete();

	                    Code.write(new FileOutputStream(outputObjFile));
	                    log.info("Krerian objektni fajl.");

	                } else {
	                	log.error("Generisanje koda PROPALO.");
	                }
	            }
				
				
	        	
	        	
			} else {
				log.error("Semanticka PROPALA!");
			}

		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}

	}

	public static void tsdump() {
		SymbolTableVisitor visitor = new MySymbolTableVisitor();
		MyTabWithBool.dump(visitor);
	}

}