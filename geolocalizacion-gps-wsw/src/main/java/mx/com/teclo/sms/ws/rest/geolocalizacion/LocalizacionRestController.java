package mx.com.teclo.sms.ws.rest.geolocalizacion;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.localizacion.LocalizacionService;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.BitacoraCoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.CoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.RutasCoordenadasVO;
import mx.com.teclo.sms.ws.persistencia.vo.ruta.RutaVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping("/movil")
public class LocalizacionRestController {

	@Autowired
	private LocalizacionService localizacionService;

	private static final Logger logger = LoggerFactory.getLogger(LocalizacionRestController.class);

	@RequestMapping(value = "/coordenadas", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CoordenadaVO> saveCoordenada(@RequestBody CoordenadaVO coordenadaVO) throws BusinessException, NotFoundException {

		logger.info("/coordenadas:::Longitud[{}]:::Latitud[{}]", coordenadaVO.getLongitud(), coordenadaVO.getLatitud());
		CoordenadaVO responseCoordenadaVO = localizacionService.saveCoordenada(coordenadaVO);
		return new ResponseEntity<CoordenadaVO>(responseCoordenadaVO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/coordenadas/rutas/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CoordenadaVO>> findCoordenadasByRuta(@PathVariable("id") Long idRuta)throws NotFoundException {

		logger.info("/coordenadas/ruta/:::idRuta[{}] ", idRuta);
		List<CoordenadaVO> listCoordenadaVO = localizacionService.getCoordenadasByRuta(idRuta);
		;
		return new ResponseEntity<List<CoordenadaVO>>(listCoordenadaVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/coordenadas/users/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CoordenadaVO>> findCoordenadasByEmpleado(@PathVariable("id") Long idEmpleado) throws NotFoundException {

		logger.info("/coordenadas/user/:::idEmpleado[{}] ]", idEmpleado);
		List<CoordenadaVO> listCoordenadaVO = localizacionService.getCoordenadasByEmpleado(idEmpleado);
		if (listCoordenadaVO.isEmpty())
			throw new NotFoundException("No se encontraron coordenadas");

		return new ResponseEntity<List<CoordenadaVO>>(listCoordenadaVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/coordenadas/fecha/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CoordenadaVO>> getCoordenadasByDate(@RequestParam(value = "placaUsuario") String placa,
			@RequestParam(value = "fInicio") String fInicio , @RequestParam(value = "fFin") String fFin) throws NotFoundException, ParseException {

		logger.info("/coordenadas/fecha/users:::idEmpleado[{}], fInicio [{}], fFin [{}]]",placa ,fInicio, fFin);
		List<CoordenadaVO> listCoordenadaVO = localizacionService.getCoordenadasByDate(placa, fInicio,	fFin);

		if (listCoordenadaVO == null)
			throw new NotFoundException("No se encontró el usuario solicitado");
		if(listCoordenadaVO.isEmpty())
			throw new NotFoundException("No se encontraron coordenadas");

		return new ResponseEntity<List<CoordenadaVO>>(listCoordenadaVO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/coordenadas", method = RequestMethod.GET)
	public ResponseEntity<List<BitacoraCoordenadaVO>> usersCoordenadas(
			@RequestParam(value="placas[]") String[] placas,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
			@RequestParam(name = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal)throws NotFoundException {

		List<BitacoraCoordenadaVO> listCoordenadaVO = localizacionService.coordenadasBitacora(placas, fechaInicial,	fechaFinal);

		if (listCoordenadaVO.isEmpty())
			throw new NotFoundException("NO SE ENCONTRARON COORDENADAS");

		return new ResponseEntity<List<BitacoraCoordenadaVO>>(listCoordenadaVO, HttpStatus.OK);
	}

	@RequestMapping(value = "/rutas", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<RutaVO> saveCoordenada(@RequestBody RutaVO rutaVO)
			throws BusinessException, NotFoundException {

		logger.info("/rutas:::Origen[{}]:::Destino[{}] ]", rutaVO.getOrigenDescripcion(),
				rutaVO.getDestinoDescripcion());
		RutaVO responseRutaVO = null;

		responseRutaVO = localizacionService.saveRuta(rutaVO);

		return new ResponseEntity<RutaVO>(responseRutaVO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/rutas/coordenadas", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Boolean> saveCoordenadasXRuta(@RequestBody RutasCoordenadasVO rutasCoordenadasVO) 	throws BusinessException, NotFoundException {

		logger.info("/rutas:::Ruta[{}]:::Numero de Coordenadas[{}] ]", rutasCoordenadasVO.getRutaVO().getIdRuta(),
				rutasCoordenadasVO.getListaCordenadasVO().size());
		RutaVO responseRutaVO = null;

		responseRutaVO = localizacionService.saveCoordenadasXRuta(rutasCoordenadasVO);

		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/rutas/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<RutaVO>> findRutasByEmpleado(@RequestParam(value = "placaUsuario") String placa,@RequestParam(name = "tipoRecorrido") Long tipoRecorrido) throws NotFoundException {

		logger.info("/rutas/user/:::idEmpleado[{}] ]", placa);
		List<RutaVO> listRutaVO = localizacionService.getRutasByUser(placa, tipoRecorrido);
		if (listRutaVO == null)
			throw new NotFoundException("No se encontró el usuario solicitado");
		if (listRutaVO.isEmpty())
			throw new NotFoundException("No se encontraron rutas");

		return new ResponseEntity<List<RutaVO>>(listRutaVO, HttpStatus.OK);
	}
}
