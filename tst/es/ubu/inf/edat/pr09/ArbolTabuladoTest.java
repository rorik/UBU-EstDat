package es.ubu.inf.edat.pr09;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArbolTabuladoTest {

	ArbolTabulado<String> jerarquia;
	String[] colecciones = {"List","Queue","Set","ArrayList","LinkedList","Deque","TreeSet","HashSet"};
	String[] conjuntos = {"SortedSet","TreeSet","HashSet"};
	
	@Before
	public void setUp() throws Exception {
		jerarquia = new ArbolTabulado<String>();
	}

	@After
	public void tearDown() throws Exception {
		jerarquia.clear();
	}

	/**
	 * Comprueba la insercion en el Árbol (elementos individuales)
	 */
	@Test
	public void testInsertar() {
		assertNull(jerarquia.put("Collection",null));
		assertNull(jerarquia.put("List","Collection"));
		assertNull(jerarquia.put("Queue","Collection"));
		assertNull(jerarquia.put("Set","Collection"));
		assertNull(jerarquia.put("ArrayList","List"));
		assertNull(jerarquia.put("LinkedList","List"));
		assertNull(jerarquia.put("Deque","Queue"));
		assertNull(jerarquia.put("SortedSet","Set"));
		assertNull(jerarquia.put("TreeSet","SortedSet"));
		assertNull(jerarquia.put("HashSet","Set"));
		
		assertEquals(10,jerarquia.size());
		
	}

	// TODO - Probar a 	(1) insertar un elemento como hijo de un nodo no incluido
	// 					(2) insertar un elemento ya incluido con otro padre
	
	/**
	 * Comprueba el acceso a los descendientes de un determinado nodo
	 */
	@Test
	public void testDescendientes() {
		
		List<String> desc;
		testInsertar();
		
		desc = jerarquia.getDescendants("Collection");
		assertEquals(9,desc.size());
		assertTrue(desc.containsAll(Arrays.asList(colecciones)));
		
		desc = jerarquia.getDescendants("Set");
		assertEquals(3,desc.size());
		assertTrue(desc.containsAll(Arrays.asList(conjuntos)));
		
	}

	// TODO - Probar a obtener descendientes de nodos hojas
	
	
	/**
	 * Comprueba el acceso a los descendientes de un determinado nodo
	 */
	@Test
	public void testAntecesores() {
		
		List<String> anc;
		testInsertar();
		
		anc = jerarquia.getAncestors("Set");
		assertEquals(1,anc.size());
		assertTrue(anc.containsAll(Arrays.asList("Collection")));
		
		anc = jerarquia.getAncestors("TreeSet");
		assertEquals(3,anc.size());
		assertTrue(anc.containsAll(Arrays.asList("SortedSet","Set","Collection")));
		
	}
	
	// TODO -  Probar a obtener antecesores de la raiz
	
	/**
	 * 
	 */
	@Test
	public void testBorrado() {
		
		testInsertar();
		assertEquals(10,jerarquia.size());
		
		jerarquia.remove("SortedSet");
		assertEquals(9,jerarquia.size());
		
		List<String> anc = jerarquia.getAncestors("TreeSet");
		assertEquals(2, anc.size());
		assertTrue(anc.containsAll(Arrays.asList("Set","Collection")));

		List<String> desc = jerarquia.getDescendants("Set");
		assertEquals(2, desc.size());
		assertTrue(desc.containsAll(Arrays.asList("TreeSet","HashSet")));
		
	}
	
	// TODO  Probar a realizar: (1) el borrado de la raiz, (2) el borrado de una hoja
	
	/**
	 * 
	 */
	@Test
	public void testRecorridoAnchura(){
		
		testInsertar();
		
		List<String> anchura = jerarquia.breadthTraverse();
		assertEquals(10, anchura.size());
		
		List<String> Nivel1 = Arrays.asList("Collection");
		List<String> Nivel2 = Arrays.asList("List","Queue","Set");
		List<String> Nivel3 = Arrays.asList("ArrayList","LinkedList","Deque","HashSet","SortedSet");
		List<String> Nivel4 = Arrays.asList("TreeSet");

		assertTrue( Nivel1.containsAll(anchura.subList(0, 0)) );
		assertTrue( Nivel2.containsAll(anchura.subList(1, 3)) );
		assertTrue( Nivel3.containsAll(anchura.subList(4, 8)) );
		assertTrue( Nivel4.containsAll(anchura.subList(9, 9)) );
		
	}

	/**
	 * 
	 */
	@Test
	public void testAltura() {
		
		testInsertar();
	
		assertEquals(0,jerarquia.height("TreeSet"));
		assertEquals(2,jerarquia.height("Set"));
		assertEquals(1,jerarquia.height("Queue"));
		assertEquals(3,jerarquia.height("Collection"));
		
	}

	// TODO  Probar a consultar: la altura de un nodo que no existe
	
	/**
	 * 
	 */
	@Test
	public void testProfundidad() {
		
		testInsertar();
	
		assertEquals(3,jerarquia.depth("TreeSet"));
		assertEquals(1,jerarquia.depth("Set"));
		assertEquals(1,jerarquia.depth("Queue"));
		assertEquals(0,jerarquia.depth("Collection"));
		assertEquals(2,jerarquia.depth("Deque"));
		
	}
	
	// TODO  Probar a realizar: (1) el recorrido de un árbol vacío

	
}
