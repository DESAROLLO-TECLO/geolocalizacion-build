package mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AreaOperativaEmpleadoIdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1125694717084547982L;
	
	@Basic(optional = false)
	@Column(name = "EMP_ID", nullable = false)
	private Long empId;

	@Basic(optional = false)
	@Column(name = "ID_CARGO", nullable = false)
	private Long cargoId;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	@Override
	public String toString() {
		return "AreaOperativaEmpleadoIdDTO [empId=" + empId + ", cargoId=" + cargoId + "]";
	}
	
}
