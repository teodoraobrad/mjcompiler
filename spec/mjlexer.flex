package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
    
%}

%cup 
//za skener interfejsa
%line
%column
//stanje za citanje ulaznog fajla

%xstate COMMENT 
//stanje komentara

%eofval{
	return new_symbol(sym.EOF);
%eofval} 
//stanje citanja kraja fajla

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }


"program"   { return new_symbol(sym.PROG, yytext()); }
"break"     { return new_symbol(sym.BREAK, yytext()); }
"class"   	{ return new_symbol(sym.CLASS, yytext()); }
//"namespace" { return new_symbol(sym.NAMESPACE, yytext()); }
"range"     { return new_symbol(sym.RANGE, yytext()); }
"else" 		{ return new_symbol(sym.ELSE, yytext()); }
"const" 	{ return new_symbol(sym.CONST, yytext()); }
"if"    	{ return new_symbol(sym.IF, yytext()); }
"new" 		{ return new_symbol(sym.NEW, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read"  	{ return new_symbol(sym.READ, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"for"    	{ return new_symbol(sym.FOR, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"static" 	{ return new_symbol(sym.STATIC, yytext()); }
"in"    	{ return new_symbol(sym.IN, yytext()); }


"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.PROC, yytext()); }
"==" 		{ return new_symbol(sym.EQ, yytext()); }
"!=" 		{ return new_symbol(sym.NOEQ, yytext()); }
":"			{ return new_symbol(sym.COL, yytext()); }
"." 		{ return new_symbol(sym.DOT, yytext()); }
"[" 		{ return new_symbol(sym.LSQBRACE, yytext()); }
"]" 		{ return new_symbol(sym.RSQBRACE, yytext()); }
"=>" 		{ return new_symbol(sym.IMPL, yytext()); }
">" 		{ return new_symbol(sym.GR, yytext()); }
">=" 		{ return new_symbol(sym.GRE, yytext()); }
"<" 		{ return new_symbol(sym.LE, yytext()); }
"<=" 		{ return new_symbol(sym.LEE, yytext()); }
"&&" 		{ return new_symbol(sym.AND, yytext()); }
"||" 		{ return new_symbol(sym.OR, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--" 		{ return new_symbol(sym.DEC, yytext()); }


<YYINITIAL> "//" 		    { yybegin(COMMENT); }
<COMMENT> .      			{ yybegin(COMMENT); }
<COMMENT>"\n" 				{ yybegin(YYINITIAL); }
<COMMENT> "\r\n" 			{ yybegin(YYINITIAL); }

[0-9]+  						{ return new_symbol(sym.NUMCONST, Integer.parseInt (yytext())); }
"true"|"false"					{ return new_symbol(sym.BOOLCONST,Boolean.valueOf(yytext())); } //yytext()
([a-zA-Z])[a-zA-Z0-9_]* 	    {return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" 				{return new_symbol (sym.CHARCONST, Character.valueOf(yytext().charAt(1)));}//yytext().charAt(1)


. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)+" na poziciji "+yycolumn); }//(yycolumn + 1)

