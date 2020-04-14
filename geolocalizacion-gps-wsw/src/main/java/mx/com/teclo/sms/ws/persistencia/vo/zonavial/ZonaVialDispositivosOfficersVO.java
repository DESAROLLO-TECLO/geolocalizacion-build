package mx.com.teclo.sms.ws.persistencia.vo.zonavial;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;

public class ZonaVialDispositivosOfficersVO {
	
	private Long idZonaVial;
	private String nombreZonaVial;
	private String codigoZonaVial;
	private Integer activo;
	private List<DispositivoMovilVO> dispositivos;
	private List<EmpleadosRespectivosAreaOperativaVO> oficiales;
	public Long getIdZonaVial() {
		return idZonaVial;
	}
	public String getNombreZonaVial() {
		return nombreZonaVial;
	}
	public String getCodigoZonaVial() {
		return codigoZonaVial;
	}
	public Integer getActivo() {
		return activo;
	}
	public List<DispositivoMovilVO> getDispositivos() {
		return dispositivos;
	}
	public List<EmpleadosRespectivosAreaOperativaVO> getOficiales() {
		return oficiales;
	}
	public void setIdZonaVial(Long idZonaVial) {
		this.idZonaVial = idZonaVial;
	}
	public void setNombreZonaVial(String nombreZonaVial) {
		this.nombreZonaVial = nombreZonaVial;
	}
	public void setCodigoZonaVial(String codigoZonaVial) {
		this.codigoZonaVial = codigoZonaVial;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public void setDispositivos(List<DispositivoMovilVO> dispositivos) {
		this.dispositivos = dispositivos;
	}
	public void setOficiales(List<EmpleadosRespectivosAreaOperativaVO> oficiales) {
		this.oficiales = oficiales;
	}
	@Override
	public String toString() {
		return "ZonaVialAndDispositosMovilesVO [idZonaVial=" + idZonaVial + ", nombreZonaVial=" + nombreZonaVial
				+ ", codigoZonaVial=" + codigoZonaVial + ", activo=" + activo + ", dispositivos=" + dispositivos
				+ ", oficiales=" + oficiales + "]";
	}
	
}
