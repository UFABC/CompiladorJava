package br.edu.compilador;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.ws.ServiceMode;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.Authenticator.Success;

import antlr.RecognitionException;
import antlr.TokenStreamException;

public class JujuLexerTest {
	private JujuLexer lexer;
	private JujuParser parser;
	
	private void setup(String filePath) {
//		filePath = "resources/" + filePath;
		InputStream ib = null;
		try {
			ib = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo de teste não encontrado, por favor faça algo: ");
			System.out.println(this.getClass().getResource(filePath).getPath());
			e.printStackTrace();
			return;
		}
		lexer = new JujuLexer(ib);
		parser = new JujuParser(lexer);
	}
	
	@Test
	public void test1SimpleFile() {
		setup("teste1");
		parser.init();
		try {
			parser.programStart();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		
		assertEquals("ConvertedCode;", parser.convertedProgram);
	}

}
