package br.edu.compilador;

public class JujuUtils {
	public static String tokenToString (int operator) {
		switch (operator) {
		case JujuParserTokenTypes.T_plus:
			return "+";
			
		case JujuParserTokenTypes.T_minus:
			return "-";
			
		case JujuParserTokenTypes.T_times:
			return "*";
			
		case JujuParserTokenTypes.T_div:
			return "/";
			
		case JujuParserTokenTypes.T_and:
			return "&&";
			
		case JujuParserTokenTypes.T_or:
			return "||";
			
		case JujuParserTokenTypes.T_lt:
			return "<";
			
		case JujuParserTokenTypes.T_lteq:
			return "<=";
			
		case JujuParserTokenTypes.T_gt:
			return ">";
			
		case JujuParserTokenTypes.T_gteq:
			return ">=";
		default:
			return "==";
			
		}
	}
}
