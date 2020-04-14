package mx.com.teclo.sms.ws.negocio.service.eventogps;

import java.util.Date;
import java.util.List;

import mx.com.teclo.arquitectura.ortogonales.responsehttp.PersistenceHttpResponse;
import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositoMovilEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoEventosGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisDispositivoEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.MyBatisOficialEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.SimpleEventoVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.response.ReponseEventoGpsVO;

public interface EventoGpsService {
	
	/**
	 * @author Francisco Martinez
	 * @param eventoGpsVO
	 * @return ReponseEventoGpsVO
	 * @throws PersistenceHttpResponse 
	 */
	public ReponseEventoGpsVO saveEvento(EventoGpsVO eventoGpsVO);
	
	/**
	 * @author Francisco Martinez
	 * @param listaEventoGpsVO
	 * @return ReponseEventoGpsVO
	 */
	public ReponseEventoGpsVO saveListaEvento(List<EventoGpsVO> listaEventoGpsVO);
	
	/**
	 * @author Kevin Ojeda
	 * @param series
	 * @param fI
	 * @param fF
	 * @return List<DispositoMovilEventosGpsVO>
	 */
	public List<DispositoMovilEventosGpsVO> eventosByHHAndFechas(String[] series, Date fI, Date fF);

	/**
	 * @author Kevin Ojeda
	 * @param plcs
	 * @param fI
	 * @param fF
	 * @return List<EmpleadoEventosGpsVO>
	 */
	public List<EmpleadoEventosGpsVO> eventosByPlacasAndFechas(String[] plcs, Date fI, Date fF);

	/**
	 * @author Kevin Ojeda
	 * @param series
	 * @param fI
	 * @param fF
	 * @return List<MyBatisDispositivoEventoVO>
	 */
	public List<MyBatisDispositivoEventoVO> optimizedEventosByHHAndFechas(String[] series, Date fI);
	
	/**
	 * @author Kevin Ojeda
	 * @param plcs
	 * @param fI
	 * @return List<MyBatisOficialEventoVO>
	 */
	public List<MyBatisOficialEventoVO> optimizedEventosByPlacasAndFechas(String[] plcs, Date fI);

	/**
	 * @author Kevin Ojeda
	 * @param empId
	 * @param numSerie
	 * @param fI
	 * @param fF
	 * @return List<SimpleEventoVO>
	 */
	public List<SimpleEventoVO> eventosByItem(Long empId, String numSerie, Date fI);
	
}
