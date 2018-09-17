package es.ubu.inf.edat.sesion07;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * [es.ubu.inf.edat.sesion07.MultiMapaTestAdicionales] Created by Roderick D. on 2018/04/19.
 *
 * @author Roderick D.
 *         <a href="https://www.rorik.me">rorik.me</a>
 *         <a href="https://www.github.com/rorik">github.com/rorik</a>
 */
@SuppressWarnings("OverwrittenKey")
public class MultiMapaTestAdicionales {

    private MultiMapa<String, Character> mapa;

    @Before
    public void setUp() {
        mapa = new MultiMapa<>();
    }

    private void config1() {
        final String clave = "clave uno";
        mapa.put(clave, 'o');
        mapa.put(clave, 'm');
        mapa.put(clave, 'e');
        mapa.put(clave, 'g');
        mapa.put(clave, 'a');
        mapa.put(clave, 'L');
        mapa.put(clave, 'u');
        mapa.put(clave, 'l');
    }

    @Test(expected = NullPointerException.class)
    public void putNullK() {
        mapa.put(null, 'a');
    }

    @Test(expected = NullPointerException.class)
    public void putNullV() {
        mapa.put("abc", null);
    }

    @Test(expected = NullPointerException.class)
    public void putAllNullK() {
        mapa.putAllMappings("abc", null);
    }

    @Test(expected = NullPointerException.class)
    public void putAllNullV() {
        mapa.putAllMappings(null, Arrays.asList('a', 'b', 'c'));
    }

