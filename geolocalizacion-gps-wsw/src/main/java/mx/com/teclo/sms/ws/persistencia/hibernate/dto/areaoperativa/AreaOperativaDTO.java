package mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TAI002C_AREAS_OPERATIVAS")
public class AreaOperativaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -442925982706375339L;

	@Id
	@Column(name = "ID_AREA_OPERATIVA", unique = true, nullable = false)
	private Long areaOperativaId;
	
	@Column(name = "NB_AREA_OPERATIVA")
	private String areaOperativaNombre;
	
	@Column(name = "CD_AREA_OPERATIVA")
	private String areaOperativaCodigo;
	
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

	public Long getAreaOperativaId() {
		return areaOperativaId;
	}

	public void setAreaOperativaId(Long areaOperativaId) {
		this.areaOperativaId = areaOperativaId;
	}

	public String getAreaOperativaNombre() {
		return areaOperativaNombre;
	}

	public void setAreaOperativaNombre(String areaOperativaNombre) {
		this.areaOperativaNombre = areaOperativaNombre;
	}

	public String getAreaOperativaCodigo() {
		return areaOperativaCodigo;
	}

	public void setAreaOperativaCodigo(String areaOperativaCodigo) {
		this.areaOperativaCodigo = areaOperativaCodigo;
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
		return "AreasOperativasDTO [areaOperativaId=" + areaOperativaId + ", areaOperativaNombre=" + areaOperativaNombre
				+ ", areaOperativaCodigo=" + areaOperativaCodigo + ", activo=" + activo + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion="
				+ fechaModificacion + "]";
	}
	
}
