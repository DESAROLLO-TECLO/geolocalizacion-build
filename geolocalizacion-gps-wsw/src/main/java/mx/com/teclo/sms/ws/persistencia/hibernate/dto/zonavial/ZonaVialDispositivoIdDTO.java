package mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ZonaVialDispositivoIdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6645487910329629352L;
	
	@Basic(optional = false)
	@Column(name = "ID_ZONA_VIAL", nullable = false)
	private Long zonaVialId;

	@Basic(optional = false)
	@Column(name = "ID_DISPOSITIVO", nullable = false)
	private Long dispositivoId;

	public Long getZonaVialId() {
		return zonaVialId;
	}

	public void setZonaVialId(Long zonaVialId) {
		this.zonaVialId = zonaVialId;
	}

	public Long getDispositivoId() {
		return dispositivoId;
	}

	public void setDispositivoId(Long dispositivoId) {
		this.dispositivoId = dispositivoId;
	}

	@Override
	public String toString() {
		return "ZonaVialDispositivoIdDTO [zonaVialId=" + zonaVialId + ", dispositivoId=" + dispositivoId + "]";
	}
	
}