package mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.TipoEventoAlgDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface TipoEventoAlgDAO extends BaseDao<TipoEventoAlgDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return TipoEventoAlgDTO
	 */
	public TipoEventoAlgDTO activoByCode(String code);

	/**
	 * @author Kevin Ojeda
	 * @return List<TipoEventoAlgDTO>
	 */
	public List<TipoEventoAlgDTO> getTipoEventosActivos();

}
