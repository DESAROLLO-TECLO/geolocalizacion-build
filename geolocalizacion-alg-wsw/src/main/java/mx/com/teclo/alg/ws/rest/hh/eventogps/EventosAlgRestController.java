package mx.com.teclo.alg.ws.rest.hh.eventogps;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mx.com.teclo.alg.ws.negocio.service.eventogps.EventoAlgFileService;
import mx.com.teclo.alg.ws.negocio.service.eventogps.EventoAlgService;
import mx.com.teclo.alg.ws.negocio.service.eventogps.EventoAlgServiceOptBDService;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.request.RequestEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BusinessHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.NotFoundHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.OKHttpResponse;
import mx.com.teclo.sms.ws.util.enums.NumeroServicio;

@RestController
public class EventosAlgRestController {
	
	@Autowired
	private EventoAlgService eventoAlgService;
	
	@Autowired
	private EventoAlgFileService eventoAlgFileService;
	
	@Autowired
	private EventoAlgServiceOptBDService eventoAlgServiceOptBDService;
	
	private static final Logger logger = LoggerFactory.getLogger(EventosAlgRestController.class);

	@RequestMapping(value = "/gps/eventos", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<List<ReponseEventoAlgVO>> saveListaEventosGps(
			@RequestBody List<EventoAlgVO> listaEventoAlgVO) throws NotFoundHttpResponse, OKHttpResponse, ParseException, SQLException, NamingException {
		logger.info("/coordenadas:::Tamaño de Lista[{}]", listaEventoAlgVO.size());
		ReponseEventoAlgVO ReponseEventoAlgVO = eventoAlgService.saveArrayEvent(listaEventoAlgVO, 0);
		throw new OKHttpResponse(ReponseEventoAlgVO.getDescripcion(), ReponseEventoAlgVO.getCodigoHttp()+NumeroServicio.SERVICIO2.getNumServicio(), ReponseEventoAlgVO.getListaEventoAlgVO());
	}
	
	@RequestMapping(value = "/gps/eventosHistorico", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<List<ReponseEventoAlgVO>> saveListaEventosHistoricoGps(
			@RequestBody List<EventoAlgVO> listaEventoAlgVO) throws NotFoundHttpResponse, OKHttpResponse, ParseException, SQLException, NamingException {
		ReponseEventoAlgVO ReponseEventoAlgVO = eventoAlgService.saveArrayEvent(listaEventoAlgVO, 1);
		throw new OKHttpResponse(ReponseEventoAlgVO.getDescripcion(), ReponseEventoAlgVO.getCodigoHttp()+NumeroServicio.SERVICIO2.getNumServicio(), ReponseEventoAlgVO.getListaEventoAlgVO());
	}
	
	@RequestMapping(value = "/gps/eventos/v2", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody RequestEventoAlgVO processFile (@RequestParam(value = "file") MultipartFile file,
									  @RequestParam(value = "data", required = false) String archivoVO) throws BusinessHttpResponse, IOException, ParseException, SQLException, NamingException, OKHttpResponse {
		Map<String, Object> map = null;
		try{
			map = eventoAlgFileService.processFile(file, archivoVO);	
		}catch(BusinessHttpResponse ex) {
			throw new BusinessHttpResponse(ex.getMessage(), ex.getCodigo(), null);
		}
		RequestEventoAlgVO objectResultbitac = eventoAlgServiceOptBDService.saveBitac(map);
		ReponseEventoAlgVO response = (ReponseEventoAlgVO) map.get("reponseEventoAlgVO");
		//objectResultbitac.setErrorFile(null);
		throw new OKHttpResponse(response.getDescripcion(), response.getCodigoHttp()+NumeroServicio.SERVICIO3.getNumServicio(), objectResultbitac);
	}
	
	@RequestMapping(value = "/gps/evento", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ReponseEventoAlgVO> saveEvento(@RequestBody EventoAlgVO eventoAlgVO)
			throws NotFoundHttpResponse, OKHttpResponse, BusinessHttpResponse, BadRequestHttpResponse, SQLException, NamingException {
		logger.info("/coordenada:::Longitud[{}]:::Latitud[{}]", eventoAlgVO.getLongitudGps(), eventoAlgVO.getLatitudGps());
		ReponseEventoAlgVO reponseEventoAlgVO;
		reponseEventoAlgVO = eventoAlgFileService.saveEvent(eventoAlgVO);
		throw new OKHttpResponse(reponseEventoAlgVO.getDescripcion(), reponseEventoAlgVO.getCodigoHttp()+NumeroServicio.SERVICIO1.getNumServicio(), reponseEventoAlgVO.getEventoAlgVO());
	}
}
