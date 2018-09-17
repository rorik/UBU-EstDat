package es.ubu.inf.edat.sesion06;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb;
import es.ubu.lsi.edat.datos.accesosWeb8.GeneradorAccesos;

public class AnalisisWebTest {

	List<AccesoWeb> listaAccesos;
	AnalisisWeb analisis;
	
	@Before
	public void setUp() {
		listaAccesos = GeneradorAccesos.accesosAleatorios(12);
		System.out.println("Contenido de listaAccesos: "+listaAccesos);
		
		analisis = new AnalisisWeb();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testAccesosEnHora() {
		
		analisis.insertaAccesos(listaAccesos);
		
		List<AccesoWeb> control = new ArrayList<AccesoWeb>(3);
		control.add(listaAccesos.get(4));
		control.add(listaAccesos.get(8));
		control.add(listaAccesos.get(11));
		
		List<AccesoWeb> respuesta = analisis.accesosEnHora(20);
		
		assertEquals(control.size(),respuesta.size());
		assertTrue(control.containsAll(respuesta));

		control = new ArrayList<AccesoWeb>(2);
		control.add(listaAccesos.get(0));
		control.add(listaAccesos.get(3));
		
		respuesta = analisis.accesosEnHora(21);
		
		assertEquals(control.size(),respuesta.size());
		assertTrue(control.containsAll(respuesta));
		
	}

	@Test
	public void testAccesosDeProcedencia (){
		
		analisis.insertaAccesos(listaAccesos);
		
		List<AccesoWeb> control = new ArrayList<AccesoWeb>(3);
		control.add(listaAccesos.get(3));
		control.add(listaAccesos.get(5));
		control.add(listaAccesos.get(11));

		List<AccesoWeb> respuesta = analisis.accesosDeProcedencia("121.15.");
		
		assertEquals(control.size(),respuesta.size());
		assertTrue(control.containsAll(respuesta));

		control = new ArrayList<AccesoWeb>(3);
		respuesta = analisis.accesosDeProcedencia("MiraMamaSinManos!");

		assertEquals(control.size(),respuesta.size());
		assertTrue(control.containsAll(respuesta));
		
	}
	
	@Test
	public void testAccesosSeccion() {

		analisis.insertaAccesos(listaAccesos);
		
		List<AccesoWeb> control = new ArrayList<AccesoWeb>(3);
		control.add(listaAccesos.get(3));
		control.add(listaAccesos.get(5));
		control.add(listaAccesos.get(7));

		List<AccesoWeb> respuesta = analisis.accesosSeccion(AccesoWeb.SeccionWeb.Organizacion);
		
		assertEquals(control.size(),respuesta.size());
		assertTrue(control.containsAll(respuesta));

	}

	@Test
	public void testHoraMayorTrafico() {
		
		analisis.insertaAccesos(listaAccesos);
		List<Integer> control = Arrays.asList(20,21);
		List<Integer> respuesta = analisis.horaMayorTrafico(2);
		
		assertEquals(control,respuesta);			
		
	}
}
