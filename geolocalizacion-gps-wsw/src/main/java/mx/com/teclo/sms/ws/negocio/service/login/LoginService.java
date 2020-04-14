package mx.com.teclo.sms.ws.negocio.service.login;

import mx.com.teclo.sms.ws.persistencia.vo.login.UsuarioVO;
import mx.com.teclomexicana.arquitectura.ortogonales.seguridad.vo.AuthenticationRequestVO;

public interface LoginService {
	
	 
	public UsuarioVO loginByUserPassword(AuthenticationRequestVO authenticationRequestVO );
 


}