    @Test
    public void allMappings() {
        final String clave = "clave uno";
        config1();
        assertEquals(8, mapa.getAllMappings(clave).size());
        MultiMapa<String, Character> comparacion = new MultiMapa<>();
        comparacion.putAllMappings(clave, "omegaLul".chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        assertEquals(mapa.getAllMappings(clave), comparacion.getAllMappings(clave));
        assertEquals(8, mapa.getAllMappings(clave).size());
        assertEquals(8, comparacion.getAllMappings(clave).size());
        assertEquals(mapa.getAllMappings(clave), comparacion.removeAllMappings(clave));
        assertEquals(8, mapa.getAllMappings(clave).size());
        assertEquals(0, comparacion.getAllMappings(clave).size());
        assertFalse(comparacion.containsKey(clave));
        assertNotEquals(mapa.getAllMappings(clave), comparacion.removeAllMappings(clave));
        assertEquals(0, comparacion.removeAllMappings(clave).size());
        mapa.putAllMappings(clave, mapa.getAllMappings(clave));
        assertEquals(8, mapa.getAllMappings(clave).size());
        mapa.putAllMappings(clave, Arrays.asList(null, 'X', null));
        assertEquals(9, mapa.getAllMappings(clave).size());
        assertEquals((Character)'o',  mapa.get(clave));
        assertEquals(9, mapa.removeAllMappings(clave).size());
        assertEquals(0, mapa.removeAllMappings(clave).size());
        assertEquals(0, mapa.getAllMappings(clave).size());
    }

    @Test(expected = IllegalStateException.class)
    public void removePrimero() {
        config1();
        mapa.remove("clave uno");
    }

    @Test(expected = IllegalStateException.class)
    public void removeRepetido() {
        config1();
        mapa.get("clave uno");
        mapa.remove("clave uno");
        mapa.remove("clave uno");
    }

    @Test
    public void removeTodo() {
        config1();
        for (int i = 0, fin = mapa.getAllMappings("clave uno").size(); i < fin; i++) {
            mapa.get("clave uno");
            mapa.remove("clave uno");
        }
        assertFalse(mapa.containsKey("clave uno"));
    }

    @Test
    public void values() {
        assertEquals(0, mapa.values().size());
        config1();
        assertEquals(8, mapa.values().size());
        mapa.put("clave dos", 'o');
        mapa.put("clave dos", 'm');
        mapa.put("clave dos", 'e');
        mapa.put("clave dos", 'g');
        mapa.put("clave dos", 'a');
        assertEquals(13, mapa.values().size());
    }

    @Test
    public void putAllNuevos() {
        config1();
        assertFalse(mapa.containsKey("clave dos"));
        assertFalse(mapa.containsKey("clave tres"));
        assertEquals(8, mapa.size());
        MultiMapa<String, Character> mapa2 = new MultiMapa<>(2);
        mapa2.put("clave dos", 'a');
        mapa2.put("clave dos", 'c');
        mapa2.put("clave dos", 'b');
        mapa2.put("clave tres", 'A');
        mapa2.put("clave tres", 'C');
        mapa2.put("clave tres", 'B');
        mapa2.put("clave tres", 'X');
        mapa.putAll(mapa2);
        assertTrue(mapa.containsKey("clave dos"));
        assertTrue(mapa.containsKey("clave tres"));
        assertEquals(15, mapa.size());
        assertEquals(3, mapa.getAllMappings("clave dos").size());
        assertEquals(4, mapa.getAllMappings("clave tres").size());
    }

    @Test
    public void putAllRepetidos() {
        config1();
        assertEquals(8, mapa.size());
        MultiMapa<String, Character> mapa2 = new MultiMapa<>(1);
        mapa2.put("clave uno", 'o'); // repetido
        mapa2.put("clave uno", 'm'); // repetido
        mapa2.put("clave uno", 'e'); // repetido
        mapa2.put("clave uno", 'g'); // repetido
        mapa2.put("clave uno", 'a'); // repetido
        mapa2.put("clave uno", 'A'); // nuevo
        mapa2.put("clave uno", 'B'); // nuevo
        mapa2.put("clave uno", 'C'); // nuevo
        mapa2.put("clave uno", 'o'); // repetido
        mapa.putAll(mapa2);
        assertEquals(11, mapa.size()); // 8 + 3 nuevos
    }

    @Test
    public void putAllMap() {
        config1();
        assertFalse(mapa.containsKey("clave dos"));
        assertFalse(mapa.containsKey("clave tres"));
        assertEquals(8, mapa.size());
        Map<String, Character> mapa2 = new HashMap<>(2);
        mapa2.put("clave dos", 'a');
        mapa2.put("clave dos", 'c');
        mapa2.put("clave dos", 'b');
        mapa2.put("clave tres", 'A');
        mapa2.put("clave tres", 'C');
        mapa2.put("clave tres", 'B');
        mapa2.put("clave tres", 'X');
        mapa.putAll(mapa2);
        assertTrue(mapa.containsKey("clave dos"));
        assertTrue(mapa.containsKey("clave tres"));
        assertEquals(10, mapa.size());
        assertEquals(1, mapa.getAllMappings("clave dos").size());
        assertEquals('b', mapa.get("clave dos").charValue());
        assertEquals(1, mapa.getAllMappings("clave tres").size());
        assertEquals('X', mapa.get("clave tres").charValue());
    }

    @Test
    public void putAllHerencia() {
        MultiMapa<Comparable, Object> mapaHerencia = new MultiMapa<>(1);
        final Date clave1 = new Date(0);
        final UUID clave2 = new UUID(0, 1);
        mapaHerencia.put(clave1, "Objeto1");
        mapaHerencia.put(clave1, 2);
        mapaHerencia.put(clave1, "Objeto3");
        mapaHerencia.put(clave1, '4');
        mapaHerencia.put(clave1, new Date(5));
        mapaHerencia.put(clave2, "Objeto6");
        mapaHerencia.put(clave2, new ArrayList<>(7));
        assertEquals(7, mapaHerencia.size());
        assertEquals(5, mapaHerencia.getAllMappings(clave1).size());
        assertEquals(2, mapaHerencia.getAllMappings(clave2).size());

        Map<Date, String> mapa2 = new HashMap<>(2);
        final Date clave3 = new Date(1);
        mapa2.put(clave1, "Objeto1"); // repetido
        mapa2.put(clave3, "Objeto9"); // nuevo
        assertEquals(2, mapa2.size());

        mapaHerencia.putAll(mapa2);
        assertEquals(8, mapaHerencia.size());
        assertEquals(5, mapaHerencia.getAllMappings(clave1).size());
        assertEquals(2, mapaHerencia.getAllMappings(clave2).size());
        assertEquals(1, mapaHerencia.getAllMappings(clave3).size());

        MultiMapa<Date, Character> mapa3 = new MultiMapa<>(1);
        mapa3.put(clave1, '4'); // repetido
        mapa3.put(clave1, 'X'); // nuevo
        mapa3.put(clave3, 'X'); // nuevo
        mapa3.put(new Date(2), 'm'); // nuevo

        mapaHerencia.putAll(mapa3);
        assertEquals(11, mapaHerencia.size());
        assertEquals(6, mapaHerencia.getAllMappings(clave1).size());
        assertEquals(2, mapaHerencia.getAllMappings(clave2).size());
        assertEquals(2, mapaHerencia.getAllMappings(clave3).size());
    }
}