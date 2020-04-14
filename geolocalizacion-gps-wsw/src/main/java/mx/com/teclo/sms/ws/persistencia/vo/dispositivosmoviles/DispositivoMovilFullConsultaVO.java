package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

public class DispositivoMovilFullConsultaVO {
	
	private Long idDispositivo;
	private String numSerie;
	private String numSim;
	private String numIp;
	private String zonaVial;
	private DispositivoMovilTipoVO dispositivoMovil;
	private DispositivoMovilModeloConMarcaVO dispositivoModelo;
	
	/**
	 * @return the idDispositivo
	 */
	public Long getIdDispositivo() {
		return idDispositivo;
	}
	/**
	 * @param idDispositivo the idDispositivo to set
	 */
	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
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
	 * @return the zonaVial
	 */
	public String getZonaVial() {
		return zonaVial;
	}
	/**
	 * @param zonaVial the zonaVial to set
	 */
	public void setZonaVial(String zonaVial) {
		this.zonaVial = zonaVial;
	}
	/**
	 * @return the dispositivoMovil
	 */
	public DispositivoMovilTipoVO getDispositivoMovil() {
		return dispositivoMovil;
	}
	/**
	 * @param dispositivoMovil the dispositivoMovil to set
	 */
	public void setDispositivoMovil(DispositivoMovilTipoVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}
	/**
	 * @return the dispositivoModelo
	 */
	public DispositivoMovilModeloConMarcaVO getDispositivoModelo() {
		return dispositivoModelo;
	}
	/**
	 * @param dispositivoModelo the dispositivoModelo to set
	 */
	public void setDispositivoModelo(DispositivoMovilModeloConMarcaVO dispositivoModelo) {
		this.dispositivoModelo = dispositivoModelo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DispositivoMovilFullConsulta [idDispositivo=" + idDispositivo + ", numSerie=" + numSerie + ", numSim="
				+ numSim + ", numIp=" + numIp + ", zonaVial=" + zonaVial + ", dispositivoMovil=" + dispositivoMovil
				+ ", dispositivoModelo=" + dispositivoModelo + "]";
	}
}
