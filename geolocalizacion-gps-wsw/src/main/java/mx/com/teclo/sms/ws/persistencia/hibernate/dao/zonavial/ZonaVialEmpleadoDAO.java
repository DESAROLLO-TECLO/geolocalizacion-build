package mx.com.teclo.sms.ws.persistencia.hibernate.dao.zonavial;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.zonavial.ZonaVialCargoEmpleadoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface ZonaVialEmpleadoDAO extends BaseDao<ZonaVialCargoEmpleadoDTO>{
 
	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @return List<ZonaVialEmpleadoDTO>
	 */
	public List<ZonaVialCargoEmpleadoDTO> findZonasByPlaca(String empPlaca);

	/**
	 * @author Kevin Ojeda
	 * @param zona
	 * @return List<ZonaVialEmpleadoDTO>
	 */
	public List<ZonaVialCargoEmpleadoDTO> oficialesActivosByZona(Long zona);

	/**
	 * @author Kevin Ojeda
	 * @param zona
	 * @return List<ZonaVialEmpleadoDTO>
	 */
	public List<ZonaVialCargoEmpleadoDTO> oficialesByZona(Long zona);

	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @param nombre
	 * @param paterno
	 * @param materno
	 * @return List<ZonaVialEmpleadoDTO>
	 */
	public List<ZonaVialCargoEmpleadoDTO> activoByOfficerInfo(String placa, String nombre, String paterno, String materno);

	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @return ZonaVialEmpleadoDTO
	 */
	public ZonaVialCargoEmpleadoDTO zonaByEmpPlaca(String placa);

}
