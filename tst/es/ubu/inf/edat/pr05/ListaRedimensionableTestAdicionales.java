package es.ubu.inf.edat.pr05;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests adicionales para las soluciones de la práctica 5 de la clase
 * Estructuras de Datos de la Universidad de Burgos, curso 2017-2018.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 *
 * @see ListaRedimensionableArray
 * @see ListaRedimensionableList
 * @see AbstractListaRedimensionable
 * @see ListaRedimensionableTest
 */
@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class ListaRedimensionableTestAdicionales {

    private List<List<Character>> listas;

    @Before
    public void setUp() {
        listas = new ArrayList<>(3);
        listas.add(new ListaRedimensionableArray<>());
        listas.add(new ListaRedimensionableList<>());
        listas.add(new ListaRedimensionableList<>(new LinkedList<>()));
    }

    /**
     * [null, a, null, null, b, null, c, null, null, d]
     */
    private void configuracionUno() {
        final Character[] control = new Character[]{null, 'a', null, null, 'b', null, 'c', null, null, 'd'};
        for (List<Character> lista : listas) {
            //noinspection ObjectEqualsNull
            assertFalse(lista.equals(null));
            assertEquals(0, lista.size());
            lista.add(1,'a');
            lista.add(4,'b');
            lista.add(6,'c');
            lista.add(9,'d');
            fullEquals(lista, control);
        }
    }

    /**
     * [?]
     */
    private void configuracionDos() {
        final Character[] control = new Character[]{'?'};
        for (List<Character> lista : listas) {
            assertEquals(0, lista.size());
            lista.add('?');
            fullEquals(lista, control);
        }
    }

    private static <E> void fullEquals(List<? extends E> lista, E[] control) {
        assertEquals(control.length, lista.size());
        for (int i = 0; i < lista.size(); i++) {
            assertEquals(control[i], lista.get(i));
        }
        assertTrue(lista.equals(control));
    }

    @Test
    public void testModificaciones1(){
        configuracionUno();
        //[null, a, null, null, b, null, c, null, null, d]

        final Character[] control1 = {'a', null, null, 'b', null, 'c', null, null, 'd'};
        final Character[] control2 = {null, null, 'b', null, 'c', null, null, 'd'};
        final Character[] control3 = {null, null, null, 'b', null, 'c', null, null, 'd'};
        final Character[] control4 = {null, null, 'a', null, null, 'b', null, 'c', null, null, 'd'};
        final Character[] control5 = {'a', 'b', 'c', 'd'};
        final Character[] control6 = {'d', 'c', 'b', 'a'};
        final Character[] control7 = {'d', '-', 'c', '-', 'b', '-', 'a'};
        for (List<Character> lista : listas) {
            assertNull(lista.remove(0));
            fullEquals(lista, control1);
            assertEquals('a', lista.remove(0).charValue());
            fullEquals(lista, control2);
            assertNull(lista.remove(-1));
            fullEquals(lista, control2);
            assertNull(lista.remove(-2));
            fullEquals(lista, control3);
            assertNull(lista.set(0, 'a'));
            assertNull(lista.remove(-3));
            fullEquals(lista, control4);
            assertNull(lista.remove(0));
            assertNull(lista.remove(0));
            assertNull(lista.remove(1));
            assertNull(lista.remove(1));
            assertNull(lista.remove(2));
            assertNull(lista.remove(3));
            assertNull(lista.remove(3));
            fullEquals(lista, control5);
            assertEquals('d', lista.set(3, 'a').charValue());
            assertEquals('c', lista.set(2, 'b').charValue());
            assertEquals('b', lista.set(1, 'c').charValue());
            assertEquals('a', lista.set(0, 'd').charValue());
            fullEquals(lista, control6);
            lista.add(1, '-');
            lista.add(3, '-');
            lista.add(5, '-');
            fullEquals(lista, control7);
        }
    }

    @Test
    public void testModificaciones2(){
        configuracionUno();
        //[null, a, null, null, b, null, c, null, null, d]

        final Character[] control1 = {};
        final Character[] control2 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        for (List<Character> lista : listas) {
            for (int i = lista.size() - 1; i >= 0; i--) {
                lista.remove(i);
            }
            fullEquals(lista, control1);
            for (int i = 'A'; i <= 'Z'; i++) {
                lista.add((char) i);
            }
            fullEquals(lista, control2);
            for (int i = lista.size() - 1; i >= 0; i--) {
                lista.remove(i/2);
            }
            fullEquals(lista, control1);
            assertTrue(lista.add('A'));
            for (int i = 1; i < control2.length; i++) {
                lista.add(i, (char) (lista.get(i-1)+1));
            }
            fullEquals(lista, control2);
            for (int i = lista.size(); i > 0; i--) {
                lista.remove(0);
            }
            fullEquals(lista, control1);
            for (int i = 'Z'; i >= 'A'; i--) {
                lista.add(0, (char) i);
            }
            fullEquals(lista, control2);
            lista.remove(-1);
            fullEquals(lista, control2);
            lista.remove(lista.size());
            fullEquals(lista, control2);
        }
    }

    @Test
    public void testModificaciones3() {
        configuracionDos();
        //[?]

        final Character[] control1 = {};
        final Character[] control2 = {null};
        final Character[] control3 = {null, null};
        for (List<Character> lista : listas) {
            lista.clear();
            fullEquals(lista, control1);
            lista.remove(-1);
            fullEquals(lista, control2);
            lista.remove(0);
            fullEquals(lista, control1);
            lista.remove(-2);
            fullEquals(lista, control3);
            lista.remove(-1);
            fullEquals(lista, control3);
            lista.remove(1);
            fullEquals(lista, control2);
            lista.remove(0);
            fullEquals(lista, control1);
            lista.remove(lista.size());
            fullEquals(lista, control1);
            lista.remove(1);
            fullEquals(lista, control2);
        }
    }

    @Test
    public void testModificaciones4() {
        configuracionDos();
        //[?]

        final Character[] control1 = {null, '?'};
        final Character[] control2 = {null, '?', null};
        final Character[] control3 = {'?', null};
        final Character[] control4 = {'?'};
        for (List<Character> lista : listas) {
            lista.remove(-2);
            fullEquals(lista, control1);
            lista.remove(-1);
            fullEquals(lista, control1);
            lista.remove(3);
            fullEquals(lista, control2);
            lista.remove(-1);
            fullEquals(lista, control2);
            lista.remove(0);
            fullEquals(lista, control3);
            lista.remove(-1);
            fullEquals(lista, control3);
            lista.remove(1);
            fullEquals(lista, control4);
        }
    }

    @Test
    public void testModificaciones5(){
        configuracionDos();
        //[?]

        final Character[] control1 = {'?', null, null, null, '!'};
        final Character[] control2 = {'?', null, null, '&', null, '!'};
        final Character[] control3 = {'?', null, '%', null, '&', null, '!'};
        final Character[] control4 = {'?', null, null, null};
        final Character[] control5 = {null, null, null};
        for (List<Character> lista : listas) {
            lista.add(4,'!');
            fullEquals(lista, control1);
            lista.add(3,'&');
            fullEquals(lista, control2);
            lista.add(2,'%');
            fullEquals(lista, control3);
            lista.remove((Character) '%');
            fullEquals(lista, control2);
            lista.remove((Character) '&');
            fullEquals(lista, control1);
            lista.remove((Character) '!');
            fullEquals(lista, control4);
            lista.remove((Character) '?');
            fullEquals(lista, control5);
        }
    }

    @Test
    public void testIgualdad() {
        configuracionUno();
        //[null, a, null, null, b, null, c, null, null, d]

        final List<Character[]> verdaderos = new ArrayList<>(3);
        verdaderos.add(new Character[]{'a', 'b', 'c', 'd'});
        verdaderos.add(new Character[]{'a', 'b', 'c', 'd', null});
        verdaderos.add(new Character[]{'a', null, null, null, 'b', 'c', 'd', null});
        final List<Character[]> falsos = new ArrayList<>(8);
        falsos.add(new Character[]{'a', 'b', 'c', 'd', 'e'});
        falsos.add(new Character[]{'a', 'b', 'c',});
        falsos.add(new Character[]{'a', 'b', 'c', 'c'});
        falsos.add(new Character[]{'a', 'b', 'd', 'd'});
        falsos.add(new Character[]{'d', 'c', 'b', 'a'});
        falsos.add(new Character[]{'a', null, null, null, 'b', 'c', 'c', null});
        falsos.add(new Character[]{'a', null, null, null, 'b', 'c', 'd', null, 'e'});
        falsos.add(new Character[]{});

        for (List<Character> lista : listas) {
            for (Character[] control : verdaderos) {
                assertTrue(lista.equals(control));
            }
            for (Character[] control : falsos) {
                assertFalse(lista.equals(control));
            }
        }
    }

}
