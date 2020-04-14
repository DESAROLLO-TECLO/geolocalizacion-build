package mx.com.teclo.sms.ws.rest.geolocalizacion;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.localizacion.LocalizacionHandHeldService;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.BitacoraCoordenadaVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping()
public class LocalizacionHHRestController {

	@Autowired
	private LocalizacionHandHeldService localizacionHandHeldService;

	private static final Logger logger = LoggerFactory.getLogger(LocalizacionHHRestController.class);

	@RequestMapping(value = "/coordenadas", method = RequestMethod.GET)
	public ResponseEntity<List<BitacoraCoordenadaVO>> handHeldCoordenadas(
			@RequestParam(value="series[]") String[] series,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
			@RequestParam(name = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal)throws NotFoundException {

		List<BitacoraCoordenadaVO> listCoordenadaVO = localizacionHandHeldService.coordenadasBitacora(series, fechaInicial,	fechaFinal);

		if (listCoordenadaVO.isEmpty())
			throw new NotFoundException("No se encontraron coordenadas");

		return new ResponseEntity<List<BitacoraCoordenadaVO>>(listCoordenadaVO, HttpStatus.OK);
	}

 
}
