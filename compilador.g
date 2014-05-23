{ 
	import java.util.Hashtable;
	import java.util.Enumeration; 
}

class CompiladorParser extends Parser;
{
	int escopo = 0;
	String write = "";
	String tipoVariavel = "";
	String nomeVariavel = "";
	String valorVariavel = "";
	Hashtable<String, Variavel> TabelaSimbolos = new Hashtable<String, Variavel>();
	String tipoAtribuicao = "";
	
	public void removeVariaveisEscopoAtual()
	{
		Enumeration<Variavel> en = TabelaSimbolos.elements();
		while(en.hasMoreElements()){
			Variavel var = en.nextElement();
			if(var.getEscopo() == escopo)
				TabelaSimbolos.remove(var.getNome());
		}
	}
	public void avaliaVariavel(Variavel var)
	{
		if (TabelaSimbolos.containsKey(var.getNome())){
	  		System.out.printf("Erro: Váriavel \"%s\" já declarada.\n", var.getNome());
	  	}
	  	else {
		  	TabelaSimbolos.put(var.getNome(), var); 
	  		System.out.println("Tabela de símbolos: " + TabelaSimbolos); 
	  	}
	}
	public void avaliaVariavelValor(Variavel var)
	{
		if(var.getTipo().equals("int")){
	  		try {
	  			int i = Integer.parseInt(valorVariavel);
	  			var.setValor(i);						  				
	  		}
	  		catch(Exception ex){
	  			System.out.printf("Erro: Tipos incompátiveis na atribuição. '%s' não é do tipo int \n", valorVariavel);
	  		}
	  	}
	  	else if(var.getTipo().equals("string")){
	  		if(valorVariavel.startsWith("\"") && valorVariavel.endsWith("\"")) {
  				var.setValor(valorVariavel);	
	  		}
	  		else {
	  			System.out.printf("Erro: Tipos incompátiveis na atribuição. '%s' não é do tipo string \n", valorVariavel);
	  		}
	  	}
	  	TabelaSimbolos.put(var.getNome(), var); 
  		System.out.println("Tabela de símbolos: " + TabelaSimbolos);
	}
	public void avaliaVariavelTokenId(String nome)
	{
		if(TabelaSimbolos.containsKey(nome)){
			valorVariavel = TabelaSimbolos.get(nome).getValor().toString(); 										
		}
		else {
			valorVariavel = null;
			System.out.printf("Erro: Váriavel '%s' não declarada\n", nome);
		} 
	}
}

programa:	"programa"
			( corpo )*
			"fimprog"
			{
				removeVariaveisEscopoAtual();
			}   
	;      

corpo:	(		cmd_declara
			|   cmd_se
			|   cmd_leia
			|   cmd_escreva
			|   cmd_atribuicao
			|	cmd_enquanto
			|   cmd_facaenquanto
		)
	;
	
cmd_declara:	"declara"	pr_tipo 	{ 
											tipoVariavel = LT(0).getText(); 
											//System.out.println("Tipo da variável: " + tipoVariavel);
										}
							TOKEN_ID 	{ 
											nomeVariavel = LT(0).getText(); 
											//System.out.println("Nome da variável: " + nomeVariavel); 
										}
				{
				  	Variavel var = new Variavel(tipoVariavel, nomeVariavel, escopo, null);
				  	avaliaVariavel(var);
				}
				(	TOKEN_ATRB 
					(	TOKEN_NUM 	{ 
								  		valorVariavel = LT(0).getText(); 
							  		}
					| 	TOKEN_ID	{ 
										avaliaVariavelTokenId(LT(0).getText());
							  		}
					| 	TOKEN_TEXTO	{ 
								  		valorVariavel = LT(0).getText(); 
							  		}
					)	
					  	{ 
						  	avaliaVariavelValor(var); 
				  	  	}
				)?
				(	TOKEN_VIRGULA 
					TOKEN_ID 			{ 
											nomeVariavel = LT(0).getText(); 
											//System.out.println("Tipo da variável: " + tipoVariavel); 
											//System.out.println("Nome da variável: " + nomeVariavel); 
										}
					{
						Variavel var2 = new Variavel(tipoVariavel, nomeVariavel, escopo, null);
				  		avaliaVariavel(var2);						
					}
					(	TOKEN_ATRB 
						(	TOKEN_NUM 	{ 
								  			valorVariavel = LT(0).getText(); 
								  		}
						| 	TOKEN_ID	{ 
											avaliaVariavelTokenId(LT(0).getText());
								  		}
						| 	TOKEN_TEXTO	{ 
									  		valorVariavel = LT(0).getText(); 
								  		}
						)	
							{
								avaliaVariavelValor(var2);	
							}	
					)?
				)*
	;

cmd_atribuicao:	TOKEN_ID 	{
								if(TabelaSimbolos.containsKey(LT(0).getText())){
									tipoAtribuicao = TabelaSimbolos.get(LT(0).getText()).getTipo(); 										
								}
								else {
									tipoAtribuicao = "";
									System.out.printf("Erro: Atribuição inválida. Váriavel '%s' não declarada\n", LT(0).getText());
								}
							}
				TOKEN_ATRB 
				expr	{
							
						}
	;

