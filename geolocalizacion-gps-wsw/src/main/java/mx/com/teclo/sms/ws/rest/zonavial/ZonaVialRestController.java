package mx.com.teclo.sms.ws.rest.zonavial;

import java.util.List;

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
import mx.com.teclo.sms.ws.negocio.service.zonavial.ZonaVialService;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialDispositivosOfficersVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialAsignacionesResponseVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController()
public class ZonaVialRestController {
	
	@Autowired
	private ZonaVialService zonaVialService;
	
	@RequestMapping(value="/zonasviales", method = RequestMethod.GET)
	public ResponseEntity<List<ZonaVialVO>> zonasVialesBySupervisor(
			@RequestParam(value = "placa") String placa,@RequestParam(value = "procedence") String procedence) throws NotFoundException{
		List<ZonaVialVO> listaZonaVial = zonaVialService.zonasByPlaca(placa);
		
		if(listaZonaVial == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<List<ZonaVialVO>>(listaZonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/dispositivosoficiales", method = RequestMethod.GET)
	public ResponseEntity<List<ZonaVialDispositivosOfficersVO>> zonasAndDispositivosBySupervisor(
			@RequestParam(value = "placa") String placa,
			@RequestParam(value = "procedence") String procedence,
			@RequestParam(value = "codeRequest") String codeRequest) throws NotFoundException{
		List<ZonaVialDispositivosOfficersVO> listaZonaVial = zonaVialService.zonasAndDispositivosByPlaca(placa, codeRequest, procedence);
		
		if(listaZonaVial == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<List<ZonaVialDispositivosOfficersVO>>(listaZonaVial, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/zonasviales/dispositivosoficialesPorZonaCodigo", method = RequestMethod.GET)
	public ResponseEntity<ZonaVialDispositivosOfficersVO> zonasAndDispositivosBySupervisor(
			@RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "codeRequest") String codeRequest) throws NotFoundException{
		ZonaVialDispositivosOfficersVO zonaVial = zonaVialService.zonasAndDispositivosAndOfficersByZonaCode(codigo, codeRequest);
		
		if(zonaVial == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<ZonaVialDispositivosOfficersVO>(zonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/allInfoBySingleDisp", method = RequestMethod.GET)
	public ResponseEntity<ZonaVialDispositivosOfficersVO> allDataByDispoInfo(
			@RequestParam(value = "numSerie") String numSerie,
			@RequestParam(value = "numSim") String numSim,
			@RequestParam(value = "numIp") String numIp) throws NotFoundException{
		ZonaVialDispositivosOfficersVO zonaVial = zonaVialService.allDataByDispoInfo(numSerie, numSim, numIp);
		
		if(zonaVial == null) throw new NotFoundException("Sin información encontrada");
		
		return new ResponseEntity<ZonaVialDispositivosOfficersVO>(zonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/officersInfo", method = RequestMethod.GET)
	public ResponseEntity<List<ZonaVialDispositivosOfficersVO>> allDataByOfficerInfo(
			@RequestParam(value = "placa") String placa,
			@RequestParam(value = "nombre") String nombre,
			@RequestParam(value = "paterno") String paterno,
			@RequestParam(value = "materno") String materno) throws NotFoundException{
		List<ZonaVialDispositivosOfficersVO> zonaVial = zonaVialService.allDataByOfficerInfo(placa, nombre, paterno, materno);
		
		if(zonaVial == null) throw new NotFoundException("Sin información encontrada");
		
		return new ResponseEntity<List<ZonaVialDispositivosOfficersVO>>(zonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/allInfoBySingleOfficer", method = RequestMethod.GET)
	public ResponseEntity<ZonaVialDispositivosOfficersVO> allDataByDispoInfo(
			@RequestParam(value = "zonaCode") String zonaCode,
			@RequestParam(value = "empId") Long empId) throws NotFoundException{
		ZonaVialDispositivosOfficersVO zonaVial = zonaVialService.allOfficerDataByZonaCode(zonaCode, empId);
		
		if(zonaVial == null) throw new NotFoundException("Sin información encontrada");
		
		return new ResponseEntity<ZonaVialDispositivosOfficersVO>(zonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/dispositivosOficialesSinAsignacion", method = RequestMethod.GET)
	public ResponseEntity<ZonaVialDispositivosOfficersVO> objetosSinAsignacion(
//			@RequestParam(value = "codigo") String codigo,
			@RequestParam(value = "codeRequest") String codeRequest
			) throws NotFoundException{
		ZonaVialDispositivosOfficersVO zonaVial = zonaVialService.zonasAndDispositivosSinAsignación(codeRequest, -1L);
		
		if(zonaVial == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<ZonaVialDispositivosOfficersVO>(zonaVial, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/zonasActivas", method = RequestMethod.GET)
	public ResponseEntity<List<ZonaVialVO>> zonasActivas(@RequestParam(value = "codeRequest") String codeRequest) throws NotFoundException{
		List<ZonaVialVO> zonas = zonaVialService.zonasActivasPlusZonaDefault(codeRequest);
		
		if(zonas == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<List<ZonaVialVO>>(zonas, HttpStatus.OK);
	}
	
	@RequestMapping(value="/zonasviales/zonasCatalogo", method = RequestMethod.GET)
	public ResponseEntity<List<ZonaVialVO>> zonasCatalogo(@RequestParam(value = "empPlaca") String empPlaca) throws NotFoundException{
		List<ZonaVialVO> zonas = zonaVialService.zonasActivasPorPlacaSupervisor(empPlaca);
		
		if(zonas == null) throw new NotFoundException("Sin Zonas Viales");
		
		return new ResponseEntity<List<ZonaVialVO>>(zonas, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/zonasviales/asignaciones", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ZonaVialAsignacionesResponseVO> zonasAsignaciones(@RequestBody ZonaVialAsignacionesResponseVO vo)
			throws NotFoundHttpResponse, OKHttpResponse {
		vo = zonaVialService.persistAsignaciones(vo);
		throw new OKHttpResponse(vo.getDescripcion(), vo.getCodigoHttp(), vo.getAsignaciones());
	}
}