package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilMarcaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface DispositivoMovilMarcaDAO extends BaseDao<DispositivoMovilMarcaDTO>{

	/**
	 * @author Kevin Ojeda
	 * @return List<DispositivoMovilMarcaDTO>
	 */
	public List<DispositivoMovilMarcaDTO> allActivos();

}
