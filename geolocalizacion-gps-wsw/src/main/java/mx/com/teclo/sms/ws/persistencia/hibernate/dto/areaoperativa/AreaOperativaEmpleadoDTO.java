package mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.CargoDTO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;

@Entity
@Table(name="TAI003D_USUARIO_AREA")
public class AreaOperativaEmpleadoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3224937683646143856L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "areaOperativaId", column = @Column(name = "ID_AREA_OPERATIVA")),
		@AttributeOverride(name = "cargoId", column = @Column(name = "ID_CARGO")),
		@AttributeOverride(name = "empId", column = @Column(name = "EMP_ID"))})
	private AreaOperativaEmpleadoIdDTO id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CARGO", referencedColumnName="ID_CARGO", insertable=false, updatable=false)
	private CargoDTO cargo;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AREA_OPERATIVA", referencedColumnName="ID_AREA_OPERATIVA", insertable=false, updatable=false)
	private AreaOperativaDTO area;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", referencedColumnName="EMP_ID", insertable=false, updatable=false)
	private EmpleadosDTO empleado;

	public AreaOperativaEmpleadoIdDTO getId() {
		return id;
	}

	public void setId(AreaOperativaEmpleadoIdDTO id) {
		this.id = id;
	}

	public CargoDTO getCargo() {
		return cargo;
	}

	public void setCargo(CargoDTO cargo) {
		this.cargo = cargo;
	}

	public AreaOperativaDTO getArea() {
		return area;
	}

	public void setArea(AreaOperativaDTO area) {
		this.area = area;
	}

	public EmpleadosDTO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosDTO empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "AreaOperativaEmpleadoDTO [id=" + id + ", cargo=" + cargo + ", area=" + area + ", empleado=" + empleado
				+ "]";
	}
	
}
