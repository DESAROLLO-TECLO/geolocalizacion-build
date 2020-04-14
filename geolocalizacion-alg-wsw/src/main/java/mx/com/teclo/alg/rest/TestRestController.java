package mx.com.teclo.alg.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
public class TestRestController {

	
	@RequestMapping(value="/consultaWs", method=RequestMethod.GET)
	public ResponseEntity<String> consultarProspectos(@RequestParam(name="parametro") String parametro) throws NotFoundException{
		return new ResponseEntity<String>("Hola Mundo", HttpStatus.OK);
	}

}