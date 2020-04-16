package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.stereotype.Service;

import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response.ReponseEventoAlgVO;

@Service
public interface EventoAlgService {

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
	
	/**
	 * @Descripcion: Método para guardar una lista de eventos del objeto EventoAlgVO
	 * se reutiliza el servicio que procesa los archivos de texto
	 * @author jorgeluis
	 * @return ReponseEventoAlgVO
	 */
	public ReponseEventoAlgVO saveArrayEvent (List<EventoAlgVO> listaEventoAlgVO, Integer stHistorico) throws ParseException, SQLException, NamingException;
}
