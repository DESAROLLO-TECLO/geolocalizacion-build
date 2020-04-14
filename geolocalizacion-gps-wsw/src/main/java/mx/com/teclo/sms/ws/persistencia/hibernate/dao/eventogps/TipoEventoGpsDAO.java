package mx.com.teclo.sms.ws.persistencia.hibernate.dao.eventogps;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.hh.eventogps.TipoEventoGpsDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface TipoEventoGpsDAO extends BaseDao<TipoEventoGpsDTO> {

	/**
	 * @author Francisco Martinez
	 * @param idTipoEvento
	 * @return
	 */
	public TipoEventoGpsDTO getTipoEvento(Long idTipoEvento);

	/**
	 * @author Kevin Ojeda
	 * @return List<TipoEventoGpsDTO>
	 */
	public List<TipoEventoGpsDTO> getTipoEventosActivos();

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return TipoEventoGpsDTO
	 */
	public TipoEventoGpsDTO getTipoEventoByCode(String code);

 }