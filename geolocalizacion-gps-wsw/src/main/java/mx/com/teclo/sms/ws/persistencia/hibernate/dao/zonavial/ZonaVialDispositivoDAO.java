package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialDispositivoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface ZonaVialDispositivoDAO extends BaseDao<ZonaVialDispositivoDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param zona
	 * @return List<ZonaVialDispositivoDTO>
	 */
	public List<ZonaVialDispositivoDTO> byZona(Long zona);

	/**
	 * @author Kevin Ojeda
	 * @param zona
	 * @return List<ZonaVialDispositivoDTO>
	 */
	public List<ZonaVialDispositivoDTO> activosByZona(Long zona);

	/**
	 * @author Kevin Ojeda
	 * @param serie
	 * @param sim
	 * @param ip
	 * @return ZonaVialDispositivoDTO
	 */
	public ZonaVialDispositivoDTO activoByDispoInfo(String serie, String sim, String ip);

}
