class JujuParser extends Parser;
{
    private RTSymbolTable st;

    //TODO Tem que mudar esse RTVAR pra Variable, que é uma classe estendida (tirar os comentarios ao executar)
	private RTVar var;
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
					//TODO Possivelmente será necessário uma classe function, ou uma classe class que eu adiciono function?
					st.add(new RTVar(LT(0).getText(),0,false));
				}
			;

comando   	: cmdLeitura 
			| cmdEscrita
			| declara
			| comandoIfElse
			;
	
declara		: T_tipo T_id 
                   {
				         st.add(new RTVar(LT(0).getText(),0,false));
				   }
                   (T_vir T_id
				        {
				            if(st.getByName(LT(0).getText()) != null){
							   throw new RecognitionException("Variavel ja declarada");					  
							}
							else{
							   st.add(new RTVar(LT(0).getText(),0,false));
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
					       var = st.getByName(LT(0).getText());
					       if (var == null){
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
										   var = st.getByName(LT(0).getText());
										   if (var == null)
										      System.err.println("Nao declarado");
										   else
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