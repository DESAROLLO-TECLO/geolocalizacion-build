package mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import mx.com.teclo.sms.ws.persistencia.vo.dispositivosmoviles.DispositivoMovilVO;
import mx.com.teclo.sms.ws.persistencia.vo.empleado.EmpleadoVO;

public class EventoGpsReporteGeneralVO {
//	@JsonIgnore
	private Long idEvento;
	private Long idEmpleado;
	private EmpleadoVO empleado;
	private String tipoEventoCodigo;
	private TipoEventoGpsVO tipoEvento;
	private Long idArticulo;
	private Double latitudGps;
	private Double longitudGps;
	private String numSerieHH;
	private DispositivoMovilVO dispositivoMovil;
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
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
	public EmpleadoVO getEmpleado() {
		return empleado;
	}
	public void setEmpleado(EmpleadoVO empleado) {
		this.empleado = empleado;
	}
	public String getTipoEventoCodigo() {
		return tipoEventoCodigo;
	}
	public void setTipoEventoCodigo(String tipoEventoCodigo) {
		this.tipoEventoCodigo = tipoEventoCodigo;
	}
	public TipoEventoGpsVO getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(TipoEventoGpsVO tipoEvento) {
		this.tipoEvento = tipoEvento;
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
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
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
		return "EventoGpsReporteGeneralVO [idEvento=" + idEvento + ", idEmpleado=" + idEmpleado + ", empleado="
				+ empleado + ", tipoEventoCodigo=" + tipoEventoCodigo + ", tipoEvento=" + tipoEvento
				+ ", idArticulo=" + idArticulo + ", latitudGps=" + latitudGps + ", longitudGps=" + longitudGps
				+ ", numSerieHH=" + numSerieHH + ", dispositivoMovil=" + dispositivoMovil + ", direccion=" + direccion
				+ ", numInfraccion=" + numInfraccion + ", fechaCreacion=" + fechaCreacion + ", fechaHoraEvento="
				+ fechaHoraEvento + "]";
	}
}