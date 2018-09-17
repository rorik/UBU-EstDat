package es.ubu.inf.edat.examen;

import es.ubu.inf.edat.examen.examenes2015.Abril.NumerosTriangulares;
import es.ubu.inf.edat.examen.examenes2015.Junio;
import es.ubu.inf.edat.examen.examenes2015.Junio.ContabilidadAccesos;
import es.ubu.inf.edat.examen.examenes2015.Junio.ContabilidadAccesos.CompraProducto;
import es.ubu.inf.edat.examen.examenes2015.Junio.ContabilidadAccesos.CompraProducto.Seccion;
import es.ubu.inf.edat.examen.examenes2015.Junio.GestorProductos;
import es.ubu.inf.edat.examen.examenes2015.Junio.MiArbol;
import es.ubu.inf.edat.examen.examenes2015.Junio.MiArbol.Nodo;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SolucionesTest {

    @Test
    public void normalizarListas() {
        final Integer[] lista1 = new Integer[]{1, 9, 4, 10, 8};
        final Integer[] lista2 = new Integer[]{3, 4, 0,  1, 7};
        final Integer[] resultado = new Integer[]{10, 10, 11, 8, 8};
        assertArrayEquals(resultado, Nuevos.normalizarListas(lista1, lista2));
    }

    @Test
    public void numerosTriangulares() {
        testNumerosTriangulares(20, new Integer[]{1, 3, 6, 10, 15});
        testNumerosTriangulares(0, new Integer[]{});
        testNumerosTriangulares(1, new Integer[]{1});
        testNumerosTriangulares(27, new Integer[]{1, 3, 6, 10, 15, 21});
        testNumerosTriangulares(28, new Integer[]{1, 3, 6, 10, 15, 21, 28});
        testNumerosTriangulares(29, new Integer[]{1, 3, 6, 10, 15, 21, 28});
    }

    private void testNumerosTriangulares(int limiteSuperior, Integer[] resultadoEsperado) {
        final Iterator<Integer> iterador = new NumerosTriangulares(limiteSuperior).iterator();
        for (final Integer esperado : resultadoEsperado) {
            if (!iterador.hasNext()) {
                fail();
            }
            assertEquals(esperado, iterador.next());
        }
        if (iterador.hasNext()) {
            fail();
        }
    }

    @Test
    public void miArbol() {
        MiArbol<Integer> arbol = new MiArbol<>();
        Nodo<Integer> raiz = new Nodo<>(111);
        Nodo<Integer> nodo1 = new Nodo<>(222);
        Nodo<Integer> nodo2 = new Nodo<>(333);
        Nodo<Integer> nodo2bis = new Nodo<>(444);
        Nodo<Integer> nodo2bis2 = new Nodo<>(55);
        Nodo<Integer> nodo3 = new Nodo<>(666);
        Nodo<Integer> nodo4 = new Nodo<>(777);
        Nodo<Integer> nodo5 = new Nodo<>(88);
        arbol.setRaiz(raiz);
        raiz.addHijo(nodo1);
        raiz.addHijo(new Nodo<>(99));
        nodo1.addHijo(nodo2);
        nodo1.addHijo(new Nodo<>(98));
        nodo2bis.addHijo(nodo2bis2);
        nodo1.addHijo(nodo2bis);
        nodo2.addHijo(nodo3);
        nodo3.addHijo(new Nodo<>(97));
        nodo3.addHijo(new Nodo<>(96));
        nodo3.addHijo(new Nodo<>(95));
        nodo3.addHijo(new Nodo<>(94));
        nodo3.addHijo(nodo4);
        nodo3.addHijo(new Nodo<>(93));
        nodo3.addHijo(new Nodo<>(92));
        nodo4.addHijo(nodo5);

        /* Depth */
        assertEquals(0, arbol.depth(88));
        assertEquals(1, arbol.depth(777));
        assertEquals(2, arbol.depth(666));
        assertEquals(0, arbol.depth(55));
        assertEquals(1, arbol.depth(444));
        assertEquals(3, arbol.depth(333));
        assertEquals(4, arbol.depth(222));
        assertEquals(5, arbol.depth(111));
        assertEquals(-1, arbol.depth(0));
        assertEquals(-1, arbol.depth(91));

        /* Hojas */
        List<Integer> hojas = arbol.nodosHoja();
        assertEquals(10, hojas.size());
        assertTrue(hojas.containsAll(Arrays.asList(99, 98, 97, 96, 95, 94, 93, 92, 88, 55)));
    }

    @Test
    public void gestorProductos() {
        GestorProductos<Integer> gestor = new GestorProductos<>();
        gestor.addProducto(99, 3);
        gestor.addProducto(88, 5);
        gestor.addProducto(77, 2);
        gestor.addProducto(66, 2);
        gestor.addProducto(55, 1);
        gestor.addProducto(44, 6);

        assertEquals((Integer) 55, gestor.siguienteProducto());
        assertEquals((Integer) 77, gestor.siguienteProducto());
        assertEquals((Integer) 77, gestor.siguienteProducto());
        assertEquals((Integer) 66, gestor.siguienteProducto());
        assertEquals((Integer) 66, gestor.siguienteProducto());
        assertEquals((Integer) 99, gestor.siguienteProducto());
        assertEquals((Integer) 99, gestor.siguienteProducto());
        assertEquals((Integer) 99, gestor.siguienteProducto());
        for (int i = 0; i < 5; i++) {
            assertEquals((Integer) 88, gestor.siguienteProducto());
        }
        for (int i = 0; i < 6; i++) {
            assertEquals((Integer) 44, gestor.siguienteProducto());
        }
        assertNull(gestor.siguienteProducto());
    }

    @Test
    public void ContabilidadAccesos() {
        ContabilidadAccesos contabilidadAccesos = new ContabilidadAccesos();
        contabilidadAccesos.addCompra(new CompraProducto("4House", Seccion.Casa, new Date(1000), 1));
        contabilidadAccesos.addCompra(new CompraProducto("ABC", Seccion.Moda, new Date(200), 2));
        contabilidadAccesos.addCompra(new CompraProducto("abc", Seccion.Motor, new Date(300), 3));
        contabilidadAccesos.addCompra(new CompraProducto("4House", Seccion.Telefonia, new Date(1000000123), 4));
        contabilidadAccesos.addCompra(new CompraProducto("4House", Seccion.Telefonia, new Date(1000000124), 5));
        contabilidadAccesos.addCompra(new CompraProducto("4House", Seccion.Telefonia, new Date(1100000125), 6));
        contabilidadAccesos.addCompra(new CompraProducto("ABC", Seccion.Telefonia, new Date(1500), 7));
        contabilidadAccesos.addCompra(new CompraProducto("XYZ", Seccion.Telefonia, new Date(1600), 8));

        /* Ordenación */
        Iterator<CompraProducto> iterator = contabilidadAccesos.iterator();
        int[] ordenEsperado = new int[]{2,3,1,7,8,4,5,6};
        for (int i = 0; i < ordenEsperado.length; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(ordenEsperado[i], (int) iterator.next().getImporte());
        }
        assertFalse(iterator.hasNext());

        /* UsuariosRetornan */
        List<String> usuariosRetornan = contabilidadAccesos.usuariosRetornan();
        assertEquals(2, usuariosRetornan.size());
        assertTrue(usuariosRetornan.contains("4House"));
        assertTrue(usuariosRetornan.contains("ABC"));

        /* ComprasCercanas */
        CompraProducto referencia1 = new CompraProducto("TESTING", Seccion.Casa, new Date(1000000000), 0);
        assertEquals(3, contabilidadAccesos.comprasCercanas(referencia1, 1).size());
        assertEquals(2, contabilidadAccesos.comprasCercanas(referencia1, 0).size());
        assertEquals(3, contabilidadAccesos.comprasCercanas(referencia1, 10).size());
        assertEquals(8, contabilidadAccesos.comprasCercanas(referencia1, 20).size());

        CompraProducto referencia2 = new CompraProducto("TESTING", Seccion.Casa, new Date(0), 0);
        assertEquals(5, contabilidadAccesos.comprasCercanas(referencia2, 0).size());
        assertEquals(5, contabilidadAccesos.comprasCercanas(referencia2, 1).size());

        CompraProducto referencia3 = new CompraProducto("TESTING", Seccion.Casa, new Date(100000000), 0);
        assertEquals(0, contabilidadAccesos.comprasCercanas(referencia3, 0).size());
        assertEquals(5, contabilidadAccesos.comprasCercanas(referencia3, 1).size());
        assertEquals(7, contabilidadAccesos.comprasCercanas(referencia3, 10).size());
        assertEquals(8, contabilidadAccesos.comprasCercanas(referencia3, 11).size());


    }
}