package mx.com.teclo.sms.ws.persistencia.vo.empleado;

import java.io.Serializable;
import java.util.Date;


public class EmpleadoVO implements Serializable{
	
	private Long empId;
	private String empCod;
	private String empPlaca;
	private Long empTipId;
	private Long agrpId;
	private Long secId;
	private String empApePaterno;
	private String empApeMaterno;
	private String empNombre;
	private String empPwd;
	private String empStatus;
	private String empRfc;
	
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
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
	public String getEmpRfc() {
		return empRfc;
	}
	public void setEmpRfc(String empRfc) {
		this.empRfc = empRfc;
	}
	
	
	
	
}
