package mx.com.teclo.sms.ws.rest.recorridos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.recorridos.TipoRecorridoService;
import mx.com.teclo.sms.ws.persistencia.vo.ruta.TipoRecorridoVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping("/movil")
public class TipoRecorridoRestController {

	@Autowired
	TipoRecorridoService  tipoRecorridoService;
	
	private static final Logger logger = LoggerFactory.getLogger(TipoRecorridoRestController.class);
 
	@RequestMapping(value = "/recorridos", method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<List<TipoRecorridoVO>> findTiposRecorridos()	throws NotFoundException {
		
		logger.info("/recorridos");
		List <TipoRecorridoVO>	 listaTipoRecorridoVO=  tipoRecorridoService.getTiposRecorrido();
		return new ResponseEntity<List<TipoRecorridoVO>>(listaTipoRecorridoVO, HttpStatus.OK);
	}
}
