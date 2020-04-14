package mx.com.teclo.sms.ws.negocio.service.reportes;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsReporteGeneralVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDataVO;
import mx.com.teclo.sms.ws.persistencia.vo.reportes.ReporteGeneralDispositivosOficialesDataVO;
import mx.com.teclomexicana.arquitectura.ortogonales.exception.BusinessException;

public interface ReporteService {
	
	@SuppressWarnings("rawtypes")
	public Map infraccionesPorZonaVial(Long zonaVial, String dia);
	
	@SuppressWarnings("rawtypes")
	public Map catalogos(String empPlaca) throws BusinessException;

	/**
	 * @author Kevin Ojeda
	 * @param zonaVial
	 * @param anio
	 * @return List<Map>
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> infraccionesMensualesPorZonaVial(Long zonaVial, Integer anio);

	/**
	 * @author Kevin Ojeda
	 * @param zonaVial
	 * @param anio
	 * @return List<Map>
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> infraccionesAnualesPorZonaVial(Long zonaVial, List<Integer> anio);

	/**
	 * @author Kevin Ojeda
	 * @param empId
	 * @return ReporteGeneralDataVO
	 */
	public ReporteGeneralDataVO reporteGeneralData(String empPlaca);

	/**
	 * @author Kevin Ojeda
	 * @param zonasVialesId
	 * @param fI
	 * @param fF
	 * @param idsTipoEvento
	 * @param codigosTipoInfraccion
	 * @param Integer loginRestriction
	 * @return EventoGpsReporteGeneralVO
	 */
	public List<EventoGpsReporteGeneralVO> reporteGeneralFiltrado(Integer loginRestriction, List<Long> zonasVialesId, Date fI, Date fF, List<Long> idsTipoEvento,
			List<String> codigosTipoInfraccion);

	/**
	 * @author Kevin Ojeda
	 * @param reportKind
	 * @return Map<String, Object>
	 */
	public Map<String, Object> reporteGeneralXls(Integer reportKind);

	/**
	 * @author Kevin Ojeda
	 * @param empPlaca
	 * @return ReporteGeneralDispositivosOficialesDataVO
	 */
	public ReporteGeneralDispositivosOficialesDataVO reporteGeneralOficialesDispositivosData(String empPlaca);

	/**
	 * @author Kevin Ojeda
	 * @param oficiales
	 * @param dispositivos
	 * @param fI
	 * @param fF
	 * @param idsTipoEvento
	 * @param codigosTipoInfraccion
	 * @return List<EventoGpsReporteGeneralVO>
	 */
	public List<EventoGpsReporteGeneralVO> reporteGeneralFiltradoOficialesDispostivos(Integer loginRestriction, boolean busquedaConjunta, List<Long> oficiales, List<Long> dispositivos, Date fI, Date fF, List<Long> idsTipoEvento, List<String> codigosTipoInfraccion);
}
