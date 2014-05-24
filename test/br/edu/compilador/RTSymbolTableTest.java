package br.edu.compilador;

import static org.junit.Assert.*;

import org.junit.Test;

public class RTSymbolTableTest {

	@Test
	public void testIfExistsSymbol() {
		RTSymbolTable table = new RTSymbolTable();
		String name = "test";
		IntegerVariable intTested = new IntegerVariable(name);
		table.add(intTested);
		boolean existsTest = table.exists(name, IntegerVariable.class);
		
		assertTrue(existsTest);
	}
	
	@Test
	public void testIfNotExistsSymbol() {
		RTSymbolTable table = new RTSymbolTable();
		String name = "test";
		IntegerVariable intTested = new IntegerVariable(name);
		table.add(intTested);
		boolean existsTest = table.exists("notTest", IntegerVariable.class);
		
		assertFalse(existsTest);
	}
	
	@Test
	public void testIfNotExistsSymbolType() {
		RTSymbolTable table = new RTSymbolTable();
		String name = "test";
		IntegerVariable intTested = new IntegerVariable(name);
		table.add(intTested);
		boolean existsTest = table.exists(name, StringVariable.class);
		
		assertFalse(existsTest);
	}
	
	@Test
	public void testIfExistsVariableSymbolType() {
		RTSymbolTable table = new RTSymbolTable();
		String name = "test";
		IntegerVariable intTested = new IntegerVariable(name);
		table.add(intTested);
		boolean existsTest = table.exists(name, Variable.class);
		
		assertTrue(existsTest);
	}
}
