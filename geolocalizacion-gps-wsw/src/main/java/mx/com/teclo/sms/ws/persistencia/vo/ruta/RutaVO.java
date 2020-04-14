package mx.com.teclo.sms.ws.persistencia.vo.ruta;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RutaVO {
	
	private Long idRuta;
	private String cdTipoRecorrido;
	private String origenLatitud;
	private String origenLongitud;
	private String origenDescripcion;
	private String destinoLatitud;
 	private String destinoLongitud;
 	private String destinoDescripcion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone="America/Mexico_City")
 	private Date fechaCreacionRuta;
	private Long usuarioId;
 
	public Long getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}
	public String getOrigenLatitud() {
		return origenLatitud;
	}
	public void setOrigenLatitud(String origenLatitud) {
		this.origenLatitud = origenLatitud;
	}
	public String getOrigenLongitud() {
		return origenLongitud;
	}
	public void setOrigenLongitud(String origenLongitud) {
		this.origenLongitud = origenLongitud;
	}
	public String getOrigenDescripcion() {
		return origenDescripcion;
	}
	public void setOrigenDescripcion(String origenDescripcion) {
		this.origenDescripcion = origenDescripcion;
	}
	public String getDestinoLatitud() {
		return destinoLatitud;
	}
	public void setDestinoLatitud(String destinoLatitud) {
		this.destinoLatitud = destinoLatitud;
	}
	public String getDestinoLongitud() {
		return destinoLongitud;
	}
	public void setDestinoLongitud(String destinoLongitud) {
		this.destinoLongitud = destinoLongitud;
	}
	public String getDestinoDescripcion() {
		return destinoDescripcion;
	}
	public void setDestinoDescripcion(String destinoDescripcion) {
		this.destinoDescripcion = destinoDescripcion;
	}
	public Date getFechaCreacionRuta() {
		return fechaCreacionRuta;
	}
	public void setFechaCreacionRuta(Date fechaCreacionRuta) {
		this.fechaCreacionRuta = fechaCreacionRuta;
	}
	public Long getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getCdTipoRecorrido() {
		return cdTipoRecorrido;
	}
	public void setCdTipoRecorrido(String cdTipoRecorrido) {
		this.cdTipoRecorrido = cdTipoRecorrido;
	}
	 
	 
	 
	

}
