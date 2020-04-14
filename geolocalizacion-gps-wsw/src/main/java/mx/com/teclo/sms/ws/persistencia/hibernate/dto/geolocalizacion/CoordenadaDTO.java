package mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;

//@Entity
//@Table(name = "TLG002B_COORDENADAS")
public class CoordenadaDTO implements Serializable {

	private static final long serialVersionUID = -6934096314092113039L;

	@Id
	@SequenceGenerator(name = "entity1Seq", sequenceName="TLG002B_COORDENADAS_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity1Seq")
	@Column(name = "ID_CORDENADA", unique = true, nullable = false)
	private Long idCoordenada;

	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
 	@JoinColumn(name = "ID_RUTA")
 	private RutaDTO ruta;

	@Column(name = "LATITUD")
	private String latitud;

	@Column(name = "LONGITUD")
	private String longitud;
	
	@Column(name = "FECHA_CORDENADA")
	private Date fechaCoordenada;
	
	@Column(name = "NU_SERIE")
	private String numSerie;
	
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "CREADO_POR")
	private Long creadoPor;
	
	@Column(name = "ULTIMA_MODIFICACION")
	private Date ultimaModificacion;

	@Column(name = "MODIFICADO_POR")
	private Long modificadoPor;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", referencedColumnName="EMP_ID")
	private EmpleadosDTO empleado;

	public Long getIdCoordenada() {
		return idCoordenada;
	}

	public void setIdCoordenada(Long idCoordenada) {
		this.idCoordenada = idCoordenada;
	}

	public RutaDTO getRuta() {
		return ruta;
	}

	public void setRuta(RutaDTO ruta) {
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

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public EmpleadosDTO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosDTO empleado) {
		this.empleado = empleado;
	}

}
