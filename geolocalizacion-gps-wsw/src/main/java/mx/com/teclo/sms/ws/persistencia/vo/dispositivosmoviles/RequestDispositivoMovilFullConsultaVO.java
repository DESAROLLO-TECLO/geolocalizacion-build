package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.List;

public class RequestDispositivoMovilFullConsultaVO {
	
	private String numSerie;
	private String numSim;
	private String numIp;
	private List<Long> modelos;
	private List<Long> tipoDispositivo;
	private List<Long> zonaVial;
	private Boolean complementacion;
	private Boolean sinZonaVial;
	
	public RequestDispositivoMovilFullConsultaVO(String numSerie, String numSim, String numIp, List<Long> modelos,
			List<Long> tipoDispositivo, List<Long> zonaVial, Boolean complementacion, Boolean sinZonaVial) {
		super();
		this.numSerie = numSerie;
		this.numSim = numSim;
		this.numIp = numIp;
		this.modelos = modelos;
		this.tipoDispositivo = tipoDispositivo;
		this.zonaVial = zonaVial;
		this.complementacion = complementacion;
		this.sinZonaVial = sinZonaVial;
	}
	/**
	 * @return the numSerie
	 */
	public String getNumSerie() {
		return numSerie;
	}
	/**
	 * @param numSerie the numSerie to set
	 */
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	/**
	 * @return the numSim
	 */
	public String getNumSim() {
		return numSim;
	}
	/**
	 * @param numSim the numSim to set
	 */
	public void setNumSim(String numSim) {
		this.numSim = numSim;
	}
	/**
	 * @return the numIp
	 */
	public String getNumIp() {
		return numIp;
	}
	/**
	 * @param numIp the numIp to set
	 */
	public void setNumIp(String numIp) {
		this.numIp = numIp;
	}
	/**
	 * @return the modelos
	 */
	public List<Long> getModelos() {
		return modelos;
	}
	/**
	 * @param modelos the modelos to set
	 */
	public void setModelos(List<Long> modelos) {
		this.modelos = modelos;
	}
	/**
	 * @return the tipoDispositivo
	 */
	public List<Long> getTipoDispositivo() {
		return tipoDispositivo;
	}
	/**
	 * @param tipoDispositivo the tipoDispositivo to set
	 */
	public void setTipoDispositivo(List<Long> tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}
	/**
	 * @return the zonaVial
	 */
	public List<Long> getZonaVial() {
		return zonaVial;
	}
	/**
	 * @param zonaVial the zonaVial to set
	 */
	public void setZonaVial(List<Long> zonaVial) {
		this.zonaVial = zonaVial;
	}
	/**
	 * @return the complementacion
	 */
	public Boolean getComplementacion() {
		return complementacion;
	}
	/**
	 * @param complementacion the complementacion to set
	 */
	public void setComplementacion(Boolean complementacion) {
		this.complementacion = complementacion;
	}
	/**
	 * @return the sinZonaVial
	 */
	public Boolean getSinZonaVial() {
		return sinZonaVial;
	}
	/**
	 * @param sinZonaVial the sinZonaVial to set
	 */
	public void setSinZonaVial(Boolean sinZonaVial) {
		this.sinZonaVial = sinZonaVial;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestDispositivoMovilFullConsulta [numSerie=" + numSerie + ", numSim=" + numSim + ", numIp=" + numIp
				+ ", modelos=" + modelos + ", tipoDispositivo=" + tipoDispositivo + ", zonaVial=" + zonaVial
				+ ", complementacion=" + complementacion + ", sinZonaVial=" + sinZonaVial + "]";
	}
	
}
