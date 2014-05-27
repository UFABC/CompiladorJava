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
	
	private Variable<?> actualVar;

	private Expression<?> actualExpression;

	private LogicExpression actualLogic;

	public String convertProgram()
	{
		return prog.convert();
	}
	 
	public void init(){
		prog = new Program();
	}

	private void atrib(int actualType) throws RecognitionException
	{
		if (actualVar == null) {
			throw new RecognitionException("Variavel nao declarada, impossivel atribuir");
		} else {
			if (actualType == T_msg && (actualExpression instanceof StringExpression)) {
				if (actualVar instanceof StringVariable){
					prog.addCommand(new CommandAttribution(actualVar, actualExpression));										
				}
				else
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem texto nos numero");
			} else if (actualType == T_num && (actualExpression instanceof MathExpression)) {
				if (actualVar instanceof IntegerVariable) {
					prog.addCommand(new CommandAttribution(actualVar, actualExpression));
				} else {
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem numero nos texto");										
				}
			}
		}	
	}

	private Integer toInt(String actualValue)
	{
		if (actualValue.contains("."))
			return Math.round(Float.parseFloat(actualValue));
		else
			return Integer.parseInt(actualValue);
	}	 

	private Object logicExpressTerm (String text, int type) throws RecognitionException
	{
		Object expressionTerm = null;

		if(type == T_id)
		{
			IntegerVariable intVar = (IntegerVariable) prog.getVariable(text);
			if (intVar == null)
				throw new RecognitionException("Erro loco na sua expressao logica, esse bagulho nao ecxiste. Lembra que so comparo inteiro por conta de preguica mesmo.");

			expressionTerm = intVar;
		} else 
		{
			expressionTerm = toInt(text);
		}
		return expressionTerm;
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
			case LITERAL_while:
			{
				comandoWhile();
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
			case T_num:
			case T_msg:
			{
				value();
				
													       prog.addCommand(new CommandWrite(actualExpression));
													
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
			match(LITERAL_if);
			exprif();
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
			prog.addCommand(new CommandEnd());
			{
			switch ( LA(1)) {
			case LITERAL_else:
			{
				match(LITERAL_else);
				prog.addCommand(new CommandElse());
				match(LITERAL_begin);
				{
				int _cnt18=0;
				_loop18:
				do {
					if ((_tokenSet_2.member(LA(1)))) {
						comando();
					}
					else {
						if ( _cnt18>=1 ) { break _loop18; } else {throw new NoViableAltException(LT(1), getFilename());}
					}
					
					_cnt18++;
				} while (true);
				}
				match(LITERAL_end);
				prog.addCommand(new CommandEnd());
				break;
			}
			case T_id:
			case LITERAL_end:
			case T_tipo:
			case LITERAL_if:
			case LITERAL_while:
			case LITERAL_input:
			case LITERAL_output:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void comandoWhile() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_while);
			exprwhile();
			match(LITERAL_then);
			{
			int _cnt22=0;
			_loop22:
			do {
				if ((_tokenSet_2.member(LA(1)))) {
					comando();
				}
				else {
					if ( _cnt22>=1 ) { break _loop22; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt22++;
			} while (true);
			}
			match(LITERAL_end);
			prog.addCommand(new CommandEnd());
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void atrib() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(T_id);
			
							actualVar = prog.getVariable(LT(0).getText());
						
			match(T_atrib);
			value();
			
							atrib(LT(0).getType());
						
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
			case T_num:
			{
				op_math();
				break;
			}
			case T_msg:
			{
				op_text();
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
	
	public final void op_math() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			match(T_num);
			actualExpression = new MathExpression(toInt(LT(0).getText()));
			{
			_loop32:
			do {
				if (((LA(1) >= T_plus && LA(1) <= T_div))) {
					int type = 0;
					{
					switch ( LA(1)) {
					case T_plus:
					{
						match(T_plus);
						type = LT(0).getType();
						break;
					}
					case T_minus:
					{
						match(T_minus);
						type = LT(0).getType();
						break;
					}
					case T_times:
					{
						match(T_times);
						type = LT(0).getType();
						break;
					}
					case T_div:
					{
						match(T_div);
						type = LT(0).getType();
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(T_num);
					((MathExpression) actualExpression).add(toInt(LT(0).getText()), type);
				}
				else {
					break _loop32;
				}
				
			} while (true);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void op_text() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			match(T_msg);
			actualExpression = new StringExpression(LT(0).getText());
			{
			_loop36:
			do {
				if ((LA(1)==T_plus)) {
					int type = 0;
					match(T_plus);
					type = LT(0).getType();
					match(T_msg);
					((StringExpression) actualExpression).add(LT(0).getText(), type);
				}
				else {
					break _loop36;
				}
				
			} while (true);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void exprif() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			expr();
			
								CommandIf cmdif = new CommandIf(actualLogic);
			
								prog.addCommand(cmdif);
							
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_6);
		}
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			expressTerm();
			
								Object leftTerm = logicExpressTerm(LT(0).getText(),  LT(0).getType());
							
			operator();
			
								int operator = LT(0).getType();
							
			expressTerm();
			
								Object rightTerm = logicExpressTerm(LT(0).getText(),  LT(0).getType());
			
								actualLogic = new LogicExpression(leftTerm, rightTerm, operator);
							
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_6);
		}
	}
	
	public final void exprwhile() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			expr();
			
								CommandWhile cmdwhile = new CommandWhile(actualLogic);
								
								prog.addCommand(cmdwhile);
							
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_6);
		}
	}
	
	public final void expressTerm() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case T_id:
			{
				match(T_id);
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
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_7);
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
			recover(ex,_tokenSet_8);
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
		"\"if\"",
		"\"then\"",
		"\"else\"",
		"\"begin\"",
		"\"while\"",
		"T_num",
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
		"T_msg",
		"\"input\"",
		"T_ap",
		"T_fp",
		"\"output\"",
		"T_vir",
		"T_gteq",
		"T_lteq",
		"T_ws"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 1207977202L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 1207977120L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 18L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 1207977184L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 536871168L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 2048L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 4130816L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 32800L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	
	}
