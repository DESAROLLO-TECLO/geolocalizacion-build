package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.EmpleadosCargosDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface EmpleadosCargosDAO extends BaseDao<EmpleadosCargosDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param codigo
	 * @return EmpleadosCargosDTO
	 */
	public EmpleadosCargosDTO byCodeAndPlaca(String codigo, String placa);

}
