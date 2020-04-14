package mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.EventoAlgDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDaoHibernate;

@Repository
public class EventoAlgDAOImpl extends BaseDaoHibernate<EventoAlgDTO> implements EventoAlgDAO{
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EventoAlgDTO> saveListaEvento(List<EventoAlgDTO> listaEventoAlgDTO) {
		List<EventoAlgDTO> responseListaEventoAlgDTO = new ArrayList<EventoAlgDTO>();
		for (EventoAlgDTO eventoGpsDTO : listaEventoAlgDTO) {
			save(eventoGpsDTO);
			responseListaEventoAlgDTO.add(eventoGpsDTO);
		}
		return responseListaEventoAlgDTO;
	}
}
