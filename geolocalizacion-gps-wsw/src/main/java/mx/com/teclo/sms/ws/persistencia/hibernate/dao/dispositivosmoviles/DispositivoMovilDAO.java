package mx.com.teclo.sms.ws.persistencia.hibernate.dao.dispositivosmoviles;

import java.util.List;
import java.util.Map;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilDTO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.RequestDispositivoMovilFullConsultaVO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface DispositivoMovilDAO extends BaseDao<DispositivoMovilDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return
	 */
	public DispositivoMovilDTO activoBySerie(String code);

	/**
	 * @author Kevin Ojeda
	 * @param ip
	 * @param numSerie
	 * @param numSim
	 * @return List<String>
	 */
	public Map<String, String> flagDispositivo(String ip, String numSerie, String numSim);

	/**
	 * @author Kevin Ojeda
	 * @return List<DispositivoMovilDTO>
	 */
	public List<DispositivoMovilDTO> dispositivosSinAsignacion();

	/**
	 * @author Kevin Ojeda
	 * @param serie
	 * @param sim
	 * @param ip
	 * @return DispositivoMovilDTO
	 */
	public DispositivoMovilDTO activoByDispoInfo(String serie, String sim, String ip);

	/**
	 * @author Kevin Ojeda
	 * @param ids
	 * @return List<DispositivoMovilDTO>
	 */
	public List<DispositivoMovilDTO> dispositivosPorZonasViales(List<Long> ids);

	/**
	 * @author Kevin Ojeda
	 * @param id
	 * @return List<DispositivoMovilDTO>
	 */
	public List<DispositivoMovilDTO> dispositivosPorZonaVial(Long id);

	/**
	 * @author Kevin Ojeda
	 * @param RequestDispositivoMovilFullConsultaVO 
	 * @return
	 */
	public List<DispositivoMovilDTO> fullSearch(RequestDispositivoMovilFullConsultaVO vo);

	/**
	 * @author Kevin Ojeda
	 * @param dispId
	 * @param ip
	 * @param numSerie
	 * @param numSim
	 * @return Map<String, String>
	 */
	public Map<String, String> flagDispositivoForUpdate(Long dispId, String ip, String numSerie, String numSim);

}
