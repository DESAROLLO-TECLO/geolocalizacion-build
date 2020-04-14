package mx.com.teclo.sms.ws.rest.areasoperativas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.com.teclo.sms.ws.negocio.service.areaoperativa.AreaOperativaService;
import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaSupervisorVO;
import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

@RestController
@RequestMapping("/movil")
public class AreasOperativasRestController {
	
	@Autowired
	private AreaOperativaService areaOperativaService;
	
	@RequestMapping(value="/areasOperativas", method = RequestMethod.GET)
	public ResponseEntity<List<AreaOperativaVO>> areasBySupervisor(
			@RequestParam(value = "placa") String placa,
			@RequestParam(value = "procedence") String procedence) throws NotFoundException{
		List<AreaOperativaVO> lvo = areaOperativaService.areasByEmp(placa,procedence);
		if(lvo == null) throw new NotFoundException("SIN AREAS OPERATIVAS");
		return new ResponseEntity<List<AreaOperativaVO>>(lvo, HttpStatus.OK);
	}
	
	@RequestMapping(value="/areasOperativasSupervisor", method = RequestMethod.GET)
	public ResponseEntity<List<AreaOperativaSupervisorVO>> areasBySupervisor() throws NotFoundException{
		List<AreaOperativaSupervisorVO> lvo = areaOperativaService.areasSupervisor();
		if(lvo == null) throw new NotFoundException("SIN AREAS OPERATIVAS");
		return new ResponseEntity<List<AreaOperativaSupervisorVO>>(lvo, HttpStatus.OK);
	}
	
}
