package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

/**
 * @author Kevin Ojeda
 *
 */

public class DispositivoMovilMarcaVO{

	private Long marcaId;
	private String marcaCodigo;
	private String marca;
	
	public Long getMarcaId() {
		return marcaId;
	}
	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}
	public String getMarcaCodigo() {
		return marcaCodigo;
	}
	public void setMarcaCodigo(String marcaCodigo) {
		this.marcaCodigo = marcaCodigo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	@Override
	public String toString() {
		return "DispositivoMovilMarcaVO [marcaId=" + marcaId + ", marcaCodigo=" + marcaCodigo + ", marca=" + marca
				+ "]";
	}
	
}
