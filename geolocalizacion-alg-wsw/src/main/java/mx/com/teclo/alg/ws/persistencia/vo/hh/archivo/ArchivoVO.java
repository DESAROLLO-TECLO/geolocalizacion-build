package mx.com.teclo.alg.ws.persistencia.vo.hh.archivo;

import java.io.Serializable;

public class ArchivoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8510607118738153819L;

	private Long idArchivo;
	private String nbArchivo;
	private String nbFinalPath;
	private String txPath;
	private String txFormato;
	private String idServicio;
	private String idTipoEvento;
	private Long nuRegistroOriginal;
	private Long nuRegistroGuardado;
	private Long nuRegistroIgnorado;
	private byte [] fileError;

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
	 * @return the idTipoEvento
	 */
	public String getIdTipoEvento() {
		return idTipoEvento;
	}

	/**
	 * @param idTipoEvento the idTipoEvento to set
	 */
	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
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
	 * @return the fileError
	 */
	public byte[] getFileError() {
		return fileError;
	}

	/**
	 * @param fileError the fileError to set
	 */
	public void setFileError(byte[] fileError) {
		this.fileError = fileError;
	}

	
}
