package es.ubu.inf.edat.pr09;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests adicionales de la práctica 9.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 */
@SuppressWarnings("OverwrittenKey")
public class ArbolTabuladoTestAdicionales {

    private ArbolTabulado<Character> arbol;

    @Before
    public void before() {
        arbol = new ArbolTabulado<>();
    }

    private void config1() {
        arbol.put('a', null);
        arbol.put('b', 'a');
        arbol.put('B', 'a');
        arbol.put('c', 'b');
        arbol.put('d', 'b');
        arbol.put('C', 'B');
        arbol.put('D', 'B');
        arbol.put('E', 'D');
        arbol.put('F', 'E');
        arbol.put('G', 'F');
    }

    @Test
    public void config() {
        config1();
        assertEquals(10, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'b', 'B'},
                {'c', 'd', 'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void putDobleRaiz() {
        arbol.put('X', null);
        arbol.put('Y', null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void putDobleRaizDespues() {
        arbol.put('X', null);
        arbol.put('a', 'X');
        arbol.put('Y', null);
    }

    @Test
    public void putDobleRaizDespuesIgual() {
        arbol.put('X', null);
        arbol.put('a', 'X');
        arbol.put('X', null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putPadreDesconocido() {
        arbol.put('X', null);
        arbol.put('a', 'v');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putSinPadre() {
        arbol.put('X', 'Y');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putSinPadreReflexivo() {
        arbol.put('X', 'X');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putReflexivo() {
        arbol.put('X', null);
        arbol.put('A', 'X');
        arbol.put('A', 'A');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putReflexivoRaiz() {
        arbol.put('X', null);
        arbol.put('X', 'X');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putReciprocoDoble() {
        try {
            arbol.put('X', null);
            arbol.put('A', 'X');
            arbol.put('B', 'X');
            arbol.put('A', 'B');
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
        arbol.put('B', 'A');
    }

    @Test(expected = IllegalArgumentException.class)
    public void putReciprocoCuadruple() {
        try {
            arbol.put('X', null);
            arbol.put('A', 'X');
            arbol.put('B', 'X');
            arbol.put('C', 'X');
            arbol.put('D', 'X');
            arbol.put('A', 'B');
            arbol.put('B', 'C');
            arbol.put('C', 'D');
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        }
        arbol.put('D', 'A');
    }

    @Test
    public void height() {
        config1();
        assertTrue(arbol.height('X') < 0);
        assertTrue(arbol.height(null) < 0);
        assertEquals(0, arbol.height('c'));
        assertEquals(1, arbol.height('b'));
        assertEquals(5, arbol.height('a'));
        assertEquals(1, arbol.height('F'));
        assertEquals('F', (char) arbol.remove('G'));
        assertEquals(4, arbol.height('a'));
        assertEquals(0, arbol.height('F'));
        assertEquals('D', (char) arbol.remove('E'));
        assertEquals(3, arbol.height('a'));
        assertEquals('D', (char) arbol.remove('F'));
        assertEquals(2, arbol.height('a'));
        assertEquals('B', (char) arbol.remove('C'));
        assertEquals(2, arbol.height('a'));
        assertEquals('B', (char) arbol.remove('D'));
        assertEquals(2, arbol.height('a'));
        assertEquals('b', (char) arbol.remove('c'));
        assertEquals(2, arbol.height('a'));
        assertEquals('b', (char) arbol.remove('d'));
        assertEquals(1, arbol.height('a'));
        assertEquals('a', (char) arbol.remove('b'));
        assertEquals(1, arbol.height('a'));
        assertEquals('a', (char) arbol.remove('B'));
        assertEquals(0, arbol.height('a'));
    }

    @Test
    public void removeRaiz() {
        config1();
        arbol.remove('a');
        assertEquals(9, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'b'},
                {'c', 'd', 'B'},
                {'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('b');
        assertEquals(8, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'B'},
                {'c', 'd', 'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('B');
        assertEquals(7, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'c'},
                {'d', 'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('c');
        assertEquals(6, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'C'},
                {'d', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('C');
        assertEquals(5, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'d'},
                {'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('d');
        assertEquals(4, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('D');
        assertEquals(3, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('E');
        assertEquals(2, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'F'},
                {'G'}
        });
        arbol.remove('F');
        assertEquals(1, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'G'}
        });
        arbol.remove('G');
        assertEquals(0, arbol.size());
        checkBreadthTraverse();
    }

    @Test
    public void removeNoRaiz() {
        config1();
        arbol.remove('b');
        assertEquals(9, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'd', 'B'},
                {'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('d');
        assertEquals(8, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'B'},
                {'C', 'D'},
                {'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('D');
        assertEquals(7, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'B'},
                {'C', 'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('B');
        assertEquals(6, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'C', 'E'},
                {'F'},
                {'G'}
        });
        arbol.remove('F');
        assertEquals(5, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'C', 'E'},
                {'G'}
        });
        arbol.remove('E');
        assertEquals(4, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'c', 'C', 'G'}
        });
        arbol.remove('c');
        assertEquals(3, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'C', 'G'}
        });
        arbol.remove('G');
        assertEquals(2, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'},
                {'C'}
        });
        arbol.remove('C');
        assertEquals(1, arbol.size());
        checkBreadthTraverse(new Character[][]{
                {'a'}
        });
    }

    private void checkBreadthTraverse(Character[]... levels) {
        final int size = arbol.size();
        final List<Character> result = arbol.breadthTraverse();
        for (int i = 0, pos = 0; i < levels.length && pos < size; i++) {
            final List<Character> expected = Arrays.asList(levels[i]);
            final List<Character> actual = result.subList(pos, pos + expected.size());
            //System.out.println(expected.toString().concat("  -  ").concat(actual.toString()));
            assertTrue(actual.containsAll(expected));
            pos += expected.size();
        }
    }
}