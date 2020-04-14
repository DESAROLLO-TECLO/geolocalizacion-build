package mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TMS008D_EMPLEADOS_CARGOS")
public class EmpleadosCargosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -779218572703662784L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "cargoId", column = @Column(name = "ID_CARGO")),
		@AttributeOverride(name = "empId", column = @Column(name = "EMP_ID"))})
	private EmpleadosCargosIdDTO id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CARGO", referencedColumnName="ID_CARGO", insertable=false, updatable=false)
	private CargoDTO cargo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", referencedColumnName="EMP_ID", insertable=false, updatable=false)
	private EmpleadosDTO empleado;
	
	@Column(name = "ST_ACTIVO")
	private Integer activo;
	
	@Column(name = "FH_CREACION")
	private Date fechaCreacion;
	
	@Column(name = "FH_MODIFICACION")
	private Date fechaModificacion;
	
	@Column(name = "ID_USR_CREACION")
	private Long creadoPor;
	
	@Column(name = "ID_USR_MODIFICA")
	private Long modificadoPor;

	public EmpleadosCargosIdDTO getId() {
		return id;
	}

	public void setId(EmpleadosCargosIdDTO id) {
		this.id = id;
	}

	public CargoDTO getCargo() {
		return cargo;
	}

	public void setCargo(CargoDTO cargo) {
		this.cargo = cargo;
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

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	@Override
	public String toString() {
		return "EmpleadosCargosDTO [id=" + id + ", cargo=" + cargo + ", empleado=" + empleado + ", activo=" + activo
				+ ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", creadoPor="
				+ creadoPor + ", modificadoPor=" + modificadoPor + "]";
	}
	
}
