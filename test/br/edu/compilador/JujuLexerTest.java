package br.edu.compilador;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import antlr.RecognitionException;
import antlr.TokenStreamException;

public class JujuLexerTest {
	private JujuLexer lexer;
	private JujuParser parser;
	
	private void setup(String filePath) {
//		filePath = "resources/" + filePath;
		InputStream ib = LoadInputStream(filePath);
		lexer = new JujuLexer(ib);
		parser = new JujuParser(lexer);
	}

	private InputStream LoadInputStream(String filePath) {
		InputStream ib = null;
		try {
			ib = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de teste não encontrado, por favor faça algo: ");
			System.out.println(this.getClass().getResource(filePath).getPath());
			e.printStackTrace();
			return null;
		}
		return ib;
	}
	
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
	
	@Test
	public void test1SimpleFile() throws RecognitionException, TokenStreamException {
		setup("teste1.jj");
		parser.init();
		parser.programStart();
		
		assertEquals(getStringFromInputStream(LoadInputStream("teste1.cpp")), parser.convertProgram());
	}
	
	@Test
	public void test1Operations() throws RecognitionException, TokenStreamException {
		setup("teste2.jj");
		parser.init();
		parser.programStart();
		
		assertEquals(getStringFromInputStream(LoadInputStream("teste2.cpp")), parser.convertProgram());
	}
	
	@Test
	public void test1IfFile() throws RecognitionException, TokenStreamException {
		setup("teste3.jj");
		parser.init();
		parser.programStart();
		
		
		assertEquals(getStringFromInputStream(LoadInputStream("teste3.cpp")), parser.convertProgram());
	}

}
