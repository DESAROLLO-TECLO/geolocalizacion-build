package mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadosRespectivosAreaOperativaVO;
import mx.com.teclo.sms.ws.persistencia.vo.geolocalizacion.IndicadorVO;

public class EventoGpsOficialesVO {
	
	@JsonIgnore
	private Long idEvento;
	private EmpleadosRespectivosAreaOperativaVO empleado;
	private TipoEventoGpsVO tipoEvento;
	private IndicadorVO indicador;
	
	private Long idEmpleado;
	private Long idArticulo;
	private Double latitudGps;
	private Double longitudGps;
	private String numSerieHH;
	private String direccion;
	private String numInfraccion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaCreacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaHoraEvento;
	
	
	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public EmpleadosRespectivosAreaOperativaVO getEmpleado() {
		return empleado;
	}

	public void setEmpleado(EmpleadosRespectivosAreaOperativaVO empleado) {
		this.empleado = empleado;
	}

	public TipoEventoGpsVO getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEventoGpsVO tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public IndicadorVO getIndicador() {
		return indicador;
	}

	public void setIndicador(IndicadorVO indicador) {
		this.indicador = indicador;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
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

	public String getNumSerieHH() {
		return numSerieHH;
	}

	public void setNumSerieHH(String numSerieHH) {
		this.numSerieHH = numSerieHH;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumInfraccion() {
		return numInfraccion;
	}

	public void setNumInfraccion(String numInfraccion) {
		this.numInfraccion = numInfraccion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaHoraEvento() {
		return fechaHoraEvento;
	}

	public void setFechaHoraEvento(Date fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}

	@Override
	public String toString() {
		return "EventoGpsIndicadorVO [idEvento=" + idEvento + ", idEmpleado=" + idEmpleado + ", tipoEvento="
				+ tipoEvento + ", idArticulo=" + idArticulo + ", latitudGps=" + latitudGps + ", longitudGps="
				+ longitudGps + ", numSerieHH=" + numSerieHH + ", direccion=" + direccion + ", numInfraccion="
				+ numInfraccion + ", fechaCreacion=" + fechaCreacion + ", fechaHoraEvento=" + fechaHoraEvento
				+ ", empleado=" + empleado + ", indicador=" + indicador + "]";
	}
	
}