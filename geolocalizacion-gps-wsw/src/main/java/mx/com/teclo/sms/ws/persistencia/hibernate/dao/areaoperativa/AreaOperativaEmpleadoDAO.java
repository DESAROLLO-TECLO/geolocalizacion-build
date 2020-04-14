package mx.com.teclo.sms.ws.persistencia.hibernate.dao.areaoperativa;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.areaoperativa.AreaOperativaEmpleadoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface AreaOperativaEmpleadoDAO extends BaseDao<AreaOperativaEmpleadoDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param empId
	 * @param cargo
	 * @return List<AreaOperativaEmpleadoDTO>
	 */
	public List<AreaOperativaEmpleadoDTO> findByPlacaAndCargo(Long empId, String cargoCodigo);

	/**
	 * @author Kevin Ojeda
	 * @param area
	 * @param cargoCodigo
	 * @return List<AreaOperativaEmpleadoDTO>
	 */
	public List<AreaOperativaEmpleadoDTO> findByAreaAndCargo(Long area, String cargoCodigo);

	/**
	 * @author Kevin Ojeda
	 * @param cargoCodigo
	 * @return List<AreaOperativaEmpleadoDTO>
	 */
	public List<AreaOperativaEmpleadoDTO> findByCargo(String cargoCodigo);

}
