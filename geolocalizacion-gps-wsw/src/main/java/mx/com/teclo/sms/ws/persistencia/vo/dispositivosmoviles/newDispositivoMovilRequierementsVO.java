package mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.zonavial.ZonaVialVO;

public class newDispositivoMovilRequierementsVO {
	
	private List<MarcaModeloVO> marcasModelos;
	private List<DispositivoMovilTipoVO> tiposDispositivos;
	private List<ZonaVialVO> zonasViales;
	
	public List<MarcaModeloVO> getMarcasModelos() {
		return marcasModelos;
	}
	public void setMarcasModelos(List<MarcaModeloVO> marcasModelos) {
		this.marcasModelos = marcasModelos;
	}
	public List<DispositivoMovilTipoVO> getTiposDispositivos() {
		return tiposDispositivos;
	}
	public void setTiposDispositivos(List<DispositivoMovilTipoVO> tiposDispositivos) {
		this.tiposDispositivos = tiposDispositivos;
	}
	public List<ZonaVialVO> getZonasViales() {
		return zonasViales;
	}
	public void setZonasViales(List<ZonaVialVO> zonasViales) {
		this.zonasViales = zonasViales;
	}
	@Override
	public String toString() {
		return "newDispositivoMovilRequierementsVO [marcasModelos=" + marcasModelos + ", tiposDispositivos="
				+ tiposDispositivos + ", zonasViales=" + zonasViales + "]";
	}
	
}
