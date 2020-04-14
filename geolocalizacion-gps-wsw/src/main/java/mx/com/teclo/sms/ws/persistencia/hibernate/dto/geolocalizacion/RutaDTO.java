package mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;

 
 
//@Entity
//@Table(name = "TLG001D_RUTAS")
public class RutaDTO implements Serializable {

	private static final long serialVersionUID = -6934096314092113039L;

	@Id
	@SequenceGenerator(name = "entity1Seq", sequenceName = "TLG001D_RUTAS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity1Seq")
	@Column(name = "ID_RUTA", unique = true, nullable = false)
	private Long idRuta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMP")
	private EmpleadosDTO empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_RECORRIDO")
	private TipoRecorridoDTO tipoRecorrido;

	@Column(name = "ORIGEN_LATITUD")
	private String origenLatitud;

	@Column(name = "ORIGEN_LONGITUD")
	private String origenLongitud;

	@Column(name = "ORIGEN_DESCRIPCION")
	private String origenDescripcion;

	@Column(name = "DESTINO_LATITUD")
	private String destinoLatitud;

	@Column(name = "DESTINO_LONGITUD")
	private String destinoLongitud;

	@Column(name = "DESTINO_DESCRIPCION")
	private String destinoDescripcion;

	@Column(name = "FECHA_CREACION_RUTA")
	private Date fechaCreacionRuta;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "CREADO_POR")
	private Long creadoPor;

	@Column(name = "ULTIMA_MODIFICACION")
	private Date ultimaModificacion;

	@Column(name = "MODIFICADO_POR")
	private Long modificadoPor;

	@OneToMany(mappedBy = "ruta",cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
 	private List<CoordenadaDTO> rutaCoordenadas = new ArrayList<>();

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public EmpleadosDTO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosDTO empleado) {
		this.empleado = empleado;
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

	public TipoRecorridoDTO getTipoRecorrido() {
		return tipoRecorrido;
	}

	public void setTipoRecorrido(TipoRecorridoDTO tipoRecorrido) {
		this.tipoRecorrido = tipoRecorrido;
	}

	public List<CoordenadaDTO> getRutaCoordenadas() {
		return rutaCoordenadas;
	}

	public void setRutaCoordenadas(List<CoordenadaDTO> rutaCoordenadas) {
		this.rutaCoordenadas = rutaCoordenadas;
	}

}
