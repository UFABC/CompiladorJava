class JujuParser extends Parser;
{
    private RTSymbolTable st;

	private Variable var;
	private Program prog;

	public String convertedProgram;
	 
	public void init(){
	    st = new RTSymbolTable();	 
		prog = new Program();
	}

	private void atrib(Variable<?> actualVar)
	{
		if (actualVar == null) {
			throw new RecognitionException("Variavel nao declarada, impossivel atribuir");
		} else {
			int actualType = LT(0).getType();
			String actualValue = LT(0).getText();
			if (actualType == T_msg) {
				if (actualVar instanceof StringVariable){
					((StringVariable) actualVar).setValue(actualValue);										
				}
				else
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem texto nos numero");
			} else if (actualType == T_num) {
				if (actualVar instanceof IntegerVariable)
				{
					((IntegerVariable) actualVar).setValue(Integer.parseInt(actualValue));																				
				} else {
					throw new RecognitionException("Ish ta atribuindo errado isso ae, verifica que tem numero nos texto");										
				}
			}
		}	
	}
	 
}

programStart: (declara)* (function)*
				{
					convertedProgram = prog.convert();
				}
			;

function	:  "function" T_id  
				{
					st.add(new Function(LT(0).getText()));
				}
				(comando)+ "end"
			;

comando   	: cmdLeitura 
			| cmdEscrita
			| declara
			| comandoIfElse
			| atrib
			;
	
declara		: T_tipo 
			{
				String tipo = LT(0).getText();
			}
			T_id
                   {
                   		if (tipo.equals("Int")) {
                   			st.add(new IntegerVariable(LT(0).getText()));
                   		} else if (tipo.equals("String")) {
                   			st.add(new StringVariable(LT(0).getText()));                   			
                   		}
				        
				   }
				   T_pv
				;

atrib		: T_id 
			{
				Variable actualVar = (Variable) st.getSymbol(LT(0).getText(), Variable.class);
			}
			T_atrib value
			{
				atrib(actualVar);
			}
			T_pv
			;

value		: T_msg | T_num
			;

comandoIfElse	: "if" expr "then" comando "end" "else" "begin" comando "end"
				;

expr			: "colocarumbagulhodemaiorigualprapodervalidaraexpressao" T_op_logico "fimdacomparacao"
				;

op_aritmetica   : "matematica"
				;
				
cmdLeitura :  "input" T_ap T_id
                    {
					       if (!st.exists(LT(0).getText(), Variable.class)){
						       System.err.println("Variavel nao declarada");
						   }
					}

                    T_fp T_pv
					{
					     prog.addComando(new ComandoLeitura(var));
					}
				;
				
cmdEscrita :  "output" T_ap (T_id 
										{
										   	var = (Variable) st.getSymbol(LT(0).getText(), Variable.class);
											if (var == null) {
												throw new RecognitionException("Variavel nao declarada");
											} else
										 	prog.addComando(new ComandoEscrita(var));
										   
										}
                                       | 
									   T_msg
									   {
									       prog.addComando(new ComandoEscrita(new String(LT(0).getText())));
									   }
									   ) T_fp T_pv
				;

class JujuLexer extends Lexer;

T_tipo			: "Int" | "String"
				;

T_id			: ('a'..'z') (('a'..'z') | ('0'..'9'))*
				;
				
T_vir			: ','
				;

T_pv			: ';'
				;

T_ap			: '('
				;
				
T_fp			: ')'
				;

T_atrib			: '='
				;

T_op_logico		: '<' | '>' | "<=" | ">=" | "==" | "!="
				;
	
T_msg			: '\"' ( ('a'..'z') 
                       | ('0'..'9') 
					   | ('A'..'Z') 
					   | (' ') 
					   )* 
				   '\"'
				;

T_num			:('0'..'9')+ | ('0'..'9')+ '.' ('0'..'9')+
				;

T_ws        	: ('\n' | '\r' | '\t' | ' ') { $setType(Token.SKIP); }
				;