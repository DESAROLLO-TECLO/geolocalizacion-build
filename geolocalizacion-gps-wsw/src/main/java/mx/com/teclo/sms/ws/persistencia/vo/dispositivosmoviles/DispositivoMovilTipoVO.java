package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

/**
 * 
 * @author Kevin Ojeda
 *
 */
public class DispositivoMovilTipoVO{

	
	private Long idTipoDispositivo;
	private String cdTipoDispositivo;
	private String tipoDispositivo;
	
	public Long getIdTipoDispositivo() {
		return idTipoDispositivo;
	}
	public void setIdTipoDispositivo(Long idTipoDispositivo) {
		this.idTipoDispositivo = idTipoDispositivo;
	}
	public String getCdTipoDispositivo() {
		return cdTipoDispositivo;
	}
	public void setCdTipoDispositivo(String cdTipoDispositivo) {
		this.cdTipoDispositivo = cdTipoDispositivo;
	}
	public String getTipoDispositivo() {
		return tipoDispositivo;
	}
	public void setTipoDispositivo(String tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}
	@Override
	public String toString() {
		return "DispositivoMovilTipoVO [idTipoDispositivo=" + idTipoDispositivo + ", cdTipoDispositivo="
				+ cdTipoDispositivo + ", tipoDispositivo=" + tipoDispositivo + "]";
	}
	
}
