package mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

//@Embeddable
public class UsuarioAplicacionIdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4144705300264674171L;

//	@Basic(optional = false)
//	@Column(name = "USU_ID", nullable = false)
	private Long usuarioId;
	
//	@Basic(optional = false)
//	@Column(name = "ID_APLICACION", nullable = false)
	private Long aplicacionId;

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getAplicacionId() {
		return aplicacionId;
	}

	public void setAplicacionId(Long aplicacionId) {
		this.aplicacionId = aplicacionId;
	}

	@Override
	public String toString() {
		return "UsuarioAplicacionIdDTO [usuarioId=" + usuarioId + ", aplicacionId=" + aplicacionId + "]";
	}
	
}
