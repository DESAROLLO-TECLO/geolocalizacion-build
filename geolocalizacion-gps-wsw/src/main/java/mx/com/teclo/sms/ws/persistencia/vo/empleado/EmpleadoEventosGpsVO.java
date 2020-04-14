package mx.com.teclo.sms.ws.persistencia.vo.empleado;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.IndicadorVO;
import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.EventoGpsVO;

public class EmpleadoEventosGpsVO {
	
	private EmpleadosRespectivosAreaOperativaVO empleado;
	private IndicadorVO indicador;
	private List<EventoGpsVO> eventos;
	
	public EmpleadosRespectivosAreaOperativaVO getEmpleado() {
		return empleado;
	}
	public void setEmpleado(EmpleadosRespectivosAreaOperativaVO empleado) {
		this.empleado = empleado;
	}
	public IndicadorVO getIndicador() {
		return indicador;
	}
	public void setIndicador(IndicadorVO indicador) {
		this.indicador = indicador;
	}
	public List<EventoGpsVO> getEventos() {
		return eventos;
	}
	public void setEventos(List<EventoGpsVO> eventos) {
		this.eventos = eventos;
	}
	
	@Override
	public String toString() {
		return "EmpleadoEventosGpsVO [empleado=" + empleado + ", indicador=" + indicador + ", eventos=" + eventos + "]";
	}
}
