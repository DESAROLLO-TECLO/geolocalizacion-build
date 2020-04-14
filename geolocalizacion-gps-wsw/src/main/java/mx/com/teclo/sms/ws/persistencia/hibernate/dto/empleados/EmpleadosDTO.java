package mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoDTO;

@Entity
@Table(name = "EMPLEADOS")
@Immutable
public class EmpleadosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EMP_ID", unique = true, nullable = false)
	private long empId;
	@Column(name = "EMP_COD", unique = true)
	private String empCod;
	@Column(name = "EMP_PLACA")
	private String empPlaca;
	@Column(name = "EMP_TIP_ID")
	private Long empTipId;
	@Column(name = "AGRP_ID")
	private Long agrpId;
	@Column(name = "SEC_ID")
	private Long secId;
	@Column(name = "EMP_APE_PATERNO")
	private String empApePaterno;
	@Column(name = "EMP_APE_MATERNO")
	private String empApeMaterno;
	@Column(name = "EMP_NOMBRE")
	private String empNombre;
	@Column(name = "EMP_PWD")
	private String empPwd;
	@Column(name = "EMP_STATUS")
	private String empStatus;
	@Column(name = "CREADO_POR")
	private Long creadoPor;
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	@Column(name = "MODIFICADO_POR")
	private Long modificadoPor;
	@Temporal(TemporalType.DATE)
	@Column(name = "ULTIMA_MODIFICACION")
	private Date ultimaModificacion;
	@Column(name = "EMP_RFC")
	private String empRfc;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empleado")
	private Set<EmpleadosCargosDTO> cargos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empleado")
	private Set<ZonaVialCargoEmpleadoDTO> zonaVialEmpleados;

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getEmpCod() {
		return empCod;
	}

	public void setEmpCod(String empCod) {
		this.empCod = empCod;
	}

	public String getEmpPlaca() {
		return empPlaca;
	}

	public void setEmpPlaca(String empPlaca) {
		this.empPlaca = empPlaca;
	}

	public Long getEmpTipId() {
		return empTipId;
	}

	public void setEmpTipId(Long empTipId) {
		this.empTipId = empTipId;
	}

	public Long getAgrpId() {
		return agrpId;
	}

	public void setAgrpId(Long agrpId) {
		this.agrpId = agrpId;
	}

	public Long getSecId() {
		return secId;
	}

	public void setSecId(Long secId) {
		this.secId = secId;
	}

	public String getEmpApePaterno() {
		return empApePaterno;
	}

	public void setEmpApePaterno(String empApePaterno) {
		this.empApePaterno = empApePaterno;
	}

	public String getEmpApeMaterno() {
		return empApeMaterno;
	}

	public void setEmpApeMaterno(String empApeMaterno) {
		this.empApeMaterno = empApeMaterno;
	}

	public String getEmpNombre() {
		return empNombre;
	}

	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
	}

	public String getEmpPwd() {
		return empPwd;
	}

	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
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

	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	public String getEmpRfc() {
		return empRfc;
	}

	public void setEmpRfc(String empRfc) {
		this.empRfc = empRfc;
	}

	public Set<EmpleadosCargosDTO> getCargos() {
		return cargos;
	}

	public void setCargos(Set<EmpleadosCargosDTO> cargos) {
		this.cargos = cargos;
	}

	public Set<ZonaVialCargoEmpleadoDTO> getZonaVialEmpleados() {
		return zonaVialEmpleados;
	}

	public void setZonaVialEmpleados(Set<ZonaVialCargoEmpleadoDTO> zonaVialEmpleados) {
		this.zonaVialEmpleados = zonaVialEmpleados;
	}

	@Override
	public String toString() {
		return "EmpleadosDTO [empId=" + empId + ", empCod=" + empCod + ", empPlaca=" + empPlaca + ", empTipId="
				+ empTipId + ", agrpId=" + agrpId + ", secId=" + secId + ", empApePaterno=" + empApePaterno
				+ ", empApeMaterno=" + empApeMaterno + ", empNombre=" + empNombre + ", empPwd=" + empPwd
				+ ", empStatus=" + empStatus + ", creadoPor=" + creadoPor + ", fechaCreacion=" + fechaCreacion
				+ ", modificadoPor=" + modificadoPor + ", ultimaModificacion=" + ultimaModificacion + ", empRfc="
				+ empRfc + ", cargos=" + cargos + ", zonaVialEmpleados=" + zonaVialEmpleados + "]";
	}
	
}
