package mx.com.teclo.alg.ws.persistencia.hibernate.dto.hh.archivo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TALG005B_ARCHIVO")
public class ArchivoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5981637435188700728L;

	@Id
	@SequenceGenerator(name = "SQLG005", sequenceName = "SQLG005_ARCHIVO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQLG005")
	@Column(name = "ID_ARCHIVO", unique = true, nullable = false, precision = 11, scale = 0)
	private Long idArchivo;

	@Column(name = "NB_ARCHIVO", nullable = false, length = 100)
	private String nbArchivo;

	@Column(name = "NB_FINAL_PATH", nullable = false, length = 100)
	private String nbFinalPath;

	@Column(name = "NU_PESO_ARCHIVO", nullable = false, length = 100)
	private String nuPesoArchivo;

	@Column(name = "TX_PATH", nullable = false, length = 100)
	private String txPath;

	@Column(name = "TX_FORMATO", nullable = false, length = 100)
	private String txFormato;

	@Column(name = "ID_SERVICIO", nullable = false, length = 100)
	private String idServicio;

	@Column(name = "TX_TIPO_EVENTO", nullable = false, length = 100)
	private String txTipoEvento;

	@Column(name = "NU_REGISTRO_ORIGINAL", nullable = false, length = 100)
	private Long nuRegistroOriginal;

	@Column(name = "NU_REGISTRO_GUARDADO", nullable = false, length = 100)
	private Long nuRegistroGuardado;

	@Column(name = "NU_REGISTRO_IGNORADO", nullable = false, length = 100)
	private Long nuRegistroIgnorado;

	@Column(name = "NU_TIEMPO_PROCESAMIENTO", nullable = true, length = 200)
	private String nuTiempoProcesamiento;

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
	 * @return the idArchivo
	 */
	public Long getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo the idArchivo to set
	 */
	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return the nbArchivo
	 */
	public String getNbArchivo() {
		return nbArchivo;
	}

	/**
	 * @param nbArchivo the nbArchivo to set
	 */
	public void setNbArchivo(String nbArchivo) {
		this.nbArchivo = nbArchivo;
	}

	/**
	 * @return the nbFinalPath
	 */
	public String getNbFinalPath() {
		return nbFinalPath;
	}

	/**
	 * @param nbFinalPath the nbFinalPath to set
	 */
	public void setNbFinalPath(String nbFinalPath) {
		this.nbFinalPath = nbFinalPath;
	}

	/**
	 * @return the txPath
	 */
	public String getTxPath() {
		return txPath;
	}

	/**
	 * @param txPath the txPath to set
	 */
	public void setTxPath(String txPath) {
		this.txPath = txPath;
	}

	/**
	 * @return the txFormato
	 */
	public String getTxFormato() {
		return txFormato;
	}

	/**
	 * @param txFormato the txFormato to set
	 */
	public void setTxFormato(String txFormato) {
		this.txFormato = txFormato;
	}

	/**
	 * @return the idServicio
	 */
	public String getIdServicio() {
		return idServicio;
	}

	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	/**
	 * @return the txTipoEvento
	 */
	public String getTxTipoEvento() {
		return txTipoEvento;
	}

	/**
	 * @param txTipoEvento the txTipoEvento to set
	 */
	public void setTxTipoEvento(String txTipoEvento) {
		this.txTipoEvento = txTipoEvento;
	}

	/**
	 * @return the nuRegistroOriginal
	 */
	public Long getNuRegistroOriginal() {
		return nuRegistroOriginal;
	}

	/**
	 * @param nuRegistroOriginal the nuRegistroOriginal to set
	 */
	public void setNuRegistroOriginal(Long nuRegistroOriginal) {
		this.nuRegistroOriginal = nuRegistroOriginal;
	}

	/**
	 * @return the nuRegistroGuardado
	 */
	public Long getNuRegistroGuardado() {
		return nuRegistroGuardado;
	}

	/**
	 * @param nuRegistroGuardado the nuRegistroGuardado to set
	 */
	public void setNuRegistroGuardado(Long nuRegistroGuardado) {
		this.nuRegistroGuardado = nuRegistroGuardado;
	}

	/**
	 * @return the nuRegistroIgnorado
	 */
	public Long getNuRegistroIgnorado() {
		return nuRegistroIgnorado;
	}

	/**
	 * @param nuRegistroIgnorado the nuRegistroIgnorado to set
	 */
	public void setNuRegistroIgnorado(Long nuRegistroIgnorado) {
		this.nuRegistroIgnorado = nuRegistroIgnorado;
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
	 * @return the nuPesoArchivo
	 */
	public String getNuPesoArchivo() {
		return nuPesoArchivo;
	}

	/**
	 * @param nuPesoArchivo the nuPesoArchivo to set
	 */
	public void setNuPesoArchivo(String nuPesoArchivo) {
		this.nuPesoArchivo = nuPesoArchivo;
	}

	/**
	 * @return the nuTiempoProcesamiento
	 */
	public String getNuTiempoProcesamiento() {
		return nuTiempoProcesamiento;
	}

	/**
	 * @param nuTiempoProcesamiento the nuTiempoProcesamiento to set
	 */
	public void setNuTiempoProcesamiento(String nuTiempoProcesamiento) {
		this.nuTiempoProcesamiento = nuTiempoProcesamiento;
	}

}
