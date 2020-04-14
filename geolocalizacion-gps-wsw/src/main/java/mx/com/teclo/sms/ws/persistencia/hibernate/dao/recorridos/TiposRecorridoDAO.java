package mx.com.teclo.sms.ws.persistencia.hibernate.dao.recorridos;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.TipoRecorridoDTO;
import mx.com.teclomexicana.arquitectura.persistencia.comun.dao.BaseDao;

public interface TiposRecorridoDAO  extends  BaseDao<TipoRecorridoDTO>{
	
  	public List<TipoRecorridoDTO> getTiposRecorrido( );
 	 
  	public  TipoRecorridoDTO  getTiposRecorridoByCodigo(String cdRecorrido );

}
