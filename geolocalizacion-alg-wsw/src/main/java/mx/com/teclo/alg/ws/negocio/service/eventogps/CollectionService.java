package mx.com.teclo.alg.ws.negocio.service.eventogps;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.TipoEventoAlgVO;
import mx.com.teclo.alg.ws.persistencia.vo.hh.layout.LayoutVO;

public interface CollectionService {

	/**
	 * @Descripcion: Servicio para consultar los tipos de eventos
	 * @author jorgeluis
	 * @return List<TipoEventoAlgVO>
	 */
	public List<TipoEventoAlgVO> typeEvent();
	
	/**
	 * @Descripcion: Servicio para consultar los tipos de dispositivos
	 * @author jorgeluis
	 * @return List<DispositivoMovilTipoVO>
	 */
	public List<DispositivoMovilTipoVO> typeDevice();
	
	
	/**
	 * Método para consultar el layout actual del archivo de eventos
	 * recibido en la petición HTTP para su validación.
	 * Filtramos este resultado mediante el código identificador del
	 * tipo de layout
	 * @author jorgeluis
	 * @return List<LayoutVO>
	 */
	public List<LayoutVO> layout(String cdTpLayout);
}
