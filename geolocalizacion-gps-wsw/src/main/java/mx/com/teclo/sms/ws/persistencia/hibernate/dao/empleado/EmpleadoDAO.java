package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import java.util.ArrayList;
import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface EmpleadoDAO  extends  BaseDao<EmpleadosDTO>{
	
	/**
	 * @author Francisco Martinez
	 * @param placa
	 * @return EmpleadosDTO
	 */
	public EmpleadosDTO findUserByPlaca( String placa );

	/**
	 * @author Kevin Ojeda
	 * @param placas
	 * @return ArrayList<EmpleadosDTO>
	 */
	public List<EmpleadosDTO> findUsersByPlacas(ArrayList<String> placas);

	/**
	 * @author Kevin Ojeda
	 * @return List<EmpleadosDTO>
	 */
	public List<EmpleadosDTO> oficialesSinAsignacion();

	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @param nombre
	 * @param paterno
	 * @param materno
	 * @return List<EmpleadosDTO>
	 */
	public List<EmpleadosDTO> activoByOfficerInfo(String placa, String nombre, String paterno, String materno);

	/**
	 * @author Kevin Ojeda
	 * @param id
	 * @return List<EmpleadosDTO>
	 */
	public List<EmpleadosDTO> oficialesPorZonaVial(Long id);

}
