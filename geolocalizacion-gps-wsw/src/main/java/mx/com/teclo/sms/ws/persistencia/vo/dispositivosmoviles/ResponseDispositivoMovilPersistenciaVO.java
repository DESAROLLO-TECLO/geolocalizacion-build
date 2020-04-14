package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

public class ResponseDispositivoMovilPersistenciaVO {

	
	private DispositivoMovilPersistenciaVO dispositivo;
	private String codigoHttp;
	private String descripcion;
	
	public DispositivoMovilPersistenciaVO getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(DispositivoMovilPersistenciaVO dispositivo) {
		this.dispositivo = dispositivo;
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
		return "ResponseDispositivoMovilPersistenciaVO [dispositivo=" + dispositivo + ", codigoHttp=" + codigoHttp
				+ ", descripcion=" + descripcion + "]";
	}
	
}
