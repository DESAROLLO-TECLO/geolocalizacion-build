package mx.com.teclo.sms.ws.persistencia.hibernate.dao.empleado;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.empleados.CargoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface CargoDAO extends BaseDao<CargoDTO>{

	/**
	 * @author Kevin Ojeda
	 * @param code
	 * @return CargoDTO
	 */
	public CargoDTO activoByCode(String code);

}
