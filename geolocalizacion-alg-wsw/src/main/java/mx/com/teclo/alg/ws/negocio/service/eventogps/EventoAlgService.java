package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BadRequestHttpResponse;
import mx.com.teclo.arquitectura.ortogonales.responsehttp.BusinessHttpResponse;

@Service
public interface EventoAlgService {
	
	/**
	 * @author Kevin Ojeda
	 * @param eventoVO
	 * @return ReponseEventoAlgVO
	 * @throws BadRequestHttpResponse 
	 */
	public ReponseEventoAlgVO saveEventoAlg(EventoAlgVO eventoVO) throws BusinessHttpResponse, BadRequestHttpResponse;

	/**
	 * @author Kevin Ojeda
	 * @param listaEventoAlgVO
	 * @return ReponseEventoAlgVO
	 */
	public ReponseEventoAlgVO saveListaEventoAlg(List<EventoAlgVO> listaEventoAlgVO, Integer stHistorico);

	/**
	 * @Descripcion: Método para validar los atributos del opbjeto
	 * @author jorgeluis
	 * @return Boolean
	 */
	public Boolean validObject(EventoAlgVO object, List<TipoEventoAlgVO> listEventos);
}
