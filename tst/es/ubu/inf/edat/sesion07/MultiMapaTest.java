package es.ubu.inf.edat.sesion07;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que permite la prueba de la clase @code{ MultiMapa }
 * 
 * @author bbaruque
 *
 */
public class MultiMapaTest {

	MultiMapa<Character, Integer> miMultiMapa = new MultiMapa<>();

	@Before
	public void setUp() throws Exception {
		//		miMultiMapa = new MultiMapa<>();
	}

	@After
	public void tearDown() throws Exception {
		miMultiMapa.clear();
	}

	@Test
	public void testEntrySet() {
		Set<Map.Entry<Character,Integer>> control = new HashSet<>();
		control.add(new AbstractMap.SimpleEntry<>('a', 1));
		control.add(new AbstractMap.SimpleEntry<>('a', 2));
		control.add(new AbstractMap.SimpleEntry<>('a', 3));
		control.add(new AbstractMap.SimpleEntry<>('b', 1));
		control.add(new AbstractMap.SimpleEntry<>('b', 2));
		control.add(new AbstractMap.SimpleEntry<>('c', 3));
		control.add(new AbstractMap.SimpleEntry<>('c', 4));
		control.add(new AbstractMap.SimpleEntry<>('c', 5));

		testPut();
		Set<Map.Entry<Character,Integer>> devuelto = miMultiMapa.entrySet();

		assertEquals(control,devuelto);

	}

	@Test
	public void testPut() {

		miMultiMapa.put('a', 1);
		miMultiMapa.put('b', 1);
		miMultiMapa.put('a', 2);
		miMultiMapa.put('b', 2);
		miMultiMapa.put('c', 3);
		miMultiMapa.put('a', 3);
		miMultiMapa.put('c', 5);
		miMultiMapa.put('c', 4);

		assertEquals("Error en el tamaño tras insertar.", 8, miMultiMapa.size());

	}

	@Test
	public void testContainsKey() {

		testPut();

		assertTrue(miMultiMapa.containsKey('a'));
		assertTrue(miMultiMapa.containsKey('b'));
		assertFalse(miMultiMapa.containsKey('d'));
		assertTrue(miMultiMapa.containsKey('c'));

	}


	@Test
	public void testGet() {

		testPut();

		assertEquals(Integer.valueOf(1),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(2),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(3),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(3),miMultiMapa.get('c'));
		assertEquals(Integer.valueOf(5),miMultiMapa.get('c'));
		assertEquals(Integer.valueOf(1),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(4),miMultiMapa.get('c'));
		assertEquals(Integer.valueOf(1),miMultiMapa.get('b'));

	}

	@Test
	public void testGetAllMappings() {
		testPut();
	
		List<Integer> control = Arrays.asList(1,2,3);
		assertEquals(control,miMultiMapa.getAllMappings('a'));
	
		control = Arrays.asList(1,2);
		assertEquals(control,miMultiMapa.getAllMappings('b'));
		
		control = Arrays.asList(3,5,4);
		assertEquals(control,miMultiMapa.getAllMappings('c'));
		
	}

	@Test
	public void testRemove() {
		testPut();

		assertEquals(Integer.valueOf(1),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(1),miMultiMapa.remove('a'));
		assertEquals(7,miMultiMapa.size());

		assertEquals(Integer.valueOf(2),miMultiMapa.get('a'));
		assertEquals(Integer.valueOf(2),miMultiMapa.remove('a'));
		assertEquals(6,miMultiMapa.size());

		assertEquals(Integer.valueOf(3),miMultiMapa.get('c'));
		assertEquals(Integer.valueOf(3),miMultiMapa.remove('c'));
		assertEquals(5,miMultiMapa.size());

	}
	
	@Test
	public void testValues() {

		testPut();

		List<Integer> control = Arrays.asList(1,1,2,2,3,3,5,4);
		Collection<Integer> devuelto = miMultiMapa.values();

		assertEquals(control.size(),devuelto.size());
		devuelto.retainAll(Arrays.asList(1));
		assertEquals(2,devuelto.size());

		devuelto = miMultiMapa.values();
		devuelto.retainAll(Arrays.asList(3));
		assertEquals(2,devuelto.size());

		devuelto = miMultiMapa.values();
		devuelto.retainAll(Arrays.asList(4));
		assertEquals(1,devuelto.size());

	}
	
	@Test 
	public void testGenericidad() {
		
		MultiMapa<Integer, String> otroMultiMapa = new MultiMapa<>();
		otroMultiMapa.put(1, "uno");
		otroMultiMapa.put(1, "otrouno");
		otroMultiMapa.put(2, "dos");
		
		assertEquals("dos", otroMultiMapa.get(2));
		assertEquals("uno", otroMultiMapa.get(1));
		assertEquals("otrouno", otroMultiMapa.get(1));
		
	}

	@Test
	public void testIsEmpty() {
		assertTrue(miMultiMapa.isEmpty());
		testPut();
		assertFalse(miMultiMapa.isEmpty());
		miMultiMapa.clear();
		assertTrue(miMultiMapa.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals(0,miMultiMapa.size());
		testPut();
		assertEquals(8,miMultiMapa.size());
		miMultiMapa.clear();
		assertEquals(0,miMultiMapa.size());
	}

}
