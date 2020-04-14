package mx.com.teclo.sms.ws.persistencia.vo.zonavial;

import java.util.List;

public class ZonaVialAsignacionesResponseVO {

	private List<ZonaVialAsignacionDispositivosOficialesVO> asignaciones;
	private String codigoHttp;
	private String descripcion;

	public List<ZonaVialAsignacionDispositivosOficialesVO> getAsignaciones() {
		return asignaciones;
	}
	public void setAsignaciones(List<ZonaVialAsignacionDispositivosOficialesVO> asignaciones) {
		this.asignaciones = asignaciones;
	}
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
	
	@Override
	public String toString() {
		return "ZonaVialAsignacionesResponseVO [asignaciones=" + asignaciones + ", codigoHttp=" + codigoHttp
				+ ", descripcion=" + descripcion + "]";
	}

}
