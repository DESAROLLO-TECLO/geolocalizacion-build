package mx.com.teclo.sms.ws.rest.hh.eventogps;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.arquitectura.ortogonales.responsehttp.NotFoundHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.OKHttpResponse;
import mx.com.teclo.sms.ws.negocio.service.eventogps.EventoGpsService;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositoMovilEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisDispositivoEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisOficialEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.SimpleEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.response.ReponseEventoGpsVO;
import mx.com.teclo.sms.ws.util.enums.NumeroServicio;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
public class EventosGpsRestController {

	@Autowired
	private EventoGpsService eventoGpsService;

	private static final Logger logger = LoggerFactory.getLogger(EventosGpsRestController.class);

	@RequestMapping(value = "/handheld/coordenada", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ReponseEventoGpsVO> saveEventoGps(@RequestBody EventoGpsVO eventoGpsVO)
			throws NotFoundHttpResponse, OKHttpResponse {
		
		logger.info("/coordenada:::Longitud[{}]:::Latitud[{}]", eventoGpsVO.getLongitudGps(), eventoGpsVO.getLatitudGps());

		ReponseEventoGpsVO reponseEventoGpsVO = eventoGpsService.saveEvento(eventoGpsVO);
		throw new OKHttpResponse(reponseEventoGpsVO.getDescripcion(), reponseEventoGpsVO.getCodigoHttp()+NumeroServicio.SERVICIO1.getNumServicio(),reponseEventoGpsVO.getEventoGpsVO());
	}

	@RequestMapping(value = "/handheld/coordenadas", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<List<ReponseEventoGpsVO>> saveListaEventosGps(
			@RequestBody List<EventoGpsVO> listaEventoGpsVO) throws NotFoundHttpResponse, OKHttpResponse {
		
		logger.info("/coordenadas:::Tamaño de Lista[{}]", listaEventoGpsVO.size());

		ReponseEventoGpsVO reponseEventoGpsVO = eventoGpsService.saveListaEvento(listaEventoGpsVO);
		throw new OKHttpResponse(reponseEventoGpsVO.getDescripcion(), reponseEventoGpsVO.getCodigoHttp()+NumeroServicio.SERVICIO2.getNumServicio(), reponseEventoGpsVO.getListaEventoGpsVO());
	}
 
	@RequestMapping(value = "/optimizedEventos/hh", method = RequestMethod.GET)
	public ResponseEntity<List<MyBatisDispositivoEventoVO>> optimizedEventosHH(
			@RequestParam(value="series[]") String[] series,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial) throws NotFoundException {

		List<MyBatisDispositivoEventoVO> eventos = eventoGpsService.optimizedEventosByHHAndFechas(series, fechaInicial);
		if (eventos == null)
			throw new NotFoundException("NO SE ENCONTRARON EVENTOS");
		return new ResponseEntity<List<MyBatisDispositivoEventoVO>>(eventos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/optimizedEventos/officers", method = RequestMethod.GET)
	public ResponseEntity<List<MyBatisOficialEventoVO>> optimizedEventosOfficers(
			@RequestParam(value="emp[]") String[] placas,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial) throws NotFoundException {

		List<MyBatisOficialEventoVO> eventos = eventoGpsService.optimizedEventosByPlacasAndFechas(placas, fechaInicial);
		if (eventos == null)
			throw new NotFoundException("NO SE ENCONTRARON EVENTOS");
		return new ResponseEntity<List<MyBatisOficialEventoVO>>(eventos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/eventos/hh", method = RequestMethod.GET)
	public ResponseEntity<List<DispositoMovilEventosGpsVO>> eventosHH(
			@RequestParam(value="series[]") String[] series,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
			@RequestParam(name = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal) throws NotFoundException {

		List<DispositoMovilEventosGpsVO> eventos = eventoGpsService.eventosByHHAndFechas(series, fechaInicial,	fechaFinal);
		if (eventos == null)
			throw new NotFoundException("NO SE ENCONTRARON EVENTOS");
		return new ResponseEntity<List<DispositoMovilEventosGpsVO>>(eventos, HttpStatus.OK);
	}
	

	
	@RequestMapping(value = "/eventos/item", method = RequestMethod.GET)
	public ResponseEntity<List<SimpleEventoVO>> eventosByItem(
			@RequestParam(value="emp") Long empId,
			@RequestParam(value="numSerie") String numSerie,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial) throws NotFoundException{
		List<SimpleEventoVO> eventos = eventoGpsService.eventosByItem(empId, numSerie, fechaInicial);
		if (eventos == null)
			throw new NotFoundException("NO SE ENCONTRARON EVENTOS");
		return new ResponseEntity<List<SimpleEventoVO>>(eventos, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/eventos/emp", method = RequestMethod.GET)
	public ResponseEntity<List<EmpleadoEventosGpsVO>> eventosEmp(
			@RequestParam(value="emp[]") String[] placas,
			@RequestParam(name = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
			@RequestParam(name = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal) throws NotFoundException {

		List<EmpleadoEventosGpsVO> eventos = eventoGpsService.eventosByPlacasAndFechas(placas, fechaInicial,	fechaFinal);
		if (eventos == null)
			throw new NotFoundException("NO SE ENCONTRARON EVENTOS");
		return new ResponseEntity<List<EmpleadoEventosGpsVO>>(eventos, HttpStatus.OK);
	}
	
}
