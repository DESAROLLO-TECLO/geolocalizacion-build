package mx.com.teclo.sms.ws.persistencia.vo.reportes;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.TipoEventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.infracciones.TipoInfraccionVO;

public class ReporteGeneralDispositivosOficialesDataVO {
	
	private String codigoHttp;
	private String descripcion;
	private List<ZonaOficialesDispositivosVO> zonasViales;
	private List<TipoEventoGpsVO> tipoEventos;
	private List<TipoInfraccionVO> tipoInfracciones;
	
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
	public List<ZonaOficialesDispositivosVO> getZonasViales() {
		return zonasViales;
	}
	public void setZonasViales(List<ZonaOficialesDispositivosVO> zonasViales) {
		this.zonasViales = zonasViales;
	}
	public List<TipoEventoGpsVO> getTipoEventos() {
		return tipoEventos;
	}
	public void setTipoEventos(List<TipoEventoGpsVO> tipoEventos) {
		this.tipoEventos = tipoEventos;
	}
	public List<TipoInfraccionVO> getTipoInfracciones() {
		return tipoInfracciones;
	}
	public void setTipoInfracciones(List<TipoInfraccionVO> tipoInfracciones) {
		this.tipoInfracciones = tipoInfracciones;
	}
	
	@Override
	public String toString() {
		return "ReporteGeneralDispositivosOficialesDataVO [codigoHttp=" + codigoHttp + ", descripcion=" + descripcion
				+ ", zonasViales=" + zonasViales + ", tipoEventos=" + tipoEventos + ", tipoInfracciones="
				+ tipoInfracciones + "]";
	}
}
