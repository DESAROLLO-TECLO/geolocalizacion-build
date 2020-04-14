package mx.com.teclo.sms.ws.persistencia.vo.areaoperativa;

import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;

public class AreaOperativaSupervisorVO {

	private Long areaOperativaId;
	private String areaOperativaNombre;
	private String areaOperativaCodigo;
	private EmpleadosRespectivosAreaOperativaVO supervisor;
	
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
	public EmpleadosRespectivosAreaOperativaVO getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(EmpleadosRespectivosAreaOperativaVO supervisor) {
		this.supervisor = supervisor;
	}
	@Override
	public String toString() {
		return "AreaOperativaSupervisorVO [areaOperativaId=" + areaOperativaId + ", areaOperativaNombre="
				+ areaOperativaNombre + ", areaOperativaCodigo=" + areaOperativaCodigo + ", supervisor=" + supervisor
				+ "]";
	}
	
}
