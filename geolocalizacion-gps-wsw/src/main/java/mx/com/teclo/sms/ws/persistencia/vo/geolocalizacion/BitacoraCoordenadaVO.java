package mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion;

import java.util.List;

import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;

public class BitacoraCoordenadaVO {
 
	private EmpleadosRespectivosAreaOperativaVO empleado;
	private List<SimpleCoordenadaVO> coordenadas;
	private IndicadorVO indicador;
	
	public EmpleadosRespectivosAreaOperativaVO getEmpleado() {
		return empleado;
	}
	public void setEmpleado(EmpleadosRespectivosAreaOperativaVO empleado) {
		this.empleado = empleado;
	}
	public List<SimpleCoordenadaVO> getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(List<SimpleCoordenadaVO> coordenadas) {
		this.coordenadas = coordenadas;
	}
	public IndicadorVO getIndicador() {
		return indicador;
	}
	public void setIndicador(IndicadorVO indicador) {
		this.indicador = indicador;
	}
	@Override
	public String toString() {
		return "BitacoraCoordenadaVO [empleado=" + empleado + ", coordenadas=" + coordenadas + ", indicador="
				+ indicador + "]";
	}
}
