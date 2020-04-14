package mx.com.teclo.alg.ws.rest.hh.tclfw.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.seguridad.service.webservice.WSService;
import mx.com.teclo.arquitectura.ortogonales.seguridad.vo.webservice.NewTokenDataVO;
import mx.com.teclo.arquitectura.ortogonales.seguridad.vo.webservice.TokenUsuarioFuncionVO;
import mx.com.teclo.arquitectura.ortogonales.seguridad.vo.webservice.WSInfoVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping("/wsAuthorized")
public class WebServiceSecurityTCLRestController {
	
	@Autowired
	private WSService wsService;
	
	@Value("${app.config.codigo}")
	private String codeApplication;
	
	/**
	 * Version 3.2.7
	 * @param vo
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value="/token", method=RequestMethod.POST)
	public ResponseEntity<TokenUsuarioFuncionVO> nuevoTokenAcceso(@RequestBody NewTokenDataVO vo) throws Exception{
		return new ResponseEntity<TokenUsuarioFuncionVO> (wsService.newTokenData(vo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/wsInfo", method = RequestMethod.GET)
	public ResponseEntity<WSInfoVO> getServicesAvailable() throws NotFoundException {
		WSInfoVO response = wsService.webServiceInfo(codeApplication);
		if (response==null)
			throw new NotFoundException("No se encontraron registros");
		return new ResponseEntity<WSInfoVO>(response, HttpStatus.OK);
	}
	
}
