package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.Map;

public class DispositivoMovilPersistenciaVO {

	private Long idDispositivo;
	private String numSerie;
	private String numSim;
	private String numIp;
	private Long modelo;
	private Long zonaVial;
	private Long tipoDispositivo;
	private Map<String, String> flagRecord;
	public Long getIdDispositivo() {
		return idDispositivo;
	}
	public void setIdDispositivo(Long idDispositivo) {
		this.idDispositivo = idDispositivo;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public String getNumSim() {
		return numSim;
	}
	public void setNumSim(String numSim) {
		this.numSim = numSim;
	}
	public String getNumIp() {
		return numIp;
	}
	public void setNumIp(String numIp) {
		this.numIp = numIp;
	}
	public Long getModelo() {
		return modelo;
	}
	public void setModelo(Long modelo) {
		this.modelo = modelo;
	}
	public Long getZonaVial() {
		return zonaVial;
	}
	public void setZonaVial(Long zonaVial) {
		this.zonaVial = zonaVial;
	}
	public Long getTipoDispositivo() {
		return tipoDispositivo;
	}
	public void setTipoDispositivo(Long tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
	}
	public Map<String, String> getFlagRecord() {
		return flagRecord;
	}
	public void setFlagRecord(Map<String, String> flagRecord) {
		this.flagRecord = flagRecord;
	}
	@Override
	public String toString() {
		return "DispositivoMovilPersistenciaVO [idDispositivo=" + idDispositivo + ", numSerie=" + numSerie + ", numSim="
				+ numSim + ", numIp=" + numIp + ", modelo=" + modelo + ", zonaVial=" + zonaVial + ", tipoDispositivo="
				+ tipoDispositivo + ", flagRecord=" + flagRecord + "]";
	}
	
}
