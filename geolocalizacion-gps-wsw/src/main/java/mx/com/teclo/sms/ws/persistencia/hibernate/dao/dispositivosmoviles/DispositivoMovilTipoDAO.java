package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface DispositivoMovilTipoDAO extends BaseDao<DispositivoMovilTipoDTO>{

	/**
	 * @author Kevin Ojeda
	 * @return List<DispositivoMovilTipoDTO>
	 */
	public List<DispositivoMovilTipoDTO> allActivos();

}
