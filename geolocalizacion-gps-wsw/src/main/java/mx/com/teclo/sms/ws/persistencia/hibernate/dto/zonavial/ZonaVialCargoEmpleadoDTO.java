package mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosCargosDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;

@Entity
@Table(name = "TMS004D_ZONA_VIAL_CARGO_EMP")
public class ZonaVialCargoEmpleadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3224937683646143856L;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "zonaVialId", column = @Column(name = "ID_ZONA_VIAL")),
	@AttributeOverride(name = "empleadoId", column = @Column(name = "EMP_ID")) })
	private ZonaVialCargoEmpleadoIdDTO id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", insertable = false, updatable = false)
	private EmpleadosDTO empleado;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ZONA_VIAL", referencedColumnName = "ID_ZONA_VIAL", insertable = false, updatable = false)
	private ZonaVialDTO zonaVial;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO", insertable = false, updatable = false, nullable = false),
	@JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", insertable = false, updatable = false, nullable = false)})
	private EmpleadosCargosDTO empleadoCargo;

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

	public ZonaVialCargoEmpleadoIdDTO getId() {
		return id;
	}

	public void setId(ZonaVialCargoEmpleadoIdDTO id) {
		this.id = id;
	}

	public ZonaVialDTO getZonaVial() {
		return zonaVial;
	}

	public void setZonaVial(ZonaVialDTO zonaVial) {
		this.zonaVial = zonaVial;
	}

	public EmpleadosDTO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosDTO empleado) {
		this.empleado = empleado;
	}
	
	public Integer getActivo() {
		return activo;
	}

	public EmpleadosCargosDTO getEmpleadoCargo() {
		return empleadoCargo;
	}

	public void setEmpleadoCargo(EmpleadosCargosDTO empleadoCargo) {
		this.empleadoCargo = empleadoCargo;
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
		return "ZonaVialEmpleadoDTO [id=" + id + ", empleado=" + empleado + ", zonaVial=" + zonaVial
				+ ", empleadoCargo=" + empleadoCargo + ", activo=" + activo + ", creadoPor=" + creadoPor
				+ ", fechaCreacion=" + fechaCreacion + ", modificadoPor=" + modificadoPor + ", fechaModificacion="
				+ fechaModificacion + "]";
	}
	
}
