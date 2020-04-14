package mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.layout;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TALG007C_TP_LAYOUT")
public class TpLayoutDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8598607240075193230L;

	@Id
	@Column(name = "ID_TP_LAYOUT", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idTpLayout;

	@Column(name = "CD_TP_LAYOUT", nullable = false, length = 15)
	private String cdTpLayout;

	@Column(name = "NB_TP_LAYOUT", nullable = false, length = 100)
	private String nbTpLayout;

	@Column(name = "TX_TP_LAYOUT", nullable = false, length = 250)
	private String txTpLayout;

	@Column(name = "ST_ACTIVO", nullable = false, precision = 1, scale = 0)
	private Integer stActivo;

	@Column(name = "ID_USR_CREACION", nullable = false, precision = 11, scale = 0)
	private Long idUsrCreacion;

	@Column(name = "FH_CREACION", nullable = false, length = 7)
	private Date fhCreacion;

	@Column(name = "ID_USR_MODIFICA", nullable = false, precision = 11, scale = 0)
	private Long idUsrModifica;

	@Column(name = "FH_MODIFICACION", nullable = false, length = 7)
	private Date fhModificacion;

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

}
