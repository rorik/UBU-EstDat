package es.ubu.inf.edat.sesion07;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Mapa que permite incluir asociaciones que incluyan la misma clave.
 * <p>
 * En los mapas standard del paquete java.util, sólo se puede asociar
 * un valor a una determinada clave. Cuando se vuelve a insertar un
 * valor diferente sobre la misma clave, el resultado es que el
 * 2º valor sobreescribe al 1º. En el caso de los multi-mapas,
 * el comportamiento esperado es que ambos valores se puedan almacenar
 * asociados a la misma clave. Cada vez que se solicite el valor
 * asociado a la clave, se devolverá alternativamente uno de los valores
 * que tiene asociado, el valor si solo tiene uno asociado o null,
 * si no existe la asociación en el mapa.
 * <p>
 * La llamada al método para eliminar con una determinada clave,
 * eliminará solo el último valor que se devolvió para esa clave
 * antes de la llamada o IllegalStateException si para esa clave
 * se dispone de más de un valor y no se ha pedido obtener
 * ninguno anteriormente.
 * <p>
 * m[] //mapa vacío
 * m[] ← añadimos la asociación (1, ‘a’)
 * m[ [1, ‘a’] ] ← añadimos la asociación (1, ‘b’)
 * m [ [1, ‘a’], [1, ‘b’] ] → solicitamos el valor de la clave 1 → ‘a’
 * m [ [1, ‘a’], [1, ‘b’] ] → solicitamos el valor de la clave 1 → ‘b’
 * m [ [1, ‘a’], [1, ‘b’] ] → solicitamos el valor de la clave 1 → ‘a’
 * m [ [1, ‘a’], [1, ‘b’] ] → solicitamos el valor de la clave 25 → null
 * m [ [1, ‘a’], [1, ‘b’] ] ← solicitamos eliminar el valor de la clave 1
 * m [ [1, ‘b’] ]
 * <p>
 * Para mayor funcionalidad de nuestra clase, se deberán añadir varias
 * operaciones auxiliares: En el caso del borrado, se podrá elegir
 * el borrar el último valor asociado que se ha devuelto o eliminar
 * todos los valores asociados a una determinada clave.
 * Se incluirán también operaciones para almacenar varios valores sobre
 * una clave y para obtener todos los valores asociados a una clave.
 * El tamaño que devolverá el MultiMapa al consultarlo será el número de
 * asociaciones almacenadas, que difiere del número de claves almacenadas.
 * Mapa que permite incluir asociaciones que incluyan la misma clave.
 *
 * @param <K> El tipo de datos de la clave.
 * @param <V> El tipo de datos de los elementos.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class MultiMapa<K, V> extends AbstractMap<K, V> {

    /**
     * Mapa de los elementos, la primera key corresponde a la clave,
     * y el valor es una {@link java.util.Map.Entry Entrada} con la key
     * correspondiendo con la lista de elementos y el value a el apuntador.
     */
    private Map<K, Entry<List<V>, Integer>> mapa;

    /**
     * Permite definir un mapa por defecto.
     * Tamaño 16 y Factor de carga 0.75.
     */
    public MultiMapa() {
        mapa = new HashMap<>();
    }

    /**
     * Permite definir un mapa con capacidad inicial y
     * factor de carga por defecto (0.75).
     *
     * @param capacidadInicial el tamaño inicial del mapa.
     */
    public MultiMapa(int capacidadInicial) {
        mapa = new HashMap<>(capacidadInicial);
    }

    /**
     * Permite definir un mapa con capacidad inicial y
     * factor de carga seleccionados por el usuario.
     *
     * @param capacidadInicial el tamaño inicial del mapa.
     * @param factorCarga      el factor de carga del mapa.
     */
    public MultiMapa(int capacidadInicial, float factorCarga) {
        mapa = new HashMap<>(capacidadInicial, factorCarga);
    }

    /**
     * Permite obtener en una sola llamada todos los valores asociados
     * a una determinada clave. Devolverá una colección vacía
     * en caso de que no existiera la clave en el mismo.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param clave clave de la que se desean recuperar todas las asociaciones.
     * @return coleccion incluyendo los valores correspondientes a la
     *         clave en todas las asociaciones.
     *         Colección vacía si no se encuentra la clave en el mapa.
     */
    public Collection<V> getAllMappings(K clave) {
        if (containsKey(clave)) { // O(1)
            return mapa.get(clave).getKey(); // O(1)
        }
        return new LinkedList<>();
    }

    /**
     * Permite eliminar en una sola llamada todas las asociaciones
     * que incluyeran como clave aquella que se pasa como parámetro.
     * Como resultado devuelve una colección incluyendo todos los
     * valores que se encontraban asociados a dicha clave o
     * una colección vacía si no se encontraba almacenada.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param clave clave de la que se desean eliminar todas las asociaciones.
     * @return valores que se encontraban asociados a la clave eliminada.
     *         Vacía si no existía la clave.
     */
    public Collection<V> removeAllMappings(K clave) {
        if (containsKey(clave)) { // O(1)
            return mapa.remove(clave).getKey(); // O(1)
        }
        return new LinkedList<>();
    }

    /**
     * Permite asociar en una sola llamada varios valores a una misma clave.
     * El resultado deberá ser el mismo que si se realizaran varias
     * llamadas al método put, pasando como parámetro la clave y
     * uno de los valores de la colección.
     * <p>
     * Complejidad algorítmica: O(n'+n/m), siendo n' el número de elementos de la colección dada,
     *                                     y n/m el número de elementos en la clave dada.
     *
     * @param clave   clave común para todas las asociaciones a insertar.
     * @param valores colección de valores que se quieren
     *                insertar junto con la clave.
     */
    public void putAllMappings(K clave, Collection<V> valores) {
        // Comprobamos que ni la clave ni los valores sean null.
        if (clave == null) {
            throw new NullPointerException("El mapa no puede contener claves nulas.");
        } else if (valores == null) {
            throw new NullPointerException("El mapa no puede contener valores nulos.");
        }

        // Eliminamos valores nulos y repetidos.
        final List<V> valoresFiltrados = valores.stream() // O(n'), siendo n' el número de elementos de la colección dada
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        /* valoresFiltrados sin stream:
        final List<V> valoresFiltrados = new ArrayList<>(new HashSet<>(valores));
        */

        if (valoresFiltrados.size() > 0) {
            // Lista donde se encuentran los valores en el mapa.
            final List<V> listaEntrada;
            if (!containsKey(clave)) { // O(1)
                // Creamos una nueva entrada en caso de que no exista.
                listaEntrada = new ArrayList<>(valoresFiltrados.size());
                mapa.put(clave, new SimpleEntry<>(listaEntrada, -1));
            } else {
                // Filtramos los valores que ya existan en el mapa.
                listaEntrada = mapa.get(clave).getKey();
                valoresFiltrados.removeAll(listaEntrada); // O(n/m)
            }
            listaEntrada.addAll(valoresFiltrados); // O(n')
        }
    }

    /**
     * Devuelve un {@link Set} con todas las claves del mapa.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return el {@link Set} de entradas.
     */
    @Override
    public Set<K> keySet() {
        return mapa.keySet(); // O(1)
    }

    /**
     * Devuelve un {@link Set} con todos los elementos del mapa
     * con su correspondiente clave.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @return el {@link Set} de entradas.
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return mapa.entrySet().stream() // O(m), siendo m el número de claves
                // Convertimos cada lista en varias entradas con la misma clave y cada elemento.
                .flatMap(entrada -> entrada.getValue().getKey().stream() // O(n), siendo n el número de elementos
                        .map(valor -> new SimpleEntry<>(entrada.getKey(), valor)))
                .collect(Collectors.toSet());
        /* entrySet sin stream:
        final Set<Entry<K, V>> resultado = new HashSet<>();
        for (Entry<K, Entry<List<V>, Integer>> parKV : mapa.entrySet()) { // O(m)
            for (V elemento : parKV.getValue().getKey()) { // O(n)
                resultado.add(new SimpleEntry<>(parKV.getKey(), elemento));
            }
        }
        return resultado;
        */
    }

    /**
     * Coloca un elemento en una determinada clave, en caso de que ya
     * exista algún elemento con la misma clave, se añade a la lista de elementos
     * de esa clave.
     * <p>
     * Complejidad algorítmica: O(n/m), es decir la densidad de elementos por cada clave.
     *
     * @param clave la clave identificativa.
     * @param valor el elemento a ser añadido.
     * @return el elemento reemplazado.
     * @throws NullPointerException si el valor es nulo.
     * @implSpec este método siempre devolverá {@code null} al no ser que se sobreescriba.
     */
    @Override
    public V put(K clave, V valor) {
        // Comprobamos que ni la clave ni los valores sean null.
        if (clave == null) {
            throw new NullPointerException("El mapa no puede contener claves nulas.");
        } else if (valor == null) {
            throw new NullPointerException("El mapa no puede contener valores nulos.");
        }

        // Creamos una nueva entrada en caso de que no exista.
        if (!containsKey(clave)) {
            mapa.put(clave, new SimpleEntry<>(new ArrayList<>(), -1)); // O(1)
        }

        // Entrada que contiene los valores y el apuntador.
        final Entry<List<V>, Integer> entrada = mapa.get(clave); // O(1)
        // Comprobamos que no sea un valor repetido.
        if (!entrada.getKey().contains(valor)) { // O(n/m), ya que debe recorrer todos los elementos de la clave.
            entrada.getKey().add(valor); // O(1*n/m)
        }
        return null;
    }

    /**
     * Obtiene el elemento correspondiente con su clave.
     * El apuntador de la lista de elementos pasa a la siguiente posición.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param clave la clave del elemento.
     * @return el elemento con la clave dada y al cual esté apuntando el apuntador;
     *         {@code null} en caso de que no exista ningun elemento con esa clave.
     * @throws ClassCastException si la clave no es del tipo correcto de este mapa.
     */
    @Override
    public V get(Object clave) {
        if (containsKey(clave)) { // O(1)
            // Entrada que contiene los valores y el apuntador.
            final Entry<List<V>, Integer> entrada = mapa.get(clave); // O(1)
            // Pasamos a la siguiente posición.
            entrada.setValue(Math.abs((entrada.getValue() + 1) % entrada.getKey().size())); // O(1)
            // Obtenemos el valor el cual está siendo apuntado.
            return entrada.getKey().get(entrada.getValue()); // O(1)
        }
        return null;
    }

    /**
     * Obtiene el tamaño del mapa, es decir la cantidad de elementos que hay.
     * <p>
     * Complejidad algorítmica: O(m), es decir, el número de claves.
     *
     * @return el número de elementos en el mapa.
     */
    @Override
    public int size() {
        return mapa.entrySet().stream() // O(m), siendo m el número de claves
                // Obtiene el tamaño de cada lista.
                .mapToInt(entrada -> entrada.getValue().getKey().size())
                .sum();
        /* size sin stream:
        int resultado = 0;
        for (Entry<K, Entry<List<V>, Integer>> parKV : mapa.entrySet()) { // O(m)
            resultado += parKV.getValue().getKey().size();
        }
        return resultado;
        */
    }

    /**
     * Elimina todos los elementos del mapa.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @see #remove(Object)
     * @see #removeAllMappings(Object)
     */
    @Override
    public void clear() {
        mapa.clear(); // O(1)
    }

    /**
     * Comprueba si una determinada clave ya existe en el mapa.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param clave la clave a ser comprobada.
     * @return {@code true} en la caso de que el mapa contenga la clave;
     *         {@code falso} en caso contrario.
     * @throws ClassCastException si la llave no es del tipo correcto de este mapa.
     */
    @Override
    public boolean containsKey(Object clave) {
        return mapa.containsKey(clave); // O(1)
    }

    /**
     * Elimina el elemento especificado por la clave y apuntado por el apuntador.
     * Solo se puede realizar remove tras realizar {@link #get(Object)} y una única vez
     * por llamada al método.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @param clave la clave del elemento a borrar.
     * @return el elemento borrado; {@code null} en caso de que no exista la clave.
     * @throws ClassCastException    si la llave no es del tipo correcto de este mapa.
     * @throws IllegalStateException si no existe un elemento que se haya obtenido
     *                               previamente por el método {@link #get(Object)}.
     */
    @Override
    public V remove(Object clave) {
        if (containsKey(clave)) { // O(1)
            // Entrada que contiene los valores y el apuntador.
            final Entry<List<V>, Integer> entrada = mapa.get(clave); // O(1)
            // Si el apuntador tiene un valor negativo, quiere decir que no existe elemento para borrar.
            if (entrada.getValue() >= 0) {
                // Eliminamos de la lista el elemento apuntado.
                final V resultado = entrada.getKey().remove(entrada.getValue().intValue()); // O(1)
                if (entrada.getKey().isEmpty()) {
                    // Eliminar la clave en caso de que no tenga entradas.
                    mapa.remove(clave); // O(1)
                }
                else {
                    /* Marcamos el elemento como eliminado, para ello movemos el apuntador al valor negativo
                     * de tal manera que al hacer get() el apuntador vuelva a la misma posición que la actual
                     * pero no se pueda utilizar el método remove().
                     */
                    entrada.setValue(-entrada.getValue() - 1); // O(1)
                }
                return resultado;
            }
            // En caso de que no haya un elemento previamente obtenido y no borrado, lanzar excepción.
            throw new IllegalStateException("No se llamado a la función get() o" +
                    "ya ha sido eliminado el elemento obtenido");
        }
        return null;
    }

    /**
     * Devuelve la colección de todos los elementos del mapa.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @return la colección de todos los elementos del mapa.
     * @implSpec devuelve una lista {@link ArrayList}.
     * @see #keySet()
     */
    @Override
    public Collection<V> values() {
        return mapa.entrySet().stream() // O(m), siendo m el número de claves
                // Junta los valores de cada lista.
                .flatMap(entrada -> entrada.getValue().getKey().stream()) // O(n/m*m) = O(n)
                .collect(Collectors.toList());
        /* values sin stream:
        List<V> resultado = new ArrayList<>();
        for (Entry<K, Entry<List<V>, Integer>> parKV : mapa.entrySet()) { // O(m)
            resultado.addAll(parKV.getValue().getKey()); // O(n)
        }
        return resultado;
        */
    }

    /**
     * Comprueba si el mapa está vacio, es decir, no tiene ningun elemento almacenado.
     * <p>
     * Complejidad algorítmica: O(1)
     *
     * @return {@code true} si el mapa está vacio; {@code false} en caso contrario.
     */
    @Override
    public boolean isEmpty() {
        return mapa.isEmpty(); // O(1), ya que llama a HashMap.size(), que es O(1)
    }

    /**
     * Introduce todos los elementos de un mapa en este mapa.
     * <p>
     * Complejidad algorítmica: O(n'*n/m), siendo n' el número de elementos del mapa dado.
     *
     * @param m el mapa del cual obtener los elementos.
     * @throws ClassCastException en caso de que las claves y/o valores del
     *                            mapa no sean compatibles.
     * @implSpec Esta implementación permite la inserción de valores
     *         de otro MultiMapa sin la necesidad de llamar a entrySet().
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m != null && !m.isEmpty()) {
            if (m instanceof MultiMapa) {
                @SuppressWarnings("unchecked") final MultiMapa<K, V> otro = (MultiMapa<K, V>) m;
                otro.mapa.forEach((clave, valor) -> putAllMappings(clave, valor.getKey())); // O(m' * O(n'/m' * n/m)), siendo n' el número de elementos del otro mapa y m' las claves.
            } else {
                m.forEach(this::put); // O(n' * O(n/m)), siendo n' el número de elementos del otro mapa.
            }
        }
    }

    /**
     * Realiza una acción sobre cada elemento del mapa.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param accion La acción que realizar en cada entrada.
     * @throws NullPointerException            si la acción es {@code null}.
     * @throws ConcurrentModificationException si el mapa no puede ser
     *                                         modificado concurrentemente.
     * @implSpec Esta implementación permite ejecutar la acción
     *         sin la necesidad de llamar a entrySet().
     */
    @Override
    public void forEach(BiConsumer<? super K, ? super V> accion) {
        mapa.forEach((clave, entrada) -> entrada.getKey().forEach(elemento -> accion.accept(clave, elemento))); // O(n)
    }

    /**
     * Comprueba si un valor dado existe en el mapa,
     * independientemente de la clave.
     * <p>
     * Complejidad algorítmica: O(n)
     *
     * @param valor el valor a comprobar.
     * @return {@code true} si el mapa contiene el valor;
     *         {@code false} en caso contrario.
     * @throws ClassCastException   si el valor es de un tipo
     *                              no compatible con el mapa.
     * @throws NullPointerException si el valor es {@code null} y
     *                              el mapa no permite elementos nulos.
     * @implSpec Esta implementación permite comprobar el valor
     *         sin la necesidad de llamar a entrySet().
     * @implSpec Este método no lanza ninguna de las dos excepciones al
     *         no ser que se sobreescriba.
     */
    @Override
    public boolean containsValue(Object valor) {
        return mapa.entrySet().stream() // O(m)
                .map(entradaClave -> entradaClave.getValue().getKey())
                .anyMatch(lista -> lista.contains(valor)); // O(n/m * m) = O(n)
        /* containsValue sin stream:
        for (Entry<K, Entry<List<V>, Integer>> parKV : mapa.entrySet()) { // O(m)
            if (parKV.getValue().getKey().contains(valor)) { // O(n)
                return true;
            }
        }
        return false;
        */
    }
}