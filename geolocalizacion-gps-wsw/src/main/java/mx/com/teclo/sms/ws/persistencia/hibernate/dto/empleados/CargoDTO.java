package mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TMS007C_CARGOS")
public class CargoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8842964042190313367L;
	
	@Id
	@Column(name = "ID_CARGO", unique = true, nullable = false)
	private Long cargoId;
	
	@Column(name = "CD_CARGO")
	private String cargoCodigo;
	
	@Column(name = "TX_CARGO")
	private String cargoNombre;
	
	@Column(name = "NU_JERARQUIA")
	private Integer cargoJerarquia;
	
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

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getCargoCodigo() {
		return cargoCodigo;
	}

	public void setCargoCodigo(String cargoCodigo) {
		this.cargoCodigo = cargoCodigo;
	}

	public String getCargoNombre() {
		return cargoNombre;
	}

	public void setCargoNombre(String cargoNombre) {
		this.cargoNombre = cargoNombre;
	}

	public Integer getCargoJerarquia() {
		return cargoJerarquia;
	}

	public void setCargoJerarquia(Integer cargoJerarquia) {
		this.cargoJerarquia = cargoJerarquia;
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
		return "CargoDTO [cargoId=" + cargoId + ", cargoCodigo=" + cargoCodigo + ", cargoNombre=" + cargoNombre
				+ ", cargoJerarquia=" + cargoJerarquia + ", activo=" + activo + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion="
				+ fechaModificacion + "]";
	}
	
}
