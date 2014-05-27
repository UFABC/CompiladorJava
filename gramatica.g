{
import br.edu.compilador.commands.*;
}

class JujuParser extends Parser;
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
}

programStart: (declara)* (function)*
			;

function	:  "function" T_id  
				{
					prog.addCommand(new CommandFunction(LT(0).getText()));
				}
				(comando)+ "end"
				{
					prog.addCommand(new CommandReturn());

					prog.addCommand(new CommandEnd());
				}
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
                   			prog.addCommand(new CommandNew (new IntegerVariable(LT(0).getText())));
                   		} else if (tipo.equals("String")) {
                   			prog.addCommand(new CommandNew (new StringVariable(LT(0).getText())));	
                   		}
				        
				   }
				   T_pv
				;

atrib		: T_id 
			{
				Variable actualVar = prog.getVariable(LT(0).getText());
			}
			T_atrib value
			{
				atrib(actualVar, LT(0).getType(), LT(0).getText());
			}
			T_pv
			;

value		: op_math | T_msg | T_num
			;

comandoIfElse	: 	"if" expr
 					"then"						
					 (comando)+ 
					 "end" 
					 "else" 
					 "begin" 
					 (comando)+ 
					 "end"
					| 
					"if"
 					expr
 					"then"						
					 (comando)+ 
					"end" 
				;
				
expr		:	{
					CommandIf cmdif = new CommandIf();
				}
				(T_id
				{	
					if(prog.existsVariable(LT(0).getText())) {
						cmdif.setExprL(LT(0).getText());
					}
					else {
						throw new RecognitionException("Voce nao criou essa variavel");
					}
				} | T_msg
					{
						cmdif.setExprL(LT(0).getText());
					}
				)		

				operator
				{
					cmdif.setOperator(LT(0).getType());
				}
				(T_id
				{	
					if (prog.existsVariable(LT(0).getText())) {
						cmdif.setExprR(LT(0).getText());
					}
					else 
						throw new RecognitionException("Voce nao criou essa variavel");	
				} | T_msg {cmdif.setExprR(LT(0).getText());})
				{prog.addCommand(cmdif);}	
 			;

operator  	: 	T_or
			|
			T_and
			|
			T_eq 
			|
			T_neq
			|
			T_gt 
			|
			T_lt 
		;
		
op_math			: (T_num (T_plus|T_minus|T_times|T_div) T_num)* T_pv
				;

				
cmdLeitura :  "input" T_ap T_id
                    {
                    	Variable var = prog.getVariable(LT(0).getText());
					    if (var == null){
							System.err.println("Variavel nao declarada");
						}
					}

                    T_fp T_pv
					{
					     prog.addCommand(new CommandRead(var));
					}
				;
				
cmdEscrita :  "output" T_ap (T_id 
										{
										   	Variable var = prog.getVariable(LT(0).getText());
											if (var == null) {
												throw new RecognitionException("Variavel nao declarada");
											} else
										 	prog.addCommand(new CommandWrite(var));
										   
										}
                                       | 
									   T_msg
									   {
									       prog.addCommand(new CommandWrite(new String(LT(0).getText())));
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
				
T_or			: "||"
				;
T_and 			: "&&"
				;
T_eq 			: "=="
				;
T_neq 			: "!="
				;
T_gt 			: ">="
				;
T_lt 			: "<="
				;

T_plus			: '+' ;

T_minus			: '-' ;

T_div			: '/' ;

T_times			: '*' ;

T_num			: ('0'..'9')+ '.' ('0'..'9')+ | ('0'..'9')+
				;

T_ws        	: ('\n' | '\r' | '\t' | ' ') { $setType(Token.SKIP); }
				;
