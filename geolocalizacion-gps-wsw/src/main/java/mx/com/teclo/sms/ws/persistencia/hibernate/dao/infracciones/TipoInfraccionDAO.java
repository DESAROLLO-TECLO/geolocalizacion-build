package mx.com.teclo.sms.ws.persistencia.hibernate.dao.infracciones;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.infracciones.TipoInfraccionDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface TipoInfraccionDAO extends BaseDao<TipoInfraccionDTO> {

	/**
	 * @author Kevin Ojeda
	 * @param dispositivo
	 * @return
	 */
	public List<TipoInfraccionDTO> allActivosByNombreDispositivo(String dispositivo);

}