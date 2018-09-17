package es.ubu.inf.edat.pr01;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IteradorPrimosTest {

	Iterator<Integer> ItPrimos;

	@Before
	public void setUp() throws Exception {
		ItPrimos = new IteradorPrimos();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIteracion() {
		int[] control = { 2, 3, 5, 7, 11, 13, 17, 19, 
				23, 29, 31, 37, 41, 43, 47, 53, 59,
				61, 67, 71, 73, 79, 83, 89, 97, 101, 
				103, 107, 109, 113, 127, 131, 137, 139, 149};
		for(int i=0; i<control.length; i++) {
			int devuelto = ItPrimos.next();
			System.out.println(devuelto);
			assertEquals(control[i],devuelto);
		}
	}

}
