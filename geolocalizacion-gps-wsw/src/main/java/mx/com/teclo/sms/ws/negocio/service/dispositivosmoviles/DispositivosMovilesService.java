package mx.com.teclo.sms.ws.negocio.service.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivoMovilPersistenciaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.ResponseDispositivosMovilFullConsultaVO;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.newDispositivoMovilRequierementsVO;

public interface DispositivosMovilesService {

	/**
	 * @author Kevin Ojeda
	 * @return newDispositivoMovilRequierementsVO
	 */
	public newDispositivoMovilRequierementsVO newVoRequierements();

	/**
	 * @author Kevin Ojeda
	 * @param DispositivoMovilPersistenciaVO
	 * @return ResponseDispositivoMovilPersistenciaVO
	 */
	public ResponseDispositivoMovilPersistenciaVO persistDevice(DispositivoMovilPersistenciaVO dispositivoVO);
	
	/**
	 * @author Kevin Ojeda
	 * @param dispositivoVO
	 * @return ResponseDispositivoMovilPersistenciaVO
	 */
	public ResponseDispositivoMovilPersistenciaVO updateDevice(DispositivoMovilPersistenciaVO dispositivoVO);

	/**
	 * @author Kevin Ojeda
	 * @param numSerie
	 * @param numSim
	 * @param numIp
	 * @param modelos
	 * @param tipoDispositivo
	 * @param zonaVial
	 * @param complementacion
	 * @param sinZonaVial
	 * ResponseDispositivosMovilFullConsultaVO
	 */
	public ResponseDispositivosMovilFullConsultaVO searchDevice(String numSerie, String numSim, String numIp, List<Long> modelos,
			List<Long> tipoDispositivo, List<Long> zonaVial, Boolean complementacion, Boolean sinZonaVial);

}
