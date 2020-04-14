package mx.com.teclo.sms.ws.persistencia.vo.login;

import java.io.Serializable;


public class UsuarioVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1729254665276583965L;
	
	private Long empId;
	private String empCod;
	private String empPlaca;
  	private String empApePaterno;
	private String empApeMaterno;
	private String empNombre;
 	private boolean isAuthenticated;
	
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
 
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
  
	
}
