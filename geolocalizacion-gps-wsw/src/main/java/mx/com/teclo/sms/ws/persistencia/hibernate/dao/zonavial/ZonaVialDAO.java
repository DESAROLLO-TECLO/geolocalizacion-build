package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface ZonaVialDAO extends BaseDao<ZonaVialDTO>{

	/**
	 * @author Kevin Ojeda
	 * @return List<ZonaVialDTO>
	 */
	public List<ZonaVialDTO> allActivos();

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return ZonaVialDTO
	 */
	public ZonaVialDTO activoByCode(String code);

}
