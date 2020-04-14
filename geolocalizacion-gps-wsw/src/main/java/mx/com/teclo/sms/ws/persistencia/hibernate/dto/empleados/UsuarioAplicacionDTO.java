package mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

//@Entity
//@Table(name = "TSEG_USUARIO_APLICACION")
//@Immutable
public class UsuarioAplicacionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5941435531228440337L;
	
//	@EmbeddedId
	private UsuarioAplicacionIdDTO id;

	public UsuarioAplicacionIdDTO getId() {
		return id;
	}

	public void setId(UsuarioAplicacionIdDTO id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UsuarioAplicacionDTO [id=" + id + "]";
	}
	
}
