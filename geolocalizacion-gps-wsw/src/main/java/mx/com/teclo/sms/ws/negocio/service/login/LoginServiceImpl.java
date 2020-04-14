package mx.com.teclo.sms.ws.negocio.service.login;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.sms.ws.persistencia.hibernate.dao.login.UsuarioDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.vo.login.UsuarioVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;
import mx.com.teclomexicana.arquitectura.ortogonales.seguridad.vo.AuthenticationRequestVO;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Override
	@Transactional
	public UsuarioVO loginByUserPassword(AuthenticationRequestVO authenticationRequestVO) {
		EmpleadosDTO empleadoDTO = usuarioDAO.loginByUserPassword(authenticationRequestVO);
		UsuarioVO usuarioVO = null;

		if (empleadoDTO != null && authenticationRequestVO.getPassword().equals(empleadoDTO.getEmpPwd())) {
			usuarioVO = new UsuarioVO();
			ResponseConverter.copiarPropriedades(usuarioVO, empleadoDTO);
			usuarioVO.setAuthenticated(true);
		}
		return usuarioVO;
	}

}
