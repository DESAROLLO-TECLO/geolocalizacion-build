package mx.com.teclo.sms.ws.negocio.service.zonavial;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialDispositivosOfficersVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialAsignacionesResponseVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;

public interface ZonaVialService {
 
	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @return List<ZonaVialVO>
	 * Zonas por placa
	 */
	public List<ZonaVialVO> zonasByPlaca(String empPlaca);

	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @param codeRequest
	 * @param procedence
	 * @return List<ZonaVialAndDispositosMovilesVO>
	 * Zonas y sus dispositivos móviles por placa
	 */
	public List<ZonaVialDispositivosOfficersVO> zonasAndDispositivosByPlaca(String empPlaca, String codeRequest, String procedence);

	/**
	 * @author Kevin Ojeda
	 * @param zonaCode
	 * @param codeRequest
	 * @return ZonaVialAndDispositosMovilesVO
	 */
	public ZonaVialDispositivosOfficersVO zonasAndDispositivosAndOfficersByZonaCode(String zonaCode, String codeRequest);

	/**
	 * @author Kevin Ojeda
	 * @return List<ZonaVialVO>
	 */
	public List<ZonaVialVO> zonasActivas();

	/**
	 * @author Kevin Ojeda
	 * @return List<ZonaVialVO>
	 */
	public List<ZonaVialVO> zonasActivasPlusZonaDefault(String codeRequest);
	
	/**
	 * @author Kevin Ojeda
	 * @param vo
	 * @return
	 */
	public ZonaVialAsignacionesResponseVO persistAsignaciones(ZonaVialAsignacionesResponseVO vo);

	/**
	 * @author Kevin Ojeda
	 * @param codeRequest
	 * @return ZonaVialAndDispositosMovilesVO
	 */
	public ZonaVialDispositivosOfficersVO zonasAndDispositivosSinAsignación(String codeRequest, Long lastIdDefDisp);

	/**
	 * @author Kevin Ojeda
	 * @param serie
	 * @param sim
	 * @param ip
	 * @return ZonaVialAndDispositivosMovilesVO
	 */
	public ZonaVialDispositivosOfficersVO allDataByDispoInfo(String serie, String sim, String ip);

	/**
	 * @author Kevin Ojeda
	 * @param placa
	 * @param nombre
	 * @param paterno
	 * @param materno
	 * @return List<ZonaVialDispositivosOfficersVO>
	 */
	public List<ZonaVialDispositivosOfficersVO> allDataByOfficerInfo(String placa, String nombre, String paterno, String materno);

	/**
	 * @author Kevin Ojeda
	 * @param zonaCode
	 * @param empId
	 * @return
	 */
	public ZonaVialDispositivosOfficersVO allOfficerDataByZonaCode(String zonaCode, Long empId);

	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @return List<ZonaVialVO>
	 */
	public List<ZonaVialVO> zonasActivasPorPlacaSupervisor(String empPlaca);

	ZonaVialDispositivosOfficersVO zonasAndDispositivosSinAsignaciónForEvents(String codeRequest, String procedence,
			Long lastIdDefDisp);

}
