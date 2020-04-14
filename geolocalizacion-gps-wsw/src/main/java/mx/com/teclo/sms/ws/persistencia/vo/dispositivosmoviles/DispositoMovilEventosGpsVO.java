package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.IndicadorVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;

public class DispositoMovilEventosGpsVO {
	
	private DispositivoMovilVO dispositivo;
	private IndicadorVO indicador;
	private List<EventoGpsVO> eventos;
	
	public DispositivoMovilVO getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(DispositivoMovilVO dispositivo) {
		this.dispositivo = dispositivo;
	}
	public List<EventoGpsVO> getEventos() {
		return eventos;
	}
	public void setEventos(List<EventoGpsVO> eventos) {
		this.eventos = eventos;
	}
	public IndicadorVO getIndicador() {
		return indicador;
	}
	public void setIndicador(IndicadorVO indicador) {
		this.indicador = indicador;
	}
	
}
