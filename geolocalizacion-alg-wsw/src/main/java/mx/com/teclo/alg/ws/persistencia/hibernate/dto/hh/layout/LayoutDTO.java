package mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.layout;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TALG006D_LAYOUT")
public class LayoutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3548503805839738911L;

	@Id
	@Column(name = "ID_LAYOUT", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idLayout;

	@Column(name = "CD_INDICADOR_REG", unique = false, nullable = false, length = 55)
	private String cdIndicadorReg;

	@Column(name = "NB_CAMPO", nullable = false, length = 100)
	private String nbCampo;

	@Column(name = "CD_TIPO_DATO", nullable = false, length = 50)
	private String cdTipoDato;

	@Column(name = "NU_LONGITUD_MIN", nullable = false, precision = 11, scale = 0)
	private Integer nuLongitudMin;

	@Column(name = "NU_LONGITUD_MAX", nullable = false, precision = 11, scale = 0)
	private Integer nuLongitudMax;

	@Column(name = "ST_REQUERIDO", nullable = false, precision = 1, scale = 0)
	private Integer stRequerido;

	@Column(name = "TX_MASCARA", nullable = false, length = 50)
	private String txMascara;

	@Column(name = "ST_FORMAT_DATE", nullable = false, precision = 1, scale = 0)
	private Integer stFormatDate;

	@Column(name = "ST_APLICA_VALIDACION", nullable = false, precision = 1, scale = 0)
	private Integer stAplicaValidacion;

	@Column(name = "TX_VALOR_DEFECTO", nullable = true, length = 50)
	private String txValorDefecto;

	@Column(name = "TX_DESCRIPCION", nullable = true, length = 250)
	private String txDescripcion;

	@Column(name = "NU_ORDEN_REGISTRO", nullable = false, precision = 11, scale = 0)
	private Long nuOrdenRegistro;

	@Column(name = "ST_ACTIVO", nullable = false, precision = 1, scale = 0)
	private Integer stActivo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TP_LAYOUT", nullable = false)
	private TpLayoutDTO tpLayout;

	@Column(name = "ID_USR_CREACION", nullable = false, precision = 11, scale = 0)
	private Long idUsrCreacion;

	@Column(name = "FH_CREACION", nullable = false, length = 7)
	private Date fhCreacion;

	@Column(name = "ID_USR_MODIFICA", nullable = false, precision = 11, scale = 0)
	private Long idUsrModifica;

	@Column(name = "FH_MODIFICACION", nullable = false, length = 7)
	private Date fhModificacion;

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
	 * @return the idUsrCreacion
	 */
	public Long getIdUsrCreacion() {
		return idUsrCreacion;
	}

	/**
	 * @param idUsrCreacion the idUsrCreacion to set
	 */
	public void setIdUsrCreacion(Long idUsrCreacion) {
		this.idUsrCreacion = idUsrCreacion;
	}

	/**
	 * @return the fhCreacion
	 */
	public Date getFhCreacion() {
		return fhCreacion;
	}

	/**
	 * @param fhCreacion the fhCreacion to set
	 */
	public void setFhCreacion(Date fhCreacion) {
		this.fhCreacion = fhCreacion;
	}

	/**
	 * @return the idUsrModifica
	 */
	public Long getIdUsrModifica() {
		return idUsrModifica;
	}

	/**
	 * @param idUsrModifica the idUsrModifica to set
	 */
	public void setIdUsrModifica(Long idUsrModifica) {
		this.idUsrModifica = idUsrModifica;
	}

	/**
	 * @return the fhModificacion
	 */
	public Date getFhModificacion() {
		return fhModificacion;
	}

	/**
	 * @param fhModificacion the fhModificacion to set
	 */
	public void setFhModificacion(Date fhModificacion) {
		this.fhModificacion = fhModificacion;
	}

	/**
	 * @return the tpLayout
	 */
	public TpLayoutDTO getTpLayout() {
		return tpLayout;
	}

	/**
	 * @param tpLayout the tpLayout to set
	 */
	public void setTpLayout(TpLayoutDTO tpLayout) {
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
