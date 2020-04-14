package mx.com.teclo.alg.ws.persistencia.hibernate.dao.empleados;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.empleados.EmpleadosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface EmpleadoDAO extends BaseDao<EmpleadosDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @return EmpleadosDTO
	 */
	public EmpleadosDTO findUserByPlaca(String placa);

}