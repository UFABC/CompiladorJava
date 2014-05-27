{
import br.edu.compilador.commands.*;
}

class JujuParser extends Parser;
{
	private Program prog;
	
	private Variable<?> actualVar;

	private Expression<?> actualExpression;

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
			| comandoWhile
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
				actualVar = prog.getVariable(LT(0).getText());
			}
			T_atrib value
			{
				atrib(LT(0).getType());
			}
			T_pv
			;

value		: op_math  | op_text
			;

comandoIfElse	: 	"if" exprif "then" (comando)+ "end" "else" "begin" (comando)+  "end" 
				|	"if" exprif "then" (comando)+ "end"
				;
				
exprif		:	{
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
				} )		

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
				} | T_num {cmdif.setExprR(LT(0).getText());})
				{prog.addCommand(cmdif);}	
 			;
			
comandoWhile	: "while" exprwhile "then" (comando)+ "end" 
				;
				
exprwhile		:{
					CommandWhile cmdwhile = new CommandWhile();
				}
				(T_id
				{	
					if(prog.existsVariable(LT(0).getText())) {
						cmdwhile.setExprL(LT(0).getText());
					}
					else {
						throw new RecognitionException("Voce nao criou essa variavel");
					}
				} )
				operator
				{
					cmdwhile.setOperator(LT(0).getType());
				}
				(T_id
				{	
					if (prog.existsVariable(LT(0).getText())) {
						cmdwhile.setExprR(LT(0).getText());
					}
					else 
						throw new RecognitionException("Voce nao criou essa variavel");	
				} 
				| T_msg {cmdwhile.setExprR(LT(0).getText());})
				
				|(T_id
				{	
					if(prog.existsVariable(LT(0).getText())) {
						cmdwhile.setExprL(LT(0).getText());
					}
					else if((LT(0).getText()).equals("true") || LT(0).getText().equals("false")){
						cmdwhile.setExprL(LT(0).getText());
					}
					else {
						throw new RecognitionException("Voce nao criou essa variavel");
					}
				} | T_num
					{
						cmdwhile.setExprL(LT(0).getText());
					}
				)		
				
				{prog.addCommand(cmdwhile);}
				
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
		
op_math			: 
					(T_num {actualExpression = new MathExpression(toInt(LT(0).getText()));}
						( {int type = 0;}
							(T_plus  {type = LT(0).getType();}
							|T_minus {type = LT(0).getType();}
							|T_times {type = LT(0).getType();}
							|T_div	 {type = LT(0).getType();}
							)
							T_num {((MathExpression) actualExpression).add(toInt(LT(0).getText()), type);}
						)*
					)
				;

op_text			: 
					(T_msg {actualExpression = new StringExpression(LT(0).getText());}
						( {int type = 0;}
							T_plus  {type = LT(0).getType();}
							T_msg {((StringExpression) actualExpression).add(LT(0).getText(), type);}
						)*
					)
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
options {
  k=2;
}

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
	
T_msg			: '\"' ( ('a'..'z') 
                       | ('0'..'9') 
					   | ('A'..'Z') 
					   | (' ') 
					   )* 
				   '\"'
				;
T_eq			: "=="
				;
T_or			: "||"
				;
T_and 			: "&&"
				;
T_neq 			: "!="
				;
T_gt 			: ">"
				;
T_lt 			: "<"
				;
T_gteq			: ">="
				;
T_lteq			: "<="
				;
T_plus			: '+' ;

T_minus			: '-' ;

T_div			: '/' ;

T_times			: '*' ;

T_num			: ('0'..'9')+ ('.' ('0'..'9')+)?
				;

T_ws        	: ('\n' | '\r' | '\t' | ' ') { $setType(Token.SKIP); }
				;
