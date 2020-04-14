package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.springframework.web.multipart.MultipartFile;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.parametro.ParametroVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BusinessHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.OKHttpResponse;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.NotFoundException;

public interface EventoAlgFileService {

	/**
	 * @author jorgeluis
	 * @param  MultipartFile
	 * @param  String
	 * @return Map<String, Object>
	 * @throws BadRequestHttpResponse
	 * @throws NotFoundException
	 * @throws IOExceptionO
	 * @throws BusinessHttpResponse
	 * @throws OKHttpResponse
	 */
	public Map<String, Object> processFile (MultipartFile file, String voInfo) throws BusinessHttpResponse, IOException, ParseException, SQLException, NamingException;
	
	/**
	 * @Descripcion: Servicio para filtrar los tipos de evento
	 * @author jorgeluis
	 * @return TipoEventoAlgVO
	 */
	public TipoEventoAlgVO filterTypeEvent (List<TipoEventoAlgVO> l, String cdTipoEvento);
	
	/**
	 * @Descripcion: Servicio para filtrar los tipos de dispositivo
	 * @author jorgeluis
	 * @return TipoEventoAlgVO
	 */
	public DispositivoMovilTipoDTO filterTypeDevice(List<DispositivoMovilTipoVO> l, String cdTipoDispositivo);
	
	/**
	 * @Descripcion: Servicio para filtrar los tipos de parámetro
	 * @author jorgeluis
	 * @return TipoEventoAlgVO
	 */
	public ParametroVO restriction(String cdParam, List<ParametroVO> l);
	
	/**
	 * @Descripcion: Servicio para guardar los eventos uno por uno
	 * @author jorgeluis
	 * @return ReponseEventoAlgVO
	 */
	public ReponseEventoAlgVO saveEvent(EventoAlgVO eventoVO) throws BadRequestHttpResponse, SQLException, NamingException ;
}
