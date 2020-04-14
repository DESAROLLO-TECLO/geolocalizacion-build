package mx.com.teclo.alg.ws.persistencia.vo.hh.layout;

import java.io.Serializable;

public class LayoutVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7588565050489325977L;

	private Long idLayout;
	private String cdIndicadorReg;
	private String nbCampo;
	private String cdTipoDato;
	private Integer nuLongitudMax;
	private Integer nuLongitudMin;
	private Integer stRequerido;
	private String txMascara;
	private Integer stFormatDate;
	private String txValorDefecto;
	private String txDescripcion;
	private Long nuOrdenRegistro;
	private Integer stActivo;
	private TpLayoutVO tpLayout;
	private Integer stAplicaValidacion;

	/**
	 * @return the idLayout
	 */
	public Long getIdLayout() {
		return idLayout;
	}

	/**
	 * @param idLayout the idLayout to set
	 */
	public void setIdLayout(Long idLayout) {
		this.idLayout = idLayout;
	}

	/**
	 * @return the cdIndicadorReg
	 */
	public String getCdIndicadorReg() {
		return cdIndicadorReg;
	}

	/**
	 * @param cdIndicadorReg the cdIndicadorReg to set
	 */
	public void setCdIndicadorReg(String cdIndicadorReg) {
		this.cdIndicadorReg = cdIndicadorReg;
	}

	/**
	 * @return the nbCampo
	 */
	public String getNbCampo() {
		return nbCampo;
	}

	/**
	 * @param nbCampo the nbCampo to set
	 */
	public void setNbCampo(String nbCampo) {
		this.nbCampo = nbCampo;
	}

	/**
	 * @return the cdTipoDato
	 */
	public String getCdTipoDato() {
		return cdTipoDato;
	}

	/**
	 * @param cdTipoDato the cdTipoDato to set
	 */
	public void setCdTipoDato(String cdTipoDato) {
		this.cdTipoDato = cdTipoDato;
	}

	/**
	 * @return the nuLongitudMax
	 */
	public Integer getNuLongitudMax() {
		return nuLongitudMax;
	}

	/**
	 * @param nuLongitudMax the nuLongitudMax to set
	 */
	public void setNuLongitudMax(Integer nuLongitudMax) {
		this.nuLongitudMax = nuLongitudMax;
	}

	/**
	 * @return the nuLongitudMin
	 */
	public Integer getNuLongitudMin() {
		return nuLongitudMin;
	}

	/**
	 * @param nuLongitudMin the nuLongitudMin to set
	 */
	public void setNuLongitudMin(Integer nuLongitudMin) {
		this.nuLongitudMin = nuLongitudMin;
	}

	/**
	 * @return the stRequerido
	 */
	public Integer getStRequerido() {
		return stRequerido;
	}

	/**
	 * @param stRequerido the stRequerido to set
	 */
	public void setStRequerido(Integer stRequerido) {
		this.stRequerido = stRequerido;
	}

	/**
	 * @return the txMascara
	 */
	public String getTxMascara() {
		return txMascara;
	}

	/**
	 * @param txMascara the txMascara to set
	 */
	public void setTxMascara(String txMascara) {
		this.txMascara = txMascara;
	}

	/**
	 * @return the stFormatDate
	 */
	public Integer getStFormatDate() {
		return stFormatDate;
	}

	/**
	 * @param stFormatDate the stFormatDate to set
	 */
	public void setStFormatDate(Integer stFormatDate) {
		this.stFormatDate = stFormatDate;
	}

	/**
	 * @return the txValorDefecto
	 */
	public String getTxValorDefecto() {
		return txValorDefecto;
	}

	/**
	 * @param txValorDefecto the txValorDefecto to set
	 */
	public void setTxValorDefecto(String txValorDefecto) {
		this.txValorDefecto = txValorDefecto;
	}

	/**
	 * @return the txDescripcion
	 */
	public String getTxDescripcion() {
		return txDescripcion;
	}

	/**
	 * @param txDescripcion the txDescripcion to set
	 */
	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	/**
	 * @return the nuOrdenRegistro
	 */
	public Long getNuOrdenRegistro() {
		return nuOrdenRegistro;
	}

	/**
	 * @param nuOrdenRegistro the nuOrdenRegistro to set
	 */
	public void setNuOrdenRegistro(Long nuOrdenRegistro) {
		this.nuOrdenRegistro = nuOrdenRegistro;
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

	/**
	 * @return the tpLayout
	 */
	public TpLayoutVO getTpLayout() {
		return tpLayout;
	}

	/**
	 * @param tpLayout the tpLayout to set
	 */
	public void setTpLayout(TpLayoutVO tpLayout) {
		this.tpLayout = tpLayout;
	}

	/**
	 * @return the stAplicaValidacion
	 */
	public Integer getStAplicaValidacion() {
		return stAplicaValidacion;
	}

	/**
	 * @param stAplicaValidacion the stAplicaValidacion to set
	 */
	public void setStAplicaValidacion(Integer stAplicaValidacion) {
		this.stAplicaValidacion = stAplicaValidacion;
	}

}
