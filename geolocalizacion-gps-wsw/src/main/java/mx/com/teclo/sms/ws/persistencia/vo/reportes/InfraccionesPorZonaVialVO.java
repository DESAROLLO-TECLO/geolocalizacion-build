package mx.com.teclo.sms.ws.persistencia.vo.reportes;

public class InfraccionesPorZonaVialVO {
	
	public InfraccionesPorZonaVialVO(){}
	
	public InfraccionesPorZonaVialVO(Integer anio, Integer mes, String zonaVial, String tipoInfraccion, String codigoInfraccion, Integer totalInfracciones, Float porcentajeInfracciones) {
		this.yr = anio;
		this.mes = mes;
		this.zonaVial = zonaVial;
		this.tipoInfraccion = tipoInfraccion;
		this.codigoInfraccion = codigoInfraccion;
		this.totalInfracciones = totalInfracciones;
		this.porcentajeInfracciones = porcentajeInfracciones;
	}
	
	public InfraccionesPorZonaVialVO(Integer anio, Integer mes, String zonaVial) {
		this.yr = anio;
		this.mes = mes;
		this.zonaVial = zonaVial;
		this.tipoInfraccion = "-1";
		this.codigoInfraccion = "-1";
		this.totalInfracciones = 0;
		this.porcentajeInfracciones = 0F;
	}
	
	private Integer yr;
	
	private Integer mes;
	
	private String zonaVial;
	
	private String tipoInfraccion;
	
	private String codigoInfraccion;
	
	private Integer totalInfracciones;
	
	private Float porcentajeInfracciones;

	public Integer getYr() {
		return yr;
	}

	public void setYr(Integer yr) {
		this.yr = yr;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getZonaVial() {
		return zonaVial;
	}

	public void setZonaVial(String zonaVial) {
		this.zonaVial = zonaVial;
	}

	public String getTipoInfraccion() {
		return tipoInfraccion;
	}

	public void setTipoInfraccion(String tipoInfraccion) {
		this.tipoInfraccion = tipoInfraccion;
	}

	public String getCodigoInfraccion() {
		return codigoInfraccion;
	}

	public void setCodigoInfraccion(String codigoInfraccion) {
		this.codigoInfraccion = codigoInfraccion;
	}

	public Integer getTotalInfracciones() {
		return totalInfracciones;
	}

	public void setTotalInfracciones(Integer totalInfracciones) {
		this.totalInfracciones = totalInfracciones;
	}

	public Float getPorcentajeInfracciones() {
		return porcentajeInfracciones;
	}

	public void setPorcentajeInfracciones(Float porcentajeInfracciones) {
		this.porcentajeInfracciones = porcentajeInfracciones;
	}
}
