package es.ubu.inf.edat.sesion11;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb;
import es.ubu.lsi.edat.datos.accesosWeb8.AccesoWeb.SeccionWeb;

public class EjerciciosPipelines {

	public static List<String> toUpper_Iter(Collection<String> collection) {
		final List<String> upper = new ArrayList<>(collection.size());
		for (final Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
			upper.add(iterator.next().toUpperCase());
		}
		return upper;
	}

	public static List<String> toUpper_Stream(List<? extends String> collection) {
		return collection
				.stream()
				.map(e -> e.toUpperCase())
				.collect(Collectors.toList());
	}

	public static List<String> minimumLenght_Iter(Collection<String> collection, int longitud) {
		final List<String> filtrado = new ArrayList<>();
		for (final Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
			final String elemento = iterator.next();
			if (elemento.length() <  longitud) {
				filtrado.add(elemento);
			}
		}
		return filtrado;
	}

	public static List<String> minimumLenght_Stream(Collection<String> collection, int longitud) {
		return collection
				.stream()
				.filter(e -> e.length() < longitud)
				.collect(Collectors.toList());
	}

	public static void accesosEnPeriodo_iter(Collection<AccesoWeb> collection, LocalDateTime inicio, LocalDateTime fin) {
		for (final Iterator<AccesoWeb> iterator = collection.iterator(); iterator.hasNext();) {
			final AccesoWeb elemento = iterator.next();
			if (elemento.getFechaYHora().compareTo(inicio) >= 0 && elemento.getFechaYHora().compareTo(fin) <= 0) {
				iterator.remove();
			}
		}
	}

	public static void accesosEnPeriodo_stream(Collection<AccesoWeb> collection, LocalDateTime inicio, LocalDateTime fin) {
		collection
		.stream()
		.filter(e -> e.getFechaYHora().compareTo(inicio) >= 0 && e.getFechaYHora().compareTo(fin) <= 0);
	}

	public static List<AccesoWeb> accesosDesdeIP_iter(Collection<AccesoWeb> collection, String prefijoIP) {
		final List<AccesoWeb> filtrado = new ArrayList<>();
		for (final Iterator<AccesoWeb> iterator = collection.iterator(); iterator.hasNext();) {
			final AccesoWeb elemento = iterator.next();
			if (elemento.getIP().startsWith(prefijoIP)) {
				filtrado.add(elemento);
			}
		}
		return filtrado;
	}

	public static List<AccesoWeb> accesosDesdeIP_stream(Collection<AccesoWeb> collection, String prefijoIP) {
		return collection
				.stream()
				.filter(e -> e.getIP().startsWith(prefijoIP))
				.collect(Collectors.toList());
	}

	public static int cuantosAccesosDia_iter(Collection<AccesoWeb> collection, DayOfWeek diaSemana) {
		int contador = 0;
		for (final Iterator<AccesoWeb> iterator = collection.iterator(); iterator.hasNext();) {
			final AccesoWeb elemento = iterator.next();
			if (elemento.getFechaYHora().getDayOfWeek() == diaSemana) {
				contador++;
			}
		}
		return contador;
	}

	public static int cuantosAccesosDia_stream(Collection<AccesoWeb> collection, DayOfWeek diaSemana) {
		return (int) collection
				.stream()
				.filter(e -> e.getFechaYHora().getDayOfWeek() == diaSemana)
				.count();
	}

	public static List<String> ipsUnicas_iter(Collection<AccesoWeb> collection) {
		final Set<String> filtrado = new HashSet<>();
		for (final Iterator<AccesoWeb> iterator = collection.iterator(); iterator.hasNext();) {
			filtrado.add(iterator.next().getIP());
		}
		return new ArrayList<>(filtrado);
	}

	public static List<String> ipsUnicas_stream(Collection<AccesoWeb> collection) {
		return collection
				.stream()
				.map(AccesoWeb::getIP)
				.distinct()
				.collect(Collectors.toList());
	}

	public static boolean algunoSeccion_iterator(Collection<AccesoWeb> collection, SeccionWeb destino) {
		boolean encontrado = false;
		for (final Iterator<AccesoWeb> iterator = collection.iterator(); iterator.hasNext() && ! encontrado;) {
			encontrado = iterator.next().getDestino() == destino;
		}
		return encontrado;
	}

	public static boolean algunoSeccion_stream(Collection<AccesoWeb> collection, SeccionWeb destino) {
		return collection
				.stream()
				.anyMatch(e -> e.getDestino() == destino);
	}

}
