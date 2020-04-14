package mx.com.teclo.sms.ws.persistencia.hibernate.dao.coordenadas;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.IndicadorDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface IndicadorDAO extends BaseDao<IndicadorDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param minutos
	 * @return
	 */
	public IndicadorDTO lastIndicador(Long minutos);

	/**
	 * @author Kevin Ojeda
	 * @return List<IndicadorDTO>
	 */
	public List<IndicadorDTO> allActivos();

}
