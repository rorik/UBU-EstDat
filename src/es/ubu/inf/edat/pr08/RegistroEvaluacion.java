package es.ubu.inf.edat.pr08;

import java.util.*;
import java.util.stream.Collectors;

public class RegistroEvaluacion {
    private final TreeSet<Alumno> alumnos = new TreeSet<>(new ComparadorAlumnos());

    public Alumno anadeNota(String dni, float nota) {
        Alumno alumno = obtenerAlumno(dni, true);
        alumno.acumulaNota(nota);
        return alumno;
    }

    private Alumno obtenerAlumno(String dni, boolean crearSiAusente) {
        return alumnos.stream()
                .filter(alumno -> alumno.getDNI().equals(dni))
                .findFirst()
                .orElse(crearSiAusente ? nuevoAlumno(dni) : null);
    }

    private Alumno nuevoAlumno(String dni) {
        final Alumno alumno = new Alumno(dni);
        alumnos.add(alumno);
        return alumno;
    }

    public List<Alumno> devuelveListado() {
        return new ArrayList<>(alumnos);
    }

    public List<Alumno> devuelveListadoDesde(String dniInicio) {
        return new ArrayList<>(alumnos.subSet(
                obtenerAlumno(dniInicio, false),
                true,
                alumnos.last(),
                true));
    }

    public List<Alumno> devuelveListadoEntre(String dniInicio, String dniFin) {
        return new ArrayList<>(alumnos.subSet(
                obtenerAlumno(dniInicio, false),
                true,
                obtenerAlumno(dniFin, false),
                false));
    }

    public List<Alumno> devuelveListadoHasta(String dniFin) {
        return new ArrayList<>(alumnos.subSet(alumnos.first(),
                true,
                obtenerAlumno(dniFin, false),
                false));
    }

    public List<Alumno> devuelveListadoNotas() {
        return alumnos.stream()
                .sorted(Comparator.comparingDouble(Alumno::getMedia))
                .collect(Collectors.toList());
    }

    public float devuelveNota(String dni) {
        Alumno alumno = obtenerAlumno(dni, false);
        return alumno == null ? 0 : alumno.getMedia();
    }

    public Alumno eliminaNota(String dni, float nota) {
        Alumno alumno = obtenerAlumno(dni, false);
        if (alumno != null) {
            alumno.eliminaNota(nota);
            if (alumno.getNumEval() == 0) {
                alumnos.remove(alumno);
            }
        }
        return alumno;
    }

    public Set<Alumno> filtraExcluyendoAlumnos(Set<String> dniExcluir) {
        Set<Alumno> resultado = new HashSet<>(alumnos);
        resultado.removeAll(dniExcluir.stream()
                .map(dni -> obtenerAlumno(dni, false))
                .collect(Collectors.toSet()));
        return resultado;
    }

    public Set<Alumno> filtraIncluyendoAlumnos(Set<String> dniIncluir) {
        Set<Alumno> resultado = new HashSet<>(alumnos);
        resultado.retainAll(dniIncluir.stream()
                .map(dni -> obtenerAlumno(dni, false))
                .collect(Collectors.toSet()));
        return resultado;
    }

    public void limpiaListado() {
        alumnos.clear();
    }


    public int numeroAlumnos() {
        return alumnos.size();
    }

    private class ComparadorAlumnos implements Comparator<Alumno> {
        @Override
        public int compare(Alumno alumno1, Alumno alumno2) {
            return alumno1.getDNI().compareTo(alumno2.getDNI());
        }
    }
}
