package mx.com.teclo.sms.ws.rest.dispositivosmoviles;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.responsehttp.NotFoundHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.OKHttpResponse;
import mx.com.teclo.sms.ws.negocio.service.dispositivosmoviles.DispositivosMovilesService;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivosMovilFullConsultaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.newDispositivoMovilRequierementsVO;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController()
public class DispositivosMovilesRestController {

	@Autowired
	private DispositivosMovilesService dispositivosMovilesService;
		
	@RequestMapping(value="/catalogosNuevoDispositivo", method = RequestMethod.GET)
	public ResponseEntity<newDispositivoMovilRequierementsVO> catalogosNuevoDispositivo() throws NotFoundException{
		newDispositivoMovilRequierementsVO catalogos = dispositivosMovilesService.newVoRequierements();
		
		if(catalogos == null) throw new NotFoundException("Sin catálogos");
		
		return new ResponseEntity<newDispositivoMovilRequierementsVO>(catalogos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dispositivo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseDispositivoMovilPersistenciaVO> saveEventoGps(@RequestBody DispositivoMovilPersistenciaVO dispositivoVO)
			throws NotFoundHttpResponse, OKHttpResponse {
		ResponseDispositivoMovilPersistenciaVO response = dispositivosMovilesService.persistDevice(dispositivoVO);
		throw new OKHttpResponse(response.getDescripcion(), response.getCodigoHttp(), response.getDispositivo());
	}
	
	@RequestMapping(value = "/dispositivo", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ResponseDispositivoMovilPersistenciaVO> updateDev(@RequestBody DispositivoMovilPersistenciaVO dispositivoVO)
			throws NotFoundHttpResponse, OKHttpResponse {
		ResponseDispositivoMovilPersistenciaVO response = dispositivosMovilesService.updateDevice(dispositivoVO);
		throw new OKHttpResponse(response.getDescripcion(), response.getCodigoHttp(), response.getDispositivo());
	}
	
	@RequestMapping(value = "/dispositivos", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseDispositivosMovilFullConsultaVO> searchDevice
	(@RequestParam(name = "numSerie") String numSerie,
	@RequestParam(name = "numSim") String numSim,
	@RequestParam(name = "numIp") String numIp,
	@RequestParam(name = "modelos[]") Long[] modelos,
	@RequestParam(name = "tipoDispositivo[]") Long[] tipoDispositivo,
	@RequestParam(name = "zonaVial[]") Long[] zonaVial,
	@RequestParam(name = "complementacion") Boolean complementacion,
	@RequestParam(name = "sinZonaVial") Boolean sinZonaVial)
	throws NotFoundHttpResponse, OKHttpResponse {
		ResponseDispositivosMovilFullConsultaVO response = dispositivosMovilesService.searchDevice(numSerie, numSim, numIp, Arrays.asList(modelos), Arrays.asList(tipoDispositivo), Arrays.asList(zonaVial), complementacion, sinZonaVial);
		if(response.getCodigoHttp().equals(Codigos.NOT_FOUND.getProcesoId())) 
			throw new NotFoundHttpResponse(response.getDescripcion(), response.getCodigoHttp(), response.getDispositivos());
		return new ResponseEntity<ResponseDispositivosMovilFullConsultaVO>(response, HttpStatus.OK);
	}
}
