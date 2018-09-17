package es.ubu.inf.edat.sesion11;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.ubu.inf.edat.sesion11.EjerciciosPipelines;
import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb;
import es.ubu.lsi.edat.datos.accesosWeb8.GeneradorAccesos;

public class EjerciciosPipelinesTest {

	List<AccesoWeb> listaAccesos;
	
	@Before
	public void setUp() {
		listaAccesos = GeneradorAccesos.accesosAleatorios(12);
		System.out.println("Contenido de listaAccesos: "+listaAccesos);
	}
	
    @Test
    public void elementsToUpperCase() {
        List<String> collection = asList("My", "name", "is", "John", "Doe");
        List<String> expected = asList("MY", "NAME", "IS", "JOHN", "DOE");
        
        List<String> lI = EjerciciosPipelines.toUpper_Iter(collection);
        List<String> lS = EjerciciosPipelines.toUpper_Stream(collection);
        
        System.out.println(lI);
        System.out.println(lS); 
        
        assertEquals(expected, lI);
        assertEquals(expected, lS);
    }
    
	/*
	Filter collection so that only elements with less than 4 characters are returned.
	 */
    @Test
    public void filterCollectionByLength() {
        List<String> collection = asList("My", "name", "is", "John", "Doe");
        List<String> expected = asList("My", "is", "Doe");
        
        List<String> lI = EjerciciosPipelines.minimumLenght_Iter(collection,4);
        List<String> lS = EjerciciosPipelines.minimumLenght_Stream(collection,4);
        
        System.out.println(lI);
        System.out.println(lS); 
        
        assertEquals(expected, lI);
        assertEquals(expected, lS);
        
    }
    
    @Test
    public void accesosEnPeriodo_Test() {
    	
    	LocalDateTime ini = LocalDateTime.parse("2017-04-19T00:00:00");
    	LocalDateTime fin = LocalDateTime.parse("2017-04-19T23:59:00");
    
    	System.out.println("\n\n");
    	EjerciciosPipelines.accesosEnPeriodo_iter (listaAccesos, ini, fin);
    	System.out.println("\n\n");
    	EjerciciosPipelines.accesosEnPeriodo_stream (listaAccesos, ini, fin);
    	
    	/**
    	 * Puesto que solo se pide imprimir, es importante comprobar de forma
    	 * manual que todos los accesos impresos pertenecen al 2017-04-19
    	 */
    	
    }

    @Test
    public void accesosDesdeIP_Test() {
    
    	List<AccesoWeb> control = new ArrayList<AccesoWeb>();
    	control.add(listaAccesos.get(8));
    	control.add(listaAccesos.get(9));
    	
    	List<AccesoWeb> result_iter = EjerciciosPipelines.accesosDesdeIP_iter (listaAccesos, "182.146.186.");
    	List<AccesoWeb> result_stream = EjerciciosPipelines.accesosDesdeIP_stream (listaAccesos, "182.146.186.");

    	assertEquals(control,result_stream);
		assertEquals(control,result_iter);
    	
    }
    
    @Test
    public void cuantosAccesosDia_Test() {

    	int result = EjerciciosPipelines.cuantosAccesosDia_iter (listaAccesos, DayOfWeek.THURSDAY);
    	assertEquals(6,result);
    	
    	result = EjerciciosPipelines.cuantosAccesosDia_stream (listaAccesos, DayOfWeek.THURSDAY);
    	assertEquals(6,result);
    	
    	result = EjerciciosPipelines.cuantosAccesosDia_iter (listaAccesos, DayOfWeek.SUNDAY);
    	assertEquals(0,result);

    	result = EjerciciosPipelines.cuantosAccesosDia_stream (listaAccesos, DayOfWeek.SUNDAY);
    	assertEquals(0,result);
    	
    }


    @Test
    public void ipsUnicas_Test() {
    	
    	String[] unicas = {"53.184.200.233", "130.44.242.174", "56.44.225.243",
    			"121.15.13.62", "180.12.191.35", "230.151.141.104", 
    			"214.236.244.28", "182.146.186.42"};
    	
    	List<String> control = Arrays.asList(unicas);
    	
    	List<String> retorno_s = EjerciciosPipelines.ipsUnicas_stream(listaAccesos);
    	assertEquals(control, retorno_s);

    	List<String> retorno_i = EjerciciosPipelines.ipsUnicas_iter(listaAccesos);
    	assertEquals(control.size(), retorno_i.size());
    	assertTrue(retorno_i.containsAll(control));

    	
    }

    @Test
    public void contieneSeccion_Test() {
    	
    	assertTrue(	EjerciciosPipelines.algunoSeccion_iterator(listaAccesos,AccesoWeb.SeccionWeb.Inicio) );
    	assertTrue(	EjerciciosPipelines.algunoSeccion_stream(listaAccesos,AccesoWeb.SeccionWeb.Inicio) );
    	
    	assertFalse(	EjerciciosPipelines.algunoSeccion_iterator(listaAccesos.subList(0, 6),AccesoWeb.SeccionWeb.Inicio) );
    	
    }

    
}
