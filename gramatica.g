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
	 
}

programStart: function
				{
					convertedProgram = prog.convert();
				}
			;

function	:  "function" T_id  (comando)+ "end"
				{
					st.add(new Function(LT(0).getText()));
				}
			;

comando   	: cmdLeitura 
			| cmdEscrita
			| declara
			| comandoIfElse
			;
	
declara		: T_tipo T_id 
                   {
                   		if (LT(0).getText() == "Int") {
                   			st.add(new IntegerVariable(LT(1).getText()));
                   		} else if (LT(0).getText() == "String") {
                   			st.add(new StringVariable(LT(1).getText()));                   			
                   		}
				        
				   }
                   (T_vir T_id
				        {
				            if(st.exists(LT(0).getText(), Variable.class)){
							   throw new RecognitionException("Variavel ja declarada");					  
							}
							else {
		                   		if (LT(0).getText() == "Int") {
		                   			st.add(new IntegerVariable(LT(1).getText()));
		                   		} else if (LT(0).getText() == "String") {
		                   			st.add(new StringVariable(LT(1).getText()));                   			
		                   		}
		                   	}
						}
				   )* T_pv
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
										   // verificar se foi declarado
											if (!st.exists(LT(0).getText(), Variable.class)){
						       					System.err.println("Variavel nao declarada");
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

T_op_logico		: '<' | '>' | "<=" | ">=" | "==" | "!="
				;
	
T_msg			: '\"' ( ('a'..'z') 
                       | ('0'..'9') 
					   | ('A'..'Z') 
					   | (' ') 
					   )* 
				   '\"'
				;
				
T_ws        	: ('\n' | '\r' | '\t' | ' ') { $setType(Token.SKIP); }
				;