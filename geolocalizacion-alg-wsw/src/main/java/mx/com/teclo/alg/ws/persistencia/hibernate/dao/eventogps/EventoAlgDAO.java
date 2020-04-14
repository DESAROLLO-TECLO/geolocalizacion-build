package mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.EventoAlgDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface EventoAlgDAO extends BaseDao<EventoAlgDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param listaEventoAlgDTO
	 * @return List<EventoAlgDTO>
	 */
	public List<EventoAlgDTO> saveListaEvento(List<EventoAlgDTO> listaEventoAlgDTO);

}
