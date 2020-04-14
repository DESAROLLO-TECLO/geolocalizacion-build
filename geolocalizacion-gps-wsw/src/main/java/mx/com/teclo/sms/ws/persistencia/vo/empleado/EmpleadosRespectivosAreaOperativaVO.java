package mx.com.teclo.sms.ws.persistencia.vo.empleado;

public class EmpleadosRespectivosAreaOperativaVO {
	
	private String empPlaca;
	private String empNombre;
	private String empApePaterno;
	private String empApeMaterno;
	private long empId;
	
	public EmpleadosRespectivosAreaOperativaVO(String empPlaca, String empNombre, String empApePaterno,
			String empApeMaterno, long empId) {
		super();
		this.empPlaca = empPlaca;
		this.empNombre = empNombre;
		this.empApePaterno = empApePaterno;
		this.empApeMaterno = empApeMaterno;
		this.empId = empId;
	}
	
	public EmpleadosRespectivosAreaOperativaVO(){
		super();
	}
	
	public String getEmpPlaca() {
		return empPlaca;
	}
	public void setEmpPlaca(String empPlaca) {
		this.empPlaca = empPlaca;
	}
	public String getEmpNombre() {
		return empNombre;
	}
	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
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
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	@Override
	public String toString() {
		return "EmpleadosRespectivosAreaOperativaVO [empPlaca=" + empPlaca + ", empNombre=" + empNombre
				+ ", empApePaterno=" + empApePaterno + ", empApeMaterno=" + empApeMaterno + ", empId=" + empId + "]";
	}
}
