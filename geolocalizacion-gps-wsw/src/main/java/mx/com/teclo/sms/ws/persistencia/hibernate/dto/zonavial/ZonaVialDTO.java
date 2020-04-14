package mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TMS002C_ZONA_VIAL")
public class ZonaVialDTO implements Serializable {

	private static final long serialVersionUID = -442925982706375339L;

	@Id
	@Column(name = "ID_ZONA_VIAL", unique = true, nullable = false)
	private Long idZonaVial;

	@Column(name = "CD_ZONA_VIAL")
	private String codigoZonaVial;
	
	@Column(name = "NB_ZONA_VIAL")
	private String nombreZonaVial;

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
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zonaVial")
//	private Set<ZonaVialDispositivoDTO> zonaVialDispositivos;

	public Long getIdZonaVial() {
		return idZonaVial;
	}

	public void setIdZonaVial(Long idZonaVial) {
		this.idZonaVial = idZonaVial;
	}

	public String getCodigoZonaVial() {
		return codigoZonaVial;
	}

	public void setCodigoZonaVial(String codigoZonaVial) {
		this.codigoZonaVial = codigoZonaVial;
	}

	public String getNombreZonaVial() {
		return nombreZonaVial;
	}

	public void setNombreZonaVial(String nombreZonaVial) {
		this.nombreZonaVial = nombreZonaVial;
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
//	public Set<ZonaVialDispositivoDTO> getZonaVialDispositivos() {
//		return zonaVialDispositivos;
//	}
//
//	public void setZonaVialDispositivos(Set<ZonaVialDispositivoDTO> zonaVialDispositivos) {
//		this.zonaVialDispositivos = zonaVialDispositivos;
//	}

	@Override
	public String toString() {
		return "ZonaVialDTO [idZonaVial=" + idZonaVial + ", codigoZonaVial=" + codigoZonaVial + ", nombreZonaVial="
				+ nombreZonaVial + ", activo=" + activo + ", creadoPor=" + creadoPor + ", fechaCreacion="
				+ fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion=" + fechaModificacion + "]";
	}

}
