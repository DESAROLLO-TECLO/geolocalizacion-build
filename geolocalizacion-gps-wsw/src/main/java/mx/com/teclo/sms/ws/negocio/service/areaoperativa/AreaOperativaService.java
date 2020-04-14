package mx.com.teclo.sms.ws.negocio.service.areaoperativa;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaSupervisorVO;
import mx.com.teclo.sms.ws.persistencia.vo.areaoperativa.AreaOperativaVO;

public interface AreaOperativaService {

	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @return List<AreaOperativaVO>
	 */
	public List<AreaOperativaVO> areasByEmp(String empPlaca, String procedence);

	/**
	 * @author Kevin Ojeda
	 * @return List<AreaOperativaSupervisorVO>
	 */
	public List<AreaOperativaSupervisorVO> areasSupervisor();
}
