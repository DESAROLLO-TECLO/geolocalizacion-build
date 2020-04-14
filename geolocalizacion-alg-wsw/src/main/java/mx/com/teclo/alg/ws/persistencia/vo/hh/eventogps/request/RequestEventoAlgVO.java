package mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps.request;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RequestEventoAlgVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4603589828502112307L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private Date fhEnvio;
	@JsonIgnore
	private String nbArchivo;
	@JsonIgnore
	private String nbArchivoFinal;
	private String txExtension;
	private String nuPesoArchivo;
	private String txTipoEvento;
	@JsonIgnore
	private String txRuta;
	private String txFormato;
	private String idServicio;
	private String tpOperacion;
	private Boolean stRespuestaOpt;
	@JsonIgnore
	private String nbArchivoErrorFinal;
	@JsonIgnore
	private String nbArchivoError;
	private Long nuRegistroOriginal;
	private Long nuRegistrosCorrectos;
	private Long nuRegistrosCorruptos;

	private byte[] errorFile;

	/**
	 * @return the fhEnvio
	 */
	public Date getFhEnvio() {
		return fhEnvio;
	}

	/**
	 * @param fhEnvio the fhEnvio to set
	 */
	public void setFhEnvio(Date fhEnvio) {
		this.fhEnvio = fhEnvio;
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
	 * @return the txRuta
	 */
	public String getTxRuta() {
		return txRuta;
	}

	/**
	 * @param txRuta the txRuta to set
	 */
	public void setTxRuta(String txRuta) {
		this.txRuta = txRuta;
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
	 * @return the tpOperacion
	 */
	public String getTpOperacion() {
		return tpOperacion;
	}

	/**
	 * @param tpOperacion the tpOperacion to set
	 */
	public void setTpOperacion(String tpOperacion) {
		this.tpOperacion = tpOperacion;
	}

	/**
	 * @return the stRespuestaOpt
	 */
	public Boolean getStRespuestaOpt() {
		return stRespuestaOpt;
	}

	/**
	 * @param stRespuestaOpt the stRespuestaOpt to set
	 */
	public void setStRespuestaOpt(Boolean stRespuestaOpt) {
		this.stRespuestaOpt = stRespuestaOpt;
	}

	/**
	 * @return the txExtension
	 */
	public String getTxExtension() {
		return txExtension;
	}

	/**
	 * @param txExtension the txExtension to set
	 */
	public void setTxExtension(String txExtension) {
		this.txExtension = txExtension;
	}

	/**
	 * @return the nbArchivoError
	 */
	public String getNbArchivoError() {
		return nbArchivoError;
	}

	/**
	 * @param nbArchivoError the nbArchivoError to set
	 */
	public void setNbArchivoError(String nbArchivoError) {
		this.nbArchivoError = nbArchivoError;
	}

	/**
	 * @return the nbArchivoFinal
	 */
	public String getNbArchivoFinal() {
		return nbArchivoFinal;
	}

	/**
	 * @param nbArchivoFinal the nbArchivoFinal to set
	 */
	public void setNbArchivoFinal(String nbArchivoFinal) {
		this.nbArchivoFinal = nbArchivoFinal;
	}

	/**
	 * @return the nuRegistrosCorrectos
	 */
	public Long getNuRegistrosCorrectos() {
		return nuRegistrosCorrectos;
	}

	/**
	 * @param nuRegistrosCorrectos the nuRegistrosCorrectos to set
	 */
	public void setNuRegistrosCorrectos(Long nuRegistrosCorrectos) {
		this.nuRegistrosCorrectos = nuRegistrosCorrectos;
	}

	/**
	 * @return the nuRegistrosCorruptos
	 */
	public Long getNuRegistrosCorruptos() {
		return nuRegistrosCorruptos;
	}

	/**
	 * @param nuRegistrosCorruptos the nuRegistrosCorruptos to set
	 */
	public void setNuRegistrosCorruptos(Long nuRegistrosCorruptos) {
		this.nuRegistrosCorruptos = nuRegistrosCorruptos;
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
	 * @return the errorFile
	 */
	public byte[] getErrorFile() {
		return errorFile;
	}

	/**
	 * @param errorFile the errorFile to set
	 */
	public void setErrorFile(byte[] errorFile) {
		this.errorFile = errorFile;
	}

	/**
	 * @return the nbArchivoErrorFinal
	 */
	public String getNbArchivoErrorFinal() {
		return nbArchivoErrorFinal;
	}

	/**
	 * @param nbArchivoErrorFinal the nbArchivoErrorFinal to set
	 */
	public void setNbArchivoErrorFinal(String nbArchivoErrorFinal) {
		this.nbArchivoErrorFinal = nbArchivoErrorFinal;
	}

}
