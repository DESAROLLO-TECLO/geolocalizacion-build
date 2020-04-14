package mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author Kevin Ojeda
 *
 */
@Entity
@Table(name = "TMS010C_TIPOS_DISPOSITIVOS")
public class DispositivoMovilTipoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3144733352938679248L;

	@Id
	@Column(name = "ID_TIPO_DISPOSITIVO", unique = true, nullable = false)
	private Long idTipoDispositivo;
	
	@Column(name = "CD_TIPO_DISPOSITIVO")
	private String cdTipoDispositivo;
	
	@Column(name = "TX_TIPO_DISPOSITIVO")
	private String tipoDispositivo;
	
	@Column(name = "ST_ACTIVO")
	private Integer activo;
	
	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;

	@Column(name = "FH_CREACION")
	private Date fechaCreacion;

	@Column(name = "ID_USR_MODIFICACION")
	private Long modificadoPor;

	@Column(name = "FH_MODIFICACION")
	private Date fechaModificacion;

	public Long getIdTipoDispositivo() {
		return idTipoDispositivo;
	}

	public void setIdTipoDispositivo(Long idTipoDispositivo) {
		this.idTipoDispositivo = idTipoDispositivo;
	}

	public String getCdTipoDispositivo() {
		return cdTipoDispositivo;
	}

	public void setCdTipoDispositivo(String cdTipoDispositivo) {
		this.cdTipoDispositivo = cdTipoDispositivo;
	}

	public String getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(String tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
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

	@Override
	public String toString() {
		return "DispositivoMovilTipoDTO [idTipoDispositivo=" + idTipoDispositivo + ", cdTipoDispositivo="
				+ cdTipoDispositivo + ", tipoDispositivo=" + tipoDispositivo + ", activo=" + activo + ", creadoPor="
				+ creadoPor + ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor
				+ ", fechaModificacion=" + fechaModificacion + "]";
	}
	
}
