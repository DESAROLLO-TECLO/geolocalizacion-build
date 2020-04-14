package mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface DispositivoMovilTipoDAO extends BaseDao<DispositivoMovilTipoDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return DispositivoMovilTipoDTO
	 */
	public DispositivoMovilTipoDTO activoByCode(String code);

	/**
	 * @author Kevin Ojeda
	 * @return List<DispositivoMovilTipoDTO>
	 */
	public List<DispositivoMovilTipoDTO> tiposDispositivosActivos();

}
