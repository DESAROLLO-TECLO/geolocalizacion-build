package mx.com.teclo.alg.ws.persistencia.vo.hh.parametro;

import java.io.Serializable;

public class ParametroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651305001977689113L;

	private Long idParametro;
	private String cdParametro;
	private String nbParametro;
	private String txValor;
	private String txParametro;
	private Integer stActivo;

	/**
	 * @return the idParametro
	 */
	public Long getIdParametro() {
		return idParametro;
	}

	/**
	 * @param idParametro the idParametro to set
	 */
	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

	/**
	 * @return the cdParametro
	 */
	public String getCdParametro() {
		return cdParametro;
	}

	/**
	 * @param cdParametro the cdParametro to set
	 */
	public void setCdParametro(String cdParametro) {
		this.cdParametro = cdParametro;
	}

	/**
	 * @return the nbParametro
	 */
	public String getNbParametro() {
		return nbParametro;
	}

	/**
	 * @param nbParametro the nbParametro to set
	 */
	public void setNbParametro(String nbParametro) {
		this.nbParametro = nbParametro;
	}

	/**
	 * @return the txValor
	 */
	public String getTxValor() {
		return txValor;
	}

	/**
	 * @param txValor the txValor to set
	 */
	public void setTxValor(String txValor) {
		this.txValor = txValor;
	}

	/**
	 * @return the txParametro
	 */
	public String getTxParametro() {
		return txParametro;
	}

	/**
	 * @param txParametro the txParametro to set
	 */
	public void setTxParametro(String txParametro) {
		this.txParametro = txParametro;
	}

	/**
	 * @return the stActivo
	 */
	public Integer getStActivo() {
		return stActivo;
	}

	/**
	 * @param stActivo the stActivo to set
	 */
	public void setStActivo(Integer stActivo) {
		this.stActivo = stActivo;
	}

}
