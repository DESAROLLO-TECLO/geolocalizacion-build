package mx.com.teclo.sms.ws.persistencia.vo.zonavial;

public class ZonaVialVO{

	private Long idZonaVial;
	private String nombreZonaVial;
	private String codigoZonaVial;
	private Integer activo;

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

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

}
