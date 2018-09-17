package es.ubu.lsi.edat.datos.accesosWeb8;

import java.time.LocalDateTime;

public class AccesoWeb {
	
	public enum SeccionWeb {Inicio, Servicios, Organizacion, Productos, Prensa, Blog, Contacto};
	
	private String IP;
	private LocalDateTime fechaYHora;
	private SeccionWeb destino;
	
	public AccesoWeb (SeccionWeb seccion, LocalDateTime time, String ip){
		this.destino = seccion;
		this.fechaYHora =  time;
		this.IP = ip;
	}
	
	public String getIP() {
		return IP;
	}

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}
	
	public SeccionWeb getDestino() {
		return destino;
	}

	public void setDestino(SeccionWeb destino) {
		this.destino = destino;
	}

	@Override
	public String toString(){
		String s = "";
		s = s + destino.toString() + " - ";
		s = s + fechaYHora.toString() + " - ";
		s = s + IP.toString();
		return s;
	}
	
}
