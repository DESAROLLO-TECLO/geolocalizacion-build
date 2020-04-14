package mx.com.teclo.alg.ws.persistencia.hibernate.dao.parametro;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.parametro.ParametroDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface ParametroDAO extends BaseDao<ParametroDTO>{


	/**
	 * @Descripción: Método para consultar todos los parámetros 
	 * mediante app
	 * @author Jorge Luis
	 * @return List<ParametroDTO>
	 */
	public List<ParametroDTO> restriction();
	
	/**
	 * @Descripción: Método para consultar un parámetro 
	 * por su código identificador
	 * @author Jorge Luis
	 * @return ParametroDTO
	 */
	public ParametroDTO restrictionByCode(String cdParametro);
	
}
