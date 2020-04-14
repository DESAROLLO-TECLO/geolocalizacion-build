package mx.com.teclo.sms.ws.negocio.service.localizacion;

import java.util.Date;
import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.BitacoraCoordenadaVO;

public interface LocalizacionHandHeldService {

	public List<BitacoraCoordenadaVO> coordenadasBitacora(String[] serieshh, Date fechaInicio, Date fechaFin);

}
