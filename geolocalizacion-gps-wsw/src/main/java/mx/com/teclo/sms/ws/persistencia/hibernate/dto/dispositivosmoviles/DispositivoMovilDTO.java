package mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoDTO;

/**
 * @author Kevin Ojeda
 *
 */
@Entity
@Table(name = "TMS001C_DISPOSITIVOS_MOVILES")
public class DispositivoMovilDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -788085906028202879L;
	
	@Id
	@SequenceGenerator(name = "entityDispSeq", sequenceName = "TMS001C_DISPOSITIVOS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityDispSeq")
	@Column(name = "ID_DISPOSITIVO", unique = true, nullable = false)
	private Long idDispositivo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DISPOSITIVO")
	private DispositivoMovilTipoDTO dispositivoMovil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISPOSITIVO_MODELO")
	private DispositivoMovilModeloDTO dispositivoModelo;
	
	@Column(name = "NUM_SERIE")
	private String numSerie;
	
	@Column(name = "NUM_SIM")
	private String numSim;
	
	@Column(name = "NUM_IP")
	private String numIp;
	
	@Column(name = "ST_ACTIVO")
	private Integer activo;

	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date fechaModificacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivo")
	private Set<ZonaVialDispositivoDTO> zonaVialDispositivos;
	
	public Long getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public DispositivoMovilTipoDTO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilTipoDTO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public DispositivoMovilModeloDTO getDispositivoModelo() {
		return dispositivoModelo;
	}

	public void setDispositivoModelo(DispositivoMovilModeloDTO dispositivoModelo) {
		this.dispositivoModelo = dispositivoModelo;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getNumSim() {
		return numSim;
	}

	public void setNumSim(String numSim) {
		this.numSim = numSim;
	}

	public String getNumIp() {
		return numIp;
	}

	public void setNumIp(String numIp) {
		this.numIp = numIp;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
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

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	public Set<ZonaVialDispositivoDTO> getZonaVialDispositivos() {
		return zonaVialDispositivos;
	}

	public void setZonaVialDispositivos(Set<ZonaVialDispositivoDTO> zonaVialDispositivos) {
		this.zonaVialDispositivos = zonaVialDispositivos;
	}

	@Override
	public String toString() {
		return "DispositivoMovilDTO [idDispositivo=" + idDispositivo + ", dispositivoMovil=" + dispositivoMovil
				+ ", dispositivoModelo=" + dispositivoModelo + ", numSerie=" + numSerie + ", numSim=" + numSim
				+ ", numIp=" + numIp + ", activo=" + activo + ", creadoPor=" + creadoPor + ", fechaCreacion="
				+ fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion=" + fechaModificacion + "]";
	}
	
}
