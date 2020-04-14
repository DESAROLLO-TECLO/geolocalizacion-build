package mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import mx.com.teclo.sms.ws.persistencia.vo.ruta.RutaVO;

public class CoordenadaVO {

	private Long idCoordenada;
	private Long idRuta;
	private RutaVO ruta;
	private String latitud;
	private String longitud;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone="America/Mexico_City")
	private Date fechaCoordenada;
	private Long creadoPor;
	private Long modificadoPor;
	private String numSerie;
	
	public Long getIdCoordenada() {
		return idCoordenada;
	}

	public void setIdCoordenada(Long idCoordenada) {
		this.idCoordenada = idCoordenada;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public RutaVO getRuta() {
		return ruta;
	}

	public void setRuta(RutaVO ruta) {
		this.ruta = ruta;
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

	public Date getFechaCoordenada() {
		return fechaCoordenada;
	}

	public void setFechaCoordenada(Date fechaCoordenada) {
		this.fechaCoordenada = fechaCoordenada;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	@Override
	public String toString() {
		return "CoordenadaVO [idCoordenada=" + idCoordenada + ", idRuta=" + idRuta + ", ruta=" + ruta + ", latitud="
				+ latitud + ", longitud=" + longitud + ", fechaCoordenada=" + fechaCoordenada + ", creadoPor="
				+ creadoPor + ", modificadoPor=" + modificadoPor + ", numSerie=" + numSerie + "]";
	}
}
