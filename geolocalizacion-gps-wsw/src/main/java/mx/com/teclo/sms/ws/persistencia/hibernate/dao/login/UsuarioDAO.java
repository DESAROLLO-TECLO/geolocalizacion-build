package mx.com.teclo.sms.ws.persistencia.hibernate.dao.login;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.ortogonales.seguridad.vo.AuthenticationRequestVO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface UsuarioDAO extends  BaseDao<EmpleadosDTO>{
	
	public EmpleadosDTO loginByUserPassword(AuthenticationRequestVO authenticationRequestVO );
	 


}
