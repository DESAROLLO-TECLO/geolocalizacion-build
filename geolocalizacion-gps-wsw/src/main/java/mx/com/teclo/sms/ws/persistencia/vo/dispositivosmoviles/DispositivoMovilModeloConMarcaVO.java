package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

/**
 * @author Kevin Ojeda
 *
 */
public class DispositivoMovilModeloConMarcaVO{

	private Long modeloId;
	private String modeloCodigo;
	private String modelo;
	private DispositivoMovilMarcaVO modeloMarca;
	/**
	 * @return the modeloId
	 */
	public Long getModeloId() {
		return modeloId;
	}
	/**
	 * @param modeloId the modeloId to set
	 */
	public void setModeloId(Long modeloId) {
		this.modeloId = modeloId;
	}
	/**
	 * @return the modeloCodigo
	 */
	public String getModeloCodigo() {
		return modeloCodigo;
	}
	/**
	 * @param modeloCodigo the modeloCodigo to set
	 */
	public void setModeloCodigo(String modeloCodigo) {
		this.modeloCodigo = modeloCodigo;
	}
	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	/**
	 * @return the modeloMarca
	 */
	public DispositivoMovilMarcaVO getModeloMarca() {
		return modeloMarca;
	}
	/**
	 * @param modeloMarca the modeloMarca to set
	 */
	public void setModeloMarca(DispositivoMovilMarcaVO modeloMarca) {
		this.modeloMarca = modeloMarca;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DispositivoMovilModeloConMarcaVO [modeloId=" + modeloId + ", modeloCodigo=" + modeloCodigo + ", modelo="
				+ modelo + ", modeloMarca=" + modeloMarca + "]";
	}
	
}
