package mx.com.teclo.sms.ws.persistencia.vo.reportes;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps.TipoEventoGpsVO;
import mx.com.teclo.sms.ws.persistencia.vo.infracciones.TipoInfraccionVO;
import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;

public class ReporteGeneralDataVO {
	
	private String codigoHttp;
	private String descripcion;
	private List<ZonaVialVO> zonasViales;
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
	public List<ZonaVialVO> getZonasViales() {
		return zonasViales;
	}
	public void setZonasViales(List<ZonaVialVO> zonasViales) {
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
		return "ReporteGeneralDataVO [codigoHttp=" + codigoHttp + ", descripcion=" + descripcion + ", zonasViales="
				+ zonasViales + ", tipoEventos=" + tipoEventos + ", tipoInfracciones=" + tipoInfracciones+"]";
	}
}
