package mx.com.teclo.alg.ws.persistencia.hibernate.dao.layout;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.layout.LayoutDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface LayoutDAO extends BaseDao<LayoutDTO>{

	/**
	 * Método para consultar el layout actual del archivo de eventos
	 * recibido en la petición HTTP para su validación.
	 * Filtramos este resultado mediante el código identificador del
	 * tipo de layout
	 * @author jorgeluis
	 * @return List<LayoutDTO>
	 */
	public List<LayoutDTO> layout(String cdTpLayout);
}
