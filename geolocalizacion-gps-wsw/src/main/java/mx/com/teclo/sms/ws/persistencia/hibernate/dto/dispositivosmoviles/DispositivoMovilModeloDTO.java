package mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Kevin Ojeda
 *
 */
@Entity
@Table(name = "TMS012C_DISPOSITIVO_MODELO")
public class DispositivoMovilModeloDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8300767152687277434L;
	
	@Id
	@Column(name = "ID_DISPOSITIVO_MODELO", unique = true, nullable = false)
	private Long modeloId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISPOSITIVO_MARCA")
	private DispositivoMovilMarcaDTO modeloMarca;
	
	@Column(name = "CD_DIPOSITIVO_MODELO")
	private String modeloCodigo;
	
	@Column(name = "NB_DISPOSITIVO_MODELO")
	private String modelo;

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

	public Long getModeloId() {
		return modeloId;
	}

	public void setModeloId(Long modeloId) {
		this.modeloId = modeloId;
	}

	public DispositivoMovilMarcaDTO getModeloMarca() {
		return modeloMarca;
	}

	public void setModeloMarca(DispositivoMovilMarcaDTO modeloMarca) {
		this.modeloMarca = modeloMarca;
	}

	public String getModeloCodigo() {
		return modeloCodigo;
	}

	public void setModeloCodigo(String modeloCodigo) {
		this.modeloCodigo = modeloCodigo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
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
		return "DispositivoMovilModeloDTO [modeloId=" + modeloId + ", modeloMarca=" + modeloMarca + ", modeloCodigo="
				+ modeloCodigo + ", modelo=" + modelo + ", activo=" + activo + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion="
				+ fechaModificacion + "]";
	}
	
}
