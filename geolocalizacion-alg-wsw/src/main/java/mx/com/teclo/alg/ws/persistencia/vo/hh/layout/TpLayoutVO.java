package mx.com.teclo.alg.ws.persistencia.vo.hh.layout;

import java.io.Serializable;

public class TpLayoutVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7242716555241118648L;

	private Long idTpLayout;
	private String cdTpLayout;
	private String nbTpLayout;
	private String txTpLayout;
	private Integer stActivo;
	/**
	 * @return the idTpLayout
	 */
	public Long getIdTpLayout() {
		return idTpLayout;
	}
	/**
	 * @param idTpLayout the idTpLayout to set
	 */
	public void setIdTpLayout(Long idTpLayout) {
		this.idTpLayout = idTpLayout;
	}
	/**
	 * @return the cdTpLayout
	 */
	public String getCdTpLayout() {
		return cdTpLayout;
	}
	/**
	 * @param cdTpLayout the cdTpLayout to set
	 */
	public void setCdTpLayout(String cdTpLayout) {
		this.cdTpLayout = cdTpLayout;
	}
	/**
	 * @return the nbTpLayout
	 */
	public String getNbTpLayout() {
		return nbTpLayout;
	}
	/**
	 * @param nbTpLayout the nbTpLayout to set
	 */
	public void setNbTpLayout(String nbTpLayout) {
		this.nbTpLayout = nbTpLayout;
	}
	/**
	 * @return the txTpLayout
	 */
	public String getTxTpLayout() {
		return txTpLayout;
	}
	/**
	 * @param txTpLayout the txTpLayout to set
	 */
	public void setTxTpLayout(String txTpLayout) {
		this.txTpLayout = txTpLayout;
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
