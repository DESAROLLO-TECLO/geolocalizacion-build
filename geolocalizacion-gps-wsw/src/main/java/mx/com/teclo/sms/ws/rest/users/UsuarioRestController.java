package mx.com.teclo.sms.ws.rest.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.user.UsuarioService;
import mx.com.teclo.sms.ws.persistencia.vo.login.UsuarioVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping("/movil")
public class UsuarioRestController {

	@Autowired
	UsuarioService usuarioService;

	private static final Logger logger = LoggerFactory.getLogger(UsuarioRestController.class);

	@RequestMapping(value = "/users/{placa}", method = RequestMethod.GET)
	public ResponseEntity<UsuarioVO> findUserByPlaca(@PathVariable("placa") String placa) throws NotFoundException {

		logger.info("/coordenadas/ruta/:::placa[{}] ", placa);
		
		UsuarioVO usuarioVO = usuarioService.findUserByPlaca(placa);
		if (usuarioVO == null)
			throw new NotFoundException("No se encontro el usuario solicitado");
		
		
		return new ResponseEntity<UsuarioVO>(usuarioVO, HttpStatus.OK);
	}

}