cmd_se:		"se" expr_logica "entao" 
			TOKEN_ABRE_BLOCO	{
									escopo++;
								}
			(corpo)+ 
			TOKEN_FECHA_BLOCO	{
									removeVariaveisEscopoAtual();
									escopo--;
								}
		 	(	"senaose" expr_logica "entao" 
			 	TOKEN_ABRE_BLOCO	{
										escopo++;
									} 
				(corpo)+ 
				TOKEN_FECHA_BLOCO	{
										removeVariaveisEscopoAtual();
										escopo--;
									}
			)*	 
			(	"senao" 
				TOKEN_ABRE_BLOCO 	{
										escopo++;
									} 
				(corpo)+ 
				TOKEN_FECHA_BLOCO	{
										removeVariaveisEscopoAtual();
										escopo--;
									} 
			)?
			"fimse"
	;
	
cmd_enquanto:	"enquanto" expr_logica "faca" 
				TOKEN_ABRE_BLOCO  	{
										escopo++;
									}
				(corpo)+ 
				TOKEN_FECHA_BLOCO 	{
										removeVariaveisEscopoAtual();
										escopo--;
									}
	;

cmd_facaenquanto:	"faca" 
					TOKEN_ABRE_BLOCO  	{
											escopo++;
										}
					(corpo)+ 
					TOKEN_FECHA_BLOCO  	{
											removeVariaveisEscopoAtual();
											escopo--;
										}
					"enquanto" expr_logica
	;
	
cmd_leia: "leia" TOKEN_AP TOKEN_ID TOKEN_FP
	;

cmd_escreva: "escreva" TOKEN_AP (		TOKEN_ID	{ write = LT(0).getText(); }
									|	TOKEN_NUM 	{ write = LT(0).getText(); }
									| 	TOKEN_TEXTO	{ write = LT(0).getText(); }
								) 	TOKEN_FP
			{
			 	//System.out.println(write);
		 	}
	;
	
pr_tipo:	"string" | "int"
	;
	
expr_logica:	(		TOKEN_ID 
					| 	TOKEN_NUM 
					| 	TOKEN_TEXTO
				) 
				(	OP_LOGICO 
					(		TOKEN_ID 
						| 	TOKEN_NUM 
						| 	TOKEN_TEXTO
					)
				)+
	;
	
expr:	(	(	TOKEN_NUM	{
        						if(!tipoAtribuicao.equals("int")){
									System.out.println("Erro: Atribuição inválida. Tipos não compativeis.");
								} 
							}
			|	TOKEN_ID 	{
								if(TabelaSimbolos.containsKey(LT(0).getText())){
									if(!tipoAtribuicao.equals(TabelaSimbolos.get(LT(0).getText()).getTipo())){
										System.out.printf("Erro: Atribuição inválida. Tipos não compativeis.\n");
									} 										
								}
								else {
									System.out.printf("Erro: Atribuição inválida. Váriavel '%s' não declarada\n", LT(0).getText());
								}
							}
			|	TOKEN_TEXTO	{
								if(!tipoAtribuicao.equals("string")){
									System.out.println("Erro: Atribuição inválida. Tipos não compativeis.");
								}
							}
			) 
	        (	OP_ARITMETICO	{
	        						if(tipoAtribuicao.equals("string")){
        								if(!LT(0).getText().equals("+")){
											System.out.printf("Erro: Operador '%s' não aplicavel a string.\n", LT(0).getText());
        								}
									}
								}
			    (	TOKEN_NUM	{
	        						if(!tipoAtribuicao.equals("int")){
										System.out.println("Erro: Atribuição inválida. Tipos não compativeis.");
									} 
								}
				|	TOKEN_ID 	{
									if(TabelaSimbolos.containsKey(LT(0).getText())){
										if(!tipoAtribuicao.equals(TabelaSimbolos.get(LT(0).getText()).getTipo())){
											System.out.printf("Erro: Atribuição inválida. Tipos não compativeis.\n");
										} 										
									}
									else {
										System.out.printf("Erro: Atribuição inválida. Váriavel '%s' não declarada.\n", LT(0).getText());
									}
								}
				|	TOKEN_TEXTO	{
									if(!tipoAtribuicao.equals("string")){
										System.out.println("Erro: Atribuição inválida. Tipos não compativeis.");
									}
								}
				) 
	        )*
        )
   ;
       
class CompiladorLexer extends Lexer;

options {
    k=2; // needed for newline junk
    charVocabulary='\u0000'..'\u007F'; // allow ascii
}
	
TOKEN_NUM: (NUM)+ ('.' (NUM)+)*
	;
	
TOKEN_ID: LETRA (LETRA | NUM)*
	;

TOKEN_TEXTO: '"' (LETRA | ' ' | NUM)* '"'
	;

OP_LOGICO: '<' | '>' | "<=" | ">=" | "==" | "!="
	;
	
OP_ARITMETICO: '+' | '-' | '*' | '/'
	;
	
TOKEN_ATRB:	":="
	;	

TOKEN_VIRGULA : ','
	    ;

LETRA: ('a'..'z'|'A'..'Z')
	;
	
NUM   : ('0'..'9')
	;
	
TOKEN_AP	:	'('
	;
	
TOKEN_FP	:	')'
	;
	
TOKEN_ABRE_BLOCO	:	'{' 
	;
	
TOKEN_FECHA_BLOCO	:	'}'	
	;

WS    : ( ' '
        | '\r' '\n'
        | '\n'
        | '\t'
        )
        {$setType(Token.SKIP);}
      ;

