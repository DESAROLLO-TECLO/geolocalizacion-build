package mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Transient;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;

/**
 * @author jorgeluis
 *
 */
@Entity
@Table(name = "TALG002D_EVENTOS_GPS")
public class EventoAlgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6652208649848478816L;

	@Id
	@SequenceGenerator(name = "entity1Seq", sequenceName = "TALG002D_EVENTOS_GPS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity1Seq")
	@Column(name = "ID_EVENTO_GPS", unique = true, nullable = false)
	private Long idEvento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_EVENTO")
	private TipoEventoAlgDTO tipoEventoAlg;

	@Column(name = "EMP_ID")
	private Long idEmpleado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DISPOSITIVO")
	private DispositivoMovilTipoDTO tipoDispositivoMovil;

	@Column(name = "NU_GPS_LATITUD")
	private Double latitudGps;

	@Column(name = "NU_GPS_LONGITUD")
	private Double longitudGps;

	@Column(name = "NU_SERIE")
	private String numSerie;

	@Column(name = "TX_DIRECCION")
	private String direccion;

	@Column(name = "NU_IMEI")
	private Integer numImei;

	@Column(name = "NU_INFRACCION_NUM")
	private Long numInfraccion;

	@Column(name = "FH_FECHA_HORA_EVENTO")
	private Date fechaHoraEvento;

	@Column(name = "ST_EVENTO")
	private Integer estatusEvento;

	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date ultimaModificacion;

	@Column(name = "ST_HISTORICO")
	private Integer historico;

	@Transient
	private Long idTipoEvento;

	@Transient
	private Long idTipoDispositivo;

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public TipoEventoAlgDTO getTipoEventoAlg() {
		return tipoEventoAlg;
	}

	public void setTipoEventoAlg(TipoEventoAlgDTO tipoEventoAlg) {
		this.tipoEventoAlg = tipoEventoAlg;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public DispositivoMovilTipoDTO getTipoDispositivoMovil() {
		return tipoDispositivoMovil;
	}

	public void setTipoDispositivoMovil(DispositivoMovilTipoDTO tipoDispositivoMovil) {
		this.tipoDispositivoMovil = tipoDispositivoMovil;
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

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getNumImei() {
		return numImei;
	}

	public void setNumImei(Integer numImei) {
		this.numImei = numImei;
	}

	public Long getNumInfraccion() {
		return numInfraccion;
	}

	public void setNumInfraccion(Long numInfraccion) {
		this.numInfraccion = numInfraccion;
	}

	public Date getFechaHoraEvento() {
		return fechaHoraEvento;
	}

	public void setFechaHoraEvento(Date fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}

	public Integer getEstatusEvento() {
		return estatusEvento;
	}

	public void setEstatusEvento(Integer estatusEvento) {
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

	public Integer getHistorico() {
		return historico;
	}

	public void setHistorico(Integer historico) {
		this.historico = historico;
	}

	@Override
	public String toString() {
		return "EventoAlgDTO [idEvento=" + idEvento + ", tipoEventoAlg=" + tipoEventoAlg + ", idEmpleado=" + idEmpleado
				+ ", tipoDispositivoMovil=" + tipoDispositivoMovil + ", latitudGps=" + latitudGps + ", longitudGps="
				+ longitudGps + ", numSerie=" + numSerie + ", direccion=" + direccion + ", numImei=" + numImei
				+ ", numInfraccion=" + numInfraccion + ", fechaHoraEvento=" + fechaHoraEvento + ", estatusEvento="
				+ estatusEvento + ", creadoPor=" + creadoPor + ", fechaCreacion=" + fechaCreacion + ", modificadoPor="
				+ modificadoPor + ", ultimaModificacion=" + ultimaModificacion + "historico" + historico + "]";
	}

	/**
	 * @return the idTipoEvento
	 */
	public Long getIdTipoEvento() {
		return idTipoEvento;
	}

	/**
	 * @param idTipoEvento the idTipoEvento to set
	 */
	public void setIdTipoEvento(Long idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}

	/**
	 * @return the idTipoDispositivo
	 */
	public Long getIdTipoDispositivo() {
		return idTipoDispositivo;
	}

	/**
	 * @param idTipoDispositivo the idTipoDispositivo to set
	 */
	public void setIdTipoDispositivo(Long idTipoDispositivo) {
		this.idTipoDispositivo = idTipoDispositivo;
	}

}