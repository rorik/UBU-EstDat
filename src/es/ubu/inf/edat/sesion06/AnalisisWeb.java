package es.ubu.inf.edat.sesion06;

import java.util.*;
import java.util.stream.Collectors;

import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb;
import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb.SeccionWeb;

/**
 * Clase que permite almacenar las diferentes intancias de la
 * clase {@link AccesoWeb} para su posterior consulta.
 * La clase incluirá dos mapas diferentes para ello.
 * Ambos se implementarán como atributos de la clase:
 *   - El primero tendrá como clave el prefijo de la IP desde
 *     la que se realiza el acceso (los 3 primeros bloques de numeros).
 *   - El segundo tendrá como clave la hora del día a la que
 *     se realiza el acceso. Ambos mapas contendrán una
 *     {@link List Lista} asociada a cada clave que contendrá
 *     los {@link AccesoWeb AccesosWeb} que correspondan.
 *     Esto implica que ambos {@link Map Mapas} tendrán el mismo
 *     contenido, pero agrupado de diferente forma.
 *
 * @author <a href="mailto:rdg1003@alu.ubu.es">Rodrigo Díaz García</a>
 */
public class AnalisisWeb {

    private Map<String, List<AccesoWeb>> mapaIp = new HashMap<>();
    private Map<Integer, List<AccesoWeb>> mapaHora = new HashMap<>();

    /**
     * Metodo que almacena en nuestra clase de consultas los
     * diferentes objetos de AccesoWeb.
     * Se almacenarán en varios {@link Map Mapas} que
     * permitan después realizar las consultas que se solicitan.
     *
     * @param accesos {@link Collection Colección} que contiene
     *                los accesos que se quieren almacenar
     *                de forma organizada.
     * @see AccesoWeb#getIP()
     * @see AccesoWeb#getFechaYHora()
     */
    public void insertaAccesos(Collection<AccesoWeb> accesos) {
        for (AccesoWeb acceso : accesos) {
            insertar(mapaIp, acceso.getIP(), acceso);
            insertar(mapaHora, acceso.getFechaYHora().getHour(), acceso);
        }
    }

    /**
     * Clase privada que simplifica la acción de añadir
     * un elemento de tipo {@link AccesoWeb} con
     * una determinada key a una lista.
     *
     * @param mapa {@link Map Mapa} en el cual añadir el elemento.
     * @param key la key en el {@link Map Mapa} del elemento a añadir.
     * @param acceso el {@link AccesoWeb} a añadir al mapa.
     * @param <E> El tipo de key.
     */
    private <E> void insertar(Map<E, List<AccesoWeb>> mapa, E key, AccesoWeb acceso) {
        final List<AccesoWeb> listaIp = mapa.get(key);
        if (listaIp != null) {
            listaIp.add(acceso);
        } else {
            final List<AccesoWeb> nuevo = new ArrayList<>();
            nuevo.add(acceso);
            mapa.put(key, nuevo);
        }
    }

    /**
     * Metodo que permite obtener todos los accesos que se
     * han realizado a la web en una determinada hora del día,
     * sin importar en qué día se realizó.
     * Es decir, si se solicita todos los accesos realizados
     * a las 9, se deberán devolver todos los accesos
     * realizados a las 9 sin importar si se trata del lunes,
     * martes, miercoles... etc.
     *
     * @param horaDelDia hora sobre la que se quiere consultar (de 0 a 23).
     * @return {@link List Lista} de AccesosWeb realizados durante esa hora.
     */
    public List<AccesoWeb> accesosEnHora(int horaDelDia) {
        return mapaHora.getOrDefault(horaDelDia, null);
    }

    /**
     * Metodo que permite obtener todos los accesos
     * que se han realizado a la web desde un determinado
     * conjunto de IPs. Se facilita como parámetro el
     * prefijo que se desea obtener (puede ser el primer
     * bloque, los dos primeros o los 3 primeros) y se
     * devuelve el conjunto de {@link AccesoWeb AccesosWeb}
     * que cumplen esa condicion.
     *
     * @param prefijoIP cadena que representa el prefijo buscado.
     * @see AccesoWeb#getIP()
     */
    public List<AccesoWeb> accesosDeProcedencia(String prefijoIP) {
        return mapaIp.keySet().stream()
                .filter(e -> e.startsWith(prefijoIP))
                .map(e -> mapaIp.get(e))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Permite obtener todos los accesos realizados
     * sobre una determinada sección de la web,
     * sin importar en que fecha o desde que procedencia.
     *
     * @param seccion sección de la que queremos obtener
     *                los diferentes AccessoWeb.
     * @return {@link List Lista} de {@link AccesoWeb}
     *         realizados sobre la seccion solicitada.
     * @see AccesoWeb#getDestino()
     */
    public List<AccesoWeb> accesosSeccion(SeccionWeb seccion) {
        return mapaIp.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(List::stream)
                .filter(e -> e.getDestino().equals(seccion))
                .collect(Collectors.toList());
    }

    /**
     * Se quiere comprobar cuales son las horas a las
     * que se tiene mayor tráfico en la página web.
     * El metodo recibe como parámetro el número de horas
     * que se desean recuperar (como máximo 24).
     * Se devolverán ordenadas en una lista de enteros en
     * la que el número marca la hora del día (0 a 23) y
     * están ordenadas de la hora de mayor
     * tráfico a la hora de menor.
     *
     * @param numHoras número de elementos a incluir en la respuesta.
     * @return {@link List Lista} de enteros que contiene
     *         las horas de mayor tráfico.
     * @see AccesoWeb#getFechaYHora()
     */
    public List<Integer> horaMayorTrafico(int numHoras) {
        return mapaHora.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> v2.size() - v1.size()))
                .limit(numHoras)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
