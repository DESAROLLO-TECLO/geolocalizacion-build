package mx.com.teclo.sms.ws.negocio.service.recorridos;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.teclo.sms.ws.persistencia.hibernate.dao.recorridos.TiposRecorridoDAO;
import mx.com.teclo.sms.ws.persistencia.hibernate.dto.geolocalizacion.TipoRecorridoDTO;
import mx.com.teclo.sms.ws.persistencia.vo.ruta.TipoRecorridoVO;
import mx.com.teclo.sms.ws.util.comun.ResponseConverter;

@Service
public class TipoRecorridoServiceImpl implements TipoRecorridoService {

	@Autowired
	private TiposRecorridoDAO tiposRecorridoDAO;
 
	@Override
	@Transactional
	public List<TipoRecorridoVO> getTiposRecorrido() {
		
		List<TipoRecorridoDTO> listTiposRecorridoDTO = tiposRecorridoDAO.getTiposRecorrido();

		List<TipoRecorridoVO> listTipoRecorridoVO = ResponseConverter.converterLista(new ArrayList<>(), listTiposRecorridoDTO, TipoRecorridoVO.class);
		
 		return listTipoRecorridoVO;
	}
	
	 

}
