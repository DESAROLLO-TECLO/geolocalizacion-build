package mx.com.teclo.sms.ws.persistencia.vo.zonavial;

import java.util.List;

public class ZonaVialAsignacionDispositivosOficialesVO {
	
	private Long idZonaVial;
	private List<Long> dispositivos;
	private List<Long> oficiales;
	
	public Long getIdZonaVial() {
		return idZonaVial;
	}
	public void setIdZonaVial(Long idZonaVial) {
		this.idZonaVial = idZonaVial;
	}
	public List<Long> getDispositivos() {
		return dispositivos;
	}
	public void setDispositivos(List<Long> dispositivos) {
		this.dispositivos = dispositivos;
	}
	public List<Long> getOficiales() {
		return oficiales;
	}
	public void setOficiales(List<Long> oficiales) {
		this.oficiales = oficiales;
	}
	
}
