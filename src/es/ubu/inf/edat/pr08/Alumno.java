package es.ubu.inf.edat.pr08;

/**
 * Clase que encapsula la información relativa a la evaluación
 * obtenida por cada alumno.
 * 
 * @author bbaruque - bbaruque@ubu.es
 */

public class Alumno {

	// Dni asociado a cada alumno (unico)
	private String DNI = null;
	// Numero de pruebas sobre las que se ha evaluado al alumno
	private int numEval = 0;
	// Nota acumulada del alumno (en todas las pruebas)
	private float notaAcumulada = 0;
	
	/**
	 * Constructor de la clase. No puede existir un alumno
	 * que no tenga al menos un DNI asignado.
	 * @param dni DNI del alumno (único para cada alumno)
	 */
	public Alumno(String dni){
		this.DNI = dni;
	}
	
	/**
	 * Metodo de consulta del DNI (único para cada alumno)
	 * @return DNI asociado a la instacia de Alumno 
	 */
	public String getDNI(){
		return this.DNI;
	}
	
	/**
	 * Metodo de consulta del numero de pruebas evaluadas.
	 * Puede ser diferente por cada alumno.
	 * @return numero de pruebas evaluadas
	 */
	public int getNumEval() {
		return numEval;
	}

	/**
	 * Metodo que permite a�adir de forma sencilla una nueva nota
	 * a la evaluaci�n del alumno. Actualiza tambi�n el n�mero de 
	 * pruebas realizadas.
	 * 
	 * @param nota valor de la nueva nota obtenida en una prueba
	 */
	public void acumulaNota(float nota) {
		this.notaAcumulada = this.notaAcumulada + nota;
		numEval++;
	}
	
	/**
	 * Metodo que permite eliminar de forma sencilla una
	 * nota de la evaluacion del alumno (en caso de que se
	 * haya a�adido por error.
	 * 
	 * @param nota valor de la nota a eliminar
	 */
	
	public void eliminaNota(float nota){
		this.notaAcumulada = this.notaAcumulada - nota;
		numEval--;		
	}

	/**
	 * Metodo que permite la consulta de la nota media del
	 * alumno en la asignatura. La nota se obtiene como la
	 * media aritm�tica de las notas obtenidas en todas las
	 * pruebas
	 * 
	 * @return valor de la nota media del alumno en la asignatura
	 */
	public float getMedia() {
		return notaAcumulada/numEval;
	}
	
	public String toString(){
		String s = new String(this.DNI);
		s = s.concat(" - ");
		s = s.concat(Float.toString(getMedia()));
		return s;
	}
	
}
