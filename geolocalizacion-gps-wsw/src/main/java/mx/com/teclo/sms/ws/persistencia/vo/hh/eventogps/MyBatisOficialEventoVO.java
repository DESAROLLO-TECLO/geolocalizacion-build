package mx.com.teclo.sms.ws.persistencia.vo.hh.eventogps;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MyBatisOficialEventoVO {

	private Long idEvento;
	private Long idEmpleado;
	private String empPlaca;
	private String empNombre;
	private String empApePaterno;
	private String empApeMaterno;
	private String numSerieHH;
	private Double latitudGps;
	private Double longitudGps;
	private String direccion;
	private String nombreTipoEvento;
	private String indicadorCodigo;
	private Long idArticulo;
	private String numInfraccion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaCreacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Mexico_City")
	private Date fechaHoraEvento;
	private String tiempoTranscurrido;
	/**
	 * @return the idEvento
	 */
	public Long getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @return the empPlaca
	 */
	public String getEmpPlaca() {
		return empPlaca;
	}
	/**
	 * @param empPlaca the empPlaca to set
	 */
	public void setEmpPlaca(String empPlaca) {
		this.empPlaca = empPlaca;
	}
	/**
	 * @return the empNombre
	 */
	public String getEmpNombre() {
		return empNombre;
	}
	/**
	 * @param empNombre the empNombre to set
	 */
	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
	}
	/**
	 * @return the empApePaterno
	 */
	public String getEmpApePaterno() {
		return empApePaterno;
	}
	/**
	 * @param empApePaterno the empApePaterno to set
	 */
	public void setEmpApePaterno(String empApePaterno) {
		this.empApePaterno = empApePaterno;
	}
	/**
	 * @return the empApeMaterno
	 */
	public String getEmpApeMaterno() {
		return empApeMaterno;
	}
	/**
	 * @param empApeMaterno the empApeMaterno to set
	 */
	public void setEmpApeMaterno(String empApeMaterno) {
		this.empApeMaterno = empApeMaterno;
	}
	/**
	 * @return the numSerieHH
	 */
	public String getNumSerieHH() {
		return numSerieHH;
	}
	/**
	 * @param numSerieHH the numSerieHH to set
	 */
	public void setNumSerieHH(String numSerieHH) {
		this.numSerieHH = numSerieHH;
	}
	/**
	 * @return the latitudGps
	 */
	public Double getLatitudGps() {
		return latitudGps;
	}
	/**
	 * @param latitudGps the latitudGps to set
	 */
	public void setLatitudGps(Double latitudGps) {
		this.latitudGps = latitudGps;
	}
	/**
	 * @return the longitudGps
	 */
	public Double getLongitudGps() {
		return longitudGps;
	}
	/**
	 * @param longitudGps the longitudGps to set
	 */
	public void setLongitudGps(Double longitudGps) {
		this.longitudGps = longitudGps;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the nombreTipoEvento
	 */
	public String getNombreTipoEvento() {
		return nombreTipoEvento;
	}
	/**
	 * @param nombreTipoEvento the nombreTipoEvento to set
	 */
	public void setNombreTipoEvento(String nombreTipoEvento) {
		this.nombreTipoEvento = nombreTipoEvento;
	}
	/**
	 * @return the indicadorCodigo
	 */
	public String getIndicadorCodigo() {
		return indicadorCodigo;
	}
	/**
	 * @param indicadorCodigo the indicadorCodigo to set
	 */
	public void setIndicadorCodigo(String indicadorCodigo) {
		this.indicadorCodigo = indicadorCodigo;
	}
	/**
	 * @return the idArticulo
	 */
	public Long getIdArticulo() {
		return idArticulo;
	}
	/**
	 * @param idArticulo the idArticulo to set
	 */
	public void setIdArticulo(Long idArticulo) {
		this.idArticulo = idArticulo;
	}
	/**
	 * @return the numInfraccion
	 */
	public String getNumInfraccion() {
		return numInfraccion;
	}
	/**
	 * @param numInfraccion the numInfraccion to set
	 */
	public void setNumInfraccion(String numInfraccion) {
		this.numInfraccion = numInfraccion;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaHoraEvento
	 */
	public Date getFechaHoraEvento() {
		return fechaHoraEvento;
	}
	/**
	 * @param fechaHoraEvento the fechaHoraEvento to set
	 */
	public void setFechaHoraEvento(Date fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}
	/**
	 * @return the tiempoTranscurrido
	 */
	public String getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}
	/**
	 * @param tiempoTranscurrido the tiempoTranscurrido to set
	 */
	public void setTiempoTranscurrido(String tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MyBatisOficialEventoVO [idEvento=" + idEvento + ", idEmpleado=" + idEmpleado + ", empPlaca=" + empPlaca
				+ ", empNombre=" + empNombre + ", empApePaterno=" + empApePaterno + ", empApeMaterno=" + empApeMaterno
				+ ", numSerieHH=" + numSerieHH + ", latitudGps=" + latitudGps + ", longitudGps=" + longitudGps
				+ ", direccion=" + direccion + ", nombreTipoEvento=" + nombreTipoEvento + ", indicadorCodigo="
				+ indicadorCodigo + ", idArticulo=" + idArticulo + ", numInfraccion=" + numInfraccion
				+ ", fechaCreacion=" + fechaCreacion + ", fechaHoraEvento=" + fechaHoraEvento + ", tiempoTranscurrido="
				+ tiempoTranscurrido + "]";
	}
	
}