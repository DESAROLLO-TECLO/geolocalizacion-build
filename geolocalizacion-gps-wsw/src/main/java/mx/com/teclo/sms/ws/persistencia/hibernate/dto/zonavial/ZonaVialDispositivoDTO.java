package mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;

@Entity
@Table(name = "TMS003D_DISPOSITIVO_ZONA_VIAL")
public class ZonaVialDispositivoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 277459932802334469L;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "zonaVialId", column = @Column(name = "ID_ZONA_VIAL")),
	@AttributeOverride(name = "dispositivoId", column = @Column(name = "ID_DISPOSITIVO")) })
	private ZonaVialDispositivoIdDTO id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ZONA_VIAL", referencedColumnName = "ID_ZONA_VIAL", insertable = false, updatable = false)
	private ZonaVialDTO zonaVial;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DISPOSITIVO", referencedColumnName = "ID_DISPOSITIVO", insertable = false, updatable = false)
	private DispositivoMovilDTO dispositivo;
	
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

	public ZonaVialDispositivoIdDTO getId() {
		return id;
	}

	public void setId(ZonaVialDispositivoIdDTO id) {
		this.id = id;
	}

	public ZonaVialDTO getZonaVial() {
		return zonaVial;
	}

	public void setZonaVial(ZonaVialDTO zonaVial) {
		this.zonaVial = zonaVial;
	}

	public DispositivoMovilDTO getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(DispositivoMovilDTO dispositivo) {
		this.dispositivo = dispositivo;
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
		return "ZonaVialDispositivoDTO [id=" + id + ", zonaVial=" + zonaVial + ", dispositivo=" + dispositivo
				+ ", activo=" + activo + ", creadoPor=" + creadoPor + ", fechaCreacion=" + fechaCreacion
				+ ", modificadoPor=" + modificadoPor + ", fechaModificacion=" + fechaModificacion + "]";
	}

}