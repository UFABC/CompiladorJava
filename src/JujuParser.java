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

public class JujuParser extends antlr.LLkParser       implements JujuParserTokenTypes
 {

     private RTSymbolTable st;
	 private RTVar             var;
	 private Programa prog;
	 
	 public void init(){
	     st = new RTSymbolTable();	 
		 prog = new Programa();
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

	public final void programa() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(INICIO);
			declara();
			{
			int _cnt3=0;
			_loop3:
			do {
				if ((LA(1)==LITERAL_input||LA(1)==LITERAL_output)) {
					comando();
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
			match(FIM);
			
							       prog.run();
							
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_0);
		}
	}
	
	public final void declara() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_reserva);
			match(T_id);
			
							         st.add(new RTVar(LT(0).getText(),0,false));
							
			{
			_loop6:
			do {
				if ((LA(1)==T_vir)) {
					match(T_vir);
					match(T_id);
					
									            if(st.getByName(LT(0).getText()) != null){
												   throw new RecognitionException("Variavel ja declarada");					  
												}
												else{
												   st.add(new RTVar(LT(0).getText(),0,false));
												}
											
				}
				else {
					break _loop6;
				}
				
			} while (true);
			}
			match(T_pv);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
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
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void cmdLeitura() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_input);
			match(T_ap);
			match(T_id);
			
								       var = st.getByName(LT(0).getText());
								       if (var == null){
									       System.err.println("Variavel nao declarada");
									   }
								
			match(T_fp);
			match(T_pv);
			
								     prog.addComando(new ComandoLeitura(var));
								
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
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
				
														   // verificar se foi declarado
														   var = st.getByName(LT(0).getText());
														   if (var == null)
														      System.err.println("Nao declarado");
														   else
														      prog.addComando(new ComandoEscrita(var));
														   
														
				break;
			}
			case T_msg:
			{
				match(T_msg);
				
													       prog.addComando(new ComandoEscrita(new String(LT(0).getText())));
													
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
			recover(ex,_tokenSet_2);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"INICIO",
		"FIM",
		"\"reserva\"",
		"T_id",
		"T_vir",
		"T_pv",
		"\"input\"",
		"T_ap",
		"T_fp",
		"\"output\"",
		"T_msg",
		"T_ws"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 9216L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 9248L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
