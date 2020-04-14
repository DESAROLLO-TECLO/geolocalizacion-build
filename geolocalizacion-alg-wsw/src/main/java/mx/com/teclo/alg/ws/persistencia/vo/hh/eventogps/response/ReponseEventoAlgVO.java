package mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.response;

import java.util.List;

import mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.EventoAlgVO;

public class ReponseEventoAlgVO {

	private String codigoHttp;
	private String descripcion;
	private EventoAlgVO eventoAlgVO;
	private List<EventoAlgVO> listaEventoAlgVO;
	
	public String getCodigoHttp() {
		return codigoHttp;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public EventoAlgVO getEventoAlgVO() {
		return eventoAlgVO;
	}
	public List<EventoAlgVO> getListaEventoAlgVO() {
		return listaEventoAlgVO;
	}
	public void setCodigoHttp(String codigoHttp) {
		this.codigoHttp = codigoHttp;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setEventoAlgVO(EventoAlgVO eventoAlgVO) {
		this.eventoAlgVO = eventoAlgVO;
	}
	public void setListaEventoAlgVO(List<EventoAlgVO> listaEventoAlgVO) {
		this.listaEventoAlgVO = listaEventoAlgVO;
	}
	@Override
	public String toString() {
		return "ReponseEventoAlgVO [codigoHttp=" + codigoHttp + ", descripcion=" + descripcion + ", eventoAlgVO="
				+ eventoAlgVO + ", listaEventoAlgVO=" + listaEventoAlgVO + "]";
	}

}
