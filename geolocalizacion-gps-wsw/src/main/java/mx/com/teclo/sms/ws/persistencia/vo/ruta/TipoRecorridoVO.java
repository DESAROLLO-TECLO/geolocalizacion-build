package mx.com.teclo.sms.ws.persistencia.vo.ruta;

import java.io.Serializable;

public class TipoRecorridoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idTipoRecorrido;
	private String txTipoRecorrido;
	private String cdTipoRecorrido;
	private Integer stActivo;
	
	public Long getIdTipoRecorrido() {
		return idTipoRecorrido;
	}
	public void setIdTipoRecorrido(Long idTipoRecorrido) {
		this.idTipoRecorrido = idTipoRecorrido;
	}
	public String getTxTipoRecorrido() {
		return txTipoRecorrido;
	}
	public void setTxTipoRecorrido(String txTipoRecorrido) {
		this.txTipoRecorrido = txTipoRecorrido;
	}
	public String getCdTipoRecorrido() {
		return cdTipoRecorrido;
	}
	public void setCdTipoRecorrido(String cdTipoRecorrido) {
		this.cdTipoRecorrido = cdTipoRecorrido;
	}
	public Integer getStActivo() {
		return stActivo;
	}
	public void setStActivo(Integer stActivo) {
		this.stActivo = stActivo;
	}

}
