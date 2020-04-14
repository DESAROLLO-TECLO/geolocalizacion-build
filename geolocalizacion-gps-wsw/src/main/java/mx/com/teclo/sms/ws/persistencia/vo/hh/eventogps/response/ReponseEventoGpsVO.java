package mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.response;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;

public class ReponseEventoGpsVO {

	private String codigoHttp;
	private String descripcion;
	private EventoGpsVO eventoGpsVO;
	private List<EventoGpsVO> listaEventoGpsVO;

	public String getCodigoHttp() {
		return codigoHttp;
	}

	public void setCodigoHttp(String codigoHttp) {
		this.codigoHttp = codigoHttp;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EventoGpsVO getEventoGpsVO() {
		return eventoGpsVO;
	}

	public void setEventoGpsVO(EventoGpsVO eventoGpsVO) {
		this.eventoGpsVO = eventoGpsVO;
	}

	public List<EventoGpsVO> getListaEventoGpsVO() {
		return listaEventoGpsVO;
	}

	public void setListaEventoGpsVO(List<EventoGpsVO> listaEventoGpsVO) {
		this.listaEventoGpsVO = listaEventoGpsVO;
	}

}