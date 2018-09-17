package es.ubu.inf.edat.pr10;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Comparator;

/**
 * [es.ubu.inf.edat.pr10.ColaPrioridadMinMaxTestAdicionales] Created by Roderick D. on 2018/05/28.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
public class ColaPrioridadMinMaxTestAdicionales {

    private ColaPrioridadMinMax<Character> cola;
    private ColaPrioridadMinMax<ElementoNoComparable> colaComparador;

    @Before
    public void before() {
        cola = new ColaPrioridadMinMax<>();
        colaComparador = new ColaPrioridadMinMax<>(new ElementoNoComparable.Comparador());
    }

    private void config1() {
        cola.offer('B');
        cola.offer('Z');
        cola.offer('G');
        cola.offer('K');
        cola.offer('A');
        cola.offer('B');
        cola.offer('P');
        cola.offer('Y');
        cola.offer('E');
        cola.offer('B');
        colaComparador.offer(new ElementoNoComparable(2,0)); // 1
        colaComparador.offer(new ElementoNoComparable(2,1)); // 2
        colaComparador.offer(new ElementoNoComparable(2,4)); // 16
        colaComparador.offer(new ElementoNoComparable(3,2)); // 9
        colaComparador.offer(new ElementoNoComparable(0,200)); // 0
        colaComparador.offer(new ElementoNoComparable(6,0)); // 1
        colaComparador.offer(new ElementoNoComparable(4,2)); // 16
        colaComparador.offer(new ElementoNoComparable(8,1)); // 8
        colaComparador.offer(new ElementoNoComparable(5,3)); // 125
    }

    @Test
    public void config() {
        config1();
        assertFalse(cola.isEmpty());
        assertEquals(10, cola.size());
        assertTrue(cola.contains('A'));
        assertTrue(cola.contains('B'));
        assertTrue(cola.contains('E'));
        assertTrue(cola.contains('G'));
        assertTrue(cola.contains('K'));
        assertTrue(cola.contains('P'));
        assertTrue(cola.contains('Y'));
        assertTrue(cola.contains('Z'));
        assertFalse(colaComparador.isEmpty());
        assertEquals(9, colaComparador.size());
    }

    @Test(expected = IllegalStateException.class)
    public void noComparator() {
        colaComparador = new ColaPrioridadMinMax<>();
        colaComparador.offer(new ElementoNoComparable(3,3));
        colaComparador.offer(new ElementoNoComparable(3,4));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaPollPeek() {
        config1();
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('E', (char) cola.peek());
        assertEquals('E', (char) cola.poll());
        assertEquals('G', (char) cola.peek());
        assertEquals('G', (char) cola.poll());
        assertEquals('K', (char) cola.peek());
        assertEquals('K', (char) cola.poll());
        assertEquals('P', (char) cola.peek());
        assertEquals('P', (char) cola.poll());
        assertEquals('Y', (char) cola.peek());
        assertEquals('Y', (char) cola.poll());
        assertEquals('Z', (char) cola.peek());
        assertEquals('Z', (char) cola.poll());
        assertTrue(cola.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaPollPeekLast() {
        config1();
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.pollLast());
        assertEquals('Y', (char) cola.peekLast());
        assertEquals('Y', (char) cola.pollLast());
        assertEquals('P', (char) cola.peekLast());
        assertEquals('P', (char) cola.pollLast());
        assertEquals('K', (char) cola.peekLast());
        assertEquals('K', (char) cola.pollLast());
        assertEquals('G', (char) cola.peekLast());
        assertEquals('G', (char) cola.pollLast());
        assertEquals('E', (char) cola.peekLast());
        assertEquals('E', (char) cola.pollLast());
        assertEquals('B', (char) cola.peekLast());
        assertEquals('B', (char) cola.pollLast());
        assertEquals('B', (char) cola.peekLast());
        assertEquals('B', (char) cola.pollLast());
        assertEquals('B', (char) cola.peekLast());
        assertEquals('B', (char) cola.pollLast());
        assertEquals('A', (char) cola.peekLast());
        assertEquals('A', (char) cola.pollLast());
        assertTrue(cola.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaPollPeekMixto() {
        config1();
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.pollLast());
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('Y', (char) cola.peekLast());
        assertEquals('Y', (char) cola.pollLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('P', (char) cola.peekLast());
        assertEquals('P', (char) cola.pollLast());
        assertEquals('K', (char) cola.peekLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('K', (char) cola.pollLast());
        assertEquals('B', (char) cola.poll());
        assertEquals('G', (char) cola.peekLast());
        assertEquals('G', (char) cola.pollLast());
        assertEquals('E', (char) cola.peekLast());
        assertEquals('E', (char) cola.pollLast());
        assertTrue(cola.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaPollPeekOfferMixto() {
        config1();
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.peekLast());
        assertTrue(cola.offer('X'));
        assertEquals('Z', (char) cola.peekLast());
        assertEquals('Z', (char) cola.pollLast());
        assertEquals('A', (char) cola.peek());
        assertTrue(cola.offer('C'));
        assertEquals('A', (char) cola.peek());
        assertEquals('A', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('Y', (char) cola.peekLast());
        assertEquals('Y', (char) cola.pollLast());
        assertEquals('X', (char) cola.peekLast());
        assertEquals('X', (char) cola.pollLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.poll());
        assertEquals('P', (char) cola.peekLast());
        assertEquals('P', (char) cola.pollLast());
        assertEquals('K', (char) cola.peekLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('K', (char) cola.pollLast());
        assertEquals('B', (char) cola.poll());
        assertEquals('G', (char) cola.peekLast());
        assertEquals('G', (char) cola.pollLast());
        assertEquals('E', (char) cola.peekLast());
        assertEquals('E', (char) cola.pollLast());
        assertEquals('C', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertEquals('C', (char) cola.pollLast());
        assertTrue(cola.isEmpty());
        assertTrue(cola.offer('C'));
        assertEquals('C', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertTrue(cola.offer('D'));
        assertEquals('C', (char) cola.peek());
        assertEquals('D', (char) cola.peekLast());
        assertTrue(cola.offer('A'));
        assertEquals('A', (char) cola.peek());
        assertEquals('D', (char) cola.peekLast());
        assertTrue(cola.offer('B'));
        assertEquals('A', (char) cola.peek());
        assertEquals('D', (char) cola.peekLast());
        assertEquals('D', (char) cola.pollLast());
        assertEquals('A', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertTrue(cola.offer('C'));
        assertEquals('A', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertTrue(cola.offer('C'));
        assertEquals('A', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertEquals('A', (char) cola.poll());
        assertEquals('B', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertEquals('C', (char) cola.pollLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertEquals('C', (char) cola.pollLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('C', (char) cola.peekLast());
        assertEquals('C', (char) cola.pollLast());
        assertEquals('B', (char) cola.peek());
        assertEquals('B', (char) cola.peekLast());
        assertEquals('B', (char) cola.pollLast());
        assertTrue(cola.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaComparadorPollPeek() {
        config1();
        assertEquals(0, colaComparador.peek().toInt());
        assertEquals(0, colaComparador.peek().toInt());
        assertEquals(0, colaComparador.peek().toInt());
        assertEquals(0, colaComparador.peek().toInt());
        assertEquals(0, colaComparador.poll().toInt());
        assertEquals(1, colaComparador.peek().toInt());
        assertEquals(1, colaComparador.poll().toInt());
        assertEquals(1, colaComparador.peek().toInt());
        assertEquals(1, colaComparador.poll().toInt());
        assertEquals(2, colaComparador.peek().toInt());
        assertEquals(2, colaComparador.poll().toInt());
        assertEquals(8, colaComparador.peek().toInt());
        assertEquals(8, colaComparador.poll().toInt());
        assertEquals(9, colaComparador.peek().toInt());
        assertEquals(9, colaComparador.poll().toInt());
        assertEquals(16, colaComparador.peek().toInt());
        assertEquals(16, colaComparador.poll().toInt());
        assertEquals(16, colaComparador.peek().toInt());
        assertEquals(16, colaComparador.poll().toInt());
        assertEquals(125, colaComparador.peek().toInt());
        assertEquals(125, colaComparador.poll().toInt());
        assertTrue(colaComparador.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaComparadorPollPeekLast() {
        config1();
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.pollLast().toInt());
        assertEquals(16, colaComparador.peekLast().toInt());
        assertEquals(16, colaComparador.pollLast().toInt());
        assertEquals(16, colaComparador.peekLast().toInt());
        assertEquals(16, colaComparador.pollLast().toInt());
        assertEquals(9, colaComparador.peekLast().toInt());
        assertEquals(9, colaComparador.pollLast().toInt());
        assertEquals(8, colaComparador.peekLast().toInt());
        assertEquals(8, colaComparador.pollLast().toInt());
        assertEquals(2, colaComparador.peekLast().toInt());
        assertEquals(2, colaComparador.pollLast().toInt());
        assertEquals(1, colaComparador.peekLast().toInt());
        assertEquals(1, colaComparador.pollLast().toInt());
        assertEquals(1, colaComparador.peekLast().toInt());
        assertEquals(1, colaComparador.pollLast().toInt());
        assertEquals(0, colaComparador.peekLast().toInt());
        assertEquals(0, colaComparador.pollLast().toInt());
        assertTrue(colaComparador.isEmpty());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void colaComparadorPollPeekMixto() {
        config1();
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.peekLast().toInt());
        assertEquals(125, colaComparador.pollLast().toInt());
        assertEquals(0, colaComparador.peek().toInt());
        assertEquals(0, colaComparador.poll().toInt());
        assertEquals(16, colaComparador.peekLast().toInt());
        assertEquals(16, colaComparador.pollLast().toInt());
        assertEquals(1, colaComparador.peek().toInt());
        assertEquals(1, colaComparador.poll().toInt());
        assertEquals(16, colaComparador.peekLast().toInt());
        assertEquals(16, colaComparador.pollLast().toInt());
        assertEquals(9, colaComparador.peekLast().toInt());
        assertEquals(9, colaComparador.pollLast().toInt());
        assertEquals(1, colaComparador.peek().toInt());
        assertEquals(8, colaComparador.peekLast().toInt());
        assertEquals(1, colaComparador.poll().toInt());
        assertEquals(8, colaComparador.pollLast().toInt());
        assertEquals(2, colaComparador.peek().toInt());
        assertEquals(2, colaComparador.peekLast().toInt());
        assertEquals(2, colaComparador.pollLast().toInt());
        assertTrue(colaComparador.isEmpty());
    }

    private static class ElementoNoComparable {
        private int base;
        private int potencia;

        public ElementoNoComparable(int base, int potencia) {
            if (base == 0 && potencia == 0) {
                throw new IllegalArgumentException();
            }
            this.base = base;
            this.potencia = potencia;
        }

        private int toInt() {
            return (int) Math.pow(base, potencia);
        }

        private static class Comparador implements Comparator<ElementoNoComparable> {
            @Override
            public int compare(ElementoNoComparable o1, ElementoNoComparable o2) {
                return o1.toInt() - o2.toInt();
            }
        }
    }

}