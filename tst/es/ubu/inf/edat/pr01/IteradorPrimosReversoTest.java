package es.ubu.inf.edat.pr01;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IteradorPrimosReversoTest {

	IteradorPrimosReverso ItPrimosR;

	@Before
	public void setUp() throws Exception {
		ItPrimosR = new IteradorPrimosReverso(150);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIteracionReversa() {
		int[] control = {149,139,137,131,127,113,109,107, 
				103,101,97,89,83,79,73,71,67,
				61,59,53,47,43,41,37,31,29,
				23,19,17,13,11,7,5,3,2};
		
		int i = 0;
		while(ItPrimosR.hasPrevious()) {
			int devuelto = ItPrimosR.previous();
			System.out.println(devuelto);
			assertEquals(control[i++],devuelto);
		}
		
	}
	
	@Test
	(expected=NoSuchElementException.class)
	public void testIteracionReversaExcepcion() {
	
		while(ItPrimosR.hasPrevious()) {
			System.out.println(ItPrimosR.previous());
		}
		
		// Se fuerza la excepcion
		ItPrimosR.previous();
		
	}
	
	@Test
	public void testIteracionDirecta() {
		
		int[] control = {151, 157, 163, 167, 173, 179, 
				181, 191, 193, 197, 199};
		
		for(int i=0; i<control.length; i++) {
			int devuelto = ItPrimosR.next();
			System.out.println(devuelto);
			assertEquals(control[i],devuelto);
		}
		
	}


}
