package mx.com.teclo.sms.ws.rest.reportes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.reportes.ReporteService;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsReporteGeneralVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.InfraccionesPorZonaVialVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDataVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDispositivosOficialesDataVO;
import mx.com.teclo.sms.ws.util.enums.Codigos;
import mx.com.teclo.sms.ws.util.enums.Mensajes;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.BusinessException;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
public class ReporteRestController {

	@Autowired
	private ReporteService reporteService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/reportes/infraccionesporzonavial", method = RequestMethod.GET)
	public ResponseEntity<List<InfraccionesPorZonaVialVO>> getInfraccionesPorZonaVial(@RequestParam(value = "zonavialId") Long zonavialId
			, @RequestParam(value = "dia") String dia) throws NotFoundException {
		Map map = reporteService.infraccionesPorZonaVial(zonavialId, dia);
		boolean isEmpty = (boolean) map.get("isEmpty");
		if(isEmpty)
			throw new NotFoundException("Sin infracciones en la zona vial actual");
		
		List<InfraccionesPorZonaVialVO> list = (List<InfraccionesPorZonaVialVO>) map.get("list");
		return new ResponseEntity<List<InfraccionesPorZonaVialVO>>(list, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/reportes/infraccionesAnualporzonavial", method = RequestMethod.GET)
	public ResponseEntity<List<List<InfraccionesPorZonaVialVO>>> infraccionesAnualesPorZonaVial
	(@RequestParam(value = "zonavialId") Long zonavialId, @RequestParam(value = "anios[]") List<Integer> anios) throws NotFoundException {
		List<List<InfraccionesPorZonaVialVO>> response = new ArrayList<>();
		List<Map> lMap = reporteService.infraccionesAnualesPorZonaVial(zonavialId, anios);
		
		for(Map map : lMap){
			List<InfraccionesPorZonaVialVO> list;
			boolean isEmpty = (boolean) map.get("isEmpty");
			if(!isEmpty){
				list = (List<InfraccionesPorZonaVialVO>) map.get("list");
				response.add(list);
			}
		}		
		return new ResponseEntity<List<List<InfraccionesPorZonaVialVO>>>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/reportes/infraccionesMensualporzonavial", method = RequestMethod.GET)
	public ResponseEntity<List<List<InfraccionesPorZonaVialVO>>> infraccionesMensualporzonavial
	(@RequestParam(value = "zonavialId") Long zonavialId, @RequestParam(value = "anio") Integer anio) throws NotFoundException {
		List<List<InfraccionesPorZonaVialVO>> response = new ArrayList<>();
		List<Map> lMap = reporteService.infraccionesMensualesPorZonaVial(zonavialId, anio);
		
		for(Map map : lMap){
			List<InfraccionesPorZonaVialVO> list;
			boolean isEmpty = (boolean) map.get("isEmpty");
			if(!isEmpty){
				list = (List<InfraccionesPorZonaVialVO>) map.get("list");
				response.add(list);
			}
		}		
		return new ResponseEntity<List<List<InfraccionesPorZonaVialVO>>>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value="/reportes/catalogos", method = RequestMethod.GET)
	public ResponseEntity<Map> getCatalogos(@RequestParam(value = "badge") String empPlaca) throws BusinessException {
		Map map = reporteService.catalogos(empPlaca);
		return new ResponseEntity<Map>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reportes/reportegeneraldata", method = RequestMethod.GET)
	public ResponseEntity<ReporteGeneralDataVO> reporteGeneralData(@RequestParam(value = "empPlaca") String empPlaca) throws NotFoundException {
		ReporteGeneralDataVO response = reporteService.reporteGeneralData(empPlaca);
		response.setCodigoHttp(Codigos.SUCCESS.getProcesoId());
		response.setDescripcion(Mensajes.MSJ_SUCCESS.getProcesoId());
		return new ResponseEntity<ReporteGeneralDataVO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reportes/reportegeneraloficialdispositivodata", method = RequestMethod.GET)
	public ResponseEntity<ReporteGeneralDispositivosOficialesDataVO> reporteGeneralOficialesDispositivosData(@RequestParam(value = "empPlaca") String empPlaca) throws NotFoundException {
		ReporteGeneralDispositivosOficialesDataVO response = reporteService.reporteGeneralOficialesDispositivosData(empPlaca);
		response.setCodigoHttp(Codigos.SUCCESS.getProcesoId());
		response.setDescripcion(Mensajes.MSJ_SUCCESS.getProcesoId());
		return new ResponseEntity<ReporteGeneralDispositivosOficialesDataVO>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/reportes/reportegeneral", method = RequestMethod.GET)
	public ResponseEntity<List<EventoGpsReporteGeneralVO>> reporteGeneralFiltrado(
		@RequestParam(value = "loginRestriction") Integer loginRestriction,
		@RequestParam(value = "zonasViales") List<Long> zonasVialesId,
		@RequestParam(value = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fI,
		@RequestParam(value = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fF,
		@RequestParam(value = "tipoEventos") List<Long> idsTipoEvento,
		@RequestParam(value = "tipoInfracciones") List<String> codigosTipoInfraccion){
		return new ResponseEntity<List<EventoGpsReporteGeneralVO>>(reporteService.reporteGeneralFiltrado(loginRestriction, zonasVialesId, fI, fF, idsTipoEvento, codigosTipoInfraccion), HttpStatus.OK);
	}
	
	@RequestMapping(value="/reportes/reportegeneralOficialesDispositivos", method = RequestMethod.GET)
	public ResponseEntity<List<EventoGpsReporteGeneralVO>> reporteGeneralFiltradoOficialesDispositivos(
		@RequestParam(value = "loginRestriction") Integer loginRestriction,
		@RequestParam(value = "busquedaConjunta") Boolean busquedaConjunta,
		@RequestParam(value = "oficiales") List<Long> oficiales,
		@RequestParam(value = "dispositivos") List<Long> dispositivos,
		@RequestParam(value = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fI,
		@RequestParam(value = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fF,
		@RequestParam(value = "tipoEventos") List<Long> idsTipoEvento,
		@RequestParam(value = "tipoInfracciones", required = false) List<String> codigosTipoInfraccion){
		return new ResponseEntity<List<EventoGpsReporteGeneralVO>>(
				reporteService.reporteGeneralFiltradoOficialesDispostivos(loginRestriction, busquedaConjunta, oficiales, dispositivos, fI, fF, idsTipoEvento, codigosTipoInfraccion), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reportes/reportegeneral/xls", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generarReporteExcel(@RequestParam(value = "reportKind") Integer reportKind)  {
    	Map<String, Object> response = reporteService.reporteGeneralXls(reportKind);
        return new ResponseEntity<byte[]>((byte[])response.get("bytes"), (HttpHeaders)response.get("headers"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/reportes/xlsmovil/reportegeneral", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generaReporteExcelUnPaso (
    		@RequestParam(value = "loginRestriction") Integer loginRestriction,
    		@RequestParam(value = "zonasViales") List<Long> zonasVialesId,
    		@RequestParam(value = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fI,
    		@RequestParam(value = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fF,
    		@RequestParam(value = "tipoEventos") List<Long> idsTipoEvento,
    		@RequestParam(value = "tipoInfracciones", required = false) List<String> codigosTipoInfraccion,
    		@RequestParam(value = "reportKind") Integer reportKind) throws NotFoundException {
    	  	
    	List<EventoGpsReporteGeneralVO> list = reporteService.reporteGeneralFiltrado(loginRestriction, zonasVialesId, fI, fF, idsTipoEvento, codigosTipoInfraccion);
    	
    	if(list.isEmpty()) {
    		throw new NotFoundException("No se encontro información");
    	}
    	
    	Map<String, Object> response = reporteService.reporteGeneralXls(reportKind);
    	return new ResponseEntity<byte[]>((byte[])response.get("bytes"), (HttpHeaders)response.get("headers"), HttpStatus.OK);
    }
    
    /*@RequestMapping(value = "/reportes/xlsmovil/reportegeneralOficialesDispositivos", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generaReporteOficialesyDispositivosExcelUnPaso (
    		@RequestParam(value = "busquedaConjunta") Boolean busquedaConjunta,
    		@RequestParam(value = "oficiales", required = false) List<Long> oficiales,
    		@RequestParam(value = "dispositivos", required = false) List<Long> dispositivos,
    		@RequestParam(value = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fI,
    		@RequestParam(value = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fF,
    		@RequestParam(value = "tipoEventos") List<Long> idsTipoEvento,
    		@RequestParam(value = "tipoInfracciones", required = false) List<String> codigosTipoInfraccion,
    		@RequestParam(value = "reportKind") Integer reportKind) throws NotFoundException {
    	  	
    	List<EventoGpsReporteGeneralVO> list = reporteService.reporteGeneralFiltradoOficialesDispostivos(busquedaConjunta, oficiales, dispositivos, fI, fF, idsTipoEvento, codigosTipoInfraccion);
    	if(list.isEmpty()){
    		throw new NotFoundException("No se encontro información");
    	}
    	
    	Map<String, Object> response = reporteService.reporteGeneralXls(reportKind);
    	return new ResponseEntity<byte[]>((byte[])response.get("bytes"), (HttpHeaders)response.get("headers"), HttpStatus.OK);
    }*/
    
}

