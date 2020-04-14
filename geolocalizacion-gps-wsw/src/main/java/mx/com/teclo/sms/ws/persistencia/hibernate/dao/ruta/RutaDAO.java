package mx.com.teclo.sms.ws.persistencia.hibernate.dao.ruta;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.RutaDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface RutaDAO extends  BaseDao<RutaDTO> {
 
 	public List<RutaDTO> getRutasByUser(Long idEmpleado, Long tipoRecorrido);
 
}
