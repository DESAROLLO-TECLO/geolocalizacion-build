package mx.com.teclo.sms.ws.persistencia.vo.infracciones;

public class TipoInfraccionVO {
	
	private Long idTipoInfraccion;
	private String codigoTipoInfraccion;
	private String nombreTipoInfraccion;
	
	public Long getIdTipoInfraccion() {
		return idTipoInfraccion;
	}
	public void setIdTipoInfraccion(Long idTipoInfraccion) {
		this.idTipoInfraccion = idTipoInfraccion;
	}
	public String getCodigoTipoInfraccion() {
		return codigoTipoInfraccion;
	}
	public void setCodigoTipoInfraccion(String codigoTipoInfraccion) {
		this.codigoTipoInfraccion = codigoTipoInfraccion;
	}
	public String getNombreTipoInfraccion() {
		return nombreTipoInfraccion;
	}
	public void setNombreTipoInfraccion(String nombreTipoInfraccion) {
		this.nombreTipoInfraccion = nombreTipoInfraccion;
	}
	@Override
	public String toString() {
		return "TipoInfraccionVO [idTipoInfraccion=" + idTipoInfraccion + ", codigoTipoInfraccion="
				+ codigoTipoInfraccion + ", nombreTipoInfraccion=" + nombreTipoInfraccion + "]";
	}

}
