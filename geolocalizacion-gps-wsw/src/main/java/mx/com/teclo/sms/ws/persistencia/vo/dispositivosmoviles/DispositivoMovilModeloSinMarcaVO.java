package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

/**
 * @author Kevin Ojeda
 *
 */
public class DispositivoMovilModeloSinMarcaVO{

	private Long modeloId;
	private String modeloCodigo;
	private String modelo;
	
	public Long getModeloId() {
		return modeloId;
	}
	public void setModeloId(Long modeloId) {
		this.modeloId = modeloId;
	}
	public String getModeloCodigo() {
		return modeloCodigo;
	}
	public void setModeloCodigo(String modeloCodigo) {
		this.modeloCodigo = modeloCodigo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	@Override
	public String toString() {
		return "DispositivoMovilModeloSinMarcaVO [modeloId=" + modeloId + ", modeloCodigo=" + modeloCodigo + ", modelo="
				+ modelo + "]";
	}
	
}
