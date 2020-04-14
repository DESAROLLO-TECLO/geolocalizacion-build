package mx.com.teclo.sms.ws.persistencia.vo.reportes;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoVO;

public class ZonaOficialesDispositivosVO {
	
	private Long idZonaVial;
	private String nombreZonaVial;
	private String codigoZonaVial;
	private List<DispositivoMovilVO> dispositivos;
	private List<EmpleadoVO> empleados;
	
	public Long getIdZonaVial() {
		return idZonaVial;
	}
	public void setIdZonaVial(Long idZonaVial) {
		this.idZonaVial = idZonaVial;
	}
	public String getNombreZonaVial() {
		return nombreZonaVial;
	}
	public void setNombreZonaVial(String nombreZonaVial) {
		this.nombreZonaVial = nombreZonaVial;
	}
	public String getCodigoZonaVial() {
		return codigoZonaVial;
	}
	public void setCodigoZonaVial(String codigoZonaVial) {
		this.codigoZonaVial = codigoZonaVial;
	}
	public List<DispositivoMovilVO> getDispositivos() {
		return dispositivos;
	}
	public void setDispositivos(List<DispositivoMovilVO> dispositivos) {
		this.dispositivos = dispositivos;
	}
	public List<EmpleadoVO> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(List<EmpleadoVO> empleados) {
		this.empleados = empleados;
	}
	@Override
	public String toString() {
		return "ZonaOficialesDispositivosVO [idZonaVial=" + idZonaVial + ", nombreZonaVial=" + nombreZonaVial
				+ ", codigoZonaVial=" + codigoZonaVial + ", dispositivos=" + dispositivos + ", empleados=" + empleados
				+ "]";
	}
	
}