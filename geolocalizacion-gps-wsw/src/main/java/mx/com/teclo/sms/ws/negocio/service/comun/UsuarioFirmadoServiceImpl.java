package mx.com.teclo.sms.ws.negocio.service.comun;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mx.com.teclomexicana.arquitectura.ortogonales.seguridad.vo.UsuarioFirmadoVO;

@Service
public class UsuarioFirmadoServiceImpl implements UsuarioFirmadoService {

	public UsuarioFirmadoVO getUsuarioFirmadoVO() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication == null ? null : authentication.getPrincipal();
		UsuarioFirmadoVO usuarioFirmadoVO = null;
		if (principal != null && principal instanceof UsuarioFirmadoVO) {
			usuarioFirmadoVO = (UsuarioFirmadoVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return usuarioFirmadoVO;
	}
}
