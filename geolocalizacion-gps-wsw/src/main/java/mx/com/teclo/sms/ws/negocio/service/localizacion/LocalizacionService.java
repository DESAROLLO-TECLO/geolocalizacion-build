package mx.com.teclo.sms.ws.negocio.service.localizacion;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.BitacoraCoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.CoordenadaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.RutasCoordenadasVO;
import mx.com.teclo.sms.ws.persistencia.vo.ruta.RutaVO;

public interface LocalizacionService {
	
	public CoordenadaVO saveCoordenada(CoordenadaVO coordenadaVO);
	public  List<CoordenadaVO> getCoordenadasByRuta(Long idRuta );
	public  List<CoordenadaVO> getCoordenadasByEmpleado(Long idEmpleado);
	public  List<CoordenadaVO>   getCoordenadasByDate(String placa, String fechaInicio, String fechaFin ) throws ParseException ;
	public  RutaVO saveRuta(RutaVO rutaVO);
	public  RutaVO saveCoordenadasXRuta(RutasCoordenadasVO rutasCoordenadasVO);
	public  List<RutaVO> getRutasByUser(String placa ,Long tipoRecorrido);
	/**
	 * @author Kevin Ojeda
	 * @param placas
	 * @param FI
	 * @param FF
	 * @return List<BitacoraCoordenadaVO>
	 */
	public List<BitacoraCoordenadaVO> coordenadasBitacora(String[] placas, Date FI, Date FF);


}
