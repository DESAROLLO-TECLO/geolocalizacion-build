package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilModeloDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface DispositivoMovilModeloDAO extends BaseDao<DispositivoMovilModeloDTO>{

	/**
	 * @author Kevin Ojeda
	 * @return List<DispositivoMovilMarcaDTO>
	 */
	public List<DispositivoMovilModeloDTO> allActivos();

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return List<DispositivoMovilModeloDTO>
	 */
	public List<DispositivoMovilModeloDTO> byMarcaCode(String code);

}
