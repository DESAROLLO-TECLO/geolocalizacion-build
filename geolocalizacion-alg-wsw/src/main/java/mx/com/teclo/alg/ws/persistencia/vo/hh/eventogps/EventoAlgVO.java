package mx.com.teclo.alg.ws.persistencia.vo.hh.eventogps;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import mx.com.teclo.alg.ws.persistencia.vo.hh.dispositivosmoviles.DispositivoMovilTipoVO;

public class EventoAlgVO {

	private String placaOficial;
	private String tipoEventoCodigo;
	private TipoEventoAlgVO tipoEventoAlg;
	private String tipoDispositivoCodigo;
	private DispositivoMovilTipoVO tipoDispositivoMovil;
	private Double latitudGps;
	private Double longitudGps;
	private String numSerie;
	private String direccion;
	private Integer numImei;
	private Long numInfraccion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaHoraEvento;
	private Boolean guardado;
	private Date fechaCreacion;
	private Date ultimaModificacion;
	private Integer estatusEvento;
	private Long creadoPor;
	private Long modificadoPor;

	public String getPlacaOficial() {
		return placaOficial;
	}

	public void setPlacaOficial(String placaOficial) {
		this.placaOficial = placaOficial;
	}

	public String getTipoEventoCodigo() {
		return tipoEventoCodigo;
	}

	public void setTipoEventoCodigo(String tipoEventoCodigo) {
		this.tipoEventoCodigo = tipoEventoCodigo;
	}

	public TipoEventoAlgVO getTipoEventoAlg() {
		return tipoEventoAlg;
	}

	public void setTipoEventoAlg(TipoEventoAlgVO tipoEventoAlg) {
		this.tipoEventoAlg = tipoEventoAlg;
	}

	public String getTipoDispositivoCodigo() {
		return tipoDispositivoCodigo;
	}

	public void setTipoDispositivoCodigo(String tipoDispositivoCodigo) {
		this.tipoDispositivoCodigo = tipoDispositivoCodigo;
	}

	public DispositivoMovilTipoVO getTipoDispositivoMovil() {
		return tipoDispositivoMovil;
	}

	public void setTipoDispositivoMovil(DispositivoMovilTipoVO tipoDispositivoMovil) {
		this.tipoDispositivoMovil = tipoDispositivoMovil;
	}

	public Double getLatitudGps() {
		return latitudGps;
	}

	public void setLatitudGps(Double latitudGps) {
		this.latitudGps = latitudGps;
	}

	public Double getLongitudGps() {
		return longitudGps;
	}

	public void setLongitudGps(Double longitudGps) {
		this.longitudGps = longitudGps;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getNumImei() {
		return numImei;
	}

	public void setNumImei(Integer numImei) {
		this.numImei = numImei;
	}

	public Long getNumInfraccion() {
		return numInfraccion;
	}

	public void setNumInfraccion(Long numInfraccion) {
		this.numInfraccion = numInfraccion;
	}

	public Date getFechaHoraEvento() {
		return fechaHoraEvento;
	}

	public void setFechaHoraEvento(Date fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}

	public Boolean getGuardado() {
		return guardado;
	}

	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the ultimaModificacion
	 */
	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}

	/**
	 * @param ultimaModificacion the ultimaModificacion to set
	 */
	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}

	/**
	 * @return the estatusEvento
	 */
	public Integer getEstatusEvento() {
		return estatusEvento;
	}

	/**
	 * @param estatusEvento the estatusEvento to set
	 */
	public void setEstatusEvento(Integer estatusEvento) {
		this.estatusEvento = estatusEvento;
	}

	/**
	 * @return the modificadoPor
	 */
	public Long getModificadoPor() {
		return modificadoPor;
	}

	/**
	 * @param modificadoPor the modificadoPor to set
	 */
	public void setModificadoPor(Long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	/**
	 * @return the creadoPor
	 */
	public Long getCreadoPor() {
		return creadoPor;
	}

	/**
	 * @param creadoPor the creadoPor to set
	 */
	public void setCreadoPor(Long creadoPor) {
		this.creadoPor = creadoPor;
	}

}
