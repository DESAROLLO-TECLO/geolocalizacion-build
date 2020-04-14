package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.List;

public class ResponseDispositivosMovilFullConsultaVO {

	
	private List<DispositivoMovilFullConsultaVO> dispositivos;
	private String codigoHttp;
	private String descripcion;
	
	public List<DispositivoMovilFullConsultaVO> getDispositivos() {
		return dispositivos;
	}
	public void setDispositivos(List<DispositivoMovilFullConsultaVO> dispositivos) {
		this.dispositivos = dispositivos;
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
		return "ResponseDispositivoMovilPersistenciaVO [dispositivos=" + dispositivos + ", codigoHttp=" + codigoHttp
				+ ", descripcion=" + descripcion + "]";
	}
	
}
