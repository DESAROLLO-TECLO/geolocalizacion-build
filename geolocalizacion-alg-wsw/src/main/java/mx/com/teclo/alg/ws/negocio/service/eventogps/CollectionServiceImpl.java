package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.teclo.alg.ws.persistencia.hibernate.dao.dispositivosmoviles.DispositivoMovilTipoDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.eventogps.TipoEventoAlgDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dao.layout.LayoutDAO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.dispositivosmoviles.DispositivoMovilTipoDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.eventoalg.TipoEventoAlgDTO;
import mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.layout.LayoutDTO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.layout.LayoutVO;
import mx.com.teclo.arquitectura.ortogonales.util.ResponseConverter;

@Service
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	private TipoEventoAlgDAO tipoEventoAlgDAO;
	
	@Autowired
	private DispositivoMovilTipoDAO dispositivoMovilTipoDAO;
	
	@Autowired
	private LayoutDAO layoutDAO;
	
	
	@Transactional
	@Override
	public List<TipoEventoAlgVO> typeEvent() {
		List<TipoEventoAlgDTO> tipoEventosAlg = tipoEventoAlgDAO.getTipoEventosActivos();
		List<TipoEventoAlgVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), tipoEventosAlg, TipoEventoAlgVO.class);
		return listReturn;
	}
	
	@Transactional
	@Override
	public List<DispositivoMovilTipoVO> typeDevice() {
		List<DispositivoMovilTipoDTO> tipoEventosAlg = dispositivoMovilTipoDAO.tiposDispositivosActivos();
		List<DispositivoMovilTipoVO> listReturn = ResponseConverter.converterLista(new ArrayList<>(), tipoEventosAlg, DispositivoMovilTipoVO.class);
		return listReturn;
	}

	@Transactional
	@Override
	public List<LayoutVO> layout(String cdTpLayout) {
		List<LayoutDTO>  lListDTO = layoutDAO.layout(cdTpLayout);
		List<LayoutVO> lListVO = ResponseConverter.converterLista(new ArrayList<>(), lListDTO, LayoutVO.class);
		return lListVO;
	}
	
}
