package mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ZonaVialCargoEmpleadoIdDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1125694717084547982L;

	@Basic(optional = false)
	@Column(name = "ID_ZONA_VIAL", nullable = false)
	private Long zonaVialId;

	@Basic(optional = false)
	@Column(name = "EMP_ID", nullable = false)
	private Long empleadoId;
	
	@Basic(optional = false)
	@Column(name = "ID_CARGO", nullable = false)
	private Long cargoId;

	public Long getZonaVialId() {
		return zonaVialId;
	}

	public void setZonaVialId(Long zonaVialId) {
		this.zonaVialId = zonaVialId;
	}

	public Long getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(Long empleadoId) {
		this.empleadoId = empleadoId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	@Override
	public String toString() {
		return "ZonaVialEmpleadoIdDTO [zonaVialId=" + zonaVialId + ", empleadoId=" + empleadoId + ", cargoId=" + cargoId
				+ "]";
	}

}