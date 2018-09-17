package es.ubu.inf.edat.pr10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import es.ubu.inf.edat.pr10.ColaPrioridadMinMax;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ubu.inf.edat.datos.Persona;
import es.ubu.inf.edat.pr10.ColaPrioridadMinMax;

/**
 * Clase para la prueba del correcto funcionamiento de la clase cola de prioridad.
 * 
 * @author bbaruque
 *
 */
public class TestColaPrioridadMinMax {

	ColaPrioridadMinMax<Integer> colaPri;

	@Before
	public void setUp() throws Exception {
		//		colaPri = new ColaPrioridadMinMax<>(12);
		colaPri = new ColaPrioridadMinMax<>();
	}

	@After
	public void tearDown() throws Exception {
		colaPri.clear();
	}

	@Test
	public void testOffer() {
		Integer[] contenido = {21,8,10,71,46,11,31,31,41,16,51};
		for(int i : contenido) {
			colaPri.offer(i);
		}

		assertEquals(11,colaPri.size());
		assertTrue(colaPri.containsAll(Arrays.asList(contenido)));

	}

	@Test
	public void testPoll() {

		testOffer();

		List<Integer> control = Arrays.asList(8,10,11,16,21,31,31,41,46,51,71);

		List<Integer> ret = new ArrayList<>();
		while(!colaPri.isEmpty()) {
			ret.add(colaPri.poll());
		}

		assertEquals(control,ret);

	}


	@Test
	public void testPollLast() {

		testOffer();

		List<Integer> control = Arrays.asList(71,51,46,41,31,31,21,16,11,10,8);

		List<Integer> ret = new ArrayList<>();
		while(!colaPri.isEmpty()) {
			ret.add(colaPri.pollLast());
		}

		assertEquals(control,ret);

	}

	@Test
	public void testPeek() {
		testOffer();

		List<Integer> control = Arrays.asList(8,10,11,16,21,31,31,41,46,51,71);

		for (int elem : control) {
			assertEquals((Integer) elem, colaPri.peek());
			colaPri.poll();
		}

	}

	@Test
	public void testPeekLast() {

		testOffer();

		List<Integer> control = Arrays.asList(71,51,46,41,31,31,21,16,11,10,8);

		for (int elem : control) {
			assertEquals((Integer) elem, colaPri.peekLast());
			colaPri.pollLast();
		}

	}

	@Test
	public void testIterator() {

		testOffer();

		List<Integer> control = Arrays.asList(8, 71, 31, 21, 16, 10, 11, 31, 41, 46, 51);

		Iterator<Integer> itC = control.iterator();
		Iterator<Integer> it = colaPri.iterator();

		assertEquals(control.size(),colaPri.size());

		while(itC.hasNext() && it.hasNext()) {
			assertEquals(itC.next(), it.next());
		}

	}

	@Test
	public void testNoComparables() {

		ColaPrioridadMinMax<Persona> colaPriPer = new ColaPrioridadMinMax<>	(
				new Comparator<Persona>(){
					@Override
					public int compare(Persona a, Persona b) {
						return a.getEdad() - b.getEdad();
					}
				}
			);

		colaPriPer.offer(new Persona("Pedro",22));
		colaPriPer.offer(new Persona("Maria",11));
		colaPriPer.offer(new Persona("Juan",54));
		colaPriPer.offer(new Persona("Blasa",82));
		colaPriPer.offer(new Persona("Luke",19));
		colaPriPer.offer(new Persona("Han",32));
		colaPriPer.offer(new Persona("Leia",21));
		
		Persona anterior = new Persona("Baby",0);		
		while(!colaPriPer.isEmpty()) {
			
			Persona actual = colaPriPer.poll();
			
			assertTrue(actual.getEdad() > anterior.getEdad());
			anterior = actual;
			
			System.out.print(anterior+"; ");
		}
		
	}

}
