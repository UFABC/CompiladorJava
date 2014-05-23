class JujuParser extends Parser;
{
     private RTSymbolTable st;
	 private RTVar             var;
	 private Programa prog;
	 
	 public void init(){
	     st = new IsidroRTSymbolTable();	 
		 prog = new Programa();
	 }
	 
}

programa	:  INICIO declara  (comando)+ FIM

                   {
				       prog.run();
				   }
				;
				
declara		: "reserva" T_id 
                   {
				         st.add(new IsidroRTVar(LT(0).getText(),0,false));
				   }
                   (T_vir T_id
				        {
				            if(st.getByName(LT(0).getText()) != null){
							   throw new RecognitionException("Variavel ja declarada");					  
							}
							else{
							   st.add(new IsidroRTVar(LT(0).getText(),0,false));
							}
						}
				   )* T_pv
				;

comando   	:  cmdLeitura 
				|  cmdEscrita
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

class IsidroLexer extends Lexer;

INICIO          : "inicio"
				;

FIM             : "fim"
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
				
T_msg		: '\"' ( ('a'..'z') 
                       | ('0'..'9') 
					   | ('A'..'Z') 
					   | (' ') 
					   )* 
				   '\"'
				;
				
T_ws         : ('\n' | '\r' | '\t' | ' ') { $setType(Token.SKIP); }
				;