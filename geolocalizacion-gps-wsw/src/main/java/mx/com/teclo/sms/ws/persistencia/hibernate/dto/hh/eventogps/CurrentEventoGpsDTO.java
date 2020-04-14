package mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;

@Entity
@Table(name = "GPS_EVENTOS")
public class CurrentEventoGpsDTO implements Serializable {

	private static final long serialVersionUID = 9208691782866213032L;

	@Id
	@SequenceGenerator(name = "entity1Seq", sequenceName = "GPS_EVENTOS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity1Seq")
	@Column(name = "EVENTO_ID", unique = true, nullable = false)
	private Long idEvento;

	
	@Column(name = "EMP_ID")
	private Long idEmpleado;
	
	@AttributeOverrides({@AttributeOverride(name = "idEmpleado", column = @Column(name = "EMP_ID"))})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", insertable = false, updatable = false)
	private EmpleadosDTO empleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENTO_TIPO_ID")
	private TipoEventoGpsDTO tipoEventoGps;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENTO_DISPOSITIVO_ID")
	private DispositivoMovilDTO dispositivoMovil;
	
	@Column(name = "EVENTO_ART_ID")
	private Long idArticulo;
	
	@Column(name = "EVENTO_NUM_SERIE_HH")
	private String numSerieHH;
	
	@Column(name = "EVENTO_DIRECCION")
	private String direccion;

	@Column(name = "EVENTO_GPS_LAT")
	private Double latitudGps;

	@Column(name = "EVENTO_GPS_LON")
	private Double longitudGps;

	@Column(name = "EVENTO_INFRAC_NUM")
	private String numInfraccion;

	@Column(name = "EVENTO_FECHA_HORA")
	private Date fechaHoraEvento;

	@Column(name = "EVENTO_ESTATUS")
	private String estatusEvento;
	
	@Column(name = "CREADO_POR")
	private Long creadoPor;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "MODIFICADO_POR")
	private Long modificadoPor;

	@Column(name = "ULTIMA_MODIFICACION")
	private Date ultimaModificacion;

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public TipoEventoGpsDTO getTipoEventoGps() {
		return tipoEventoGps;
	}

	public void setTipoEventoGps(TipoEventoGpsDTO tipoEventoGps) {
		this.tipoEventoGps = tipoEventoGps;
	}

	public DispositivoMovilDTO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilDTO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public Long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getNumSerieHH() {
		return numSerieHH;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setNumSerieHH(String numSerieHH) {
		this.numSerieHH = numSerieHH;
	}

	public Double getLatitudGps() {
		return latitudGps;
	}

	public void setLatitudGps(Double latitudGps) {
		this.latitudGps = latitudGps;
	}

	public Double getLongitudGps() {
		return longitudGps;
	}

	public void setLongitudGps(Double longitudGps) {
		this.longitudGps = longitudGps;
	}

	public String getNumInfraccion() {
		return numInfraccion;
	}

	public void setNumInfraccion(String numInfraccion) {
		this.numInfraccion = numInfraccion;
	}

	public Date getFechaHoraEvento() {
		return fechaHoraEvento;
	}

	public void setFechaHoraEvento(Date fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}

	public String getEstatusEvento() {
		return estatusEvento;
	}

	public void setEstatusEvento(String estatusEvento) {
		this.estatusEvento = estatusEvento;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}
	public EmpleadosDTO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosDTO empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "EventoGpsDTO [idEvento=" + idEvento + ", idEmpleado=" + idEmpleado + ", tipoEventoGps=" + tipoEventoGps
				+ ", dispositivoMovil=" + dispositivoMovil + ", idArticulo=" + idArticulo + ", numSerieHH=" + numSerieHH
				+ ", direccion=" + direccion + ", latitudGps=" + latitudGps + ", longitudGps=" + longitudGps
				+ ", numInfraccion=" + numInfraccion + ", fechaHoraEvento=" + fechaHoraEvento + ", estatusEvento="
				+ estatusEvento + ", creadoPor=" + creadoPor + ", fechaCreacion=" + fechaCreacion + ", modificadoPor="
				+ modificadoPor + ", ultimaModificacion=" + ultimaModificacion + "]";
	}

}