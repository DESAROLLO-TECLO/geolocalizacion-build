package mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion;

import java.util.Date;

public class SimpleCoordenadaVO {
	private Long idCoordenada;
	private String latitud;
	private String longitud;
	private String numSerie;
	private Date fechaCoordenada;
 	
	 
	public Long getIdCoordenada() {
		return idCoordenada;
	}
	public void setIdCoordenada(Long idCoordenada) {
		this.idCoordenada = idCoordenada;
	}
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public Date getFechaCoordenada() {
		return fechaCoordenada;
	}
	public void setFechaCoordenada(Date fechaCoordenada) {
		this.fechaCoordenada = fechaCoordenada;
	}
	@Override
	public String toString() {
		return "SimpleCoordenadaVO [latitud=" + latitud + ", longitud=" + longitud + ", numSerie=" + numSerie
				+ ", fechaCoordenada=" + fechaCoordenada + "]";
	}
}
