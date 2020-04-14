package mx.com.teclo.sms.ws.persistencia.vo.areaoperativa;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;

public class AreaOperativaVO {

	private Long areaOperativaId;
	private String areaOperativaNombre;
	private String areaOperativaCodigo;
	private List<EmpleadosRespectivosAreaOperativaVO> empleados;
	
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
	public List<EmpleadosRespectivosAreaOperativaVO> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(List<EmpleadosRespectivosAreaOperativaVO> empleados) {
		this.empleados = empleados;
	}
	@Override
	public String toString() {
		return "AreaOperativaVO [areaOperativaId=" + areaOperativaId + ", areaOperativaNombre=" + areaOperativaNombre
				+ ", areaOperativaCodigo=" + areaOperativaCodigo + ", empleados=" + empleados + "]";
	}
}
