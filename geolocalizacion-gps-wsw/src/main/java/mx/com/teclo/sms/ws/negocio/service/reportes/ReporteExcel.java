package mx.com.teclo.sms.ws.negocio.service.reportes;

import java.util.List;
import java.util.Map;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsReporteGeneralVO;

public interface ReporteExcel {

	/**
	 * @author Kevin Ojeda
	 * @param listaRegistroVO
	 * @param nombreReporte
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> reporteGeneral(List<EventoGpsReporteGeneralVO> listaRegistroVO);

}
