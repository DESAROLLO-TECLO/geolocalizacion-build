package mx.com.teclo.sms.ws.negocio.service.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado.EmpleadoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclo.sms.ws.persistencia.vo.login.UsuarioVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private EmpleadoDAO empleadoDAO;

	@Override
	@Transactional
	public UsuarioVO findUserByPlaca(  String placa) {
		EmpleadosDTO empleadoDTO = empleadoDAO.findUserByPlaca(placa);
		UsuarioVO usuarioVO = null;

		if (empleadoDTO != null) {
			usuarioVO = new UsuarioVO();
			ResponseConverter.copiarPropriedades(usuarioVO, empleadoDTO);
			usuarioVO.setAuthenticated(true);
		}
		return usuarioVO;
	}

}
