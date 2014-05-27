package br.edu.compilador;
// $ANTLR 2.7.6 (2005-12-22): "gramatica.g" -> "JujuParser.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

import br.edu.compilador.commands.*;

public class JujuParser extends antlr.LLkParser       implements JujuParserTokenTypes
 {

	private Program prog;
	
	private CommandIf comando;

	public String convertProgram()
	{
		return prog.convert();
	}
	 
	public void init(){
		prog = new Program();
	}

	private void atrib(Variable<?> actualVar, int actualType, String actualValue) throws RecognitionException
	{
		if (actualVar == null) {
			throw new RecognitionException("Variavel nao declarada, impossivel atribuir");
		} else {
			if (actualType == T_msg) {
				if (actualVar instanceof StringVariable){
					prog.addCommand(new CommandAttribution(actualVar, actualValue));										
				}
				else
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem texto nos numero");
			} else if (actualType == T_num) {
				if (actualVar instanceof IntegerVariable) {
					if (actualValue.contains("."))
						prog.addCommand(new CommandAttribution(actualVar, Math.round(Float.parseFloat(actualValue))));
					else
						prog.addCommand(new CommandAttribution(actualVar, Integer.parseInt(actualValue)));
				} else {
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem numero nos texto");										
				}
			}
		}	
	}	 

protected JujuParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public JujuParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected JujuParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public JujuParser(TokenStream lexer) {
  this(lexer,1);
}

public JujuParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void programStart() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop3:
			do {
				if ((LA(1)==T_tipo)) {
					declara();
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			{
			_loop5:
			do {
				if ((LA(1)==LITERAL_function)) {
					function();
				}
				else {
					break _loop5;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
	}
	
	public final void declara() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(T_tipo);
			
							String tipo = LT(0).getText();
						
			match(T_id);
			
					if (tipo.equals("Int")) {
						prog.addCommand(new CommandNew (new IntegerVariable(LT(0).getText())));
					} else if (tipo.equals("String")) {
						prog.addCommand(new CommandNew (new StringVariable(LT(0).getText())));	
					}
							        
							
			match(T_pv);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
	}
	
	public final void function() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_function);
			match(T_id);
			
								prog.addCommand(new CommandFunction(LT(0).getText()));
							
			{
			int _cnt8=0;
			_loop8:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					comando();
				}
				else {
					if ( _cnt8>=1 ) { break _loop8; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt8++;
			} while (true);
			}
			match(LITERAL_end);
			
								prog.addCommand(new CommandReturn());
			
								prog.addCommand(new CommandEnd());
							
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_3);
		}
	}
	
	public final void comando() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_input:
			{
				cmdLeitura();
				break;
			}
			case LITERAL_output:
			{
				cmdEscrita();
				break;
			}
			case T_tipo:
			{
				declara();
				break;
			}
			case LITERAL_if:
			{
				comandoIfElse();
				break;
			}
			case T_id:
			{
				atrib();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void cmdLeitura() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_input);
			match(T_ap);
			match(T_id);
			
				Variable var = prog.getVariable(LT(0).getText());
								    if (var == null){
										System.err.println("Variavel nao declarada");
									}
								
			match(T_fp);
			match(T_pv);
			
								     prog.addCommand(new CommandRead(var));
								
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void cmdEscrita() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_output);
			match(T_ap);
			{
			switch ( LA(1)) {
			case T_id:
			{
				match(T_id);
				
														   	Variable var = prog.getVariable(LT(0).getText());
															if (var == null) {
																throw new RecognitionException("Variavel nao declarada");
															} else
														 	prog.addCommand(new CommandWrite(var));
														   
														
				break;
			}
			case T_msg:
			{
				match(T_msg);
				
													       prog.addCommand(new CommandWrite(new String(LT(0).getText())));
													
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(T_fp);
			match(T_pv);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void comandoIfElse() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			if ((LA(1)==LITERAL_if)) {
				match(LITERAL_if);
				expr();
				match(LITERAL_then);
				{
				int _cnt15=0;
				_loop15:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						comando();
					}
					else {
						if ( _cnt15>=1 ) { break _loop15; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt15++;
				} while (true);
				}
				match(LITERAL_end);
				match(LITERAL_else);
				match(LITERAL_begin);
				{
				int _cnt17=0;
				_loop17:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						comando();
					}
					else {
						if ( _cnt17>=1 ) { break _loop17; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt17++;
				} while (true);
				}
				match(LITERAL_end);
			}
			else if ((LA(1)==LITERAL_if)) {
				match(LITERAL_if);
				expr();
				match(LITERAL_then);
				{
				int _cnt19=0;
				_loop19:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						comando();
					}
					else {
						if ( _cnt19>=1 ) { break _loop19; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt19++;
				} while (true);
				}
				match(LITERAL_end);
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void atrib() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(T_id);
			
							Variable actualVar = prog.getVariable(LT(0).getText());
						
			match(T_atrib);
			value();
			
							atrib(actualVar, LT(0).getType(), LT(0).getText());
						
			match(T_pv);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void value() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case T_msg:
			{
				match(T_msg);
				break;
			}
			case T_num:
			{
				match(T_num);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			
								CommandIf cmdif = new CommandIf();
							
			{
			switch ( LA(1)) {
			case T_id:
			{
				match(T_id);
					
									if(prog.existsVariable(LT(0).getText())) {
										cmdif.setExprL(LT(0).getText());
									}
									else {
										throw new RecognitionException("Voce nao criou essa variavel");
									}
								
				break;
			}
			case T_msg:
			{
				match(T_msg);
				
										cmdif.setExprL(LT(0).getText());
									
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			operator();
			
								cmdif.setOperator(LT(0).getType());
							
			{
			switch ( LA(1)) {
			case T_id:
			{
				match(T_id);
					
									if (prog.existsVariable(LT(0).getText())) {
										cmdif.setExprR(LT(0).getText());
									}
									else 
										throw new RecognitionException("Voce nao criou essa variavel");	
								
				break;
			}
			case T_msg:
			{
				match(T_msg);
				cmdif.setExprR(LT(0).getText());
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			prog.addCommand(cmdif);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_6);
		}
	}
	
	public final void operator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case T_or:
			{
				match(T_or);
				break;
			}
			case T_and:
			{
				match(T_and);
				break;
			}
			case T_eq:
			{
				match(T_eq);
				break;
			}
			case T_neq:
			{
				match(T_neq);
				break;
			}
			case T_gt:
			{
				match(T_gt);
				break;
			}
			case T_lt:
			{
				match(T_lt);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_7);
		}
	}
	
	public final void op_math() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			_loop27:
			do {
				if ((LA(1)==T_num)) {
					match(T_num);
					{
					switch ( LA(1)) {
					case T_plus:
					{
						match(T_plus);
						break;
					}
					case T_minus:
					{
						match(T_minus);
						break;
					}
					case T_times:
					{
						match(T_times);
						break;
					}
					case T_div:
					{
						match(T_div);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(T_num);
				}
				else {
					break _loop27;
				}
				
			} while (true);
			}
			match(T_pv);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"function\"",
		"T_id",
		"\"end\"",
		"T_tipo",
		"T_pv",
		"T_atrib",
		"T_msg",
		"T_num",
		"\"if\"",
		"\"then\"",
		"\"else\"",
		"\"begin\"",
		"T_or",
		"T_and",
		"T_eq",
		"T_neq",
		"T_gt",
		"T_lt",
		"T_plus",
		"T_minus",
		"T_times",
		"T_div",
		"\"input\"",
		"T_ap",
		"T_fp",
		"\"output\"",
		"T_vir",
		"T_op_logico",
		"T_ws"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 603984114L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 603984032L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 18L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 603984096L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 256L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 8192L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 1056L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	
	}
